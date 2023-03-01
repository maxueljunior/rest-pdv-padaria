package br.com.leuxam.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.leuxam.model.CompraEstoque;
import br.com.leuxam.repositories.CompraEstoqueRepository;
import br.com.leuxam.services.CompraEstoqueService;
import br.com.leuxam.unittests.mapper.mocks.MockCompraEstoque;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CompraEstoqueServiceTest {
	
	MockCompraEstoque input;
	
	@InjectMocks
	private CompraEstoqueService service;
	
	@Mock
	CompraEstoqueRepository repository;
	
	@BeforeEach
	void setUp() throws Exception {
		input = new MockCompraEstoque();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testFindAllWithProduto() {
		
	}

	@Test
	void testFindAllWithCompra() {
		fail("Not yet implemented");
	}
	
	@Test
	void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

}