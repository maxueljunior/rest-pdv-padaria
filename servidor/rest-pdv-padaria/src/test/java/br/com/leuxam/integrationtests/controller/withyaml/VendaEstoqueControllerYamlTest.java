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

import br.com.leuxam.configs.TestConfigs;
import br.com.leuxam.data.vo.v1.security.TokenVO;
import br.com.leuxam.integrationtests.controller.withyaml.mapper.YMLMapper;
import br.com.leuxam.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.leuxam.integrationtests.vo.AccountCredentialsVO;
import br.com.leuxam.integrationtests.vo.EstoqueVO;
import br.com.leuxam.integrationtests.vo.VendaEstoqueVO;
import br.com.leuxam.integrationtests.vo.VendasVO;
import br.com.leuxam.integrationtests.vo.pagedmodels.PagedModelVendaEstoque;
import br.com.leuxam.integrationtests.vo.wrappers.WrapperVendaEstoqueVO;
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
public class VendaEstoqueControllerYamlTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static RequestSpecification specificationFindById;
	private static YMLMapper objectMapper;
	
	private static VendaEstoqueVO vendaEstoque;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new YMLMapper();
		vendaEstoque = new VendaEstoqueVO();
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
				.setBasePath("/api/venda-de-produtos")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		specificationFindById = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/venda-de-produtos/relatorio")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockVendaEstoque();
		
		var createdVendaEstoque = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(vendaEstoque, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(VendaEstoqueVO.class, objectMapper);
		
		vendaEstoque = createdVendaEstoque;
		
		assertNotNull(createdVendaEstoque);
		assertNotNull(createdVendaEstoque.getEstoque());
		assertNotNull(createdVendaEstoque.getVendas());
		
		assertTrue(createdVendaEstoque.getEstoque().getId() > 0);
		assertTrue(createdVendaEstoque.getVendas().getId() > 0);
		
		assertEquals(25.0, createdVendaEstoque.getPreco());
		assertEquals(1.0, createdVendaEstoque.getQuantidade());
	}
	
	@Test
	@Order(2)
	public void testFindByIdProduto() throws JsonMappingException, JsonProcessingException {
		
		var wrapper = given().spec(specificationFindById)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.queryParam("id", vendaEstoque.getEstoque().getId())
					.queryParam("table", "produto")
					.queryParams("page", 0, "size", 12, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(PagedModelVendaEstoque.class, objectMapper);
		
		var listVendaEstoque = wrapper.getContent();
		
		VendaEstoqueVO vendaEstoqueUm = listVendaEstoque.get(0);
		
		assertEquals(1, vendaEstoqueUm.getEstoque().getId());
		assertEquals(1, vendaEstoqueUm.getVendas().getId());
		
		assertEquals(25.0, vendaEstoqueUm.getPreco());
		assertEquals(1.0, vendaEstoqueUm.getQuantidade());
	}
	
	@Test
	@Order(3)
	public void testFindByIdVendas() throws JsonMappingException, JsonProcessingException {
		
		var wrapper = given().spec(specificationFindById)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.queryParam("id", vendaEstoque.getEstoque().getId())
					.queryParam("table", "vendas")
					.queryParams("page", 0, "size", 12, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(PagedModelVendaEstoque.class, objectMapper);
		
		var listVendaEstoque = wrapper.getContent();
		
		VendaEstoqueVO vendaEstoqueUm = listVendaEstoque.get(0);
		
		assertEquals(1, vendaEstoqueUm.getEstoque().getId());
		assertEquals(1, vendaEstoqueUm.getVendas().getId());
		
		assertEquals(25.0, vendaEstoqueUm.getPreco());
		assertEquals(1.0, vendaEstoqueUm.getQuantidade());
		
		VendaEstoqueVO vendaEstoqueTres = listVendaEstoque.get(3);
		
		assertEquals(15, vendaEstoqueTres.getEstoque().getId());
		assertEquals(1, vendaEstoqueTres.getVendas().getId());
		
		assertEquals(25.0, vendaEstoqueTres.getPreco());
		assertEquals(5.0, vendaEstoqueTres.getQuantidade());

	}
	
	@Test
	@Order(4)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		
		vendaEstoque.setPreco(500.0);
		
		var createdVendaEstoque = given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(vendaEstoque, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(VendaEstoqueVO.class, objectMapper);
		
		vendaEstoque = createdVendaEstoque;
		
		assertNotNull(createdVendaEstoque);
		assertNotNull(createdVendaEstoque.getEstoque());
		assertNotNull(createdVendaEstoque.getVendas());
		
		assertTrue(createdVendaEstoque.getEstoque().getId() > 0);
		assertTrue(createdVendaEstoque.getVendas().getId() > 0);
		
		assertEquals(500.0, createdVendaEstoque.getPreco());
		assertEquals(1.0, createdVendaEstoque.getQuantidade());
	}
	
	@Test
	@Order(5)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
		
		given().spec(specification)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.queryParam("product", vendaEstoque.getEstoque().getId())
					.queryParam("order", vendaEstoque.getVendas().getId())
					.when()
					.delete()
				.then()
					.statusCode(204);

	}

	@Test
	@Order(6)
	public void testFindByIdProdutoHATEOAS() throws JsonMappingException, JsonProcessingException {
		
		var unthreatedcontent = given().spec(specificationFindById)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.queryParam("id", vendaEstoque.getEstoque().getId())
					.queryParam("table", "produto")
					.queryParams("page", 0, "size", 12, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		var content = unthreatedcontent.replace("\n","").replace("\r","");
		
		assertTrue(content.contains("rel: \"self\"  href: \"http://localhost:8888/api/venda-de-produtos/relatorio?id=1&table=produto&page=0&size=12&direction=asc\""));
		assertTrue(content.contains("page:  size: 12  totalElements: 0  totalPages: 0  number: 0"));
	}
	
	@Test
	@Order(7)
	public void testFindByIdVendasHATEOAS() throws JsonMappingException, JsonProcessingException {
		
		var unthreatedcontent = given().spec(specificationFindById)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.queryParam("id", vendaEstoque.getEstoque().getId())
					.queryParam("table", "vendas")
					.queryParams("page", 0, "size", 12, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		var content = unthreatedcontent.replace("\n","").replace("\r","");

		assertTrue(content.contains("rel: \"self\"  href: \"http://localhost:8888/api/venda-de-produtos/relatorio?id=1&table=vendas&page=0&size=12&direction=asc\""));
		assertTrue(content.contains("page:  size: 12  totalElements: 6  totalPages: 1  number: 0"));
	}
	private void mockVendaEstoque() {
		EstoqueVO estoque = new EstoqueVO(1L, null, null, null, null, null);
		vendaEstoque.setEstoque(estoque);
		VendasVO vendas = new VendasVO(1L, null, null, CondicaoPagamento.PIX, null);
		vendaEstoque.setVendas(vendas);
		vendaEstoque.setPreco(25.0);
		vendaEstoque.setQuantidade(1.0);
	}

}
