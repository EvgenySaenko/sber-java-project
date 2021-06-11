package com.evgenys.online.shop.persistence.entities;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {//одна позиция в заказе

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price_per_product")
    private BigDecimal pricePerProduct;

    @Column(name = "price")
    private BigDecimal price;

//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;

    public OrderItem(Product product){
        this.product = product;
        this.quantity = 1;
        this.pricePerProduct = new BigDecimal(0).add(product.getPrice());//цена одного продукта
        this.price = this.pricePerProduct.multiply(new BigDecimal(this.quantity));//считаем общую сумму(за milk - 5шт) например
    }

    public void increment() {
        this.quantity++;
        this.price =  new BigDecimal(this.quantity).multiply(BigDecimal.valueOf(this.product.getPrice().doubleValue()));
        //this.price = new BigDecimal(this.quantity * this.product.getPrice().doubleValue());
    }

    public void decrement() {
        this.quantity--;
        this.price =  new BigDecimal(this.quantity).multiply(BigDecimal.valueOf(this.product.getPrice().doubleValue()));
    }

    public boolean isEmpty(){
        return quantity == 0;
    }
}
