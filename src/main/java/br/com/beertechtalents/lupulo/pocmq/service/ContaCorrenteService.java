package br.com.beertechtalents.lupulo.pocmq.service;

import br.com.beertechtalents.lupulo.pocmq.model.ContaCorrente;
import br.com.beertechtalents.lupulo.pocmq.model.Transacao;
import br.com.beertechtalents.lupulo.pocmq.repository.ContaCorrenteRepository;
import br.com.beertechtalents.lupulo.pocmq.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaCorrenteService {

    final ContaCorrenteRepository contaCorrenteRepository;

    final TransacaoRepository transacaoRepository;

    public ContaCorrente salvarContaCorrente(ContaCorrente contaCorrente){
        return contaCorrenteRepository.save(contaCorrente);
    }

    public BigDecimal buscarSaldoPorHash(UUID hash){
        return transacaoRepository.somaSaldo(hash);
    }
}
