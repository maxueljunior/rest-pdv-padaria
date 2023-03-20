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
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		List<FornecedorVO> fornecedores = objectMapper.readValue(content, new TypeReference<List<FornecedorVO>>() {});
		
		FornecedorVO fornecedorUm = fornecedores.get(0);
		
		assertEquals(1, fornecedorUm.getId());
		
		assertEquals("10.448.074/1073-42", fornecedorUm.getCnpj());
		assertEquals("Connie Izen", fornecedorUm.getNomeDoContato());
		assertEquals("Yotz", fornecedorUm.getRazaoSocial());
		assertEquals("98-33652-2662", fornecedorUm.getTelefone());
		
		FornecedorVO fornecedorCinco = fornecedores.get(5);
		
		assertEquals(6, fornecedorCinco.getId());
		
		assertEquals("92.733.108/0374-53", fornecedorCinco.getCnpj());
		assertEquals("Lissy Karlolczak", fornecedorCinco.getNomeDoContato());
		assertEquals("Yakijo", fornecedorCinco.getRazaoSocial());
		assertEquals("26-41140-2092", fornecedorCinco.getTelefone());		
		
		FornecedorVO fornecedorSete = fornecedores.get(7);
		
		assertEquals(8, fornecedorSete.getId());
		
		assertEquals("24.313.511/1390-54", fornecedorSete.getCnpj());
		assertEquals("Stephi Gerald", fornecedorSete.getNomeDoContato());
		assertEquals("Divanoodle", fornecedorSete.getRazaoSocial());
		assertEquals("90-59325-6440", fornecedorSete.getTelefone());
	}
	
	private void mockFornecedor() {
		fornecedor.setCnpj("05.729.768/0001-80");
		fornecedor.setNomeDoContato("Maxuel Vieira Tobá Junior");
		fornecedor.setRazaoSocial("TGM Turbinas");
		fornecedor.setTelefone("16992326161");
	}

}
