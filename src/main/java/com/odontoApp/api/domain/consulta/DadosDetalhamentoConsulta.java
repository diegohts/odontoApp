package com.odontoApp.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idDentista, Long idPaciente, Long idProcedimento, LocalDateTime data) {

	public DadosDetalhamentoConsulta(Consulta consulta) {
		this(consulta.getId(), consulta.getDentista().getId(), consulta.getPaciente().getId(), consulta.getProcedimento().getId() ,consulta.getData());
	}
}
