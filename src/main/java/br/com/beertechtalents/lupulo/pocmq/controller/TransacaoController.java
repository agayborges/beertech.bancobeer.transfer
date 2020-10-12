package br.com.beertechtalents.lupulo.pocmq.controller;


import br.com.beertechtalents.lupulo.pocmq.model.*;
import br.com.beertechtalents.lupulo.pocmq.service.ContaCorrenteService;
import br.com.beertechtalents.lupulo.pocmq.service.TransacaoService;
import io.swagger.annotations.*;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("transacao")
@Api(value = "Endpoints para transações")
@RequiredArgsConstructor
@Slf4j
public class TransacaoController {

    final TransacaoService transacaoService;

    final ContaCorrenteService contaCorrenteService;

    @ApiOperation(value = "Adiciona uma nova transacao", nickname = "POST", notes = "", tags = {"transacao",})
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Conta Corrente not found")})
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> novaOperacao(
            @ApiParam(value = "Operacao que vai ser criada", required = true) @RequestBody TransacaoCreateWrapper body) {

        Optional<ContaCorrente> optionalConta = contaCorrenteService.findContaByHash(body.getHash());

        if (optionalConta.isPresent()) {
            ContaCorrente conta = optionalConta.get();
            Transacao transacao = body.getTransacao();
            transacao.setContaCorrente(conta);

            // Normalizar entrada
            if(transacao.getTipo().equals(TipoTransacao.SAQUE)) {
                transacao.setValor(transacao.getValor().abs().negate());
            } else {
                transacao.setValor(transacao.getValor().abs());
            }

            transacaoService.salvarTransacao(transacao);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping(value = "/transferencia", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> transferencia(
            @ApiParam(value = "Transferencia que vai ser criada", required = true) @RequestBody TransferenciaCreateWrapper body) {

        Optional<ContaCorrente> optionalContaOrigem = contaCorrenteService.findContaByHash(body.getHashOrigem());

        if (optionalContaOrigem.isPresent()) {
            Optional<ContaCorrente> optionalContaDestino = contaCorrenteService.findContaByHash(body.getHashDestino());

            if (optionalContaDestino.isPresent()) {

                // Normalizar entrada
                body.setValor(body.getValor().abs());

                transacaoService.transferencia(optionalContaOrigem.get(), optionalContaDestino.get(), body.getValor());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }

    }



}