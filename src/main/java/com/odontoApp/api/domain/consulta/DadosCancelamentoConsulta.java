package com.odontoApp.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
	@NotNull
	Long idConsulta,

	@NotNull
	MotivoCancelamento motivo) {
}
