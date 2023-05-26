package com.odontoApp.api.domain.consulta.validacoes.cancelamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.ConsultaRepository;
import com.odontoApp.api.domain.consulta.DadosCancelamentoConsulta;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorCancelamentoAntecedencia implements ValidadorCancelamentoDeConsulta {

	private final ConsultaRepository consultaRepository;

	@Autowired
	public ValidadorCancelamentoAntecedencia(ConsultaRepository consultaRepository) {
		this.consultaRepository = consultaRepository;
	}

	@Override
	public void validar(DadosCancelamentoConsulta dadosCancelamentoConsulta) {

		var consulta = consultaRepository.getReferenceById(dadosCancelamentoConsulta.idConsulta());
		var horaConsulta = consulta.getData();
		var agora = LocalDateTime.now();
		var diferencaEmHoras = Duration.between(agora, horaConsulta).toHours();

		if (diferencaEmHoras < 24) {
			throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h");
		}
	}
}
