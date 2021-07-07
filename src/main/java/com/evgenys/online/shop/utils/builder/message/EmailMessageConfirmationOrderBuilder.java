package com.evgenys.online.shop.utils.builder.message;

import com.evgenys.online.shop.persistence.entities.OrderItem;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@Component
public class EmailMessageConfirmationOrderBuilder {
    private Context orderContext;

    @PostConstruct
    public void init() {
        this.orderContext = new Context();
    }


    public Context build(Long orderId, BigDecimal orderPrice, String address, String phone, String firstName, List<OrderItem> items){
        orderContext.setVariable("order_id",orderId);
        orderContext.setVariable("order_price",orderPrice);
        orderContext.setVariable("order_address",address);

        orderContext.setVariable("user_phone",phone);
        orderContext.setVariable("user_first_name",firstName);

        orderContext.setVariable("order_items",items);
        return orderContext;
    }
}
