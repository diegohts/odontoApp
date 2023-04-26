package com.odontoApp.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import com.odontoApp.api.domain.dentista.Especialidade;

public record DadosAgendamentoConsulta(
	Long idDentista,

	@NotNull
	Long idPaciente,

	Long idProcedimento,

	@NotNull
	@Future
	LocalDateTime data,
	Especialidade especialidade) {
}
