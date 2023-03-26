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

import br.com.leuxam.data.vo.v1.EstoqueVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.model.Estoque;
import br.com.leuxam.repositories.EstoqueRepository;
import br.com.leuxam.services.EstoqueService;
import br.com.leuxam.unittests.mapper.mocks.MockEstoque;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {
	
	MockEstoque input;
	
	@InjectMocks
	private EstoqueService service;
	
	@Mock
	EstoqueRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		input = new MockEstoque();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Estoque entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		var result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/produto/1>;rel=\"self\"]"));
		
		assertNotNull(result.getDataCompra());
		assertNotNull(result.getDataValidade());
		assertEquals("Descricao1", result.getDescricao());
		assertEquals(1.0, result.getQuantidade());
		assertEquals("KG", result.getUnidade());
	}

	@Test
	void testCreate() {
		Estoque entity = input.mockEntity(1);
		
		var persisted = entity;
		persisted.setId(1L);
		
		EstoqueVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/produto/1>;rel=\"self\"]"));
		
		assertNotNull(result.getDataCompra());
		assertNotNull(result.getDataValidade());
		assertEquals("Descricao1", result.getDescricao());
		assertEquals(1.0, result.getQuantidade());
		assertEquals("KG", result.getUnidade());
	}
	
	@Test
	void testCreateWithNullEstoque() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testUpdate() {
		Estoque entity = input.mockEntity(1);
		entity.setId(1L);
		
		var persisted = entity;
		persisted.setId(1L);
		
		EstoqueVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/produto/1>;rel=\"self\"]"));
		
		assertNotNull(result.getDataCompra());
		assertNotNull(result.getDataValidade());
		assertEquals("Descricao1", result.getDescricao());
		assertEquals(1.0, result.getQuantidade());
		assertEquals("KG", result.getUnidade());
	}
	
	@Test
	void testUpdateWithNullEstoque() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
		
	@Test
	void testDelete() {
		Estoque entity = input.mockEntity(1);
		entity.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		service.delete(1L);
	}

}
