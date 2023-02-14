package com.odontoApp.api.paciente;

public record DadosListagemPaciente (Long id, String nome, String email, String cpf) {

	public DadosListagemPaciente(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
	}
}
