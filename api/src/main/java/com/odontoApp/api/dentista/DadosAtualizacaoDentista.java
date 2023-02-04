package com.odontoApp.api.dentista;

import com.odontoApp.api.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoDentista (

	@NotNull
	Long id,

	String nome,

	String telefone,

	DadosEndereco endereco) {
}
