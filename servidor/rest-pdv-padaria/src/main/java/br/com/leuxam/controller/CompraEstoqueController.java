package br.com.leuxam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.leuxam.data.vo.v1.CompraEstoqueVO;
import br.com.leuxam.services.CompraEstoqueService;
import br.com.leuxam.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/compra-de-produtos")
@Tag(name = "Compras Estoque", description = "End point para controlar todos os produtos vinculado as Compras")
public class CompraEstoqueController {
	
	@Autowired
	private CompraEstoqueService service;
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Procura todas as compras e produtos", description = "Procura todas as compras e produtos",
		tags = {"Compras Estoque"},
		responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200",
				content = {
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = CompraEstoqueVO.class))
					)
				}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public List<CompraEstoqueVO> findAll(){
		return service.findAll();
	}
	
	@GetMapping(value = "/relatorio", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Procura todas as compras vinculados a um produto e todos os produtos vinculados a uma compra", description = "Procura todas as compras vinculados a um produto e todos os produtos vinculados a uma compra",
		tags = {"Compras Estoque"},
		responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200",
				content = {
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = CompraEstoqueVO.class))
					)
				}),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
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
	@Operation(summary = "Salva um produto em uma compra", description = "Salva um produto em uma compra",
		tags = {"Compras Estoque"},
		responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200",
				content = @Content(schema = @Schema(implementation = CompraEstoqueVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public CompraEstoqueVO create(@RequestBody CompraEstoqueVO compraEstoque) {
		return service.create(compraEstoque);
	}
	
	@PutMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Atualizar um produto em uma compra", description = "Atualizar um produto em uma compra",
		tags = {"Compras Estoque"},
		responses = {
			@ApiResponse(description = "Atualizado", responseCode = "200",
				content = @Content(schema = @Schema(implementation = CompraEstoqueVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public CompraEstoqueVO update(@RequestBody CompraEstoqueVO compraEstoque) {
		return service.update(compraEstoque);
	}
	
	@DeleteMapping()
	@Operation(summary = "Atualizar um produto em uma compra", description = "Atualizar um produto em uma compra",
		tags = {"Compras Estoque"},
		responses = {
			@ApiResponse(description = "No Content", responseCode = "204",content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public ResponseEntity<?> delete(
			@RequestParam(value = "product", defaultValue = "0") Long idProduto,
			@RequestParam(value = "purchase", defaultValue = "0") Long idCompra){
		service.delete(idProduto, idCompra);
		return ResponseEntity.noContent().build();
	}
}
