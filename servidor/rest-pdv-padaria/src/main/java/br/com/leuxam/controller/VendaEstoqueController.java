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

import br.com.leuxam.data.vo.v1.VendaEstoqueVO;
import br.com.leuxam.services.VendasEstoqueService;
import br.com.leuxam.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/venda-de-produtos")
@Tag(name = "Vendas Produtos", description = "End point para controlar toda a venda de produtos")
public class VendaEstoqueController {
	
	@Autowired
	private VendasEstoqueService service;
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Buscar todos os produtos relacionados a uma venda e toda venda relacionado a um produto", description = "Buscar todos os produtos relacionados a uma venda e toda venda relacionado a um produto",
		tags = {"Venda Produtos"},
		responses = {
				@ApiResponse(description = "Succeso", responseCode = "200",
						content = @Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = VendaEstoqueVO.class))
								)),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public List<VendaEstoqueVO> findAll(){
		return service.findAll();
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Criar um produto dentro de uma Venda", description = "Criar um produto dentro de uma Venda com JSON, XML e YAML",
		tags = {"Venda Produtos"},
		responses = {
				@ApiResponse(description = "Succeso", responseCode = "200",
						content = @Content(schema = @Schema(implementation = VendaEstoqueVO.class))
				),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public VendaEstoqueVO create(@RequestBody VendaEstoqueVO vendaEstoque) {
		return service.create(vendaEstoque);
	}
	
	@PutMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Atualizar um produto dentro de uma Venda", description = "Atualizar um produto dentro de uma Venda com JSON, XML e YAML",
		tags = {"Venda Produtos"},
		responses = {
				@ApiResponse(description = "Atualizado", responseCode = "200",
						content = @Content(schema = @Schema(implementation = VendaEstoqueVO.class))
				),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public VendaEstoqueVO update(@RequestBody VendaEstoqueVO vendaEstoque) {
		return service.updateByIdProductAndVendas(vendaEstoque);
	}
	
	@DeleteMapping()
	@Operation(summary = "Deletar um produto dentro de uma Venda", description = "Deletar um produto dentro de uma Venda com JSON, XML e YAML",
		tags = {"Venda Produtos"},
		responses = {
				@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public ResponseEntity<?> delete(
			@RequestParam(value = "product", defaultValue = "0") Long idProduto,
			@RequestParam(value = "order", defaultValue = "0") Long idVendas){
		service.delete(idProduto, idVendas);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/relatorio", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Buscar todos os produtos relacionados a uma venda ou toda venda relacionado a um produto", description = "Buscar todos os produtos relacionados a uma venda ou toda venda relacionado a um produto",
		tags = {"Venda Produtos"},
		responses = {
				@ApiResponse(description = "Succeso", responseCode = "200",
						content = @Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = VendaEstoqueVO.class))
								)),
				@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public List<VendaEstoqueVO> findAllWithProdutoOrVendas(
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
	@PatchMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseEntity<?> updateProductsWithVendas(
			@RequestParam(value = "productAnt", defaultValue = "0") Long idProdutoAntigo,
			@RequestParam(value = "order", defaultValue = "0") Long idVendas,
			@RequestParam(value = "productNew", defaultValue = "0") Long idProdutoNovo){
		service.updateProdutoWithVendas(idProdutoAntigo, idVendas, idProdutoNovo);
		return ResponseEntity.ok().build();
	}*/
}
