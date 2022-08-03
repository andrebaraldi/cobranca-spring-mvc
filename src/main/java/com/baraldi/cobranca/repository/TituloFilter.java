package com.baraldi.cobranca.repository;

// Classe para pesquisa de títulos
// Usamos para manter a descrição na tela ao pesquisarmos na tela
public class TituloFilter {

	private String descricao;
	
	public String getDescricao() {
		return this.descricao;
	}
		
	public void setDescricao(String descricao) {
		this.descricao = descricao; 
	}	
}
