package com.mont.decor.service.email;

import jakarta.mail.MessagingException;

public interface EmailService {

	void sendEmail(String to, String subject, String body) throws MessagingException;

}
