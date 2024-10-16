package com.hebertfreitas.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hebertfreitas.cursomc.domain.Cidade;
import com.hebertfreitas.cursomc.domain.Cliente;
import com.hebertfreitas.cursomc.domain.Endereco;
import com.hebertfreitas.cursomc.domain.enums.TipoCliente;
import com.hebertfreitas.cursomc.dto.ClienteDTO;
import com.hebertfreitas.cursomc.dto.ClienteNewDTO;
import com.hebertfreitas.cursomc.repositories.ClienteRepository;
import com.hebertfreitas.cursomc.repositories.EnderecoRepository;
import com.hebertfreitas.cursomc.services.exception.DataIntegrityException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new com.hebertfreitas.cursomc.services.exception.ObjectNotFoundException("Objeto não Encontrado! Id: " + id + "Tipo: " + Cliente.class.getName()));
	}
	public Cliente update(Cliente obj) {
		 Cliente newObj = buscar(obj.getId());
		 updateData(newObj, obj);
		 return repo.save(newObj);
	 }
	 public void delete(Integer id) {
		 try {
			 repo.deleteById(id);
		 }
		 catch(DataIntegrityViolationException e) {
			 throw new DataIntegrityException("Não e Possivel Excluir um Cliente que possui pedidos");
		 }
	 }
	 public List<Cliente> findAll(){
		 return repo.findAll();
	 }
	 
	 public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		 PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		 return repo.findAll(pageRequest);
	 }
	 
	 public Cliente fromDTO(@Valid ClienteDTO objDTO) {
		 return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	 }
	 
	 public Cliente fromDTO(ClienteNewDTO objDTO) {
		 Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		 Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		 Endereco end = new Endereco(null, objDTO.getLogadouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		 cli.getEnderecos().add(end);
		 cli.getTelefones().add(objDTO.getTelefone1());
		 if (objDTO.getTelefone2() != null) {
			 cli.getTelefones().add(objDTO.getTelefone2());
		 }
		 if (objDTO.getTelefone3() != null) {
			 cli.getTelefones().add(objDTO.getTelefone3());
		 }
		 return cli;
	 }
	 
	 
	 public void updateData(Cliente newObj, Cliente obj) {
		 newObj.setNome(obj.getNome());
		 newObj.setEmail(obj.getEmail());
	 }
	 
	 @Transactional
	 public Cliente insert(Cliente obj) {
			obj.setId(null);
			repo.save(obj);
			enderecoRepository.saveAll(obj.getEnderecos());
			return obj;
		}
	 
	 
}
