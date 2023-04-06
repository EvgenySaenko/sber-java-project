//package com.evgenys.online.shop.beans;
//
//import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
//import com.evgenys.online.shop.persistence.entities.OrderItem;
//import com.evgenys.online.shop.persistence.entities.Product;
//import com.evgenys.online.shop.services.ProductService;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Scope;
//import org.springframework.context.annotation.ScopedProxyMode;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.annotation.PostConstruct;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
//@Data
//@Deprecated
//public class Cart {
//    private List<OrderItem> items;
//    private final ProductService productService;
//    private BigDecimal totalPrice;
//
//    @PostConstruct
//    public void init(){
//        this.items =  new ArrayList<>();
//    }
//
//
//    public void add(Long id){
//        for (OrderItem o: items){
//            if (o.getProduct().getId().equals(id)){
//                o.increment();
//                recalculate();
//                return;
//            }
//        }
//        Product p = productService.findById(id).orElseThrow(()-> new ResourceNotFoundException("Unable to find product with id: " + id + " (add to cart)"));
//        items.add(new OrderItem(p));
//        recalculate();
//    }
//
//    public void clear(){
//        this.items.clear();
//        recalculate();
//    }
//
//    public void recalculate() {
//        this.totalPrice = new BigDecimal(0);
//        for (OrderItem o : items) {
//            this.totalPrice = this.totalPrice.add(o.getPrice());
//        }
//    }
//}
