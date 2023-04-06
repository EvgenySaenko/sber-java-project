package com.evgenys.online.shop.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem>items;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Order(User user, Cart cart, String email, String address) {
        this.user = user;
        this.items = new ArrayList<>();
        this.address = address;
        this.email = email;

       for(CartItem ci: cart.getItems()){
           OrderItem oi = new OrderItem(ci);
           oi.setOrder(this);
           this.items.add(oi);
       }

        //копируем стоимость всей корзины в наш заказ
        this.price = BigDecimal.valueOf(cart.getPrice().doubleValue());
        //cart.clear();
    }



}
