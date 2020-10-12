package br.com.beertechtalents.lupulo.pocmq.service;

import br.com.beertechtalents.lupulo.pocmq.model.ContaCorrente;
import br.com.beertechtalents.lupulo.pocmq.model.Transacao;
import br.com.beertechtalents.lupulo.pocmq.repository.ContaCorrenteRepository;
import br.com.beertechtalents.lupulo.pocmq.repository.TransacaoRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaCorrenteService {

    final ContaCorrenteRepository contaCorrenteRepository;

    final TransacaoRepository transacaoRepository;

    public ContaCorrente salvarContaCorrente(ContaCorrente contaCorrente) {
        return contaCorrenteRepository.save(contaCorrente);
    }

    public Optional<BigDecimal> buscarSaldoPorHash(String hash) {
        Optional<ContaCorrente> optionalConta = contaCorrenteRepository.findByHash(hash);
        if (optionalConta.isPresent()) {
            BigDecimal saldo = transacaoRepository.somaSaldo(optionalConta.get().getId());

            if (saldo == null) {
                saldo = BigDecimal.ZERO;
            }

            return Optional.of(saldo);
        } else {
            return Optional.empty();
        }
    }

    public Optional<ContaCorrente> findContaByHash(String hash) {
        return contaCorrenteRepository.findByHash(hash);
    }
}
