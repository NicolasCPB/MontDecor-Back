package com.mont.decor.service.twilio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class TwilioServiceImpl implements TwilioService {
	
	@Value("${twilio.sid}")
	private String ACCOUNT_SID; 
		
	@Value("${email.password}")
	private String AUTH_TOKEN;
	
	@SuppressWarnings("unused")
	@Override
	public void enviarMensagemWhatsapp(String mensagem) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	    Message message = Message.creator(
	      new PhoneNumber("whatsapp:+554884423041"),
	      new PhoneNumber("whatsapp:+14155238886"),
	      mensagem)
	    .create();
	}
}
