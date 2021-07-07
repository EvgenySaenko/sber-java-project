package com.evgenys.online.shop.services.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;//непосредственно занимается отправкой писем
    private final TemplateEngine templateEngine;
    private final JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void sendMessageConfirmationOrder(String to, String subject, Context context) {
        //подаем на вход наш шаблон
        String body = templateEngine.process("email-templates/email-confirmation-order", context);
        sendMessage(to, subject, body);
    }

    public void sendMessageSuccessfulRegistration(String to, String subject, String name,String username, String password, String activationCode) {
        String message = generateMessageSuccessfulRegistration(name,username,password,activationCode);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public void sendMessage(String emailTo, String subject, String message){
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true,"UTF-8");
            helper.setFrom(username);
            helper.setTo(emailTo);
            helper.setSubject(subject);
            helper.setText(message,true);
            helper.setEncodeFilenames(true);
            javaMailSender.send(mail);
            new MailMessage(emailTo, subject, emailTo).sussess();
        } catch (MessagingException e) {
            e.printStackTrace();
            new MailMessage(emailTo, subject, emailTo).error(e.getMessage());
        }
    }

    private String generateMessageSuccessfulRegistration(String name, String username, String password, String activationCode){
        return String.format(
                "---------------------------------------------------------------------------------------------------------\n" +
                "%s, Благодарим Вас за регистрацию на нашем сайте OnlineShop.com \n" +
                "---------------------------------------------------------------------------------------------------------\n" +
                "ЛОГИН: %s\n " +
                "ПАРОЛЬ: %s\n " +
                "---------------------------------------------------------------------------------------------------------\n" +
                "Для подтверждения e-mail, пожалуйста перейдите по ссылке на страницу активации: " +
                        " http://localhost:8189/shop/index.html?#!/activate/%s\n",
                name,username,password,activationCode
        );
    }
}