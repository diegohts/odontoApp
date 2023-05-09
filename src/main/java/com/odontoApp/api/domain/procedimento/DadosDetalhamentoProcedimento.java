package com.odontoApp.api.domain.procedimento;

import java.math.BigDecimal;

public record DadosDetalhamentoProcedimento (Long id, String nome, String descricao, BigDecimal preco) {

	public DadosDetalhamentoProcedimento(Procedimento procedimento) {
		this(procedimento.getId(), procedimento.getNome(), procedimento.getDescricao(), procedimento.getPreco());
	}
}
