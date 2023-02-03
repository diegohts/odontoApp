package com.odontoApp.api.dentista;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import com.odontoApp.api.endereco.DadosEndereco;

public record DadosCadastroDentista(

	@NotBlank
	String nome,

	@NotBlank
	@Email
	String email,

	@NotBlank
	String telefone,

	@NotBlank
	@Pattern(regexp = "\\d{4,6}")
	String cro,

	@NotNull
	Especialidade especialidade,

	@NotNull
	@Valid
	DadosEndereco endereco) {
}
