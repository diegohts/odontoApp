package com.odontoApp.api.domain.pagamento;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public record DadosListagemPagamento(Long id, Long idConsulta, String nomeDentista, String nomePaciente, String nomeProcedimento,
	LocalDateTime dataConsulta, LocalDateTime dataPagamento, FormaPagamento formaPagamento,
	BigDecimal valorPago, String quemPagou, String observacao) {

	public DadosListagemPagamento(Pagamento pagamento) {
		this(pagamento.getId(), pagamento.getConsulta().getId(), pagamento.getConsulta().getDentista().getNome(),
			pagamento.getConsulta().getPaciente().getNome(), pagamento.getConsulta().getProcedimento().getNome(),
			pagamento.getConsulta().getData(), pagamento.getDataPagamento(), pagamento.getFormaPagamento(),
			pagamento.getValorPago(), pagamento.getQuemPagou(), pagamento.getObservacao());
	}
}
