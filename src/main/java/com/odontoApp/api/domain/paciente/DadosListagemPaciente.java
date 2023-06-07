package com.odontoApp.api.domain.paciente;

public record DadosListagemPaciente (Long id, String nome, String email, String cpf, Long idConvenio, String nomeConvenio) {

	public DadosListagemPaciente(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(),
				paciente.getConvenio() != null ? paciente.getConvenio().getId() : null,
				paciente.getConvenio() != null ? paciente.getConvenio().getNome() : null);
	}
}
