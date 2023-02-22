package br.com.leuxam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.leuxam.model.VendaEstoque;
import br.com.leuxam.services.VendasEstoqueService;

@RestController
@RequestMapping("/venda-de-produtos")
public class VendaEstoqueController {
	
	@Autowired
	private VendasEstoqueService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaEstoque> findAll(){
		return service.findAll();
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public VendaEstoque create(@RequestBody VendaEstoque vendaEstoque) {
		return service.create(vendaEstoque);
	}
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public VendaEstoque update(@RequestBody VendaEstoque vendaEstoque) {
		return service.updateByIdProductAndVendas(vendaEstoque);
	}
	
	@DeleteMapping()
	public ResponseEntity<?> delete(
			@RequestParam(value = "product", defaultValue = "0") Long idProduto,
			@RequestParam(value = "order", defaultValue = "0") Long idVendas){
		service.delete(idProduto, idVendas);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/relatorio", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaEstoque> findAllWithProducts(
			@RequestParam(value = "id", defaultValue = "1") Long id,
			@RequestParam(value = "table", defaultValue = "produto") String table){
		
		if("produto".equalsIgnoreCase(table)) {
			return service.findAllWithProdutcs(id);
		}else {
			return service.findAllWithVendas(id);
		}
	}
	
	/*
	 * Essa função está comentada pois não vejo necessidade nela ainda
	 * 
	 * 
	@PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateProductsWithVendas(
			@RequestParam(value = "productAnt", defaultValue = "0") Long idProdutoAntigo,
			@RequestParam(value = "order", defaultValue = "0") Long idVendas,
			@RequestParam(value = "productNew", defaultValue = "0") Long idProdutoNovo){
		service.updateProdutoWithVendas(idProdutoAntigo, idVendas, idProdutoNovo);
		return ResponseEntity.ok().build();
	}*/
}
