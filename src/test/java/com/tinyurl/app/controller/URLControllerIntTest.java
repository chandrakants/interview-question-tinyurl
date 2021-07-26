package com.tinyurl.app.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class URLControllerIntTest {

    @LocalServerPort
    private int port;

    HttpHeaders headers = new HttpHeaders();
    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testCreateTinyURLWithValidRequestParam(){
        HttpEntity<String> entity = new HttpEntity<String>(null,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(createURLWithPort("/short?url=https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json#38"),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateTinyURLWithoutRequestParam(){
        HttpEntity<String> entity = new HttpEntity<String>(null,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(createURLWithPort("/short"),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateTinyURLWithRequestURLIsBlank(){
        HttpEntity<String> entity = new HttpEntity<String>(null,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(createURLWithPort("/short?url="),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateTinyURLWithInvalidHTTPPort(){
        HttpEntity<String> entity = new HttpEntity<String>(null,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(createURLWithPort("/short?url=ggggg://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json#38"),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testGetOriginalURLFromTiny(){
        HttpEntity<String> entity = new HttpEntity<String>(null,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(createURLWithPort("/long?tiny=Zmk="),
                HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
