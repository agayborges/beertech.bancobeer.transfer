package br.com.beertechtalents.lupulo.pocmq.model;

import lombok.Data;

import java.util.UUID;

@Data
public class TransacaoCreateWrapper {

    private Transacao transacao;

    private UUID hash;

}
