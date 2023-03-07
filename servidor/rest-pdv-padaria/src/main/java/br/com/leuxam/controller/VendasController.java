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

import br.com.leuxam.data.vo.v1.VendasVO;
import br.com.leuxam.services.VendasService;
import br.com.leuxam.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/vendas")
@Tag(name = "Vendas", description = "End point para controlar toda Venda")
public class VendasController {

	@Autowired
	private VendasService service;
	
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Buscar uma Venda", description = "Buscar uma Venda", tags = {"Vendas"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", content = 
					@Content(schema = @Schema(implementation = VendasVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public VendasVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Buscar todas Vendas", description = "Buscar todas Vendas", tags = {"Vendas"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", content = {
					@Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = VendasVO.class))
					)
			}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public List<VendasVO> findAll(){
		return service.findAll();
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Salvar uma Venda", description = "Salvar uma Venda", tags = {"Vendas"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", content = 
					@Content(schema = @Schema(implementation = VendasVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public VendasVO create(@RequestBody VendasVO venda) {
		return service.create(venda);
	}
	
	@PutMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Atualizar uma Venda", description = "Atualizar uma Venda", tags = {"Vendas"}, responses = {
			@ApiResponse(description = "Atualizado", responseCode = "200", content = 
					@Content(schema = @Schema(implementation = VendasVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public VendasVO update(@RequestBody VendasVO venda) {
		return service.update(venda);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletar uma Venda", description = "Deletar uma Venda", tags = {"Vendas"}, responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<?> delete(@PathVariable(value="id") Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
