package com.odontoApp.api.domain.consulta.validacoes;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.ConsultaRepository;
import com.odontoApp.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDentistaComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

	@Autowired
	private ConsultaRepository consultaRepository;

	public void validar(DadosAgendamentoConsulta dados) {
		var dentistaPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByDentistaIdAndData(dados.idDentista(), dados.data());

		if (dentistaPossuiOutraConsultaNoMesmoHorario) {
			throw new ValidacaoException("Dentista já possui outra consulta agendada nesse mesmo horário");
		}
	}

}
