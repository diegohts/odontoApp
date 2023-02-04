package com.odontoApp.api.dentista;

public record DadosListagemDentista(String nome, String email, String cro, Especialidade especialidade) {

	public DadosListagemDentista(Dentista dentista) {
		this(dentista.getNome(), dentista.getEmail(), dentista.getCro(), dentista.getEspecialidade());
	}
}
