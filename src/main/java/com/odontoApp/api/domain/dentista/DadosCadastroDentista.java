package com.odontoApp.api.domain.dentista;

import com.odontoApp.api.domain.endereco.DadosEndereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroDentista(

	@NotBlank(message = "{nome.obrigatorio}")
	String nome,

	@NotBlank(message = "{email.obrigatorio}")
	@Email(message = "{email.invalido}")
	String email,

	@NotBlank(message = "{telefone.obrigatorio}")
	String telefone,

	@NotBlank(message = "{cro.obrigatorio}")
	@Pattern(regexp = "\\d{4,6}", message = "{cro.invalido}")
	String cro,

	@NotNull(message = "{especialidade.obrigatoria}")
	Especialidade especialidade,

	@NotNull(message = "{endereco.obrigatorio}")
	@Valid
	DadosEndereco endereco) {
}
