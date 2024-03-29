package com.odontoApp.api.domain.consulta.validacoes.agendamento;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.ConsultaRepository;
import com.odontoApp.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDentistaComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

	private final ConsultaRepository consultaRepository;

	@Autowired
	public ValidadorDentistaComOutraConsultaNoMesmoHorario(ConsultaRepository consultaRepository) {
		this.consultaRepository = consultaRepository;
	}

	public void validar(DadosAgendamentoConsulta dados) {
		var dentistaPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByDentistaIdAndDataAndMotivoCancelamentoIsNull(dados.idDentista(), dados.data());

		if (dentistaPossuiOutraConsultaNoMesmoHorario) {
			throw new ValidacaoException("Dentista já possui outra consulta agendada nesse mesmo horário");
		}
	}

}
