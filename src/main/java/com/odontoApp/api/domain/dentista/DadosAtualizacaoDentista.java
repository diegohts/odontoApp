package com.odontoApp.api.domain.dentista;

import com.odontoApp.api.domain.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

public record DadosAtualizacaoDentista (

	@NotNull(message = "{id.obrigatorio}")
	Long id,

	String nome,

	String telefone,

	@Valid
	DadosEndereco endereco) {
}
