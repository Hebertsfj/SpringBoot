package com.hebertfreitas.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.hebertfreitas.cursomc.domain.Categoria;
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
}
