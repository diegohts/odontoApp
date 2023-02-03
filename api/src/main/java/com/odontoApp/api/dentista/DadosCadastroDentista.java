package com.odontoApp.api.dentista;

import com.odontoApp.api.endereco.DadosEndereco;

public record DadosCadastroDentista(String nome, String email, String cro,
	Especialidade especialidade, DadosEndereco endereco) {
}
