package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
public class CadastroTelefone {

	@Autowired
	private ClienteRepositorio repositorio;
	
	@PutMapping("/atualizar-telefone")
	public void cadastrarTelefone(@RequestBody Cliente atualizacao) {
		Cliente alvo = repositorio.getById(atualizacao.getId());
		alvo.getTelefones().addAll(atualizacao.getTelefones());
		repositorio.save(alvo);
	}
}
