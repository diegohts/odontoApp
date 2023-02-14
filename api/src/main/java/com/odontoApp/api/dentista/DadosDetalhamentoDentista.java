package com.odontoApp.api.dentista;

import com.odontoApp.api.endereco.Endereco;

public record DadosDetalhamentoDentista (Long id, String nome, String email, String crm, String telefone, Especialidade especialidade, Endereco endereco) {

	public DadosDetalhamentoDentista(Dentista dentista) {
		this(dentista.getId(), dentista.getNome(), dentista.getEmail(), dentista.getCro(), dentista.getTelefone(), dentista.getEspecialidade(), dentista.getEndereco());
	}
}
