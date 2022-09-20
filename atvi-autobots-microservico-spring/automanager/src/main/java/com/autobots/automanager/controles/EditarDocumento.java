package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.repositorios.DocumentosRepositorio;

@RestController
public class EditarDocumento {
	@Autowired
	private DocumentosRepositorio repositorio;
	
	@PutMapping("/editar-documento/{id}")
	public void editarDocumento(
			@PathVariable long id,
			@RequestBody Documento autalizacao
			){
		Documento doc = repositorio.getById(autalizacao.getId());
		DocumentoAtualizador atualizador = new DocumentoAtualizador();
		atualizador.atualizar(doc, autalizacao);
		repositorio.save(doc);
	}
}
