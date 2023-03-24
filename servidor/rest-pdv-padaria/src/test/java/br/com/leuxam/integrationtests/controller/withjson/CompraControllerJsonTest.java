package br.com.leuxam.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.leuxam.configs.TestConfigs;
import br.com.leuxam.data.vo.v1.security.TokenVO;
import br.com.leuxam.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.leuxam.integrationtests.vo.AccountCredentialsVO;
import br.com.leuxam.integrationtests.vo.ComprasVO;
import br.com.leuxam.integrationtests.vo.FornecedorVO;
import br.com.leuxam.integrationtests.vo.wrappers.WrapperComprasVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class CompraControllerJsonTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static ComprasVO compras;
	
	@BeforeAll
	public static void setup() {	
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		compras = new ComprasVO();
	}
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
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
				.setBasePath("/api/compras")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockCompras();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(compras)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		ComprasVO createdCompras = objectMapper.readValue(content, ComprasVO.class);
		compras = createdCompras;
		
		assertNotNull(createdCompras);
		assertNotNull(createdCompras.getId());
		assertNotNull(createdCompras.getFornecedor());
		
		assertTrue(createdCompras.getId() > 0);
		assertTrue(createdCompras.getFornecedor().getId() > 0);
		assertEquals(100.0, createdCompras.getValorTotal());
		
	}
	
	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		compras.setValorTotal(50.0);
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(compras)
					.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		ComprasVO createdCompras = objectMapper.readValue(content, ComprasVO.class);
		compras = createdCompras;
		
		assertNotNull(createdCompras);
		assertNotNull(createdCompras.getId());
		assertNotNull(createdCompras.getFornecedor());
		
		assertEquals(compras.getId(), createdCompras.getId());
		assertTrue(createdCompras.getFornecedor().getId() > 0);
		assertEquals(50.0, createdCompras.getValorTotal());
		
	}
	
	@Test
	@Order(3)
	public void testfindById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.pathParam("id", compras.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		ComprasVO persistedCompras = objectMapper.readValue(content, ComprasVO.class);
		compras = persistedCompras;
		
		assertNotNull(persistedCompras);
		assertNotNull(persistedCompras.getId());
		assertNotNull(persistedCompras.getFornecedor());
		
		assertTrue(persistedCompras.getId() > 0);
		assertTrue(persistedCompras.getFornecedor().getId() > 0);
		assertEquals(50.0, persistedCompras.getValorTotal());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
		
		given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", compras.getId())
				.when()
				.delete("{id}")
			.then()
				.statusCode(204);
		
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParams("page", 0, "size", 12, "direction", "asc")
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		WrapperComprasVO wrapper = objectMapper.readValue(content, WrapperComprasVO.class);
		var compras = wrapper.getEmbedded().getCompras();

		ComprasVO findComprasUm = compras.get(0);
		
		assertEquals(1, findComprasUm.getId());
		assertEquals(1, findComprasUm.getFornecedor().getId());
		assertEquals(150.0, findComprasUm.getValorTotal());
		
		ComprasVO findComprasDois = compras.get(1);
		
		assertEquals(2, findComprasDois.getId());
		assertEquals(50, findComprasDois.getFornecedor().getId());
		assertEquals(534.50, findComprasDois.getValorTotal());
		
	}
	
	@Test
	@Order(5)
	public void testFindAllWithNotAuthorized() throws JsonMappingException, JsonProcessingException {
		
		RequestSpecification specificationWithNotAuthorized = new RequestSpecBuilder()
				.setBasePath("/api/compras")
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
	
	private void mockCompras() {
		FornecedorVO fornecedor = new FornecedorVO(1L, null, null, null, null);
		compras.setFornecedor(fornecedor);
		compras.setValorTotal(100.0);
	}

}
