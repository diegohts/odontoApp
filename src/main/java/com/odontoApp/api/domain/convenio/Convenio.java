package com.odontoApp.api.domain.convenio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "convenios")
@Entity(name = "Convenio")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Convenio {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String contato;
	private String cobertura;
	private String observacao;
	private boolean ativo;

	public Convenio(DadosCadastroConvenio dados) {
		this.ativo = true;
		this.nome = dados.nome();
		this.contato = dados.contato();
		this.cobertura = dados.cobertura();
		this.observacao = dados.observacao();
	}

	public void atualizarInformacoes(DadosAtualizacaoConvenio dados) {
		if(dados.nome() != null){
			this.nome = dados.nome();
		}
		if(dados.contato() != null){
			this.contato = dados.contato();
		}

		if(dados.cobertura() != null){
			this.cobertura = dados.cobertura();
		}

		if(dados.observacao() != null){
			this.observacao = dados.observacao();
		}
	}

	public void excluir() {
		this.ativo = false;
	}
}
