package com.autobots.automanager.modelos;

import java.util.List;

import com.autobots.automanager.entidades.Documento;

import org.springframework.stereotype.Component;

@Component
public class DocumentoSelecionador {
	public Documento selecionar(List<Documento> documentos, long id) {
		Documento selecionado = null;
		for(Documento documento : documentos) {
			if(documento.getId() == id) {
				selecionado = documento;
			}
		}
		return selecionado;
	}
}
