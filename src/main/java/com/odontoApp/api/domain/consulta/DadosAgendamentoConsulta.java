package com.odontoApp.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import com.odontoApp.api.domain.dentista.Especialidade;

public record DadosAgendamentoConsulta(

	@JsonAlias({"id_dentista", "dentista_id"})
	Long idDentista,

	@JsonAlias({"id_paciente", "paciente_id"})
	@NotNull
	Long idPaciente,

	@JsonAlias({"id_procedimento", "procedimento_id"})
	Long idProcedimento,

	@JsonAlias({"data_consulta", "data_agendamento"})
	@NotNull
	@Future
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	LocalDateTime data,

	Especialidade especialidade) {
}
