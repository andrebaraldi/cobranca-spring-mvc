package com.baraldi.cobranca.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

// Importações para Bean Validation --------
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


// @Entity: indica que estamos criando uma entidade JPA
@Entity
public class Titulo {

	// -----------------------
	// Atributos de Título
	// -----------------------
	
	// Id - Indica que é por onde podemos identificar o nosso registro
	// GeneratedValue(strategy = GenerationType.IDENTITY) - Deixa o banco de dados tomar conta pra gente
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message = "Descrição é obrigatória") // Bean Validation
	@Size(max = 60, message = "A descrição não pode conter mais de 60 caracteres")  // Bean Validation
	private String descricao;
	
	
	@NotNull(message = "Data de vencimento é obrigatória")
	@DateTimeFormat(pattern = "dd/MM/yyyy")  // Nossa data será dia, mês e ano, sem as horas...
	@Temporal(TemporalType.DATE)
	private Date dataVencimento;
	
	
	@NotNull(message = "Valor é obrigatório")  // Bean Validation
	@DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;
	
	// No banco de dados desejamos que nosso campo StatusTitulo seja salvo
	// a string PENDENTE ou RECEBIDO da Enum StatusTitulo, ao invés de números
	@Enumerated(EnumType.STRING)
	private StatusTitulo status;
	
	
	// ------------	
	// Get/Set
	// ------------	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public StatusTitulo getStatus() {
		return status;
	}
	public void setStatus(StatusTitulo status) {
		this.status = status;
	}
	
	
	// Methods
	// ------------------------	
	public boolean isPendente() {
		return StatusTitulo.PENDENTE.equals(this.status);
	}

	// Overrides
	// ------------------------
	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Titulo other = (Titulo) obj;
		
		return Objects.equals(codigo, other.codigo);
	}
	
	
}

