package com.odontoApp.api.domain.procedimento;

import java.math.BigDecimal;

public record DadosListagemProcedimento (Long id, String nome, String descricao, BigDecimal preco) {

	public DadosListagemProcedimento(Procedimento procedimento) {
		this(procedimento.getId(), procedimento.getNome(), procedimento.getDescricao(), procedimento.getPreco());
	}
}
