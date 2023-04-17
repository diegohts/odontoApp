package com.odontoApp.api.domain.procedimento;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "procedimentos")
@Entity(name = "Procedimento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Procedimento {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private boolean ativo;

	public Procedimento(DadosCadastroProcedimento dados) {
		this.ativo = true;
		this.nome = dados.nome();
		this.descricao = dados.descricao();
		this.preco = dados.preco();
	}

	public void atualizarInformacoes(DadosAtualizacaoProcedimento dados) {
		if(dados.descricao() != null){
			this.descricao = dados.descricao();
		}

		if(dados.preco() != null){
			this.preco = dados.preco();
		}
	}

	public void excluir() {
		this.ativo = false;
	}
}
