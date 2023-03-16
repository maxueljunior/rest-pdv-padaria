package br.com.leuxam.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import br.com.leuxam.data.vo.v1.VendaEstoqueVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.model.VendaEstoque;
import br.com.leuxam.model.Vendas;
import br.com.leuxam.repositories.VendasEstoqueRepository;
import br.com.leuxam.repositories.VendasRepository;
import br.com.leuxam.services.VendasEstoqueService;
import br.com.leuxam.unittests.mapper.mocks.MockVendaEstoque;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class VendasEstoqueServiceTest {
	
	MockVendaEstoque input;
	
	@InjectMocks
	private VendasEstoqueService service;
	
	@Mock
	VendasEstoqueRepository repository;
	
	@Mock
	VendasRepository vendasRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		input = new MockVendaEstoque();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		List<VendaEstoque> list = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var vendasEstoque = service.findAll();
		
		assertNotNull(vendasEstoque);
		assertEquals(14, vendasEstoque.size());
		
		var vendaEstoqueUm = vendasEstoque.get(1);
		
		assertNotNull(vendaEstoqueUm);
		assertNotNull(vendaEstoqueUm.getEstoque().getKey());
		assertNotNull(vendaEstoqueUm.getVendas().getKey());
		assertEquals(1.0, vendaEstoqueUm.getPreco());
		assertEquals(1.0, vendaEstoqueUm.getQuantidade());
		
		var vendaEstoqueCinco = vendasEstoque.get(5);
		
		assertNotNull(vendaEstoqueCinco);
		assertNotNull(vendaEstoqueCinco.getEstoque().getKey());
		assertNotNull(vendaEstoqueCinco.getVendas().getKey());
		assertEquals(5.0, vendaEstoqueCinco.getPreco());
		assertEquals(5.0, vendaEstoqueCinco.getQuantidade());
		
		var vendaEstoqueNove = vendasEstoque.get(9);
		
		assertNotNull(vendaEstoqueNove);
		assertNotNull(vendaEstoqueNove.getEstoque().getKey());
		assertNotNull(vendaEstoqueNove.getVendas().getKey());
		assertEquals(9.0, vendaEstoqueNove.getPreco());
		assertEquals(9.0, vendaEstoqueNove.getQuantidade());
	}
	
	@Test
	void testCreate() {
		
		VendaEstoque entity = input.mockEntity(1);
		
		VendaEstoque persisted = entity;
		persisted.getEstoque().setId(1L);
		persisted.getVendas().setId(1L);
		
		VendaEstoqueVO vo = input.mockVO(1);
		vo.getEstoque().setKey(1L);
		vo.getVendas().setKey(1L);
		
		Vendas vendas = persisted.getVendas();
		System.out.println(persisted.getVendas().getId());
		
		when(vendasRepository.findById(1L)).thenReturn(Optional.of(vendas));
		when(repository.save(entity)).thenReturn(persisted);
	
		var result = service.create(vo);
		
		verify(repository, times(1)).save(eq(persisted));
		
		assertNotNull(result);
		assertNotNull(result.getEstoque().getKey());
		assertNotNull(result.getVendas().getKey());
		assertEquals(1.0, result.getPreco());
		assertEquals(1.0, result.getQuantidade());
		
		vendasRepository.updateValorTotalFromVendas(1.0, 1L);
	}

	@Test
	void testCreateWithNullVendaEstoque() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testFindAllWithProdutcs() {
		List<VendaEstoque> list = input.mockEntityListForProdutos();
		
		when(repository.findAllWithProducts(1L)).thenReturn(list);
		var vendaEstoqueByProdutos = service.findAllWithProdutcs(1L);
		
		assertNotNull(vendaEstoqueByProdutos);
		assertEquals(14, vendaEstoqueByProdutos.size());
		
		var vendaEstoqueByProdutosUm = vendaEstoqueByProdutos.get(1);
		
		assertNotNull(vendaEstoqueByProdutosUm);
		assertNotNull(vendaEstoqueByProdutosUm.getEstoque().getKey());
		assertNotNull(vendaEstoqueByProdutosUm.getVendas().getKey());
		assertEquals(1.0, vendaEstoqueByProdutosUm.getPreco());
		assertEquals(1.0, vendaEstoqueByProdutosUm.getQuantidade());
		
		var vendaEstoqueByProdutosCinco = vendaEstoqueByProdutos.get(5);
		
		assertNotNull(vendaEstoqueByProdutosCinco);
		assertNotNull(vendaEstoqueByProdutosCinco.getEstoque().getKey());
		assertNotNull(vendaEstoqueByProdutosCinco.getVendas().getKey());
		assertEquals(5.0, vendaEstoqueByProdutosCinco.getPreco());
		assertEquals(5.0, vendaEstoqueByProdutosCinco.getQuantidade());
		
		var vendaEstoqueByProdutosNove = vendaEstoqueByProdutos.get(9);
		
		assertNotNull(vendaEstoqueByProdutosNove);
		assertNotNull(vendaEstoqueByProdutosNove.getEstoque().getKey());
		assertNotNull(vendaEstoqueByProdutosNove.getVendas().getKey());
		assertEquals(9.0, vendaEstoqueByProdutosNove.getPreco());
		assertEquals(9.0, vendaEstoqueByProdutosNove.getQuantidade());
	}

	@Test
	void testFindAllWithVendas() {
		
		List<VendaEstoque> list = input.mockEntityListForVendas();
		
		when(repository.findAllWithVendas(1L)).thenReturn(list);
		var vendaEstoqueByVendas = service.findAllWithVendas(1L);
		
		assertNotNull(vendaEstoqueByVendas);
		assertEquals(14, vendaEstoqueByVendas.size());
		
		var vendaEstoqueByVendasUm = vendaEstoqueByVendas.get(1);
		
		assertNotNull(vendaEstoqueByVendasUm);
		assertNotNull(vendaEstoqueByVendasUm.getEstoque().getKey());
		assertNotNull(vendaEstoqueByVendasUm.getVendas().getKey());
		assertEquals(1.0, vendaEstoqueByVendasUm.getPreco());
		assertEquals(1.0, vendaEstoqueByVendasUm.getQuantidade());
		
		var vendaEstoqueByVendasCinco = vendaEstoqueByVendas.get(5);
		
		assertNotNull(vendaEstoqueByVendasCinco);
		assertNotNull(vendaEstoqueByVendasCinco.getEstoque().getKey());
		assertNotNull(vendaEstoqueByVendasCinco.getVendas().getKey());
		assertEquals(5.0, vendaEstoqueByVendasCinco.getPreco());
		assertEquals(5.0, vendaEstoqueByVendasCinco.getQuantidade());
		
		var vendaEstoqueByVendasNove = vendaEstoqueByVendas.get(9);
		
		assertNotNull(vendaEstoqueByVendasNove);
		assertNotNull(vendaEstoqueByVendasNove.getEstoque().getKey());
		assertNotNull(vendaEstoqueByVendasNove.getVendas().getKey());
		assertEquals(9.0, vendaEstoqueByVendasNove.getPreco());
		assertEquals(9.0, vendaEstoqueByVendasNove.getQuantidade());
	}

	@Test
	void testUpdateByIdProductAndVendas() {
		VendaEstoque entity = input.mockEntity(1);
		entity.getEstoque().setId(1L);
		entity.getVendas().setId(1L);
		
		VendaEstoque persisted = entity;
		persisted.getEstoque().setId(1L);
		persisted.getVendas().setId(1L);
		
		VendaEstoqueVO vo = input.mockVO(1);
		vo.getEstoque().setKey(1L);
		vo.getVendas().setKey(1L);
		
		when(repository.findByIdProductAndVendas(1L, 1L)).thenReturn(entity);
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.updateByIdProductAndVendas(vo);
		
		assertNotNull(result);
		assertNotNull(result.getEstoque().getKey());
		assertNotNull(result.getVendas().getKey());
		assertEquals(1.0, result.getPreco());
		assertEquals(1.0, result.getQuantidade());
	}
	
	@Test
	void testUpdateByIdProductAndVendasWithNotNullVendaEstoque() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () ->{
			service.updateByIdProductAndVendas(null);
		});
		String expectedMessage = "Não é permitido persistir um objeto nullo";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete() {
		VendaEstoque entity = input.mockEntity(1);
		entity.getVendas().setId(1L);
		entity.getEstoque().setId(1L);
		
		when(repository.findByIdProductAndVendas(1L, 1L)).thenReturn(entity);
		service.delete(1L, 1L);
	}

}
