package com.odontoApp.api.domain.dentista;

import com.odontoApp.api.domain.endereco.DadosEndereco;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoDentista (

	@NotNull
	Long id,

	String nome,

	String telefone,

	DadosEndereco endereco) {
}
