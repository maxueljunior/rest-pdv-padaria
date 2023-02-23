package br.com.leuxam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.leuxam.model.CompraEstoque;
import br.com.leuxam.services.CompraEstoqueService;

@RestController
@RequestMapping("/compra-de-produtos")
public class CompraEstoqueController {
	
	@Autowired
	private CompraEstoqueService service;
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompraEstoque findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CompraEstoque> findAll(){
		return service.findAll();
	}
	
	@GetMapping(value = "/relatorio", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CompraEstoque> findAllWithProdutoOrCompra(
			@RequestParam(value = "id", defaultValue = "1") Long id,
			@RequestParam(value = "table", defaultValue = "produto") String table){
		if("produto".equalsIgnoreCase(table)) {
			return service.findAllWithProduto(id);
		}else {
			return service.findAllWithCompra(id);
		}
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CompraEstoque create(@RequestBody CompraEstoque compraEstoque) {
		return service.create(compraEstoque);
	}
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CompraEstoque update(@RequestBody CompraEstoque compraEstoque) {
		return service.update(compraEstoque);
	}
	
	@DeleteMapping()
	public ResponseEntity<?> delete(
			@RequestParam(value = "product", defaultValue = "0") Long idProduto,
			@RequestParam(value = "purchase", defaultValue = "0") Long idCompra){
		service.delete(idProduto, idCompra);
		return ResponseEntity.noContent().build();
	}
}
