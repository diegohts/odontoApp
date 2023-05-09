package com.odontoApp.api.domain.procedimento;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public record DadosCadastroProcedimento (

	@NotBlank
	String nome,

	String descricao,

	@NotNull
	@DecimalMin("0.0")
	BigDecimal preco) {
}
