package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.data.vo.v1.ClienteVO;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.Cliente;
import br.com.leuxam.repositories.ClienteRepository;

@Service
public class ClienteServices {
	
	@Autowired
	ClienteRepository repository;
	
	public List<ClienteVO> findAll(){
		return DozerMapper.parseListObjects(repository.findAll(), ClienteVO.class);
	}
	
	public ClienteVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não tem nenhum cliente com esse ID"));
		return DozerMapper.parseObject(entity, ClienteVO.class);
	}
	
	public ClienteVO create(ClienteVO cliente) {
		var entity = DozerMapper.parseObject(cliente, Cliente.class);
		var vo = DozerMapper.parseObject(repository.save(entity), ClienteVO.class);
		return vo;
	}
	
	public ClienteVO update(ClienteVO cliente) {
		var entity = repository.findById(cliente.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("Não tem nenhum cliente com esse ID"));
		entity.setDataNascimento(cliente.getDataNascimento());
		entity.setEndereco(cliente.getEndereco());
		entity.setLucratividade(cliente.getLucratividade());
		entity.setNome(cliente.getNome());
		entity.setSexo(cliente.getSexo());
		entity.setSobrenome(cliente.getSobrenome());
		entity.setTelefone(cliente.getTelefone());

		var vo = DozerMapper.parseObject(repository.save(entity), ClienteVO.class);
		return vo;
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não tem nenhum cliente com esse ID"));
		repository.delete(entity);
	}
}
