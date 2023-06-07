package com.odontoApp.api.domain.paciente;

import com.odontoApp.api.domain.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosAtualizacaoPaciente (

	@NotNull(message = "{id.obrigatorio}")
	Long id,

	String nome,

	String telefone,

	@JsonAlias({"id_convenio", "convenio_id"})
	Long idConvenio,

	@Valid
	DadosEndereco endereco) {
}
