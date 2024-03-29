package br.com.leuxam.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.leuxam.data.vo.v1.FornecedorVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.model.Fornecedor;
import br.com.leuxam.repositories.FornecedorRepository;
import br.com.leuxam.services.FornecedorService;
import br.com.leuxam.unittests.mapper.mocks.MockFornecedor;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {
	
	MockFornecedor input;
	
	@InjectMocks
	private FornecedorService service;
	
	@Mock
	FornecedorRepository repository;
	
	@BeforeEach
	void setUp() throws Exception {
		input = new MockFornecedor();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Fornecedor entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		var result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/fornecedor/1>;rel=\"self\"]"));
		
		assertEquals("CNPJ1", result.getCnpj());
		assertEquals("Contato1", result.getNomeDoContato());
		assertEquals("Razao1", result.getRazaoSocial());
		assertEquals("Telefone1", result.getTelefone());
	}
	@Test
	void testCreate() {
		Fornecedor entity = input.mockEntity(1);
		
		Fornecedor persisted = entity;
		persisted.setId(1L);
		
		FornecedorVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/fornecedor/1>;rel=\"self\"]"));
		
		assertEquals("CNPJ1", result.getCnpj());
		assertEquals("Contato1", result.getNomeDoContato());
		assertEquals("Razao1", result.getRazaoSocial());
		assertEquals("Telefone1", result.getTelefone());
	}
	
	@Test
	void testCreateWithNullFornecedor() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() {
		Fornecedor entity = input.mockEntity(1);
		entity.setId(1L);
		
		Fornecedor persisted = entity;
		persisted.setId(1L);
		
		FornecedorVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/fornecedor/1>;rel=\"self\"]"));
		
		assertEquals("CNPJ1", result.getCnpj());
		assertEquals("Contato1", result.getNomeDoContato());
		assertEquals("Razao1", result.getRazaoSocial());
		assertEquals("Telefone1", result.getTelefone());
	}
	
	@Test
	void testUpdateWithNullFornecedor() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testDelete() {
		Fornecedor entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		service.delete(1L);
	}

}
