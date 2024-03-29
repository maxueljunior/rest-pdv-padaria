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

import br.com.leuxam.data.vo.v1.VendasVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.model.Vendas;
import br.com.leuxam.repositories.VendasRepository;
import br.com.leuxam.services.VendasService;
import br.com.leuxam.unittests.mapper.mocks.MockVendas;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class VendasServiceTest {
	
	MockVendas input;
	
	@InjectMocks
	private VendasService service;
	
	@Mock
	VendasRepository repository;
	
	@BeforeEach
	void setUp() throws Exception {
		input = new MockVendas();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Vendas entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		var result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/vendas/1>;rel=\"self\"]"));
		assertTrue(result.getCliente().toString().contains("links: [</api/cliente/1>;rel=\"self\"]"));
		
		assertNotNull(result.getCliente());
		assertNotNull(result.getCondicaoPagamento());
		assertNotNull(result.getDataVenda());
		assertEquals(1.0, result.getValorTotal());
	}

	@Test
	void testCreate() {
		Vendas entity = input.mockEntity(1);
		
		Vendas persisted = entity;
		persisted.setId(1L);
		
		VendasVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/vendas/1>;rel=\"self\"]"));
		assertTrue(result.getCliente().toString().contains("links: [</api/cliente/1>;rel=\"self\"]"));
		
		assertNotNull(result.getCliente());
		assertNotNull(result.getCondicaoPagamento());
		assertNotNull(result.getDataVenda());
		assertEquals(1.0, result.getValorTotal());
	}
	
	@Test
	void testCreateWithNullVendas() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() {
		Vendas entity = input.mockEntity(1);
		entity.setId(1L);
		
		Vendas persisted = entity;
		persisted.setId(1L);
		
		VendasVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/vendas/1>;rel=\"self\"]"));
		assertTrue(result.getCliente().toString().contains("links: [</api/cliente/1>;rel=\"self\"]"));
		
		assertNotNull(result.getCliente());
		assertNotNull(result.getCondicaoPagamento());
		assertNotNull(result.getDataVenda());
		assertEquals(1.0, result.getValorTotal());
	}

	@Test
	void testDelete() {
		Vendas entity = input.mockEntity(1);
		entity.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		service.delete(1L);
	}
}
