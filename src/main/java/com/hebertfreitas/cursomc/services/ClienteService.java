package com.hebertfreitas.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hebertfreitas.cursomc.domain.Cliente;
import com.hebertfreitas.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repo;
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new com.hebertfreitas.cursomc.services.exception.ObjectNotFoundException("Objeto não Encontrado! Id: " + id + "Tipo: " + Cliente.class.getName()));
	}
}
