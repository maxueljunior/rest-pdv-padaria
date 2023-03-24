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
import br.com.leuxam.integrationtests.vo.EstoqueVO;
import br.com.leuxam.integrationtests.vo.VendaEstoqueVO;
import br.com.leuxam.integrationtests.vo.VendasVO;
import br.com.leuxam.integrationtests.vo.wrappers.WrapperVendaEstoqueVO;
import br.com.leuxam.model.enums.CondicaoPagamento;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class VendaEstoqueControllerJsonTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static RequestSpecification specificationFindById;
	private static ObjectMapper objectMapper;
	
	private static VendaEstoqueVO vendaEstoque;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		vendaEstoque = new VendaEstoqueVO();
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
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(vendaEstoque)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		VendaEstoqueVO createdVendaEstoque = objectMapper.readValue(content, VendaEstoqueVO.class);
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
		
		var content = given().spec(specificationFindById)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
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
		
		WrapperVendaEstoqueVO wrapper = objectMapper.readValue(content, WrapperVendaEstoqueVO.class);
		var listVendaEstoque = wrapper.getEmbedded().getVendaEstoques();
		
		VendaEstoqueVO vendaEstoqueUm = listVendaEstoque.get(0);
		
		assertEquals(1, vendaEstoqueUm.getEstoque().getId());
		assertEquals(1, vendaEstoqueUm.getVendas().getId());
		
		assertEquals(25.0, vendaEstoqueUm.getPreco());
		assertEquals(1.0, vendaEstoqueUm.getQuantidade());
	}
	
	@Test
	@Order(3)
	public void testFindByIdVendas() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specificationFindById)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
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
		
		WrapperVendaEstoqueVO wrapper = objectMapper.readValue(content, WrapperVendaEstoqueVO.class);
		var listVendaEstoque = wrapper.getEmbedded().getVendaEstoques();
		
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
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(vendaEstoque)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		VendaEstoqueVO createdVendaEstoque = objectMapper.readValue(content, VendaEstoqueVO.class);
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
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.queryParam("product", vendaEstoque.getEstoque().getId())
					.queryParam("order", vendaEstoque.getVendas().getId())
					.when()
					.delete()
				.then()
					.statusCode(204);

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
