package com.odontoApp.api.domain.paciente;

import com.odontoApp.api.domain.endereco.DadosEndereco;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroPaciente (

	@NotBlank(message = "{nome.obrigatorio}")
	String nome,

	@NotBlank(message = "{email.obrigatorio}")
	@Email(message = "{email.invalido}")
	String email,

	@NotBlank(message = "{telefone.obrigatorio}")
	String telefone,

	@NotBlank(message = "{cpf.obrigatorio}")
	@Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}", message = "{cpf.invalido}")
	String cpf,

	@JsonAlias({"id_convenio", "convenio_id"})
	Long idConvenio,

	@NotNull(message = "{endereco.obrigatorio}")
	@Valid
	DadosEndereco endereco) {

}
