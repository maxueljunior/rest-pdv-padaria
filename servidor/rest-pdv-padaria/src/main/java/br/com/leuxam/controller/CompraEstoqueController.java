package br.com.leuxam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.leuxam.data.vo.v1.CompraEstoqueVO;
import br.com.leuxam.services.CompraEstoqueService;
import br.com.leuxam.util.MediaType;

@RestController
@RequestMapping("/compra-de-produtos")
public class CompraEstoqueController {
	
	@Autowired
	private CompraEstoqueService service;
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public List<CompraEstoqueVO> findAll(){
		return service.findAll();
	}
	
	@GetMapping(value = "/relatorio", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public List<CompraEstoqueVO> findAllWithProdutoOrCompra(
			@RequestParam(value = "id", defaultValue = "1") Long id,
			@RequestParam(value = "table", defaultValue = "produto") String table){
		if("produto".equalsIgnoreCase(table)) {
			return service.findAllWithProduto(id);
		}else {
			return service.findAllWithCompra(id);
		}
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public CompraEstoqueVO create(@RequestBody CompraEstoqueVO compraEstoque) {
		return service.create(compraEstoque);
	}
	
	@PutMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public CompraEstoqueVO update(@RequestBody CompraEstoqueVO compraEstoque) {
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
