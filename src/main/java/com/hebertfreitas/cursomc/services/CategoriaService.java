package com.hebertfreitas.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hebertfreitas.cursomc.domain.Categoria;
import com.hebertfreitas.cursomc.dto.CategoriaDTO;
import com.hebertfreitas.cursomc.repositories.CategoriaRepository;
import com.hebertfreitas.cursomc.services.exception.DataIntegrityException;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository repo;
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new com.hebertfreitas.cursomc.services.exception.ObjectNotFoundException("Objeto não Encontrado! Id: " + id + "Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	 public Categoria update(Categoria obj) {
		 buscar(obj.getId());
		 return repo.save(obj);
	 }
	 public void delete(Integer id) {
		 try {
			 repo.deleteById(id);
		 }
		 catch(DataIntegrityViolationException e) {
			 throw new DataIntegrityException("Não e Possivel Excluir uma categoria que Possui Produtos");
		 }
	 }
	 public List<Categoria> findAll(){
		 return repo.findAll();
	 }
	 
	 public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		 PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		 return repo.findAll(pageRequest);
	 }
	 
	 public Categoria fromDTO(CategoriaDTO objDTO) {
		 return new Categoria(objDTO.getId(), objDTO.getNome());
	 }
}
