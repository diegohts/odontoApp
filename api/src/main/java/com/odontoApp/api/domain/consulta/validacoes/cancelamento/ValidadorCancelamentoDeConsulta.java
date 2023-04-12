package com.odontoApp.api.domain.consulta.validacoes.cancelamento;

import com.odontoApp.api.domain.consulta.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {

	void validar(DadosCancelamentoConsulta dados);
}
