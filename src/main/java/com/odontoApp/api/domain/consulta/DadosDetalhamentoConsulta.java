package com.odontoApp.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idDentista, String nomeDentista, Long idPaciente,
	String nomePaciente, Long idProcedimento, String nomeProcedimento, LocalDateTime data) {

		public DadosDetalhamentoConsulta(Consulta consulta) {
			this(consulta.getId(), consulta.getDentista().getId(), consulta.getDentista().getNome(),
				consulta.getPaciente().getId(), consulta.getPaciente().getNome(), consulta.getProcedimento().getId(),
				consulta.getProcedimento().getNome(), consulta.getData());
		}
}
