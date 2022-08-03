package com.baraldi.cobranca;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
public class CobrancaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CobrancaApplication.class, args);
	}
	
	// Definimos que nossa aplicação aponta para o Local BRAZIL
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR") );
	}
	
	public static class MvcConfig extends WebMvcConfigurerAdapter {
	
		// Redirecionando a página caso o usuário insira uma barra na url do browser
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addRedirectViewController("/", "/titulos");
		}	
	}
	
	
	
}
