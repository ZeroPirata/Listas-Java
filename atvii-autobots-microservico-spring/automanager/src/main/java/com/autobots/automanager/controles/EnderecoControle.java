package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.EnderecoAtualizador;
import com.autobots.automanager.modelos.EnderecoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
public class EnderecoControle {
	
	@Autowired
	private EnderecoRepositorio repositorio;
	@Autowired
	private EnderecoSelecionador selecionador;
	@Autowired
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/enderecos")
	public ResponseEntity<List<Endereco>> obterEnderecos(){
		List<Endereco> ends = repositorio.findAll();
		if(ends.isEmpty()) {
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		}else {
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(ends, HttpStatus.FOUND);
			return resposta;
		}
	}
	@GetMapping("/enderecos/{id}")
	public ResponseEntity<Endereco> obterDocumento(@PathVariable Long id){
		List<Endereco> enderecos = repositorio.findAll();
		Endereco end = selecionador.selecionar(enderecos, id);
		if(end == null) {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		}else {
			ResponseEntity<Endereco> resposta = new ResponseEntity<Endereco>(end, HttpStatus.FOUND);
			return resposta;
		}
	}
	@PostMapping("/enderecos/cadastro/{clienteId}")
	public ResponseEntity<?> cadastroTelefone(
			@PathVariable Long clienteId, 
			@RequestBody Endereco atualizacao){
		HttpStatus status = HttpStatus.CONFLICT;
		Cliente cliente = repositorioCliente.getById(clienteId);
		if(cliente != null) {
			cliente.setEndereco(atualizacao);
			repositorio.save(atualizacao);
			status = HttpStatus.OK;
		}
		else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PutMapping("/enderecos/editar/{id}")
	public ResponseEntity<?> editarTelefone(
			@PathVariable Long id,
			@RequestBody Endereco atualizacao){
		HttpStatus status = HttpStatus.CONFLICT;
		Endereco ends = repositorio.getById(id);
		if(ends != null) {
			EnderecoAtualizador atualizador = new EnderecoAtualizador();
			atualizador.atualizar(ends, atualizacao);
			repositorio.save(ends);
			status = HttpStatus.OK;
		}else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
}
