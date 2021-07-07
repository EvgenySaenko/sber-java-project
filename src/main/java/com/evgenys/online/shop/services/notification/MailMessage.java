package com.evgenys.online.shop.services.notification;

import lombok.Data;

import java.util.Map;

@Data//модель которая представляет наш емайл
public class MailMessage {
    //имеет два статуса
    private final String EMAIL_SUCCESS = "SUCCESS";
    private final String EMAIL_ERROR = "ERROR";

    private String to;
    private String from;
    private String subject;
    private String body;
    private String status;
    private String errorMessage;

    //для публикации параметров используем мапу
    private Map<String, Object> model;

    public MailMessage() {}

    public MailMessage(final String to, final String subject, final String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public MailMessage sussess() {
        this.status = EMAIL_SUCCESS;
        return this;
    }

    public MailMessage error(String errorMessage) {
        this.status = EMAIL_ERROR;
        this.errorMessage = errorMessage;
        return this;
    }

    public boolean isSuccess() {
        return EMAIL_SUCCESS.equals(this.status);
    }

    public boolean isError() {
        return EMAIL_ERROR.equals(this.status);
    }

}