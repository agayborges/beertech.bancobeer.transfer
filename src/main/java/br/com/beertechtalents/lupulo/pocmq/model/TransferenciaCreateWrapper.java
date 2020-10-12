package br.com.beertechtalents.lupulo.pocmq.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransferenciaCreateWrapper {

    private String hashOrigem;

    private String hashDestino;

    private BigDecimal valor;
}

