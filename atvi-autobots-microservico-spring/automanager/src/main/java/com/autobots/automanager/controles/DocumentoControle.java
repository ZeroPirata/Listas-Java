package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.DocumentosRepositorio;

import java.util.List;

@RestController
public class DocumentoControle {
	@Autowired
	private DocumentosRepositorio repositorio;
	
	@GetMapping("/documentos")
	public List<Documento> docs(){
		return repositorio.findAll();
	}
}
