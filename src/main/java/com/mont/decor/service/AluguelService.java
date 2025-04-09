package com.mont.decor.service;

import java.util.Date;
import java.util.List;

public interface AluguelService {

	List<Date> getAlugueisPendentesPorProduto(Long identificadorProduto);

}
