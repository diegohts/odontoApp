package com.odontoApp.api.domain.consulta.validacoes.agendamento;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.DadosAgendamentoConsulta;
import com.odontoApp.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

	@Autowired
	private PacienteRepository pacienteRepository;

	public void validar(DadosAgendamentoConsulta dados) {
		var pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());

		if (!pacienteEstaAtivo) {
			throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
		}
	}

}
