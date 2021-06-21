package com.evgenys.online.shop.persistence.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
public class Cart {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    //orphanRemoval - если они ни к чему не будут привязаны удалит с базы
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    @Column(name = "price")
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User user;

    public void add(CartItem cartItem) {
        for (CartItem ci: this.items) {
            if (ci.getProduct().getId().equals(cartItem.getProduct().getId())){//если такие позиции товаров есть
                ci.increment(cartItem.getQuantity());//прибавляем их количество
                recalculate();
                return;
            }
        }
        this.items.add(cartItem);
        cartItem.setCart(this);
        recalculate();
    }

    public void recalculate(){
        this.price = BigDecimal.ZERO;
        for (CartItem ci: items){
            price = price.add(new BigDecimal(0).add(ci.getPrice()));
        }
    }

    public void clear() {
        for (CartItem ci: items){
            ci.setCart(null);
        }
        this.items.clear();
        recalculate();
    }

    public CartItem getItemByProductId(Long productId){
        for (CartItem ci: items){
            if(ci.getProduct().getId().equals(productId)){
                return ci;
            }
        }
        return null;
    }


    public void merge(Cart another) {
        for(CartItem ci: another.items) {
            add(ci);
        }
    }

}
