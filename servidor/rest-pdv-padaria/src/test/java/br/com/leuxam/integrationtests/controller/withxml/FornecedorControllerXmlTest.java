package br.com.leuxam.integrationtests.controller.withxml;

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
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.leuxam.configs.TestConfigs;
import br.com.leuxam.data.vo.v1.security.TokenVO;
import br.com.leuxam.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.leuxam.integrationtests.vo.AccountCredentialsVO;
import br.com.leuxam.integrationtests.vo.FornecedorVO;
import br.com.leuxam.integrationtests.vo.pagedmodels.PagedModelFornecedor;
import br.com.leuxam.integrationtests.vo.wrappers.WrapperFornecedorVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class FornecedorControllerXmlTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static XmlMapper objectMapper;
	
	private static FornecedorVO fornecedor;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new XmlMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		fornecedor = new FornecedorVO();
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
				.setBasePath("/api/fornecedor")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockFornecedor();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(fornecedor)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		FornecedorVO createdFornecedor = objectMapper.readValue(content, FornecedorVO.class);
		fornecedor = createdFornecedor;
		
		assertNotNull(createdFornecedor);
		assertNotNull(createdFornecedor.getId());
		assertTrue(createdFornecedor.getId() > 0);
		
		assertEquals("05.729.768/0001-80", createdFornecedor.getCnpj());
		assertEquals("Maxuel Vieira Tobá Junior", createdFornecedor.getNomeDoContato());
		assertEquals("TGM Turbinas", createdFornecedor.getRazaoSocial());
		assertEquals("16992326161", createdFornecedor.getTelefone());
	}
	
	@Test
	@Order(2)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockFornecedor();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.pathParam("id", fornecedor.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		FornecedorVO createdFornecedor = objectMapper.readValue(content, FornecedorVO.class);
		fornecedor = createdFornecedor;
		
		assertNotNull(createdFornecedor);
		assertNotNull(createdFornecedor.getId());
		assertTrue(createdFornecedor.getId() > 0);
		
		assertEquals("05.729.768/0001-80", createdFornecedor.getCnpj());
		assertEquals("Maxuel Vieira Tobá Junior", createdFornecedor.getNomeDoContato());
		assertEquals("TGM Turbinas", createdFornecedor.getRazaoSocial());
		assertEquals("16992326161", createdFornecedor.getTelefone());
	}
	
	@Test
	@Order(3)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		fornecedor.setNomeDoContato("Ketlhyn");
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.body(fornecedor)
					.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		FornecedorVO createdFornecedor = objectMapper.readValue(content, FornecedorVO.class);
		fornecedor = createdFornecedor;
		
		assertNotNull(createdFornecedor);
		assertNotNull(createdFornecedor.getId());
		assertTrue(createdFornecedor.getId() > 0);
		
		assertEquals("05.729.768/0001-80", createdFornecedor.getCnpj());
		assertEquals("Ketlhyn", createdFornecedor.getNomeDoContato());
		assertEquals("TGM Turbinas", createdFornecedor.getRazaoSocial());
		assertEquals("16992326161", createdFornecedor.getTelefone());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
		
		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.pathParam("id", fornecedor.getId())
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
		
		PagedModelFornecedor wrapper = objectMapper.readValue(content, PagedModelFornecedor.class);
		var fornecedores = wrapper.getContent();
		FornecedorVO fornecedorUm = fornecedores.get(0);
		
		assertEquals(36, fornecedorUm.getId());
		
		assertEquals("66.186.410/9093-23", fornecedorUm.getCnpj());
		assertEquals("Michele Skippings", fornecedorUm.getNomeDoContato());
		assertEquals("Abata", fornecedorUm.getRazaoSocial());
		assertEquals("81-86487-7858", fornecedorUm.getTelefone());
		
		FornecedorVO fornecedorCinco = fornecedores.get(5);
		
		assertEquals(87, fornecedorCinco.getId());
		
		assertEquals("20.245.034/7676-12", fornecedorCinco.getCnpj());
		assertEquals("Berta Faulder", fornecedorCinco.getNomeDoContato());
		assertEquals("Aivee", fornecedorCinco.getRazaoSocial());
		assertEquals("30-75739-6582", fornecedorCinco.getTelefone());		
		
		FornecedorVO fornecedorSete = fornecedores.get(7);
		
		assertEquals(50, fornecedorSete.getId());
		
		assertEquals("21.327.433/7101-85", fornecedorSete.getCnpj());
		assertEquals("Wynn Heymes", fornecedorSete.getNomeDoContato());
		assertEquals("Blogpad", fornecedorSete.getRazaoSocial());
		assertEquals("71-60771-5128", fornecedorSete.getTelefone());
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
		
		assertTrue(content.contains("<links><rel>first</rel><href>http://localhost:8888/api/fornecedor?direction=asc&amp;page=0&amp;size=12&amp;sort=razaoSocial,asc</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/fornecedor?page=0&amp;size=12&amp;direction=asc</href></links>"));
		assertTrue(content.contains("<links><rel>next</rel><href>http://localhost:8888/api/fornecedor?direction=asc&amp;page=1&amp;size=12&amp;sort=razaoSocial,asc</href></links>"));
		assertTrue(content.contains("<links><rel>last</rel><href>http://localhost:8888/api/fornecedor?direction=asc&amp;page=8&amp;size=12&amp;sort=razaoSocial,asc</href></links>"));
		
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/fornecedor/99</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/fornecedor/100</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/fornecedor/82</href></links>"));
		
		assertTrue(content.contains("<page><size>12</size><totalElements>100</totalElements><totalPages>9</totalPages><number>0</number></page>"));
	}
	private void mockFornecedor() {
		fornecedor.setCnpj("05.729.768/0001-80");
		fornecedor.setNomeDoContato("Maxuel Vieira Tobá Junior");
		fornecedor.setRazaoSocial("TGM Turbinas");
		fornecedor.setTelefone("16992326161");
	}

}
