package com.evgenys.online.shop.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ShopError {
    private int status;
    private String message;
    private Date timestamp;

    public ShopError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}

