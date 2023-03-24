package br.com.leuxam.integrationtests.controller.withyaml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
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
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.leuxam.configs.TestConfigs;
import br.com.leuxam.data.vo.v1.security.TokenVO;
import br.com.leuxam.integrationtests.controller.withyaml.mapper.YMLMapper;
import br.com.leuxam.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.leuxam.integrationtests.vo.AccountCredentialsVO;
import br.com.leuxam.integrationtests.vo.EstoqueVO;
import br.com.leuxam.integrationtests.vo.pagedmodels.PagedModelEstoque;
import br.com.leuxam.integrationtests.vo.wrappers.WrapperEstoqueVO;
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
public class EstoqueControllerYamlTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static YMLMapper objectMapper;
	
	private static EstoqueVO estoque;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new YMLMapper();
		estoque = new EstoqueVO();
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
		
		var createdEstoque = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(estoque, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(EstoqueVO.class, objectMapper);
		
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
		
		var createdEstoque = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.pathParam("id", estoque.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(EstoqueVO.class, objectMapper);
		
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
		
		var createdEstoque = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(estoque, objectMapper)
					.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(EstoqueVO.class, objectMapper);
		
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
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.pathParam("id", estoque.getId())
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
						.as(PagedModelEstoque.class, objectMapper);
		
		var listEstoque = wrapper.getContent();
		
		EstoqueVO estoqueUm = listEstoque.get(0);
		assertEquals(60 ,estoqueUm.getId());
		
		assertNotNull(estoqueUm.getDataCompra());
		assertNotNull(estoqueUm.getDataValidade());
		assertEquals("Agricultural Chemicals", estoqueUm.getDescricao());
		assertEquals(2.0, estoqueUm.getQuantidade());
		assertEquals("Un", estoqueUm.getUnidade());
		
		EstoqueVO estoqueCinco = listEstoque.get(5);
		assertEquals(16 ,estoqueCinco.getId());
		
		assertNotNull(estoqueCinco.getDataCompra());
		assertNotNull(estoqueCinco.getDataValidade());
		assertEquals("Auto Parts:O.E.M.", estoqueCinco.getDescricao());
		assertEquals(62.0, estoqueCinco.getQuantidade());
		assertEquals("Un", estoqueCinco.getUnidade());
		
		EstoqueVO estoqueSete = listEstoque.get(7);
		assertEquals(44 ,estoqueSete.getId());
		
		assertNotNull(estoqueSete.getDataCompra());
		assertNotNull(estoqueSete.getDataValidade());
		assertEquals("Biotechnology: Biological Products (No Diagnostic Substances)", estoqueSete.getDescricao());
		assertEquals(4.0, estoqueSete.getQuantidade());
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
