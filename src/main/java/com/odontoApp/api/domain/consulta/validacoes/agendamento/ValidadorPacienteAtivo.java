package com.odontoApp.api.domain.consulta.validacoes.agendamento;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.DadosAgendamentoConsulta;
import com.odontoApp.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

	private final PacienteRepository pacienteRepository;

	@Autowired
	public ValidadorPacienteAtivo(PacienteRepository pacienteRepository) {
		this.pacienteRepository = pacienteRepository;
	}

	public void validar(DadosAgendamentoConsulta dados) {
		var pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());

		if (!pacienteEstaAtivo) {
			throw new ValidacaoException("Consulta não pode ser agendada com paciente inativo");
		}
	}

}
