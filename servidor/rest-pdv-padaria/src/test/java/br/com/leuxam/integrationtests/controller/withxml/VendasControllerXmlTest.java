package br.com.leuxam.integrationtests.controller.withxml;

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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.leuxam.configs.TestConfigs;
import br.com.leuxam.data.vo.v1.security.TokenVO;
import br.com.leuxam.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.leuxam.integrationtests.vo.AccountCredentialsVO;
import br.com.leuxam.integrationtests.vo.ClienteVO;
import br.com.leuxam.integrationtests.vo.VendasVO;
import br.com.leuxam.integrationtests.vo.pagedmodels.PagedModelVendas;
import br.com.leuxam.model.enums.CondicaoPagamento;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class VendasControllerXmlTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static XmlMapper objectMapper;
	
	private static VendasVO vendas;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new XmlMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		vendas = new VendasVO();
	}

	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("maxuel", "maxuelt123");
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
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
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(vendas)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		VendasVO createdVendas = objectMapper.readValue(content, VendasVO.class);
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
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.pathParam("id", vendas.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		VendasVO createdVendas = objectMapper.readValue(content, VendasVO.class);
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
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(vendas)
					.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		VendasVO createdVendas = objectMapper.readValue(content, VendasVO.class);
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
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.pathParam("id", vendas.getId())
					.when()
					.delete("{id}")
				.then()
					.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.queryParams("page", 0, "size", 12, "direction", "asc")
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PagedModelVendas wrapper = objectMapper.readValue(content, PagedModelVendas.class);
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
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.queryParams("page", 0, "size", 12, "direction", "asc")
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/vendas?page=0&amp;size=12&amp;direction=asc</href></links>"));
		
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/cliente/3</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/vendas/1</href></links>"));
		
		assertTrue(content.contains("<page><size>12</size><totalElements>2</totalElements><totalPages>1</totalPages><number>0</number></page>"));
	}
	private void mockVendas() {
		ClienteVO cliente = new ClienteVO(1L, null, null, null, null, null, null, null);
		vendas.setCliente(cliente);
		vendas.setCondicaoPagamento(CondicaoPagamento.PIX);
		vendas.setDataVenda(new Date());
		vendas.setValorTotal(150.0);
	}

}
