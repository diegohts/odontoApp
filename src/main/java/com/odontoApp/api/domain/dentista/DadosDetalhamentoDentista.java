package com.odontoApp.api.domain.dentista;

import com.odontoApp.api.domain.endereco.Endereco;

public record DadosDetalhamentoDentista (Long id, String nome, String email, String cro, String telefone, Especialidade especialidade, Endereco endereco) {

	public DadosDetalhamentoDentista(Dentista dentista) {
		this(dentista.getId(), dentista.getNome(), dentista.getEmail(), dentista.getCro(), dentista.getTelefone(), dentista.getEspecialidade(), dentista.getEndereco());
	}
}
