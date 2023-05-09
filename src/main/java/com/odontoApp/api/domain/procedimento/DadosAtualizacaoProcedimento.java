package com.odontoApp.api.domain.procedimento;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoProcedimento (

	@NotNull
	Long id,

	String nome,

	String descricao,

	BigDecimal preco){

}
