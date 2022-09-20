package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentosRepositorio;

@RestController
public class ExclusaoDocumento {
	@Autowired
	private DocumentosRepositorio repositorio;
	@Autowired ClienteRepositorio clienteRepositorio;
	
	@DeleteMapping("/excluir-doc/{id}")
	public void excluirDoc(
			@RequestBody Documento exclusao, 
			@PathVariable long id) {
		Cliente cliente = clienteRepositorio.getById(id);
		Documento alvo = repositorio.getById(exclusao.getId());
		cliente.getDocumentos().remove(alvo);
		repositorio.delete(alvo);
	}
}
