package br.com.leuxam.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.leuxam.configs.TestConfigs;
import br.com.leuxam.data.vo.v1.security.TokenVO;
import br.com.leuxam.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.leuxam.integrationtests.vo.AccountCredentialsVO;
import br.com.leuxam.integrationtests.vo.CompraEstoqueVO;
import br.com.leuxam.integrationtests.vo.ComprasVO;
import br.com.leuxam.integrationtests.vo.EstoqueVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class CompraEstoqueControllerJsonTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static CompraEstoqueVO compraEstoque;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		compraEstoque = new CompraEstoqueVO();
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
				.setBasePath("/api/compra-de-produtos")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockCompraEstoque();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(compraEstoque)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		CompraEstoqueVO persistedCompraEstoque = objectMapper.readValue(content, CompraEstoqueVO.class);
		compraEstoque = persistedCompraEstoque;
		
		assertNotNull(persistedCompraEstoque);
		assertNotNull(persistedCompraEstoque.getCompras());
		assertNotNull(persistedCompraEstoque.getEstoque());
		assertNotNull(persistedCompraEstoque.getPreco());
		assertNotNull(persistedCompraEstoque.getQuantidade());
		assertTrue(persistedCompraEstoque.getEstoque().getId() > 0);
		assertTrue(persistedCompraEstoque.getCompras().getId() > 0);
		
		assertEquals(1.0, persistedCompraEstoque.getPreco());
		assertEquals(2.0, persistedCompraEstoque.getQuantidade());
		
	}

	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockCompraEstoque();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LEUXAM)
					.body(compraEstoque)
					.when()
					.post()
				.then()
					.statusCode(403)
				.extract()
					.body()
						.asString();
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}

	private void mockCompraEstoque() {
		ComprasVO compras = new ComprasVO(1L, null, null);
		EstoqueVO estoque = new EstoqueVO(1L, null, null, null, null, null);
		compraEstoque.setCompras(compras);
		compraEstoque.setEstoque(estoque);
		compraEstoque.setPreco(1.0);
		compraEstoque.setQuantidade(2.0);
	}

}
