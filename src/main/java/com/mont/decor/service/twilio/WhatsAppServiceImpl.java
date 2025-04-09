package com.mont.decor.service.twilio;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mont.decor.service.LogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WhatsAppServiceImpl implements WhatsAppService {
	
	@Value("${evolution.api-key}")
	private String API_KEY;
	
	@Value("${evolution.url}")
	private String URL;
	
	private final LogService logService;
	
	public void enviarMensagem(String mensagem, String numero) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", API_KEY);

        HashMap<String, String> body = new HashMap<>();
        body.put("number", numero);
        body.put("text", mensagem);
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
        	restTemplate.exchange(
        			URL,
        			HttpMethod.POST,
        			entity,
        			String.class
        			);
        } catch (Exception e) {
        	e.printStackTrace();
        	logService.salvarLog(new Date(), "Erro ao enviar mensagem para o WhatsApp", e.getMessage());
        	throw new RuntimeException("Ocorreu um erro ao enviar a mensagem para o WhatsApp.", e);
        }
	}
}
