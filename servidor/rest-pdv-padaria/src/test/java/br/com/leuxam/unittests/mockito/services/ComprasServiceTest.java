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

import br.com.leuxam.data.vo.v1.ComprasVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.model.Compras;
import br.com.leuxam.repositories.ComprasRepository;
import br.com.leuxam.services.ComprasService;
import br.com.leuxam.unittests.mapper.mocks.MockCompras;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ComprasServiceTest {
	
	MockCompras input;
	
	@InjectMocks
	private ComprasService service;
	
	@Mock
	ComprasRepository repository;
	
	@BeforeEach
	void setUp() throws Exception {
		input = new MockCompras();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testFindById() {
		Compras entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.getFornecedor().getLinks());
		assertTrue(result.toString().contains("links: [</api/compras/1>;rel=\"self\"]"));
		assertTrue(result.getFornecedor().toString().contains("links: [</api/fornecedor/1>;rel=\"self\"]"));
		
		assertEquals(1L, result.getFornecedor().getKey());
		assertEquals(1.0, result.getValorTotal());
	}
	
	@Test
	void testCreate() {
		Compras entity = input.mockEntity(1);
	
		Compras persisted = entity;
		persisted.setId(1L);
		persisted.getFornecedor().setId(1L);
		
		ComprasVO vo = input.mockVO(1);
		vo.setKey(1L);
		vo.getFornecedor().setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.getFornecedor().getLinks());
		assertTrue(result.toString().contains("links: [</api/compras/1>;rel=\"self\"]"));
		assertTrue(result.getFornecedor().toString().contains("links: [</api/fornecedor/1>;rel=\"self\"]"));
		
		assertEquals(1L, result.getFornecedor().getKey());
		assertEquals(1.0, result.getValorTotal());
	}

	@Test
	void testCreateWithNullCompras() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testUpdate() {
		Compras entity = input.mockEntity(1);
		entity.setId(1L);
		entity.getFornecedor().setId(1L);
		
		Compras persisted = entity;
		persisted.setId(1L);
		persisted.getFornecedor().setId(1L);
		
		ComprasVO vo = input.mockVO(1);
		vo.setKey(1L);
		vo.getFornecedor().setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.getFornecedor().getLinks());
		assertTrue(result.toString().contains("links: [</api/compras/1>;rel=\"self\"]"));
		assertTrue(result.getFornecedor().toString().contains("links: [</api/fornecedor/1>;rel=\"self\"]"));
		
		assertEquals(1L, result.getFornecedor().getKey());
		assertEquals(1.0, result.getValorTotal());
	}
	
	@Test
	void testUpdateWithNullCompras() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete() {
		Compras entity = input.mockEntity(1);
		entity.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		service.delete(1L);
	}

}
