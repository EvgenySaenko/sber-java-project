package com.evgenys.online.shop.services;


import com.evgenys.online.shop.dto.OrderDto;
import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.persistence.entities.Cart;
import com.evgenys.online.shop.persistence.entities.Order;
import com.evgenys.online.shop.persistence.entities.StorePoint;
import com.evgenys.online.shop.persistence.entities.User;
import com.evgenys.online.shop.repositories.OrderRepository;
import com.evgenys.online.shop.services.notification.MailService;
import com.evgenys.online.shop.utils.builder.message.EmailMessageConfirmationOrderBuilder;
import com.evgenys.online.shop.utils.converter.OrderConverter;
import com.evgenys.online.shop.utils.json.JsonOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final StorePointsService storePointsService;
    private final OrderConverter orderConverter;
    private final MailService mailService;
    private final EmailMessageConfirmationOrderBuilder emailMessageConfirmationOrderBuilder;
    private final AmqpTemplate amqpTemplate;
    private final JsonOrderMapper jsonOrderMapper;

    @Transactional
    public Order saveOrder(String username, UUID cartId,String address) {//сохраняя ордер-> сохраняем и ордер айтемы(включенно каскадирование)
        User user = userService.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User with this name does not exist"));
        Cart cart = cartService.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        Order order =  new Order(user,cart, user.getEmail(), address);

        Order saveOrder = orderRepository.save(order);//сохранили заказ в базе

        Context context = emailMessageConfirmationOrderBuilder.build(order.getId(),order.getPrice(),order.getAddress(),//собрали е-майл представление
                order.getUser().getPhone(),order.getUser().getFirstName(),order.getItems());

        mailService.sendMessageConfirmationOrder(user.getEmail(),"Подтверждение заказа № " + order.getId() + " в интернет магазине -Online Shop-",context);
        log.info("New order successfully created! {}", order.getId());

        StorePoint storePoint = storePointsService.searchForNearestStore(order.getAddress());//ищем адрес ближайшего магазина с адресом заказа
        String jsonStringDeliveryOrderDto = jsonOrderMapper.objectToJsonString(saveOrder,storePoint);
        amqpTemplate.convertAndSend("onlineshop.exchange", "onlineshop.order", jsonStringDeliveryOrderDto);

        return saveOrder;
    }

    @RabbitListener(queues = "onlineshop.queue")
    public void waitingForDeliveryStatus(Message message) {
        try{
            String deliveryStatus = new String(message.getBody(), StandardCharsets.UTF_8);
            log.info("New delivery status: " + deliveryStatus);
            if (deliveryStatus.equals("Доставляется")){
                mailService.sendMessageChangedDeliveryStatus("razumserdca777@gmail.com", "Статус доставки", "К вам вышел курьер, ожидайте 10-20 минут");
                log.info("Отправленно письмо от том что курьер вышел ");
            }
        }catch (Throwable th){
            log.error("Error when receiving delivery status",th);
        }
    }




    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<OrderDto> findAllOrdersForCurrentUser(String username){
        return orderRepository.findOrderByUserUsername(username).stream().map(orderConverter::convertToOrderDto).collect(Collectors.toList());
    }

}
