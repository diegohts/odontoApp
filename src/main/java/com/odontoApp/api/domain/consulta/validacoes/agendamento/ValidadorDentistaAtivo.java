package com.odontoApp.api.domain.consulta.validacoes.agendamento;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.DadosAgendamentoConsulta;
import com.odontoApp.api.domain.dentista.DentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDentistaAtivo implements ValidadorAgendamentoDeConsulta {

	private final DentistaRepository dentistaRepository;

	@Autowired
	public ValidadorDentistaAtivo(DentistaRepository dentistaRepository) {
		this.dentistaRepository = dentistaRepository;
	}

	public void validar(DadosAgendamentoConsulta dados) {

		if (dados.idDentista() == null) {
			return;
		}

		var dentistaEstaAtivo = dentistaRepository.findAtivoById(dados.idDentista());
		if (!dentistaEstaAtivo) {
			throw new ValidacaoException("Consulta não pode ser agendada com dentista inativo");
		}
	}

}
