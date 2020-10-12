package br.com.beertechtalents.lupulo.pocmq.service;

import br.com.beertechtalents.lupulo.pocmq.model.ContaCorrente;
import br.com.beertechtalents.lupulo.pocmq.model.TipoTransacao;
import br.com.beertechtalents.lupulo.pocmq.model.Transacao;
import br.com.beertechtalents.lupulo.pocmq.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    final TransacaoRepository transacaoRepository;

    public void salvarTransacao(Transacao transacao) {
        transacaoRepository.save(transacao);
    }

    public void transferencia(ContaCorrente origem, ContaCorrente destino, BigDecimal valor) {
        Transacao transacaoOrigem = new Transacao(TipoTransacao.SAQUE, valor.negate(), origem);
        salvarTransacao(transacaoOrigem);

        Transacao transacaoDestino = new Transacao(TipoTransacao.DEPOSITO, valor, destino);
        salvarTransacao(transacaoDestino);
    }

}
