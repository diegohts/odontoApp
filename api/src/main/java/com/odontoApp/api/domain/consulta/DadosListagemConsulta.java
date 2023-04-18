package com.odontoApp.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosListagemConsulta(Long id, String nomeDentista, String nomePaciente, LocalDateTime data) {

    public DadosListagemConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getDentista().getNome(), consulta.getPaciente().getNome(), consulta.getData());
    }
}
