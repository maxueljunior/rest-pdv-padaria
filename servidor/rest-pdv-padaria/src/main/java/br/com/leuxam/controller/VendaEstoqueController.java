package br.com.leuxam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public VendaEstoque update(@RequestBody VendaEstoque vendaEstoque, @RequestBody Long id){
		return service.update(vendaEstoque, id);
	}
	
}
