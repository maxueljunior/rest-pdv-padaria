package br.com.leuxam.integrationtests.controller.withyaml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
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
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.leuxam.configs.TestConfigs;
import br.com.leuxam.data.vo.v1.security.TokenVO;
import br.com.leuxam.integrationtests.controller.withyaml.mapper.YMLMapper;
import br.com.leuxam.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.leuxam.integrationtests.vo.AccountCredentialsVO;
import br.com.leuxam.integrationtests.vo.CompraEstoqueVO;
import br.com.leuxam.integrationtests.vo.ComprasVO;
import br.com.leuxam.integrationtests.vo.EstoqueVO;
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
public class CompraEstoqueControllerYamlTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static RequestSpecification specificationFindById;
	private static YMLMapper objectMapper;
	
	private static CompraEstoqueVO compraEstoque;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new YMLMapper();
		compraEstoque = new CompraEstoqueVO();
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
		
		var persistedCompraEstoque = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(compraEstoque, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(CompraEstoqueVO.class, objectMapper);
		
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
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.queryParam("id", compraEstoque.getCompras().getId())
					.queryParam("table", "compras")
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(CompraEstoqueVO[].class, objectMapper);
		
		List<CompraEstoqueVO> comprasEstoques = Arrays.asList(content);
		
		CompraEstoqueVO compraEstoqueUm = comprasEstoques.get(0);
		
		assertTrue(compraEstoqueUm.getEstoque().getId() > 0);
		assertEquals(compraEstoque.getCompras().getId(), compraEstoqueUm.getCompras().getId());
		
		assertEquals(1.0, compraEstoqueUm.getPreco());
		assertEquals(2.0, compraEstoqueUm.getQuantidade());
	}
	
	@Test
	@Order(3)
	public void testFindByIdProduto() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specificationFindById)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.queryParam("id", compraEstoque.getEstoque().getId())
					.queryParam("table", "produto")
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(CompraEstoqueVO[].class, objectMapper);
		
		List<CompraEstoqueVO> comprasEstoques = Arrays.asList(content);
		
		CompraEstoqueVO compraEstoqueUm = comprasEstoques.get(0);
		
		assertEquals(compraEstoque.getEstoque().getId() ,compraEstoqueUm.getEstoque().getId());
		assertTrue(compraEstoqueUm.getCompras().getId() > 0);
		
		assertEquals(1.0, compraEstoqueUm.getPreco());
		assertEquals(2.0, compraEstoqueUm.getQuantidade());
	}
	
	@Test
	@Order(4)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		compraEstoque.setQuantidade(100.0);
		
		var persistedCompraEstoque = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(compraEstoque, objectMapper)
					.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(CompraEstoqueVO.class, objectMapper);
		
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
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.queryParam("product", compraEstoque.getEstoque().getId())
					.queryParam("purchase", compraEstoque.getCompras().getId())
					.when()
					.delete()
				.then()
					.statusCode(204);

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
