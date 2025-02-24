package com.mont.decor.enums;

import lombok.Getter;

@Getter
public enum SituacaoProcessamentoEnum  {

    RECEBIDO(1),
    EM_PROCESSAMENTO(2),
    PROCESSADO_SUCESSO(3),
    PROCESSADO_ERRO_INTERNO(4);

    private final int identificador;

    SituacaoProcessamentoEnum(int identificador) {
        this.identificador = identificador;
    }
}
