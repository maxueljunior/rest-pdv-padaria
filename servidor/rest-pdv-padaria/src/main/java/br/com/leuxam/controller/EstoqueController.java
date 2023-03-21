package br.com.leuxam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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

import br.com.leuxam.data.vo.v1.EstoqueVO;
import br.com.leuxam.services.EstoqueService;
import br.com.leuxam.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/produto")
@Tag(name = "Estoque", description = "End point para controlar todos produtos no Estoque")
public class EstoqueController {
	
	@Autowired
	private EstoqueService service;
	
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Procura um Produtos do Estoque", description = "Procura um Produtos do Estoque", tags = {"Estoque"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200",
					content = @Content(schema = @Schema(implementation = EstoqueVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public EstoqueVO findById(@PathVariable(value = "id") Long id){
		return service.findById(id);
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Procura todos os Produtos do Estoque", description = "Procura todos os Produtos do Estoque", tags = {"Estoque"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200",
					content = @Content(
								mediaType = "application/json",
								array = @ArraySchema(schema = @Schema(implementation = EstoqueVO.class))
							)),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public ResponseEntity<PagedModel<EntityModel<EstoqueVO>>> findAll(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "12") Integer size,
			@RequestParam(name = "direction", defaultValue  = "asc") String direction
			){
		
		var directionSort = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(directionSort, "descricao"));
		
		return ResponseEntity.ok(service.findAll(pageable));
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Salvar um Produtos no Estoque", description = "Salvar um Produtos no Estoque com JSON, XML ou YAML", tags = {"Estoque"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200",
					content = @Content(schema = @Schema(implementation = EstoqueVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public EstoqueVO create(@RequestBody EstoqueVO estoque) {
		return service.create(estoque);
	}
	
	@PutMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Atualizar um Produtos no Estoque", description = "Atualizar um Produtos no Estoque com JSON, XML ou YAML", tags = {"Estoque"}, responses = {
			@ApiResponse(description = "Atualizado", responseCode = "200",
					content = @Content(schema = @Schema(implementation = EstoqueVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public EstoqueVO update(@RequestBody EstoqueVO estoque) {
		return service.update(estoque);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletar um Produtos no Estoque", description = "Deletar um Produtos no Estoque", tags = {"Estoque"}, responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
