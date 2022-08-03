package com.baraldi.cobranca.model;

public enum StatusTitulo {

	// Constantes
	PENDENTE("Pendente"),
	RECEBIDO("Recebido"),
	CANCELADO("Cancelado") 
	;	
	
	// Atributo do nosso enum
	private String descricao;
		
	// Construtor do nosso ENUM
	StatusTitulo(String descricao){
		this.descricao = descricao;
	}
	
	// Get/Set
	public String getDescricao() {
		return descricao;
	}
	
	
}
