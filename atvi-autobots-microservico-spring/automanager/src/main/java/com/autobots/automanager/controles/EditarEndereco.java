package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
public class EditarEndereco {
	@Autowired
	private EnderecoRepositorio repositorio;
	@PutMapping("/editar-endereco/{id}")
	public void editarEndereco(
			@PathVariable long id,
			@RequestBody Endereco autalizacao) {
		Endereco end = repositorio.getById(autalizacao.getId());
		EnderecoAtualizador atualizador = new EnderecoAtualizador();
		atualizador.atualizar(end, autalizacao);
		repositorio.save(end);
	}
}
