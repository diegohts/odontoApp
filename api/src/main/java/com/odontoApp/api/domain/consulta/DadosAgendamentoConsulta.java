package com.odontoApp.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
	Long idDentista,

	@NotNull
	Long idPaciente,

	@NotNull
	@Future //Data do futuro, dados do momento daqui pra frente
	LocalDateTime data) {
}
