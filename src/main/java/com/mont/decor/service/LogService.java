package com.mont.decor.service;

import java.util.Date;

public interface LogService {

	void salvarLog(Date dataErro, String mensagem, String causa);

}
