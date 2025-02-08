package com.mont.decor.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mont.decor.model.Log;
import com.mont.decor.repository.LogRepository;

@Service
public class LogServiceImpl implements LogService {
	
	@Autowired
	LogRepository repository;
	
	@Override
	public void salvarLog(Date dataErro, String mensagem, String causa) {
		Log log = Log.builder()
				.causa(causa)
				.dataErro(dataErro)
				.mensagemErro(mensagem)
				.build();
		
		repository.save(log);
	}
}
