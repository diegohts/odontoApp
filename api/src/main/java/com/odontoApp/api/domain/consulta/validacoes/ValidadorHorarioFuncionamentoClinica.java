package com.odontoApp.api.domain.consulta.validacoes;

import java.time.DayOfWeek;
import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

	public void validar(DadosAgendamentoConsulta dados) {
		var dataConsulta = dados.data();

		var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
		// A clinica fecha as 19 hrs e a duracao fixa eh uma hora, entao a ultima consulta eh as 18hrs
		var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;

		if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
			throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
		}
	}

}
