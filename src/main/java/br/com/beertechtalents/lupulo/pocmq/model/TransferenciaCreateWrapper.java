package br.com.beertechtalents.lupulo.pocmq.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransferenciaCreateWrapper {

    private UUID hashOrigem;

    private UUID hashDestino;

    private BigDecimal valor;
}

