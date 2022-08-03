package com.baraldi.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.baraldi.cobranca.model.StatusTitulo;
// Modelo e respositório
import com.baraldi.cobranca.model.Titulo;
import com.baraldi.cobranca.repository.TituloFilter;
import com.baraldi.cobranca.repository.Titulos;


// Indica que nossa classe é um serviço e possui regras de
// negócio do projeto
// Isso transforma nossa classe em um componente do Spring
// Isso quer dizer que podemos injetar esse componente em outras classes
@Service
public class CadastroTituloService {

	// Injeção de dependências
	@Autowired
	private Titulos titulos;   // Nosso repository
	
	// Salvar
	public void salvar(Titulo titulo) {
		
		try {
			titulos.save(titulo);
			
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("A data não está em um formato válido");
		}
	}

	// Excluir
	public void excluir(Long codigo) {
		
		titulos.deleteById(codigo);		
	}
	
	// Receber
	public String receber(Long codigo) {
		
		// Busca no modelo o item recebido
		Titulo titulo = titulos.getOne(codigo);
		
		// Atualiza o status do item
		titulo.setStatus(StatusTitulo.RECEBIDO);
		
		// Nossa Interface JPA fará a gravação
		titulos.save(titulo);
		
		// Retorna a descrição do novo Status pago
		return StatusTitulo.RECEBIDO.getDescricao();
	}

	// Pesquisar
	public List<Titulo> filtra(TituloFilter tituloFilter) {
		
		// Validando se a descricao do titulo é nula
		String descricao = tituloFilter.getDescricao() == null ? "" : tituloFilter.getDescricao();
		
		// Usando a inteligencia da Spring Data JPA para capturarmos os titulos por descricao
		return titulos.findByDescricaoContaining(tituloFilter.getDescricao());
	}
	
}
