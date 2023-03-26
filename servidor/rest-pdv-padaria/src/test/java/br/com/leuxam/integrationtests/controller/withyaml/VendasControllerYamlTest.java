package br.com.leuxam.integrationtests.controller.withyaml;

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
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.leuxam.configs.TestConfigs;
import br.com.leuxam.data.vo.v1.security.TokenVO;
import br.com.leuxam.integrationtests.controller.withyaml.mapper.YMLMapper;
import br.com.leuxam.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.leuxam.integrationtests.vo.AccountCredentialsVO;
import br.com.leuxam.integrationtests.vo.ClienteVO;
import br.com.leuxam.integrationtests.vo.VendasVO;
import br.com.leuxam.integrationtests.vo.pagedmodels.PagedModelVendas;
import br.com.leuxam.model.enums.CondicaoPagamento;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class VendasControllerYamlTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static YMLMapper objectMapper;
	
	private static VendasVO vendas;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new YMLMapper();
		vendas = new VendasVO();
	}

	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("maxuel", "maxuelt123");
		
		var accessToken = given()
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.body(user, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenVO.class, objectMapper)
					.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/vendas")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockVendas();
		
		var createdVendas = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(vendas, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(VendasVO.class, objectMapper);
		
		vendas = createdVendas;
		
		assertNotNull(createdVendas);
		assertNotNull(createdVendas.getId());
		assertNotNull(createdVendas.getCliente().getId());
		assertTrue(createdVendas.getId() > 0);
		assertTrue(createdVendas.getCliente().getId() > 0);
		
		assertNotNull(createdVendas.getCondicaoPagamento());
		assertNotNull(createdVendas.getDataVenda());
		assertEquals(150.0, createdVendas.getValorTotal());
	}
	
	@Test
	@Order(2)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockVendas();
		
		var createdVendas = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.pathParam("id", vendas.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(VendasVO.class, objectMapper);
		
		vendas = createdVendas;
		
		assertNotNull(createdVendas);
		assertNotNull(createdVendas.getId());
		assertNotNull(createdVendas.getCliente().getId());
		assertTrue(createdVendas.getId() > 0);
		assertTrue(createdVendas.getCliente().getId() > 0);
		
		assertNotNull(createdVendas.getCondicaoPagamento());
		assertNotNull(createdVendas.getDataVenda());
		assertEquals(150.0, createdVendas.getValorTotal());
	}
	
	@Test
	@Order(3)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		vendas.setValorTotal(1500.0);
		var createdVendas = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(vendas, objectMapper)
					.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(VendasVO.class, objectMapper);
		
		vendas = createdVendas;
		
		assertNotNull(createdVendas);
		assertNotNull(createdVendas.getId());
		assertNotNull(createdVendas.getCliente().getId());
		assertTrue(createdVendas.getId() > 0);
		assertTrue(createdVendas.getCliente().getId() > 0);
		
		assertNotNull(createdVendas.getCondicaoPagamento());
		assertNotNull(createdVendas.getDataVenda());
		assertEquals(1500.0, createdVendas.getValorTotal());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
		given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.pathParam("id", vendas.getId())
					.when()
					.delete("{id}")
				.then()
					.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var wrapper = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
				.queryParams("page", 0, "size", 12, "direction", "asc")
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(PagedModelVendas.class, objectMapper);
		
		var listVendas = wrapper.getContent();
		
		VendasVO vendasUm = listVendas.get(0);
		
		assertEquals(1,vendasUm.getId());
		assertEquals(3, vendasUm.getCliente().getId());
		
		assertNotNull(vendasUm.getCondicaoPagamento());
		assertNotNull(vendasUm.getDataVenda());
		assertEquals(1030.0, vendasUm.getValorTotal());
		
		VendasVO vendasDois = listVendas.get(1);
		
		assertEquals(2,vendasDois.getId());
		assertEquals(1, vendasDois.getCliente().getId());
		
		assertNotNull(vendasDois.getCondicaoPagamento());
		assertNotNull(vendasDois.getDataVenda());
		assertEquals(150.0, vendasDois.getValorTotal());
	}
	

	@Test
	@Order(6)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException {
		
		var unthreatedcontent = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
				.queryParams("page", 0, "size", 12, "direction", "asc")
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		var content = unthreatedcontent.replace("\n", "").replace("\r", "");
		
		assertTrue(content.contains("rel: \"self\"  href: \"http://localhost:8888/api/vendas?page=0&size=12&direction=asc\""));
		
		assertTrue(content.contains("rel: \"self\"    href: \"http://localhost:8888/api/vendas/1\""));
		assertTrue(content.contains("rel: \"self\"      href: \"http://localhost:8888/api/cliente/3\""));
		
		assertTrue(content.contains("page:  size: 12  totalElements: 2  totalPages: 1  number: 0"));
	}
	
	private void mockVendas() {
		ClienteVO cliente = new ClienteVO(1L, null, null, null, null, null, null, null);
		vendas.setCliente(cliente);
		vendas.setCondicaoPagamento(CondicaoPagamento.PIX);
		vendas.setDataVenda(new Date());
		vendas.setValorTotal(150.0);
	}

}
