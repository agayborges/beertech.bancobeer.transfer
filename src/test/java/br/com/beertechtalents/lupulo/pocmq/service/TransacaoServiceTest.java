package br.com.beertechtalents.lupulo.pocmq.service;

import br.com.beertechtalents.lupulo.pocmq.model.ContaCorrente;
import br.com.beertechtalents.lupulo.pocmq.model.TipoTransacao;
import br.com.beertechtalents.lupulo.pocmq.model.Transacao;
import br.com.beertechtalents.lupulo.pocmq.repository.TransacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Test
    public void saveTest() {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setId(1l);
        contaCorrente.setHash(UUID.randomUUID().toString());

        Transacao transacao = new Transacao(TipoTransacao.DEPOSITO, BigDecimal.TEN, contaCorrente);

        Mockito.when(transacaoRepository.save(Mockito.any(Transacao.class))).then(i -> {
            Transacao t = (Transacao) i.getArguments()[0];
            t.setId(1l);
            t.setDatahora(new Timestamp(10000l));
            return t;
        });

        transacaoService.salvarTransacao(transacao);
    }

    @Test
    public void saveTransferencia() {
        ContaCorrente origem = new ContaCorrente();
        origem.setId(1l);
        origem.setHash(UUID.randomUUID().toString());

        ContaCorrente destino = new ContaCorrente();
        destino.setId(2l);
        destino.setHash(UUID.randomUUID().toString());

        Mockito.when(transacaoRepository.save(Mockito.any(Transacao.class))).then(i -> {
            Transacao t = (Transacao) i.getArguments()[0];

            ContaCorrente contaCorrente = t.getContaCorrente();

            if (contaCorrente.getId() == origem.getId()) {
                Assertions.assertEquals(TipoTransacao.SAQUE, t.getTipo());
                Assertions.assertEquals(BigDecimal.TEN.negate().doubleValue(), t.getValor().doubleValue(), 0.01);
            } else {
                Assertions.assertEquals(TipoTransacao.DEPOSITO, t.getTipo());
                Assertions.assertEquals(BigDecimal.TEN, t.getValor());
            }

            t.setId(1l);
            t.setDatahora(new Timestamp(10000l));
            return t;
        });

        transacaoService.transferencia(origem, destino, BigDecimal.TEN);
    }

}
