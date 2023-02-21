package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.model.Estoque;
import br.com.leuxam.model.VendaEstoque;
import br.com.leuxam.repositories.EstoqueRepository;
import br.com.leuxam.repositories.VendasEstoqueRepository;

@Service
public class VendasEstoqueService {
	
	@Autowired
	VendasEstoqueRepository repository;
	
	@Autowired
	EstoqueRepository estoqueRepository;
	
	public List<VendaEstoque> findAll(){
		return repository.findAll();
	}
	
	public VendaEstoque create(VendaEstoque vendaEstoque) {
		return repository.save(vendaEstoque);
	}
	
	public VendaEstoque update(VendaEstoque vendaEstoque, Long id) {
		try {
			var entity = repository.findByProduct(vendaEstoque.getEstoque().getId(), vendaEstoque.getVendas().getId());
			Estoque newEntity = estoqueRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Não existe esse codigo de produto"));
			entity.setEstoque(newEntity);
			entity.setPreco(vendaEstoque.getPreco());
			entity.setQuantidade(vendaEstoque.getQuantidade());
			entity.setVendas(vendaEstoque.getVendas());
			return repository.updateVendaEstoque(vendaEstoque.getEstoque().getId(), vendaEstoque.getVendas().getId(), id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Não existe venda ou produto com esse ID");
		}
		
	}
}
