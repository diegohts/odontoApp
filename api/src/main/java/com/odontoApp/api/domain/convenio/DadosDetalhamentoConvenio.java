package com.odontoApp.api.domain.convenio;

public record DadosDetalhamentoConvenio (Long id, String nome, String contato, String cobertura, String observacao) {

	public DadosDetalhamentoConvenio(Convenio convenio) {
		this(convenio.getId(), convenio.getNome(), convenio.getContato(), convenio.getCobertura(), convenio.getObservacao());
	}
}
