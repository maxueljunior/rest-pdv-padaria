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
import org.springframework.web.bind.annotation.RestController;

import br.com.leuxam.data.vo.v1.FornecedorVO;
import br.com.leuxam.services.FornecedorService;
import br.com.leuxam.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/fornecedor")
@Tag(name = "Fornecedor", description = "End point para controlar todos os Fornecedores")
public class FornecedorController {
	
	@Autowired
	private FornecedorService service;
	
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Buscar um Fornecedor", description = "Buscar um Fornecedor", tags = {"Fornecedores"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200",
					content = @Content(schema = @Schema(implementation = FornecedorVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public FornecedorVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Buscar todos Fornecedores", description = "Buscar todos Fornecedores", tags = {"Fornecedores"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200",
					content = {
						@Content(
								mediaType = "application/json",
								array = @ArraySchema(schema = @Schema(implementation = FornecedorVO.class))
						)
					}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public List<FornecedorVO> findAll(){
		return service.findAll();
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Salvar um Fornecedor", description = "Salvar um Fornecedor com JSON, XML ou YAML", tags = {"Fornecedores"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200",
					content = @Content(schema = @Schema(implementation = FornecedorVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public FornecedorVO create(@RequestBody FornecedorVO fornecedor) {
		return service.create(fornecedor);
	}
	
	@PutMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Atualizar um Fornecedor", description = "Atualizar um Fornecedor com JSON, XML ou YAML", tags = {"Fornecedores"}, responses = {
			@ApiResponse(description = "Atualizado", responseCode = "200",
					content = @Content(schema = @Schema(implementation = FornecedorVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public FornecedorVO update(@RequestBody FornecedorVO fornecedor) {
		return service.update(fornecedor);
	}
	
	@DeleteMapping(value = "{id}")
	@Operation(summary = "Deletar um Fornecedor", description = "Deletar um Fornecedor", tags = {"Fornecedores"}, responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
