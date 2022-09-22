package com.autobots.automanager.controles;

import java.util.List;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.DocumentoAtualizador;
import com.autobots.automanager.modelos.DocumentoSelecionador;
import com.autobots.automanager.modelos.TelefoneAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentosRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentoControle {

	@Autowired
	private DocumentosRepositorio repositorio;
	@Autowired
	private DocumentoSelecionador selecionador;
	@Autowired
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> obterDocumentos(){
		List<Documento> docs = repositorio.findAll();
		if(docs.isEmpty()) {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		}else {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(docs, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/documentos/{docId}")
	public ResponseEntity<Documento> obterDocumento(@PathVariable Long docId){
		List<Documento> documentos = repositorio.findAll();
		Documento documento = selecionador.selecionar(documentos, docId);
		if(documento == null) {
			ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		}else {
			ResponseEntity<Documento> resposta = new ResponseEntity<Documento>(documento, HttpStatus.FOUND);
			return resposta;
		}
	}
	@PutMapping("/documentos/cadastro/{clienteId}")
	public ResponseEntity<?> cadastroTelefone(
			@PathVariable Long clienteId, 
			@RequestBody Cliente atualizacao){
		HttpStatus status = HttpStatus.CONFLICT;
		Cliente cliente = repositorioCliente.getById(clienteId);
		if(cliente != null) {
			cliente.getDocumentos().addAll(atualizacao.getDocumentos());
			repositorioCliente.save(cliente);
			status = HttpStatus.OK;
		}
		else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	@PutMapping("/documentos/editar/{docId}")
	public ResponseEntity<?> editarTelefone(
			@PathVariable Long docId,
			@RequestBody Documento atualizacao){
		HttpStatus status = HttpStatus.CONFLICT;
		Documento documento = repositorio.getById(docId);
		if(documento != null) {
			DocumentoAtualizador atualizador = new DocumentoAtualizador();
			atualizador.atualizar(documento, atualizacao);
			repositorio.save(documento);
			status = HttpStatus.OK;
		}else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	@DeleteMapping("/documentos/excluir/{clienteId}/{docId}")
	public ResponseEntity<?> excluirTelefone(
			@PathVariable Long docId, 
			@PathVariable Long clienteId){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Documento documento = repositorio.getById(docId);
		Cliente cliente = repositorioCliente.getById(clienteId);
		if (documento != null) {
			cliente.getDocumentos().remove(documento);
			repositorio.delete(documento);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
