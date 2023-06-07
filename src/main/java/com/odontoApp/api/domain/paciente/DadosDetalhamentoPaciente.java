package com.odontoApp.api.domain.paciente;

import com.odontoApp.api.domain.endereco.Endereco;

public record DadosDetalhamentoPaciente (Long id, String nome, String email, String cpf, String telefone,
	Long idConvenio, String nomeConvenio, Endereco endereco) {

		public DadosDetalhamentoPaciente(Paciente paciente) {
			this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(),
					paciente.getConvenio() != null ? paciente.getConvenio().getId() : null,
					paciente.getConvenio() != null ? paciente.getConvenio().getNome() : null, paciente.getEndereco());
		}
}
