package com.odontoApp.api.domain.pagamento;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public record DadosDetalhamentoPagamento(Long id, Long idConsulta, LocalDateTime dataPagamento, FormaPagamento formaPagamento, BigDecimal valorPago, String quemPagou, String observacao) {

	public DadosDetalhamentoPagamento(Pagamento pagamento) {
		this(pagamento.getId(), pagamento.getConsulta().getId(), pagamento.getDataPagamento(), pagamento.getFormaPagamento(), pagamento.getValorPago(), pagamento.getQuemPagou(), pagamento.getObservacao());
	}
}
