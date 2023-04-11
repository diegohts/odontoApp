package com.odontoApp.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idDentista, Long idPaciente, LocalDateTime data) {
}
