package com.odontoApp.api.domain.consulta.validacoes;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.DadosAgendamentoConsulta;
import com.odontoApp.api.domain.paciente.PacienteRepository;

public class ValidadorPacienteAtivo {

	private PacienteRepository pacienteRepository;

	public void validar(DadosAgendamentoConsulta dados) {
		var pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());

		if (!pacienteEstaAtivo) {
			throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
		}
	}

}
