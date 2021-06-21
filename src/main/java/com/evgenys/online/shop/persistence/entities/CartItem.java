package com.evgenys.online.shop.persistence.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "cart_items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price_per_product")
    private BigDecimal pricePerProduct;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CartItem(Product product){
        this.product = product;
        this.quantity = 1;
        this.pricePerProduct = new BigDecimal(0).add(product.getPrice());//цена одного продукта
        this.price = this.pricePerProduct.multiply(new BigDecimal(this.quantity));//считаем общую сумму(за milk - 5шт) например
    }

    public void increment() {
        this.quantity++;
        this.price =  new BigDecimal(this.quantity).multiply(this.pricePerProduct);
    }

    public void increment(int amount) {
        this.quantity += amount;
        this.price =  new BigDecimal(this.quantity).multiply(this.pricePerProduct);
    }

    public void decrement() {
        this.quantity--;
        this.price = new BigDecimal(this.quantity).multiply(this.pricePerProduct);
    }

}
