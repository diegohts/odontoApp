package com.odontoApp.api.paciente;

import com.odontoApp.api.endereco.Endereco;

public record DadosDetalhamentoPaciente (Long id, String nome, String email, String cpf, String telefone, Endereco endereco) {

	public DadosDetalhamentoPaciente(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(), paciente.getEndereco());
	}
}
