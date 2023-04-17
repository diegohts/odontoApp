package com.odontoApp.api.domain.convenio;

import jakarta.validation.constraints.*;

public record DadosCadastroConvenio (

	@NotBlank
	String nome,

	@NotBlank
	String contato,

	String cobertura,

	String observacao) {

}
