package br.com.leuxam.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.leuxam.data.vo.v1.ClienteVO;
import br.com.leuxam.data.vo.v1.CompraEstoqueVO;
import br.com.leuxam.data.vo.v1.ComprasVO;
import br.com.leuxam.data.vo.v1.EstoqueVO;
import br.com.leuxam.data.vo.v1.FornecedorVO;
import br.com.leuxam.data.vo.v1.VendaEstoqueVO;
import br.com.leuxam.data.vo.v1.VendasVO;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.Cliente;
import br.com.leuxam.model.CompraEstoque;
import br.com.leuxam.model.Compras;
import br.com.leuxam.model.Estoque;
import br.com.leuxam.model.Fornecedor;
import br.com.leuxam.model.VendaEstoque;
import br.com.leuxam.model.Vendas;
import br.com.leuxam.unittests.mapper.mocks.MockCliente;
import br.com.leuxam.unittests.mapper.mocks.MockCompraEstoque;
import br.com.leuxam.unittests.mapper.mocks.MockCompras;
import br.com.leuxam.unittests.mapper.mocks.MockEstoque;
import br.com.leuxam.unittests.mapper.mocks.MockFornecedor;
import br.com.leuxam.unittests.mapper.mocks.MockVendaEstoque;
import br.com.leuxam.unittests.mapper.mocks.MockVendas;

public class DozerConvertTest {
	
	MockCliente inputObjectCliente;
	MockCompraEstoque inputObjectCompraEstoque;
	MockCompras inputObjectCompras;
	MockEstoque inputObjectEstoque;
	MockFornecedor inputObjectFornecedor;
	MockVendaEstoque inputObjectVendaEstoque;
	MockVendas inputObjectVendas;
	
	@BeforeEach
	public void setUp() {
		inputObjectCliente = new MockCliente();
		inputObjectCompraEstoque = new MockCompraEstoque();
		inputObjectCompras = new MockCompras();
		inputObjectEstoque = new MockEstoque();
		inputObjectFornecedor = new MockFornecedor();
		inputObjectVendaEstoque = new MockVendaEstoque();
		inputObjectVendas = new MockVendas();
	}
	
	@Test
	public void parseEntityToVOTest() {
		ClienteVO outputCliente = DozerMapper.parseObject(inputObjectCliente.mockEntity(), ClienteVO.class);
		assertNotNull(outputCliente.getDataNascimento());
		assertEquals("Endereço0", outputCliente.getEndereco());
		assertEquals(Long.valueOf(0L), outputCliente.getId());
		assertEquals(Double.valueOf(0), outputCliente.getLucratividade());
		assertEquals("Nome0", outputCliente.getNome());
		assertEquals("M", outputCliente.getSexo());
		assertEquals("Sobrenome0", outputCliente.getSobrenome());
		assertEquals("Telefone0", outputCliente.getTelefone());
		
		CompraEstoqueVO outputCompraEstoque = DozerMapper.parseObject(inputObjectCompraEstoque.mockEntity(), CompraEstoqueVO.class);
		assertEquals(Long.valueOf(0), outputCompraEstoque.getCompras().getId());
		assertEquals(Long.valueOf(0), outputCompraEstoque.getEstoque().getId());
		assertEquals(Double.valueOf(0), outputCompraEstoque.getPreco());
		assertEquals(Double.valueOf(0), outputCompraEstoque.getQuantidade());
		
		ComprasVO outputCompra = DozerMapper.parseObject(inputObjectCompras.mockEntity(), ComprasVO.class);
		assertEquals(Long.valueOf(0), outputCompra.getFornecedor().getId());
		assertEquals(Long.valueOf(0), outputCompra.getId());
		assertEquals(Double.valueOf(0), outputCompra.getValorTotal());
		
		EstoqueVO outputEstoque = DozerMapper.parseObject(inputObjectEstoque.mockEntity(), EstoqueVO.class);
		assertNotNull(outputEstoque.getDataCompra());
		assertNotNull(outputEstoque.getDataValidade());
		assertEquals("Descricao0", outputEstoque.getDescricao());
		assertEquals(Long.valueOf(0), outputEstoque.getId());
		assertEquals(Double.valueOf(0), outputEstoque.getQuantidade());
		assertEquals("UN", outputEstoque.getUnidade());
		
		FornecedorVO outputFornecedor = DozerMapper.parseObject(inputObjectFornecedor.mockEntity(), FornecedorVO.class);
		assertEquals("CNPJ0", outputFornecedor.getCnpj());
		assertEquals(Long.valueOf(0), outputFornecedor.getId());
		assertEquals("Contato0", outputFornecedor.getNomeDoContato());
		assertEquals("Razao0", outputFornecedor.getRazaoSocial());
		assertEquals("Telefone0", outputFornecedor.getTelefone());
		
		VendaEstoqueVO outputVendaEstoque = DozerMapper.parseObject(inputObjectVendaEstoque.mockEntity(), VendaEstoqueVO.class);
		assertEquals(Long.valueOf(0), outputVendaEstoque.getEstoque().getId());
		assertEquals(Double.valueOf(0), outputVendaEstoque.getPreco());
		assertEquals(Double.valueOf(0), outputVendaEstoque.getQuantidade());
		assertEquals(Long.valueOf(0), outputVendaEstoque.getVendas().getId());
		
		VendasVO outputVenda = DozerMapper.parseObject(inputObjectVendas.mockEntity(), VendasVO.class);
		assertEquals(Long.valueOf(0), outputVenda.getCliente().getId());
		//assertNotNull(outputVenda.getCondicaoPagamento());
		assertNotNull(outputVenda.getDataVenda());
		assertEquals(Long.valueOf(0), outputVenda.getId());
		assertEquals(Double.valueOf(0), outputVenda.getValorTotal());
	}
	
