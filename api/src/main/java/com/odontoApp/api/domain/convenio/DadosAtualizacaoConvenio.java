package com.odontoApp.api.domain.convenio;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoConvenio (

	@NotNull
	Long id,

	String nome,

	String contato,

	String cobertura,

	String observacao) {
}
