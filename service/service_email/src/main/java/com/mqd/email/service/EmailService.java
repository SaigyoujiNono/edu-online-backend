package com.mqd.email.service;

import javax.mail.MessagingException;

public interface EmailService {

    boolean sendValidByMail(String to, String subject, String content) throws MessagingException;
}
