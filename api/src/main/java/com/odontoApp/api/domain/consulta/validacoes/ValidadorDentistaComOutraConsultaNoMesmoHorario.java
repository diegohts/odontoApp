package com.odontoApp.api.domain.consulta.validacoes;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.ConsultaRepository;
import com.odontoApp.api.domain.consulta.DadosAgendamentoConsulta;

public class ValidadorDentistaComOutraConsultaNoMesmoHorario {

	private ConsultaRepository consultaRepository;

	public void validar(DadosAgendamentoConsulta dados) {
		var dentistaPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByDentistaIdAndData(dados.idDentista(), dados.data());

		if (dentistaPossuiOutraConsultaNoMesmoHorario) {
			throw new ValidacaoException("Dentista já possui outra consulta agendada nesse mesmo horário");
		}
	}

}
