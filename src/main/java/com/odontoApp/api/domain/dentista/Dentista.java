package com.odontoApp.api.domain.dentista;

import com.odontoApp.api.domain.endereco.Endereco;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "dentistas")
@Entity(name = "Dentista")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Dentista {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String cro;

	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;

	@Embedded
	private Endereco endereco;

	private boolean ativo;

	public Dentista(DadosCadastroDentista dados) {
		this.ativo = true;
		this.nome = dados.nome();
		this.email = dados.email();
		this.telefone = dados.telefone();
		this.cro = dados.cro();
		this.especialidade = dados.especialidade();
		this.endereco = new Endereco(dados.endereco());
	}

	public void atualizarInformacoes(DadosAtualizacaoDentista dados) {
		if(dados.nome() != null){
			this.nome = dados.nome();
		}

		if(dados.telefone() != null){
			this.telefone = dados.telefone();
		}

		if(dados.endereco() != null){
			this.endereco.atualizarInformacoes(dados.endereco());
		}
	}

	public void excluir() {
		this.ativo = false;
	}
}
