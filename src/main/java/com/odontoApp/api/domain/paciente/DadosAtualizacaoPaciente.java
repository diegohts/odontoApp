package com.odontoApp.api.domain.paciente;

import com.odontoApp.api.domain.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPaciente (

	@NotNull(message = "{id.obrigatorio}")
	Long id,

	String nome,

	String telefone,

	@Valid
	DadosEndereco endereco) {
}
