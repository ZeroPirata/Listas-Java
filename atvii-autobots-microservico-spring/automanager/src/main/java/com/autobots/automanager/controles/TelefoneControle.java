package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.TelefoneAtualizador;
import com.autobots.automanager.modelos.TelefoneSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
public class TelefoneControle {

	@Autowired
	private TelefoneRepositorio repositorio;
	@Autowired
	private ClienteRepositorio repositorioCliente;
	@Autowired
	private TelefoneSelecionador selecionador;
	
	@GetMapping("/telefones")
	public ResponseEntity<List<Telefone>> obterTelefones(){
		List<Telefone> telefones = repositorio.findAll();
		if(telefones.isEmpty()) {
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		}else {
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/telefones/{tellId}")
	public ResponseEntity<Telefone> obterTelefone(@PathVariable Long tellId){
		List<Telefone> telefones = repositorio.findAll();
		Telefone telefone = selecionador.selecionar(telefones, tellId);
		if(telefone == null) {
			ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		}else {
			ResponseEntity<Telefone> resposta = new ResponseEntity<Telefone>(telefone, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PutMapping("/telefones/cadastro/{clienteId}")
	public ResponseEntity<?> cadastroTelefone(
			@PathVariable Long clienteId, 
			@RequestBody Cliente atualizacao){
		HttpStatus status = HttpStatus.CONFLICT;
		Cliente cliente = repositorioCliente.getById(clienteId);
		if(cliente != null) {
			cliente.getTelefones().addAll(atualizacao.getTelefones());
			repositorioCliente.save(cliente);
			status = HttpStatus.OK;
		}
		else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	@PutMapping("/telefones/editar/{tellId}")
	public ResponseEntity<?> editarTelefone(
			@PathVariable Long tellId,
			@RequestBody Telefone atualizacao){
		HttpStatus status = HttpStatus.CONFLICT;
		Telefone telefone = repositorio.getById(tellId);
		if(telefone != null) {
			TelefoneAtualizador atualizador = new TelefoneAtualizador();
			atualizador.atualizar(telefone, atualizacao);
			repositorio.save(telefone);
			status = HttpStatus.OK;
		}else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	@DeleteMapping("/telefones/excluir/{clienteId}/{tellId}")
	public ResponseEntity<?> excluirTelefone(
			@PathVariable Long tellId, 
			@PathVariable Long clienteId){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Telefone telefone = repositorio.getById(tellId);
		Cliente cliente = repositorioCliente.getById(clienteId);
		if (telefone != null) {
			cliente.getTelefones().remove(telefone);
			repositorio.delete(telefone);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}

	
	
	
}
