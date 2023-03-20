package br.com.leuxam.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
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
import br.com.leuxam.integrationtests.vo.EstoqueVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class EstoqueControllerJsonTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static EstoqueVO estoque;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		estoque = new EstoqueVO();
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
				.setBasePath("/api/produto")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockEstoque();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(estoque)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		EstoqueVO createdEstoque = objectMapper.readValue(content, EstoqueVO.class);
		estoque = createdEstoque;
		
		assertNotNull(createdEstoque);
		assertNotNull(createdEstoque.getId());
		assertTrue(createdEstoque.getId() > 0 );
		
		assertNotNull(createdEstoque.getDataCompra());
		assertNotNull(createdEstoque.getDataValidade());
		assertEquals("PÃO DE QUEIJO", createdEstoque.getDescricao());
		assertEquals(2.0, createdEstoque.getQuantidade());
		assertEquals("KG", createdEstoque.getUnidade());
	}
	
	@Test
	@Order(2)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockEstoque();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.pathParam("id", estoque.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		EstoqueVO createdEstoque = objectMapper.readValue(content, EstoqueVO.class);
		estoque = createdEstoque;
		
		assertNotNull(createdEstoque);
		assertNotNull(createdEstoque.getId());
		assertTrue(createdEstoque.getId() > 0 );
		
		assertNotNull(createdEstoque.getDataCompra());
		assertNotNull(createdEstoque.getDataValidade());
		assertEquals("PÃO DE QUEIJO", createdEstoque.getDescricao());
		assertEquals(2.0, createdEstoque.getQuantidade());
		assertEquals("KG", createdEstoque.getUnidade());
	}

	@Test
	@Order(3)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		
		estoque.setDescricao("BIGMAC");
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(estoque)
					.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		EstoqueVO createdEstoque = objectMapper.readValue(content, EstoqueVO.class);
		estoque = createdEstoque;
		
		assertNotNull(createdEstoque);
		assertNotNull(createdEstoque.getId());
		assertTrue(createdEstoque.getId() > 0 );
		
		assertNotNull(createdEstoque.getDataCompra());
		assertNotNull(createdEstoque.getDataValidade());
		assertEquals("BIGMAC", createdEstoque.getDescricao());
		assertEquals(2.0, createdEstoque.getQuantidade());
		assertEquals("KG", createdEstoque.getUnidade());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
		
		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("id", estoque.getId())
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
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		List<EstoqueVO> listEstoque = objectMapper.readValue(content, new TypeReference<List<EstoqueVO>>() {});
		
		EstoqueVO estoqueUm = listEstoque.get(0);
		assertEquals(1 ,estoqueUm.getId());
		
		assertNotNull(estoqueUm.getDataCompra());
		assertNotNull(estoqueUm.getDataValidade());
		assertEquals("Electrical Products", estoqueUm.getDescricao());
		assertEquals(11.0, estoqueUm.getQuantidade());
		assertEquals("KG", estoqueUm.getUnidade());
		
		EstoqueVO estoqueCinco = listEstoque.get(5);
		assertEquals(6 ,estoqueCinco.getId());
		
		assertNotNull(estoqueCinco.getDataCompra());
		assertNotNull(estoqueCinco.getDataValidade());
		assertEquals("Package Goods/Cosmetics", estoqueCinco.getDescricao());
		assertEquals(62.0, estoqueCinco.getQuantidade());
		assertEquals("KG", estoqueCinco.getUnidade());
		
		EstoqueVO estoqueSete = listEstoque.get(7);
		assertEquals(8 ,estoqueSete.getId());
		
		assertNotNull(estoqueSete.getDataCompra());
		assertNotNull(estoqueSete.getDataValidade());
		assertEquals("Retail: Computer Software & Peripheral Equipment", estoqueSete.getDescricao());
		assertEquals(88.0, estoqueSete.getQuantidade());
		assertEquals("Un", estoqueSete.getUnidade());
	}


	
	private void mockEstoque() {
		estoque.setDataCompra(new Date());
		estoque.setDataValidade(new Date());
		estoque.setDescricao("PÃO DE QUEIJO");
		estoque.setQuantidade(2.0);
		estoque.setUnidade("KG");
	}

}
