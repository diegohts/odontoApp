package com.odontoApp.api.domain.consulta.validacoes.cancelamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.ConsultaRepository;
import com.odontoApp.api.domain.consulta.DadosCancelamentoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Override
	public void validar(DadosCancelamentoConsulta dados) {
		var consulta = consultaRepository.getReferenceById(dados.idConsulta());
		var agora = LocalDateTime.now();
		var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

		if (diferencaEmHoras < 24) {
			throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h");
		}
	}
}
