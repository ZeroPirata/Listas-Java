package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.autobots.automanager.entidades.Cliente;

import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
public class CadastradorDocumento {
	@Autowired
	private ClienteRepositorio repositorio;
	
	@PutMapping("/atualizar-documento")
	public void cadastrarDocumento(@RequestBody Cliente atualizacao) {
		Cliente alvo = repositorio.getById(atualizacao.getId());
		alvo.getDocumentos().addAll(atualizacao.getDocumentos());
		repositorio.save(alvo);
	}
}
