package br.com.beertechtalents.lupulo.pocmq.service;

import br.com.beertechtalents.lupulo.pocmq.model.ContaCorrente;
import br.com.beertechtalents.lupulo.pocmq.model.TipoTransacao;
import br.com.beertechtalents.lupulo.pocmq.model.Transacao;
import br.com.beertechtalents.lupulo.pocmq.repository.ContaCorrenteRepository;
import br.com.beertechtalents.lupulo.pocmq.repository.TransacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ContaCorrentServiceTest {

    @InjectMocks
    private ContaCorrenteService contaCorrenteService;

    @Mock
    private ContaCorrenteRepository contaCorrenteRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Test
    public void saveTest() {
        ContaCorrente contaCorrente = new ContaCorrente();

        Mockito.when(contaCorrenteRepository.save(Mockito.any(ContaCorrente.class))).then(i -> {
            ContaCorrente mockedConta = (ContaCorrente) i.getArguments()[0];
            mockedConta.setId(1l);
            mockedConta.setHash(UUID.randomUUID());
            return mockedConta;
        });

        ContaCorrente savedConta = contaCorrenteService.salvarContaCorrente(contaCorrente);

        Assertions.assertNotNull(savedConta);
        Assertions.assertNotNull(savedConta.getId());
        Assertions.assertNotNull(savedConta.getHash());
    }


    @Test
    public void buscarSaldoTest() {

        Mockito.when(transacaoRepository.somaSaldo(Mockito.any(UUID.class))).then(i -> BigDecimal.TEN);

        BigDecimal saldo = contaCorrenteService.buscarSaldoPorHash(UUID.randomUUID());

        Assertions.assertEquals(BigDecimal.TEN, saldo);
    }
}