	@Test
	public void parseVOToEntityTest() {
		Cliente outputCliente = DozerMapper.parseObject(inputObjectCliente.mockVO(), Cliente.class);
		assertNotNull(outputCliente.getDataNascimento());
		assertEquals("Endereço0", outputCliente.getEndereco());
		assertEquals(Long.valueOf(0L), outputCliente.getId());
		assertEquals(Double.valueOf(0), outputCliente.getLucratividade());
		assertEquals("Nome0", outputCliente.getNome());
		assertEquals("M", outputCliente.getSexo());
		assertEquals("Sobrenome0", outputCliente.getSobrenome());
		assertEquals("Telefone0", outputCliente.getTelefone());
		
		CompraEstoque outputCompraEstoque = DozerMapper.parseObject(inputObjectCompraEstoque.mockVO(), CompraEstoque.class);
		assertEquals(Long.valueOf(0), outputCompraEstoque.getCompras().getId());
		assertEquals(Long.valueOf(0), outputCompraEstoque.getEstoque().getId());
		assertEquals(Double.valueOf(0), outputCompraEstoque.getPreco());
		assertEquals(Double.valueOf(0), outputCompraEstoque.getQuantidade());
		
		Compras outputCompra = DozerMapper.parseObject(inputObjectCompras.mockVO(), Compras.class);
		assertEquals(Long.valueOf(0), outputCompra.getFornecedor().getId());
		assertEquals(Long.valueOf(0), outputCompra.getId());
		assertEquals(Double.valueOf(0), outputCompra.getValorTotal());
		
		Estoque outputEstoque = DozerMapper.parseObject(inputObjectEstoque.mockVO(), Estoque.class);
		assertNotNull(outputEstoque.getDataCompra());
		assertNotNull(outputEstoque.getDataValidade());
		assertEquals("Descricao0", outputEstoque.getDescricao());
		assertEquals(Long.valueOf(0), outputEstoque.getId());
		assertEquals(Double.valueOf(0), outputEstoque.getQuantidade());
		assertEquals("UN", outputEstoque.getUnidade());
		
		Fornecedor outputFornecedor = DozerMapper.parseObject(inputObjectFornecedor.mockVO(), Fornecedor.class);
		assertEquals("CNPJ0", outputFornecedor.getCnpj());
		assertEquals(Long.valueOf(0), outputFornecedor.getId());
		assertEquals("Contato0", outputFornecedor.getNomeDoContato());
		assertEquals("Razao0", outputFornecedor.getRazaoSocial());
		assertEquals("Telefone0", outputFornecedor.getTelefone());
		
		VendaEstoque outputVendaEstoque = DozerMapper.parseObject(inputObjectVendaEstoque.mockVO(), VendaEstoque.class);
		assertEquals(Long.valueOf(0), outputVendaEstoque.getEstoque().getId());
		assertEquals(Double.valueOf(0), outputVendaEstoque.getPreco());
		assertEquals(Double.valueOf(0), outputVendaEstoque.getQuantidade());
		assertEquals(Long.valueOf(0), outputVendaEstoque.getVendas().getId());
		
		Vendas outputVenda = DozerMapper.parseObject(inputObjectVendas.mockVO(), Vendas.class);
		assertEquals(Long.valueOf(0), outputVenda.getCliente().getId());
		//assertNotNull(outputVenda.getCondicaoPagamento());
		assertNotNull(outputVenda.getDataVenda());
		assertEquals(Long.valueOf(0), outputVenda.getId());
		assertEquals(Double.valueOf(0), outputVenda.getValorTotal());
	}
}	
