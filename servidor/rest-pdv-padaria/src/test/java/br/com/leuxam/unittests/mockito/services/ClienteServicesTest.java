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
	void testFindAll() {
		List<Cliente> list = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var cliente = service.findAll();
		assertNotNull(cliente);
		assertEquals(14, cliente.size());
		
		var clienteUm = cliente.get(1);
		
		assertNotNull(clienteUm.getKey());
		assertNotNull(clienteUm.getLinks());
		assertTrue(clienteUm.toString().contains("links: [</api/cliente/1>;rel=\"self\"]"));
		
		assertNotNull(clienteUm.getDataNascimento());
		assertEquals("Endereço1", clienteUm.getEndereco());
		assertEquals(1.0, clienteUm.getLucratividade());
		assertEquals("Nome1", clienteUm.getNome());
		assertEquals("F", clienteUm.getSexo());
		assertEquals("Sobrenome1", clienteUm.getSobrenome());
		assertEquals("Telefone1", clienteUm.getTelefone());
		
		var clienteCinco = cliente.get(5);
		
		assertNotNull(clienteCinco.getKey());
		assertNotNull(clienteCinco.getLinks());
		assertTrue(clienteCinco.toString().contains("links: [</api/cliente/5>;rel=\"self\"]"));
		
		assertNotNull(clienteCinco.getDataNascimento());
		assertEquals("Endereço5", clienteCinco.getEndereco());
		assertEquals(5.0, clienteCinco.getLucratividade());
		assertEquals("Nome5", clienteCinco.getNome());
		assertEquals("F", clienteCinco.getSexo());
		assertEquals("Sobrenome5", clienteCinco.getSobrenome());
		assertEquals("Telefone5", clienteCinco.getTelefone());
		
		var clienteOito = cliente.get(8);
		
		assertNotNull(clienteOito.getKey());
		assertNotNull(clienteOito.getLinks());
		assertTrue(clienteOito.toString().contains("links: [</api/cliente/8>;rel=\"self\"]"));
		
		assertNotNull(clienteOito.getDataNascimento());
		assertEquals("Endereço8", clienteOito.getEndereco());
		assertEquals(8.0, clienteOito.getLucratividade());
		assertEquals("Nome8", clienteOito.getNome());
		assertEquals("M", clienteOito.getSexo());
		assertEquals("Sobrenome8", clienteOito.getSobrenome());
		assertEquals("Telefone8", clienteOito.getTelefone());
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
