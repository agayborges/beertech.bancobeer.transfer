package br.com.beertechtalents.lupulo.pocmq.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TransacaoAdapter {

    final RestTemplate restTemplate = new RestTemplate();

    public void operationCall(String body) {
        call("http://localhost:8080/transacao", body);
    }

    public void wireTransferCall(String body) {
        call("http://localhost:8080/transacao/transferencia", body);
    }

    private void call (String uri, String body) {
        HttpHeaders header = new HttpHeaders();

        header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> resquestBody = new HttpEntity(body, header);

        restTemplate.postForObject(uri, resquestBody, String.class);
    }
}
