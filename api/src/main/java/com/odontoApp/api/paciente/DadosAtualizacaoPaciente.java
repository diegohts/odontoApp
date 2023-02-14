package com.odontoApp.api.paciente;

import jakarta.validation.constraints.NotNull;
import com.odontoApp.api.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente (

	@NotNull
	Long id,

	String nome,

	String telefone,

	DadosEndereco endereco) {
}
