package br.com.leuxam.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

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
		assertEquals(Long.valueOf(0L), outputCliente.getKey());
		assertEquals(Double.valueOf(0), outputCliente.getLucratividade());
		assertEquals("Nome0", outputCliente.getNome());
		assertEquals("M", outputCliente.getSexo());
		assertEquals("Sobrenome0", outputCliente.getSobrenome());
		assertEquals("Telefone0", outputCliente.getTelefone());
		
		CompraEstoqueVO outputCompraEstoque = DozerMapper.parseObject(inputObjectCompraEstoque.mockEntity(), CompraEstoqueVO.class);
		assertEquals(Long.valueOf(0), outputCompraEstoque.getCompras().getKey());
		assertEquals(Long.valueOf(0), outputCompraEstoque.getEstoque().getKey());
		assertEquals(Double.valueOf(0), outputCompraEstoque.getPreco());
		assertEquals(Double.valueOf(0), outputCompraEstoque.getQuantidade());
		
		ComprasVO outputCompra = DozerMapper.parseObject(inputObjectCompras.mockEntity(), ComprasVO.class);
		assertEquals(Long.valueOf(0), outputCompra.getFornecedor().getKey());
		assertEquals(Long.valueOf(0), outputCompra.getKey());
		assertEquals(Double.valueOf(0), outputCompra.getValorTotal());
		
		EstoqueVO outputEstoque = DozerMapper.parseObject(inputObjectEstoque.mockEntity(), EstoqueVO.class);
		assertNotNull(outputEstoque.getDataCompra());
		assertNotNull(outputEstoque.getDataValidade());
		assertEquals("Descricao0", outputEstoque.getDescricao());
		assertEquals(Long.valueOf(0), outputEstoque.getKey());
		assertEquals(Double.valueOf(0), outputEstoque.getQuantidade());
		assertEquals("UN", outputEstoque.getUnidade());
		
		FornecedorVO outputFornecedor = DozerMapper.parseObject(inputObjectFornecedor.mockEntity(), FornecedorVO.class);
		assertEquals("CNPJ0", outputFornecedor.getCnpj());
		assertEquals(Long.valueOf(0), outputFornecedor.getKey());
		assertEquals("Contato0", outputFornecedor.getNomeDoContato());
		assertEquals("Razao0", outputFornecedor.getRazaoSocial());
		assertEquals("Telefone0", outputFornecedor.getTelefone());
		
		VendaEstoqueVO outputVendaEstoque = DozerMapper.parseObject(inputObjectVendaEstoque.mockEntity(), VendaEstoqueVO.class);
		assertEquals(Long.valueOf(0), outputVendaEstoque.getEstoque().getKey());
		assertEquals(Double.valueOf(0), outputVendaEstoque.getPreco());
		assertEquals(Double.valueOf(0), outputVendaEstoque.getQuantidade());
		assertEquals(Long.valueOf(0), outputVendaEstoque.getVendas().getKey());
		
		VendasVO outputVenda = DozerMapper.parseObject(inputObjectVendas.mockEntity(), VendasVO.class);
		assertEquals(Long.valueOf(0), outputVenda.getCliente().getKey());
		assertNotNull(outputVenda.getCondicaoPagamento());
		assertNotNull(outputVenda.getDataVenda());
		assertEquals(Long.valueOf(0), outputVenda.getKey());
		assertEquals(Double.valueOf(0), outputVenda.getValorTotal());
	}
	
	@Test
	public void parseEntityListToVOTest() {
		
		List<ClienteVO> outputClienteList = DozerMapper.parseListObjects(inputObjectCliente.mockEntityList(), ClienteVO.class);
		
		ClienteVO outputClienteZero = outputClienteList.get(0);
		
		assertNotNull(outputClienteZero.getDataNascimento());
		assertEquals("Endereço0", outputClienteZero.getEndereco());
		assertEquals(Long.valueOf(0L), outputClienteZero.getKey());
		assertEquals(Double.valueOf(0), outputClienteZero.getLucratividade());
		assertEquals("Nome0", outputClienteZero.getNome());
		assertEquals("M", outputClienteZero.getSexo());
		assertEquals("Sobrenome0", outputClienteZero.getSobrenome());
		assertEquals("Telefone0", outputClienteZero.getTelefone());
		
		ClienteVO outputClienteCinco = outputClienteList.get(5);
		
		assertNotNull(outputClienteCinco.getDataNascimento());
		assertEquals("Endereço5", outputClienteCinco.getEndereco());
		assertEquals(Long.valueOf(5L), outputClienteCinco.getKey());
		assertEquals(Double.valueOf(5), outputClienteCinco.getLucratividade());
		assertEquals("Nome5", outputClienteCinco.getNome());
		assertEquals("F", outputClienteCinco.getSexo());
		assertEquals("Sobrenome5", outputClienteCinco.getSobrenome());
		assertEquals("Telefone5", outputClienteCinco.getTelefone());
		
		ClienteVO outputClienteNove = outputClienteList.get(9);
		
		assertNotNull(outputClienteNove.getDataNascimento());
		assertEquals("Endereço9", outputClienteNove.getEndereco());
		assertEquals(Long.valueOf(9L), outputClienteNove.getKey());
		assertEquals(Double.valueOf(9), outputClienteNove.getLucratividade());
		assertEquals("Nome9", outputClienteNove.getNome());
		assertEquals("F", outputClienteNove.getSexo());
		assertEquals("Sobrenome9", outputClienteNove.getSobrenome());
		assertEquals("Telefone9", outputClienteNove.getTelefone());
		
		List<CompraEstoqueVO> outputCompraEstoqueList = DozerMapper.parseListObjects(inputObjectCompraEstoque.mockEntityList(), CompraEstoqueVO.class);
		
		CompraEstoqueVO outputCompraEstoqueZero = outputCompraEstoqueList.get(0);
		
		assertEquals(Long.valueOf(0), outputCompraEstoqueZero.getCompras().getKey());
		assertEquals(Long.valueOf(0), outputCompraEstoqueZero.getEstoque().getKey());
		assertEquals(Double.valueOf(0), outputCompraEstoqueZero.getPreco());
		assertEquals(Double.valueOf(0), outputCompraEstoqueZero.getQuantidade());
		
		CompraEstoqueVO outputCompraEstoqueCinco = outputCompraEstoqueList.get(5);
		
		assertEquals(Long.valueOf(5), outputCompraEstoqueCinco.getCompras().getKey());
		assertEquals(Long.valueOf(5), outputCompraEstoqueCinco.getEstoque().getKey());
		assertEquals(Double.valueOf(5), outputCompraEstoqueCinco.getPreco());
		assertEquals(Double.valueOf(5), outputCompraEstoqueCinco.getQuantidade());
		
		CompraEstoqueVO outputCompraEstoqueNove = outputCompraEstoqueList.get(9);
		
		assertEquals(Long.valueOf(9), outputCompraEstoqueNove.getCompras().getKey());
		assertEquals(Long.valueOf(9), outputCompraEstoqueNove.getEstoque().getKey());
		assertEquals(Double.valueOf(9), outputCompraEstoqueNove.getPreco());
		assertEquals(Double.valueOf(9), outputCompraEstoqueNove.getQuantidade());
		
		List<ComprasVO> outputCompraList = DozerMapper.parseListObjects(inputObjectCompras.mockEntityList(), ComprasVO.class);
		
		ComprasVO outputCompraZero = outputCompraList.get(0);
		
		assertEquals(Long.valueOf(0), outputCompraZero.getFornecedor().getKey());
		assertEquals(Long.valueOf(0), outputCompraZero.getKey());
		assertEquals(Double.valueOf(0), outputCompraZero.getValorTotal());
		
		ComprasVO outputCompraCinco = outputCompraList.get(5);
		
		assertEquals(Long.valueOf(5), outputCompraCinco.getFornecedor().getKey());
		assertEquals(Long.valueOf(5), outputCompraCinco.getKey());
		assertEquals(Double.valueOf(5), outputCompraCinco.getValorTotal());
		
		ComprasVO outputCompraNove = outputCompraList.get(9);
		
		assertEquals(Long.valueOf(9), outputCompraNove.getFornecedor().getKey());
		assertEquals(Long.valueOf(9), outputCompraNove.getKey());
		assertEquals(Double.valueOf(9), outputCompraNove.getValorTotal());
		
		List<EstoqueVO> outputEstoqueList = DozerMapper.parseListObjects(inputObjectEstoque.mockEntityList(), EstoqueVO.class);
		
		EstoqueVO outputEstoqueZero = outputEstoqueList.get(0);
		
		assertNotNull(outputEstoqueZero.getDataCompra());
		assertNotNull(outputEstoqueZero.getDataValidade());
		assertEquals("Descricao0", outputEstoqueZero.getDescricao());
		assertEquals(Long.valueOf(0), outputEstoqueZero.getKey());
		assertEquals(Double.valueOf(0), outputEstoqueZero.getQuantidade());
		assertEquals("UN", outputEstoqueZero.getUnidade());
		
		EstoqueVO outputEstoqueCinco = outputEstoqueList.get(5);
		
		assertNotNull(outputEstoqueCinco.getDataCompra());
		assertNotNull(outputEstoqueCinco.getDataValidade());
		assertEquals("Descricao5", outputEstoqueCinco.getDescricao());
		assertEquals(Long.valueOf(5), outputEstoqueCinco.getKey());
		assertEquals(Double.valueOf(5), outputEstoqueCinco.getQuantidade());
		assertEquals("KG", outputEstoqueCinco.getUnidade());
		
		EstoqueVO outputEstoqueNove = outputEstoqueList.get(9);
		
		assertNotNull(outputEstoqueNove.getDataCompra());
		assertNotNull(outputEstoqueNove.getDataValidade());
		assertEquals("Descricao9", outputEstoqueNove.getDescricao());
		assertEquals(Long.valueOf(9), outputEstoqueNove.getKey());
		assertEquals(Double.valueOf(9), outputEstoqueNove.getQuantidade());
		assertEquals("KG", outputEstoqueNove.getUnidade());
		
		List<FornecedorVO> outputFornecedorList = DozerMapper.parseListObjects(inputObjectFornecedor.mockEntityList(), FornecedorVO.class);
		
		FornecedorVO outputFornecedorZero = outputFornecedorList.get(0);
		
		assertEquals("CNPJ0", outputFornecedorZero.getCnpj());
		assertEquals(Long.valueOf(0), outputFornecedorZero.getKey());
		assertEquals("Contato0", outputFornecedorZero.getNomeDoContato());
		assertEquals("Razao0", outputFornecedorZero.getRazaoSocial());
		assertEquals("Telefone0", outputFornecedorZero.getTelefone());
		
		FornecedorVO outputFornecedorCinco = outputFornecedorList.get(5);
		
		assertEquals("CNPJ5", outputFornecedorCinco.getCnpj());
		assertEquals(Long.valueOf(5), outputFornecedorCinco.getKey());
		assertEquals("Contato5", outputFornecedorCinco.getNomeDoContato());
		assertEquals("Razao5", outputFornecedorCinco.getRazaoSocial());
		assertEquals("Telefone5", outputFornecedorCinco.getTelefone());		
		
		FornecedorVO outputFornecedorNove = outputFornecedorList.get(9);
		
		assertEquals("CNPJ9", outputFornecedorNove.getCnpj());
		assertEquals(Long.valueOf(9), outputFornecedorNove.getKey());
		assertEquals("Contato9", outputFornecedorNove.getNomeDoContato());
		assertEquals("Razao9", outputFornecedorNove.getRazaoSocial());
		assertEquals("Telefone9", outputFornecedorNove.getTelefone());
		
		List<VendaEstoqueVO> outputVendaEstoqueList = DozerMapper.parseListObjects(inputObjectVendaEstoque.mockEntityList(), VendaEstoqueVO.class);
		
		VendaEstoqueVO outputVendaEstoqueZero = outputVendaEstoqueList.get(0);
		
		assertEquals(Long.valueOf(0), outputVendaEstoqueZero.getEstoque().getKey());
		assertEquals(Double.valueOf(0), outputVendaEstoqueZero.getPreco());
		assertEquals(Double.valueOf(0), outputVendaEstoqueZero.getQuantidade());
		assertEquals(Long.valueOf(0), outputVendaEstoqueZero.getVendas().getKey());
		
		VendaEstoqueVO outputVendaEstoqueCinco = outputVendaEstoqueList.get(5);
		
		assertEquals(Long.valueOf(5), outputVendaEstoqueCinco.getEstoque().getKey());
		assertEquals(Double.valueOf(5), outputVendaEstoqueCinco.getPreco());
		assertEquals(Double.valueOf(5), outputVendaEstoqueCinco.getQuantidade());
		assertEquals(Long.valueOf(5), outputVendaEstoqueCinco.getVendas().getKey());
		
		VendaEstoqueVO outputVendaEstoqueNove = outputVendaEstoqueList.get(9);
		
		assertEquals(Long.valueOf(9), outputVendaEstoqueNove.getEstoque().getKey());
		assertEquals(Double.valueOf(9), outputVendaEstoqueNove.getPreco());
		assertEquals(Double.valueOf(9), outputVendaEstoqueNove.getQuantidade());
		assertEquals(Long.valueOf(9), outputVendaEstoqueNove.getVendas().getKey());
		
		List<VendasVO> outputVendaList = DozerMapper.parseListObjects(inputObjectVendas.mockEntityList(), VendasVO.class);
		
		VendasVO outputVendaZero = outputVendaList.get(0);
		
		assertEquals(Long.valueOf(0), outputVendaZero.getCliente().getKey());
		assertNotNull(outputVendaZero.getCondicaoPagamento());
		assertNotNull(outputVendaZero.getDataVenda());
		assertEquals(Long.valueOf(0), outputVendaZero.getKey());
		assertEquals(Double.valueOf(0), outputVendaZero.getValorTotal());
		
		VendasVO outputVendaCinco = outputVendaList.get(5);
		
		assertEquals(Long.valueOf(5), outputVendaCinco.getCliente().getKey());
		assertNotNull(outputVendaCinco.getCondicaoPagamento());
		assertNotNull(outputVendaCinco.getDataVenda());
		assertEquals(Long.valueOf(5), outputVendaCinco.getKey());
		assertEquals(Double.valueOf(5), outputVendaCinco.getValorTotal());
		
		VendasVO outputVendaNove = outputVendaList.get(9);
		
		assertEquals(Long.valueOf(9), outputVendaNove.getCliente().getKey());
		assertNotNull(outputVendaNove.getCondicaoPagamento());
		assertNotNull(outputVendaNove.getDataVenda());
		assertEquals(Long.valueOf(9), outputVendaNove.getKey());
		assertEquals(Double.valueOf(9), outputVendaNove.getValorTotal());
		
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
		assertNotNull(outputVenda.getCondicaoPagamento());
		assertNotNull(outputVenda.getDataVenda());
		assertEquals(Long.valueOf(0), outputVenda.getId());
		assertEquals(Double.valueOf(0), outputVenda.getValorTotal());
	}
	
	@Test
	public void parseVOListToEntityTest() {
		
		List<Cliente> outputClienteList = DozerMapper.parseListObjects(inputObjectCliente.mockVOList(), Cliente.class);
		
		Cliente outputClienteZero = outputClienteList.get(0);
		
		assertNotNull(outputClienteZero.getDataNascimento());
		assertEquals("Endereço0", outputClienteZero.getEndereco());
		assertEquals(Long.valueOf(0L), outputClienteZero.getId());
		assertEquals(Double.valueOf(0), outputClienteZero.getLucratividade());
		assertEquals("Nome0", outputClienteZero.getNome());
		assertEquals("M", outputClienteZero.getSexo());
		assertEquals("Sobrenome0", outputClienteZero.getSobrenome());
		assertEquals("Telefone0", outputClienteZero.getTelefone());
		
		Cliente outputClienteCinco = outputClienteList.get(5);
		
		assertNotNull(outputClienteCinco.getDataNascimento());
		assertEquals("Endereço5", outputClienteCinco.getEndereco());
		assertEquals(Long.valueOf(5L), outputClienteCinco.getId());
		assertEquals(Double.valueOf(5), outputClienteCinco.getLucratividade());
		assertEquals("Nome5", outputClienteCinco.getNome());
		assertEquals("F", outputClienteCinco.getSexo());
		assertEquals("Sobrenome5", outputClienteCinco.getSobrenome());
		assertEquals("Telefone5", outputClienteCinco.getTelefone());
		
		Cliente outputClienteNove = outputClienteList.get(9);
		
		assertNotNull(outputClienteNove.getDataNascimento());
		assertEquals("Endereço9", outputClienteNove.getEndereco());
		assertEquals(Long.valueOf(9L), outputClienteNove.getId());
		assertEquals(Double.valueOf(9), outputClienteNove.getLucratividade());
		assertEquals("Nome9", outputClienteNove.getNome());
		assertEquals("F", outputClienteNove.getSexo());
		assertEquals("Sobrenome9", outputClienteNove.getSobrenome());
		assertEquals("Telefone9", outputClienteNove.getTelefone());
		
		List<CompraEstoque> outputCompraEstoqueList = DozerMapper.parseListObjects(inputObjectCompraEstoque.mockVOList(), CompraEstoque.class);
		
		CompraEstoque outputCompraEstoqueZero = outputCompraEstoqueList.get(0);
		
		assertEquals(Long.valueOf(0), outputCompraEstoqueZero.getCompras().getId());
		assertEquals(Long.valueOf(0), outputCompraEstoqueZero.getEstoque().getId());
		assertEquals(Double.valueOf(0), outputCompraEstoqueZero.getPreco());
		assertEquals(Double.valueOf(0), outputCompraEstoqueZero.getQuantidade());
		
		CompraEstoque outputCompraEstoqueCinco = outputCompraEstoqueList.get(5);
		
		assertEquals(Long.valueOf(5), outputCompraEstoqueCinco.getCompras().getId());
		assertEquals(Long.valueOf(5), outputCompraEstoqueCinco.getEstoque().getId());
		assertEquals(Double.valueOf(5), outputCompraEstoqueCinco.getPreco());
		assertEquals(Double.valueOf(5), outputCompraEstoqueCinco.getQuantidade());
		
		CompraEstoque outputCompraEstoqueNove = outputCompraEstoqueList.get(9);
		
		assertEquals(Long.valueOf(9), outputCompraEstoqueNove.getCompras().getId());
		assertEquals(Long.valueOf(9), outputCompraEstoqueNove.getEstoque().getId());
		assertEquals(Double.valueOf(9), outputCompraEstoqueNove.getPreco());
		assertEquals(Double.valueOf(9), outputCompraEstoqueNove.getQuantidade());
		
		List<Compras> outputCompraList = DozerMapper.parseListObjects(inputObjectCompras.mockVOList(), Compras.class);
		
		Compras outputCompraZero = outputCompraList.get(0);
		
		assertEquals(Long.valueOf(0), outputCompraZero.getFornecedor().getId());
		assertEquals(Long.valueOf(0), outputCompraZero.getId());
		assertEquals(Double.valueOf(0), outputCompraZero.getValorTotal());
		
		Compras outputCompraCinco = outputCompraList.get(5);
		
		assertEquals(Long.valueOf(5), outputCompraCinco.getFornecedor().getId());
		assertEquals(Long.valueOf(5), outputCompraCinco.getId());
		assertEquals(Double.valueOf(5), outputCompraCinco.getValorTotal());
		
		Compras outputCompraNove = outputCompraList.get(9);
		
		assertEquals(Long.valueOf(9), outputCompraNove.getFornecedor().getId());
		assertEquals(Long.valueOf(9), outputCompraNove.getId());
		assertEquals(Double.valueOf(9), outputCompraNove.getValorTotal());
		
		List<Estoque> outputEstoqueList = DozerMapper.parseListObjects(inputObjectEstoque.mockVOList(), Estoque.class);
		
		Estoque outputEstoqueZero = outputEstoqueList.get(0);
		
		assertNotNull(outputEstoqueZero.getDataCompra());
		assertNotNull(outputEstoqueZero.getDataValidade());
		assertEquals("Descricao0", outputEstoqueZero.getDescricao());
		assertEquals(Long.valueOf(0), outputEstoqueZero.getId());
		assertEquals(Double.valueOf(0), outputEstoqueZero.getQuantidade());
		assertEquals("UN", outputEstoqueZero.getUnidade());
		
		Estoque outputEstoqueCinco = outputEstoqueList.get(5);
		
		assertNotNull(outputEstoqueCinco.getDataCompra());
		assertNotNull(outputEstoqueCinco.getDataValidade());
		assertEquals("Descricao5", outputEstoqueCinco.getDescricao());
		assertEquals(Long.valueOf(5), outputEstoqueCinco.getId());
		assertEquals(Double.valueOf(5), outputEstoqueCinco.getQuantidade());
		assertEquals("KG", outputEstoqueCinco.getUnidade());
		
		Estoque outputEstoqueNove = outputEstoqueList.get(9);
		
		assertNotNull(outputEstoqueNove.getDataCompra());
		assertNotNull(outputEstoqueNove.getDataValidade());
		assertEquals("Descricao9", outputEstoqueNove.getDescricao());
		assertEquals(Long.valueOf(9), outputEstoqueNove.getId());
		assertEquals(Double.valueOf(9), outputEstoqueNove.getQuantidade());
		assertEquals("KG", outputEstoqueNove.getUnidade());
		
		List<Fornecedor> outputFornecedorList = DozerMapper.parseListObjects(inputObjectFornecedor.mockVOList(), Fornecedor.class);
		
		Fornecedor outputFornecedorZero = outputFornecedorList.get(0);
		
		assertEquals("CNPJ0", outputFornecedorZero.getCnpj());
		assertEquals(Long.valueOf(0), outputFornecedorZero.getId());
		assertEquals("Contato0", outputFornecedorZero.getNomeDoContato());
		assertEquals("Razao0", outputFornecedorZero.getRazaoSocial());
		assertEquals("Telefone0", outputFornecedorZero.getTelefone());
		
		Fornecedor outputFornecedorCinco = outputFornecedorList.get(5);
		
		assertEquals("CNPJ5", outputFornecedorCinco.getCnpj());
		assertEquals(Long.valueOf(5), outputFornecedorCinco.getId());
		assertEquals("Contato5", outputFornecedorCinco.getNomeDoContato());
		assertEquals("Razao5", outputFornecedorCinco.getRazaoSocial());
		assertEquals("Telefone5", outputFornecedorCinco.getTelefone());		
		
		Fornecedor outputFornecedorNove = outputFornecedorList.get(9);
		
		assertEquals("CNPJ9", outputFornecedorNove.getCnpj());
		assertEquals(Long.valueOf(9), outputFornecedorNove.getId());
		assertEquals("Contato9", outputFornecedorNove.getNomeDoContato());
		assertEquals("Razao9", outputFornecedorNove.getRazaoSocial());
		assertEquals("Telefone9", outputFornecedorNove.getTelefone());
		
		List<VendaEstoque> outputVendaEstoqueList = DozerMapper.parseListObjects(inputObjectVendaEstoque.mockVOList(), VendaEstoque.class);
		
		VendaEstoque outputVendaEstoqueZero = outputVendaEstoqueList.get(0);
		
		assertEquals(Long.valueOf(0), outputVendaEstoqueZero.getEstoque().getId());
		assertEquals(Double.valueOf(0), outputVendaEstoqueZero.getPreco());
		assertEquals(Double.valueOf(0), outputVendaEstoqueZero.getQuantidade());
		assertEquals(Long.valueOf(0), outputVendaEstoqueZero.getVendas().getId());
		
		VendaEstoque outputVendaEstoqueCinco = outputVendaEstoqueList.get(5);
		
		assertEquals(Long.valueOf(5), outputVendaEstoqueCinco.getEstoque().getId());
		assertEquals(Double.valueOf(5), outputVendaEstoqueCinco.getPreco());
		assertEquals(Double.valueOf(5), outputVendaEstoqueCinco.getQuantidade());
		assertEquals(Long.valueOf(5), outputVendaEstoqueCinco.getVendas().getId());
		
		VendaEstoque outputVendaEstoqueNove = outputVendaEstoqueList.get(9);
		
		assertEquals(Long.valueOf(9), outputVendaEstoqueNove.getEstoque().getId());
		assertEquals(Double.valueOf(9), outputVendaEstoqueNove.getPreco());
		assertEquals(Double.valueOf(9), outputVendaEstoqueNove.getQuantidade());
		assertEquals(Long.valueOf(9), outputVendaEstoqueNove.getVendas().getId());
		
		List<Vendas> outputVendaList = DozerMapper.parseListObjects(inputObjectVendas.mockVOList(), Vendas.class);
		
		Vendas outputVendaZero = outputVendaList.get(0);
		
		assertEquals(Long.valueOf(0), outputVendaZero.getCliente().getId());
		assertNotNull(outputVendaZero.getCondicaoPagamento());
		assertNotNull(outputVendaZero.getDataVenda());
		assertEquals(Long.valueOf(0), outputVendaZero.getId());
		assertEquals(Double.valueOf(0), outputVendaZero.getValorTotal());
		
		Vendas outputVendaCinco = outputVendaList.get(5);
		
		assertEquals(Long.valueOf(5), outputVendaCinco.getCliente().getId());
		assertNotNull(outputVendaCinco.getCondicaoPagamento());
		assertNotNull(outputVendaCinco.getDataVenda());
		assertEquals(Long.valueOf(5), outputVendaCinco.getId());
		assertEquals(Double.valueOf(5), outputVendaCinco.getValorTotal());
		
		Vendas outputVendaNove = outputVendaList.get(9);
		
		assertEquals(Long.valueOf(9), outputVendaNove.getCliente().getId());
		assertNotNull(outputVendaNove.getCondicaoPagamento());
		assertNotNull(outputVendaNove.getDataVenda());
		assertEquals(Long.valueOf(9), outputVendaNove.getId());
		assertEquals(Double.valueOf(9), outputVendaNove.getValorTotal());
		
	}
	
	/*
	@Test
	public void clientesEntityToVo() {
		ClienteVO outputCliente = DozerMapper.toValueObject(inputObjectCliente.mockEntity(), ClienteVO.class);
		assertNotNull(outputCliente.getDataNascimento());
		assertEquals("Endereço0", outputCliente.getEndereco());
		assertEquals(Long.valueOf(0L), outputCliente.getKey());
		assertEquals(Double.valueOf(0), outputCliente.getLucratividade());
		assertEquals("Nome0", outputCliente.getNome());
		assertEquals("M", outputCliente.getSexo());
		assertEquals("Sobrenome0", outputCliente.getSobrenome());
		assertEquals("Telefone0", outputCliente.getTelefone());
	}*/
}	
