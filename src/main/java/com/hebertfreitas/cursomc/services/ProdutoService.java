package com.hebertfreitas.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hebertfreitas.cursomc.domain.Categoria;
import com.hebertfreitas.cursomc.domain.Produto;
import com.hebertfreitas.cursomc.repositories.CategoriaRepository;
import com.hebertfreitas.cursomc.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository repo;
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto buscar(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new com.hebertfreitas.cursomc.services.exception.ObjectNotFoundException(
				"Objeto não Encontrado! Id: " + id + "Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		 PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		 List<Categoria> categorias = categoriaRepository.findAllById(ids);
		 return repo.search(nome, categorias, pageRequest);

	}
}
