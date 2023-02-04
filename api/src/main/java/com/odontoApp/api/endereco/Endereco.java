package com.odontoApp.api.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

	private String logradouro;
	private String bairro;
	private String cep;
	private String numero;
	private String complemento;
	private String cidade;
	private String uf;

	public Endereco(DadosEndereco endereco) {
		this.logradouro = endereco.logradouro();
		this.bairro = endereco.bairro();
		this.cep = endereco.cep();
		this.numero = endereco.numero();
		this.complemento = endereco.complemento();
		this.cidade = endereco.cidade();
		this.uf = endereco.uf();
	}

	public void atualizarInformacoes(DadosEndereco dados) {
		if(logradouro != null){
			this.logradouro = dados.logradouro();
		}

		if(bairro != null){
			this.bairro = dados.bairro();
		}

		if(cep != null){
			this.cep = dados.cep();
		}

		if(numero != null){
			this.numero = dados.numero();
		}

		if(complemento != null){
			this.complemento = dados.complemento();
		}

		if(cidade != null){
			this.cidade = dados.cidade();
		}

		if(uf != null){
			this.uf = dados.uf();
		}
	}
}
