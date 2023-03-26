package br.com.leuxam.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import br.com.leuxam.integrationtests.vo.CompraEstoqueVO;
import br.com.leuxam.integrationtests.vo.ComprasVO;
import br.com.leuxam.integrationtests.vo.EstoqueVO;
import br.com.leuxam.integrationtests.vo.wrappers.WrapperComprasEstoqueVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class CompraEstoqueControllerJsonTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static RequestSpecification specificationFindById;
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
		
		specificationFindById = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/compra-de-produtos/relatorio")
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
	public void testFindByIdCompras() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specificationFindById)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.queryParam("id", compraEstoque.getCompras().getId())
					.queryParam("table", "compras")
					.queryParams("page", 0, "size", 12, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		WrapperComprasEstoqueVO wrapper = objectMapper.readValue(content, WrapperComprasEstoqueVO.class);
		var comprasEstoques = wrapper.getEmbedded().getCompraEstoque();
		
		CompraEstoqueVO compraEstoqueUm = comprasEstoques.get(0);
		
		assertEquals(1, compraEstoqueUm.getEstoque().getId());
		assertEquals(compraEstoque.getCompras().getId(), compraEstoqueUm.getCompras().getId());
		
		assertEquals(1.0, compraEstoqueUm.getPreco());
		assertEquals(2.0, compraEstoqueUm.getQuantidade());
	}
	
	@Test
	@Order(3)
	public void testFindByIdProduto() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specificationFindById)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.queryParam("id", compraEstoque.getEstoque().getId())
					.queryParam("table", "produto")
					.queryParams("page", 0, "size", 12, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		WrapperComprasEstoqueVO wrapper = objectMapper.readValue(content, WrapperComprasEstoqueVO.class);
		var comprasEstoques = wrapper.getEmbedded().getCompraEstoque();
		
		CompraEstoqueVO compraEstoqueUm = comprasEstoques.get(0);
		
		assertEquals(compraEstoque.getEstoque().getId() ,compraEstoqueUm.getEstoque().getId());
		assertEquals(1, compraEstoqueUm.getCompras().getId());
		
		assertEquals(1.0, compraEstoqueUm.getPreco());
		assertEquals(2.0, compraEstoqueUm.getQuantidade());
	}
	
	@Test
	@Order(4)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		compraEstoque.setQuantidade(100.0);
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(compraEstoque)
					.when()
					.put()
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
		assertEquals(100.0, persistedCompraEstoque.getQuantidade());
	}

	@Test
	@Order(5)
	public void testeDelete() throws JsonMappingException, JsonProcessingException {
		
		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.queryParam("product", compraEstoque.getEstoque().getId())
					.queryParam("purchase", compraEstoque.getCompras().getId())
					.when()
					.delete()
				.then()
					.statusCode(204);

	}
	
	@Test
	@Order(6)
	public void testHATEOASFindByIdCompras() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specificationFindById)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.queryParam("id", compraEstoque.getCompras().getId())
					.queryParam("table", "compras")
					.queryParams("page", 0, "size", 12, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/api/compra-de-produtos/relatorio?id=1&table=compras&page=0&size=12&direction=asc\"}}"));
		assertTrue(content.contains("\"page\":{\"size\":12,\"totalElements\":2,\"totalPages\":1,\"number\":0}}"));
	}
	
	@Test
	@Order(7)
	public void testHATEOASFindByIdProduto() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specificationFindById)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.queryParam("id", compraEstoque.getEstoque().getId())
					.queryParam("table", "produto")
					.queryParams("page", 0, "size", 12, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/api/compra-de-produtos/relatorio?id=1&table=produto&page=0&size=12&direction=asc\"}}"));
		assertTrue(content.contains("\"page\":{\"size\":12,\"totalElements\":0,\"totalPages\":0,\"number\":0}}"));
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
