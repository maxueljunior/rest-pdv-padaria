package br.com.leuxam.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.leuxam.data.vo.v1.CompraEstoqueVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.model.CompraEstoque;
import br.com.leuxam.repositories.CompraEstoqueRepository;
import br.com.leuxam.repositories.ComprasRepository;
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
	
	@Mock
	ComprasRepository comprasRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		input = new MockCompraEstoque();
		MockitoAnnotations.openMocks(this);
	}
	@Test
	void testCreate() {
		CompraEstoque entity = input.mockEntity(1);
		
		CompraEstoque persisted = entity;
		persisted.getCompras().setId(1L);
		persisted.getEstoque().setId(1L);
		
		CompraEstoqueVO vo = input.mockVO(1);
		vo.getCompras().setKey(1L);
		vo.getEstoque().setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.create(vo);
		
		assertNotNull(result.getCompras().getKey());
		assertNotNull(result.getEstoque().getKey());
		assertEquals(1.0, result.getPreco());
		assertEquals(1.0, result.getQuantidade());
		
		// De acordo com o JUnit não é necessário declarar essa função pois
		// não é chamado pelo service.
		// when(repository.updateValorTotalFromCompras(1L)).thenReturn(1.0);
		
		comprasRepository.updateValorTotalFromCompras(1.0, 1L);

	}
	
	@Test
	void testCreateWithNullCompraEstoque() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() {
		CompraEstoque entity = input.mockEntity(1);
		
		CompraEstoque persisted = entity;
		persisted.getCompras().setId(1L);
		persisted.getEstoque().setId(1L);
		
		CompraEstoqueVO vo = input.mockVO(1);
		vo.getCompras().setKey(1L);
		vo.getEstoque().setKey(1L);
		
		when(repository.findByIdProdutoAndCompras(1L, 1L)).thenReturn(entity);
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(vo);
		
		assertNotNull(result.getCompras().getKey());
		assertNotNull(result.getEstoque().getKey());
		assertEquals(1.0, result.getPreco());
		assertEquals(1.0, result.getQuantidade());
		
		// De acordo com o JUnit não é necessário declarar essa função pois
		// não é chamado pelo service.
		// when(repository.updateValorTotalFromCompras(1L)).thenReturn(1.0);
		
		comprasRepository.updateValorTotalFromCompras(1.0, 1L);
	}
	
	@Test
	void testUpdateWithNullCompraEstoque() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testDelete() {
		CompraEstoque entity = input.mockEntity(1);
		entity.getCompras().setId(1L);
		entity.getEstoque().setId(1L);
		
		when(repository.findByIdProdutoAndCompras(1L, 1L)).thenReturn(entity);
		service.delete(1L, 1L);
	}
	
	@Test
	void testDeleteWithNullCompraEstoque() {
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(0L, 0L);
		});
		String expectedMessage = "Id do Produto ou da Compra não estão sendo informados";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
}
