package com.baraldi.cobranca.repository;

import java.util.List;

//Importando o JPA
import org.springframework.data.jpa.repository.JpaRepository;

import com.baraldi.cobranca.model.Titulo;

/* Como parâmetro para a JpaRepository, enviamos nossa classe Titulo 
e o tipo de dado do nosso Id de Titulo, que é Long na classe Titulo: 
			@Id
			private Long codigo;
*/
public interface Titulos extends JpaRepository<Titulo, Long> {
	

	// Usando a Inteligência do Spring Data JPA para pesquisar por Descrição
	// Esse método será criado automaticamente e vai buscar titulo por Descricao
	public List<Titulo> findByDescricaoContaining(String descricao);
	
}
