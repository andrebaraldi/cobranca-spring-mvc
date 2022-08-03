package com.baraldi.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
// Observar os imports de Spring...
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baraldi.cobranca.model.StatusTitulo;
import com.baraldi.cobranca.model.Titulo;
import com.baraldi.cobranca.repository.TituloFilter;
import com.baraldi.cobranca.repository.Titulos;
import com.baraldi.cobranca.service.CadastroTituloService;



// Nossa Classe é um controller
// Que responde na url o que vier seguido de /titulos
@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	private static final String VIEW_CADASTRO = "CadastroTitulo";
	
	// AutoWired - Injeção de Dependência feita pelo Spring 
	// O Spring vai procurar o objeto Titulo, que é referenciado 
	// pela interface TituloS
	// Veja que não estamos em nenhum momento efetuado = new Titulo();
	@Autowired
	private Titulos titulos;

	
	// Nosso service com regras de negócio
	@Autowired
	private CadastroTituloService cadastroTituloService;
	
	
	// ---------------------------------------------------------------------- 
	// Como será chamado na url do browser
	// /titulos/novo
	// ----------------------------------------------------------------------
	@RequestMapping("/novo")
	public ModelAndView novo() {
		
		// Vamos apontar para uma VIEW
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		
		// Adicionando um objeto na VIEW, que são os valores de nossa ENUM
		// Não precisamos mais, pois Criamos um ModelAttribute, logo abaixo...
		//mv.addObject("todosStatusTitulo", StatusTitulo.values());
		
		// Adicionando um objeto Titulo vazio, para que a página HTML CadastroTitulo.html
		// possa ser carregada, pois ela valida se o objeto Título possui erros, 
		// independente se é post(salvar()) ou get(novo, aqui mesmo).
		// ... th:if="${#fields.hasAnyErros()}">
		mv.addObject(new Titulo());
		
		// Aqui estamos retornando automaticamente o arquivo
		// src/main/resources/templates/CadastroTitulo.html
		return mv;
	}
		
	// ----------------------------------------------------------------------
	// Nossa url será /titulos, pois não colocamos RequestMapping aqui
	// Se estivermos fazendo um POST para /titulos, vamos capturar os 
	// dados da nossa requisição como se fosse um objeto Titulo
	// Por isso na nossa view, temos que estar com os campos idêntico ao
	// da entidade Titulos para que ele entenda
	// ----------------------------------------------------------------------
	// A anotação @Validated é de Bean Validation, que valida o objeto
	// titulo de acordo com o especificado nos atributos da classe
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes ) {
		
		// Debug
		System.out.println("Chegou a solicitação de Salvar de " + titulo.getDescricao() );		

		// Se tivermos erro de validação, nem segue em frente, voltando para a mesma página
		if (errors.hasErrors()) { return VIEW_CADASTRO;}		

		
		try {
			// Salvando o registro em um banco de dados usando o nosso objeto JPA
			//titulos.save(titulo);
			cadastroTituloService.salvar(titulo);
			
			// O método abaixo manterá o atributo mesmo após o redirect
			attributes.addFlashAttribute("mensagem", "Título cadastrado com sucesso.");

			// Não estamos retornando um ModelAndView no método salvar e sim uma String
			return "redirect:/titulos/novo";		
			
		// Tratando possível erro retornado do nosso Service
		} catch (DataIntegrityViolationException e) {
		
			errors.rejectValue("dataVencimento",  null, e.getMessage());
			
			// Volta para a mesma VIEW que efetuou o POST, porém com erro no campo data
			return VIEW_CADASTRO;
		}
		
		
	}
	
	
	// Retorna a tela pesquisa de Títulos
	//@RequestMapping
	//public ModelAndView pesquisar(@RequestParam(defaultValue = "") String descricao) {
	//
	// ModelAttribute("filtro") indica que será será criado um objeto filtro ao usar essa action
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter tituloFilter) {
		
		//System.out.println("Chegou a solicitação");
		
		// Usando o findAll da interface jpa que retorna um list do nosso objeto
		//List<Titulo> todosTitulos = titulos.findAll();
		
		// Retornando a página ao modelo e a lista que pesquisamos
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		
		// No primeiro acesso, o valor da descricao do objeto TituloFilter pode se nula
		tituloFilter.setDescricao(tituloFilter.getDescricao() == null ? "" : tituloFilter.getDescricao()); 
		
		// Efetua a pesquisa usando o service
		List<Titulo> todosTitulos = cadastroTituloService.filtra(tituloFilter);
		
		// Usando nossa camada de serviço para pesuisar os títulos
		mv.addObject("titulos" , todosTitulos );
		
		return mv;
	}
	
	/*
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) {
		List<Titulo> todosTitulos = cadastroTituloService.filtra(filtro);
		
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", todosTitulos);
		return mv;
	}
	*/
	
	// Edição
	// --------------------
	// PathVariable indica que será enviado um parâmetro na url
	// que no caso é o código
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {
		
		ModelAndView mv = new ModelAndView(VIEW_CADASTRO);
		mv.addObject(titulo);		
		return mv;
	}
	
	
	// Exclusão
	// ---------------------
	// Observe que aqui estamos esperando o RequestMethod igual a DELETE
	// ao invés de GET ou POST
	@RequestMapping(value="{codigo}",  method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		
		// Debug
		System.out.println("Chegou a solicitação de Excluir o Titulo " + codigo );	
		
		//titulos.deleteById(codigo);
		cadastroTituloService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem", "Titulo excluído com sucesso!");
		
		return "redirect:/titulos";
	}
	
	// Recebimento de Título
	// -------------------------
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		
		// Será retornada uma String contendo o novo Status do Título
		return cadastroTituloService.receber(codigo);
	}
	
	
	// Lista disponível de status (nossso ENUM StatusTitulo) para que for utilizar nosso Controller
	// Evita assim a duplicidade de código ao retornar a página CadastroTitulo.html e ter 
	// que ficar dando mv.addObject("todosStatusTitulo", StatusTitulo.values()) nas actions anteriores
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo>  getTodosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values()); 
	}
	
	
	@ModelAttribute("filtro")
	public TituloFilter getTituloFilter() {
		return new TituloFilter(); 
	}
}
