package com.odontoApp.api.domain.convenio;

public record DadosListagemConvenio (Long id, String nome, String contato, String cobertura, String observacao) {

	public DadosListagemConvenio(Convenio convenio) {
		this(convenio.getId(), convenio.getNome(), convenio.getContato(), convenio.getCobertura(), convenio.getObservacao());
	}
}
