package com.odontoApp.api.domain.consulta.validacoes.agendamento;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.ConsultaRepository;
import com.odontoApp.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

	private final ConsultaRepository consultaRepository;

	@Autowired
	public ValidadorPacienteSemOutraConsultaNoDia(ConsultaRepository consultaRepository) {
		this.consultaRepository = consultaRepository;
	}

	public void validar(DadosAgendamentoConsulta dados) {
		var primeiroHorario = dados.data().withHour(7);
		var ultimoHorario = dados.data().withHour(18);
		var pacientePossuiOutraConsultaNoDia = consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);

		if (pacientePossuiOutraConsultaNoDia) {
			throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia");
		}
	}

}
