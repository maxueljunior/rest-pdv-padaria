package br.com.leuxam.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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

import br.com.leuxam.data.vo.v1.ClienteVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.model.Cliente;
import br.com.leuxam.repositories.ClienteRepository;
import br.com.leuxam.services.ClienteServices;
import br.com.leuxam.unittests.mapper.mocks.MockCliente;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ClienteServicesTest {
	
	MockCliente input;
	
	@InjectMocks
	private ClienteServices service;
	
	@Mock
	ClienteRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockCliente();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Cliente entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/cliente/1>;rel=\"self\"]"));
		
		assertNotNull(result.getDataNascimento());
		assertEquals("Endereço1", result.getEndereco());
		assertEquals(1.0, result.getLucratividade());
		assertEquals("Nome1", result.getNome());
		assertEquals("F", result.getSexo());
		assertEquals("Sobrenome1", result.getSobrenome());
		assertEquals("Telefone1", result.getTelefone());
	}
	
	@Test
	void testCreate() {
		Cliente entity = input.mockEntity(1);
		
		Cliente persisted = entity;
		persisted.setId(1L);
		
		ClienteVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/cliente/1>;rel=\"self\"]"));
		
		assertNotNull(result.getDataNascimento());
		assertEquals("Endereço1", result.getEndereco());
		assertEquals(1.0, result.getLucratividade());
		assertEquals("Nome1", result.getNome());
		assertEquals("F", result.getSexo());
		assertEquals("Sobrenome1", result.getSobrenome());
		assertEquals("Telefone1", result.getTelefone());
	}
	
	@Test
	void testCreateWithNullCliente() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() {
		Cliente entity = input.mockEntity(1);
		entity.setId(1L);
		
		Cliente persisted = entity;
		persisted.setId(1L);
		
		ClienteVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/cliente/1>;rel=\"self\"]"));
		
		assertNotNull(result.getDataNascimento());
		assertEquals("Endereço1", result.getEndereco());
		assertEquals(1.0, result.getLucratividade());
		assertEquals("Nome1", result.getNome());
		assertEquals("F", result.getSexo());
		assertEquals("Sobrenome1", result.getSobrenome());
		assertEquals("Telefone1", result.getTelefone());
	}
	
	@Test
	void testUpdateWithNullCliente() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete() {
		Cliente entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}

}
