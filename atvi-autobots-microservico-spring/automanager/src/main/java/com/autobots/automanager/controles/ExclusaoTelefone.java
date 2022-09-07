package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
public class ExclusaoTelefone {
	@Autowired
	private TelefoneRepositorio repositorioTelefone;
	@Autowired
	private ClienteRepositorio repositorioCliente;
	
	@DeleteMapping("/excluir-tell/{id}")
	public void excluirTell(
			@RequestBody Telefone exclusao, 
			@PathVariable long id){
	Telefone alvo = repositorioTelefone.getById(exclusao.getId());
	Cliente cliente = repositorioCliente.getById(id);
	cliente.getTelefones().remove(alvo);
	repositorioTelefone.delete(alvo);
	}
}
