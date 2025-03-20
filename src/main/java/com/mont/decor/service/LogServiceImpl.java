package com.mont.decor.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.mont.decor.model.Log;
import com.mont.decor.repository.LogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
	
	private final LogRepository repository;
	
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
