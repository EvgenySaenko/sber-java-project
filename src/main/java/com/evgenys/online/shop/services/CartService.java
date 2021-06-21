package com.evgenys.online.shop.services;


import com.evgenys.online.shop.dto.CartDto;
import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.persistence.entities.Cart;
import com.evgenys.online.shop.persistence.entities.CartItem;
import com.evgenys.online.shop.persistence.entities.Product;
import com.evgenys.online.shop.persistence.entities.User;
import com.evgenys.online.shop.repositories.CartRepository;
import com.evgenys.online.shop.utils.converter.CartConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;
    private final CartConverter converter;

    public Cart save(Cart cart){
        return cartRepository.save(cart);
    }

    public Optional<Cart> findById(UUID id){
        return cartRepository.findById(id);
    }

    @Transactional//3 транзакции (поиск продукта, поиск корзины, апдейт корзины)
    public void addToCart(UUID cartId, Long productId){
        Cart cart = findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        CartItem cartItem = cart.getItemByProductId(productId);
        if (cartItem != null){//если такой продукт в корзине есть, то увеличиваем кол-во и выходим
            cartItem.increment();
            cart.recalculate();
            return;
        }
        Product p = productService.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Unable to add product with id: " + productId + " to cart. Product doesn't exist"));
        cart.add(new CartItem(p));
        //save(cart); - корзина в состоянии персист управляется контекстом постоянства и любые изменения с ней при коммите летят в базу(за нас делает save @Transactional)
    }

    @Transactional
    public void clearCart(UUID cartId){//очистим корзину
        Cart cart = findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        cart.clear();
    }

    public Optional<Cart> findByUserId(Long id){
        return cartRepository.findByUserId(id);
    }


    @Transactional
    public UUID getCartForUser(String username, UUID cartId) {
        if (username != null && cartId != null) {//если пользователь и корзина есть
            User user = userService.findByUsername(username).get();
            Cart cart = findById(cartId).get();
            Optional<Cart> oldCart =  findByUserId(user.getId());//ищме старую корзину
            if (oldCart.isPresent()) {//если старая существует
                cart.merge(oldCart.get());//смерджили новую со старой
                cartRepository.delete(oldCart.get());//удалили старую
            }
            cart.setUser(user);
        }

        if (username == null){//если пользователя нет => создаем новую корзину "гостевую"
            Cart cart =  save(new Cart());
            return cart.getId();
        }

        //если пользователь авторизован а корзины еще нет => (восстанавливаем старую или создаем новую)
        User user = userService.findByUsername(username).get();
        Optional<Cart> cart = findByUserId(user.getId());
        if (cart.isPresent()){//если такая корзина есть
            return cart.get().getId();//возвращаем ее id не создавая новую
        }
        Cart newCart = new Cart();//сосздаем новую и привязываем
        newCart.setUser(user);
        save(newCart);
        return newCart.getId();
    }
}
