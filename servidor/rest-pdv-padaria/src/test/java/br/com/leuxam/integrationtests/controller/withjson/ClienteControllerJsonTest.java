package br.com.leuxam.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.leuxam.configs.TestConfigs;
import br.com.leuxam.data.vo.v1.security.TokenVO;
import br.com.leuxam.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.leuxam.integrationtests.vo.AccountCredentialsVO;
import br.com.leuxam.integrationtests.vo.ClienteVO;
import br.com.leuxam.integrationtests.vo.wrappers.WrapperClienteVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class ClienteControllerJsonTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static ClienteVO cliente;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		cliente = new ClienteVO();
	}
	
	@Test
	@Order(0)
	public void authorization() throws JsonProcessingException{
		AccountCredentialsVO user = new AccountCredentialsVO("maxuel", "maxuelt123");
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
					.when()
				.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenVO.class)
					.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/cliente")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonProcessingException{
		mockCliente();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(cliente)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		ClienteVO createdCliente = objectMapper.readValue(content, ClienteVO.class);
		cliente = createdCliente;
		
		assertNotNull(createdCliente);
		assertNotNull(createdCliente.getId());
		
		assertNotNull(createdCliente.getDataNascimento());
		assertNotNull(createdCliente.getEndereco());
		assertNotNull(createdCliente.getLucratividade());
		assertNotNull(createdCliente.getNome());
		assertNotNull(createdCliente.getSexo());
		assertNotNull(createdCliente.getSobrenome());
		assertNotNull(createdCliente.getTelefone());
		assertTrue(createdCliente.getId() > 0);
		
		assertEquals("Rua Silvio Jose Sarti", createdCliente.getEndereco());
		assertEquals(1.0, createdCliente.getLucratividade());
		assertEquals("Maxuel", createdCliente.getNome());
		assertEquals("H", createdCliente.getSexo());
		assertEquals("Junior", createdCliente.getSobrenome());
		assertEquals("16992326161", createdCliente.getTelefone());
	}
	
	@Test
	@Order(2)
	public void testUpdate() throws JsonProcessingException{
		cliente.setNome("Ketlhyn");
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(cliente)
				.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		ClienteVO createdCliente = objectMapper.readValue(content, ClienteVO.class);
		cliente = createdCliente;
		
		assertNotNull(createdCliente);
		assertNotNull(createdCliente.getId());
		
		assertNotNull(createdCliente.getDataNascimento());
		assertNotNull(createdCliente.getEndereco());
		assertNotNull(createdCliente.getLucratividade());
		assertNotNull(createdCliente.getNome());
		assertNotNull(createdCliente.getSexo());
		assertNotNull(createdCliente.getSobrenome());
		assertNotNull(createdCliente.getTelefone());
		assertEquals(cliente.getId(), createdCliente.getId());
		
		assertEquals("Rua Silvio Jose Sarti", createdCliente.getEndereco());
		assertEquals(1.0, createdCliente.getLucratividade());
		assertEquals("Ketlhyn", createdCliente.getNome());
		assertEquals("H", createdCliente.getSexo());
		assertEquals("Junior", createdCliente.getSobrenome());
		assertEquals("16992326161", createdCliente.getTelefone());
	}
	
	@Test
	@Order(3)
	public void testFindById() throws JsonProcessingException{
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.pathParam("id", cliente.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		ClienteVO persistedCliente = objectMapper.readValue(content, ClienteVO.class);
		cliente = persistedCliente;
		
		assertNotNull(persistedCliente);
		assertNotNull(persistedCliente.getId());
		
		assertNotNull(persistedCliente.getDataNascimento());
		assertNotNull(persistedCliente.getEndereco());
		assertNotNull(persistedCliente.getLucratividade());
		assertNotNull(persistedCliente.getNome());
		assertNotNull(persistedCliente.getSexo());
		assertNotNull(persistedCliente.getSobrenome());
		assertNotNull(persistedCliente.getTelefone());
		
		assertEquals(cliente.getId(), persistedCliente.getId());
		
		assertEquals("Rua Silvio Jose Sarti", persistedCliente.getEndereco());
		assertEquals(1.0, persistedCliente.getLucratividade());
		assertEquals("Ketlhyn", persistedCliente.getNome());
		assertEquals("H", persistedCliente.getSexo());
		assertEquals("Junior", persistedCliente.getSobrenome());
		assertEquals("16992326161", persistedCliente.getTelefone());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonProcessingException{
		
		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("id", cliente.getId())
					.when()
					.delete("{id}")
				.then()
					.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonProcessingException{
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParams("page", 0, "size", 12, "direction","asc")
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		WrapperClienteVO wrapper = objectMapper.readValue(content, WrapperClienteVO.class);
		var clientes = wrapper.getEmbedded().getClientes();
		ClienteVO foundClienteUm = clientes.get(0);
		
		assertEquals(51, foundClienteUm.getId());
		
		assertEquals("51 Tennyson Lane", foundClienteUm.getEndereco());
		assertEquals(9.0, foundClienteUm.getLucratividade());
		assertEquals("Angel", foundClienteUm.getNome());
		assertEquals("M", foundClienteUm.getSexo());
		assertEquals("Demetr", foundClienteUm.getSobrenome());
		assertEquals("18-05519-6674", foundClienteUm.getTelefone());
		
		ClienteVO foundClienteSete = clientes.get(6);
		
		assertEquals(63, foundClienteSete.getId());
		
		assertEquals("23065 Rigney Hill", foundClienteSete.getEndereco());
		assertEquals(8.0, foundClienteSete.getLucratividade());
		assertEquals("Beale", foundClienteSete.getNome());
		assertEquals("M", foundClienteSete.getSexo());
		assertEquals("Abel", foundClienteSete.getSobrenome());
		assertEquals("47-00382-8395", foundClienteSete.getTelefone());
	}
	
	@Test
	@Order(6)
	public void testFindAllWithNotAuthorized() throws JsonProcessingException{
		
		RequestSpecification specificationWithNotAuthorized = new RequestSpecBuilder()
				.setBasePath("/api/cliente")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		given().spec(specificationWithNotAuthorized)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
				.when()
					.get()
				.then()
					.statusCode(403)
				.extract()
					.body()
						.asString();
	}
	
	@Test
	@Order(7)
	public void testHateoas() throws JsonProcessingException{
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParams("page", 0, "size", 12, "direction","asc")
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/cliente/51\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/cliente/58\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/cliente/78\"}}}"));
		
		assertTrue(content.contains("\"first\":{\"href\":\"http://localhost:8888/api/cliente?direction=asc&page=0&size=12&sort=nome,asc\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/api/cliente?page=0&size=12&direction=asc\"}"));
		assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8888/api/cliente?direction=asc&page=1&size=12&sort=nome,asc\"}"));
		assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8888/api/cliente?direction=asc&page=8&size=12&sort=nome,asc\"}"));
		
		assertTrue(content.contains("\"page\":{\"size\":12,\"totalElements\":100,\"totalPages\":9,\"number\":0}}"));
		
	}
	
	private void mockCliente() {
		cliente.setDataNascimento(new Date());
		cliente.setEndereco("Rua Silvio Jose Sarti");
		cliente.setLucratividade(1.0);
		cliente.setNome("Maxuel");
		cliente.setSexo("H");
		cliente.setSobrenome("Junior");
		cliente.setTelefone("16992326161");
	}

}
