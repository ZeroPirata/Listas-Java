package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
public class AdicionarEndereco {
	
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private EnderecoRepositorio repositorioEnd;
	
	@PostMapping("/adicionar-endereco/{id}")
	public void addEndereco(@RequestBody Endereco endereco, @PathVariable long id) {
		Cliente alvo = repositorio.getById(id);
		alvo.setEndereco(endereco);
		repositorioEnd.save(endereco);
	}
	
}
