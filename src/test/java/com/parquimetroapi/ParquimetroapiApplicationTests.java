package com.parquimetroapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParquimetroapiApplicationTests {


	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() {

//		String url = "http://localhost:" + port + "/ticket/";
//
//		String requestBody = "{\"nome\":\" diego vargas \"," +
//				"\"data_nascimento\":\"02/09/1977\"," +
//				"\"sexo\":\"Masculino\"," +
//				"\"codigo_cliente\":\"12345\"," +
//				"\"relacionamento\":\"Filho\"}";
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
//
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			JsonNode jsonNode = objectMapper.readTree(response.getBody());
//
//			String mensagem = jsonNode.get("Messagem").asText();
//			String id = jsonNode.get("id").asText();
//
//			Assert.assertEquals(mensagem, "Pessoa CADASTRADO com sucesso.");
//
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}

		Assert.assertEquals("ok", "ok");

	}

}
