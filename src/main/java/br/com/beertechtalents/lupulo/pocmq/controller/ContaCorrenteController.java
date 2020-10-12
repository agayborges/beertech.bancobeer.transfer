package br.com.beertechtalents.lupulo.pocmq.controller;

import br.com.beertechtalents.lupulo.pocmq.model.ContaCorrente;
import br.com.beertechtalents.lupulo.pocmq.service.ContaCorrenteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("/conta-corrente")
@Api(value = "Endpoints para Conta-Correntes")
@RequiredArgsConstructor
public class ContaCorrenteController {

    final ContaCorrenteService contaCorrenteService;

    @ApiOperation(value = "Adiciona uma nova conta corrente", nickname = "POST", notes = "", tags = {"conta-corrente",})
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input")})
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ContaCorrente> novaContaCorrente() {
        ContaCorrente contaCorrente = contaCorrenteService.salvarContaCorrente(new ContaCorrente());
        return new ResponseEntity<>(contaCorrente, HttpStatus.OK);
    }

    @ApiOperation(value = "Busca saldo total pelo hash da conta-corrente", nickname = "GET", notes = "Busca o saldo total", response = BigDecimal.class, tags = {"tool",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = BigDecimal.class),
            @ApiResponse(code = 404, message = "Conta Corrente not found")})
    @GetMapping(path = "/{hash}")
    public ResponseEntity<BigDecimal> getSaldo(@PathVariable String hash) {
        Optional<BigDecimal> optionalSaldo = contaCorrenteService.buscarSaldoPorHash(hash);

        if (optionalSaldo.isPresent()) {
            return new ResponseEntity<>(optionalSaldo.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

}
