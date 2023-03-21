package br.com.leuxam.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

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
	void testFindAllWithProduto() {
		List<CompraEstoque> list = input.mockEntityListForProducts();
		
		when(repository.findAllWithProduto(1L)).thenReturn(list);
		
		var compraProdutos = service.findAllWithProduto(1L);
		assertNotNull(compraProdutos);
		assertEquals(14, compraProdutos.size());
		
		var compraProdutoUm = compraProdutos.get(1);
		
		assertNotNull(compraProdutoUm.getCompras().getKey());
		assertNotNull(compraProdutoUm.getEstoque().getKey());
		assertEquals(1.0, compraProdutoUm.getPreco());
		assertEquals(1.0, compraProdutoUm.getQuantidade());
		
		var compraProdutoCinco = compraProdutos.get(5);
		
		assertNotNull(compraProdutoCinco.getCompras().getKey());
		assertNotNull(compraProdutoCinco.getEstoque().getKey());
		assertEquals(5.0, compraProdutoCinco.getPreco());
		assertEquals(5.0, compraProdutoCinco.getQuantidade());
		
		var compraProdutoNove = compraProdutos.get(9);
		
		assertNotNull(compraProdutoNove.getCompras().getKey());
		assertNotNull(compraProdutoNove.getEstoque().getKey());
		assertEquals(9.0, compraProdutoNove.getPreco());
		assertEquals(9.0, compraProdutoNove.getQuantidade());
		
	}

	@Test
	void testFindAllWithCompra() {
		
		List<CompraEstoque> list = input.mockEntityListForOrders();
		
		when(repository.findAllWithCompras(1L)).thenReturn(list);
		
		var compraProdutos = service.findAllWithCompra(1L);
		assertNotNull(compraProdutos);
		assertEquals(14, compraProdutos.size());
		
		var compraProdutoUm = compraProdutos.get(1);
		
		assertNotNull(compraProdutoUm.getCompras().getKey());
		assertNotNull(compraProdutoUm.getEstoque().getKey());
		assertEquals(1.0, compraProdutoUm.getPreco());
		assertEquals(1.0, compraProdutoUm.getQuantidade());
		
		var compraProdutoCinco = compraProdutos.get(5);
		
		assertNotNull(compraProdutoCinco.getCompras().getKey());
		assertNotNull(compraProdutoCinco.getEstoque().getKey());
		assertEquals(5.0, compraProdutoCinco.getPreco());
		assertEquals(5.0, compraProdutoCinco.getQuantidade());
		
		var compraProdutoNove = compraProdutos.get(9);
		
		assertNotNull(compraProdutoNove.getCompras().getKey());
		assertNotNull(compraProdutoNove.getEstoque().getKey());
		assertEquals(9.0, compraProdutoNove.getPreco());
		assertEquals(9.0, compraProdutoNove.getQuantidade());
	}
	/*
	@Test
	void testFindAll() {
		
		List<CompraEstoque> list = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var compraProdutos = service.findAll();
		assertNotNull(compraProdutos);
		assertEquals(14, compraProdutos.size());
		
		var compraProdutoUm = compraProdutos.get(1);
		
		assertNotNull(compraProdutoUm.getCompras().getKey());
		assertNotNull(compraProdutoUm.getEstoque().getKey());
		assertEquals(1.0, compraProdutoUm.getPreco());
		assertEquals(1.0, compraProdutoUm.getQuantidade());
		
		var compraProdutoCinco = compraProdutos.get(5);
		
		assertNotNull(compraProdutoCinco.getCompras().getKey());
		assertNotNull(compraProdutoCinco.getEstoque().getKey());
		assertEquals(5.0, compraProdutoCinco.getPreco());
		assertEquals(5.0, compraProdutoCinco.getQuantidade());
		
		var compraProdutoNove = compraProdutos.get(9);
		
		assertNotNull(compraProdutoNove.getCompras().getKey());
		assertNotNull(compraProdutoNove.getEstoque().getKey());
		assertEquals(9.0, compraProdutoNove.getPreco());
		assertEquals(9.0, compraProdutoNove.getQuantidade());
	}
*/
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
