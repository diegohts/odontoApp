package com.odontoApp.api.domain.pagamento;

import java.time.LocalDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import com.odontoApp.api.domain.pagamento.*;
import java.util.Optional;

public record DadosCadastroPagamento (

	@NotNull
	@JsonAlias({"id_consulta", "consulta_id"})
	Long idConsulta,

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	LocalDateTime dataPagamento,

	@NotNull(message = "{formaPagamento.obrigatorio}")
	FormaPagamento formaPagamento,

	@NotNull(message = "{valorPago.obrigatorio}")
	BigDecimal valorPago,

	String quemPagou,

	String observacao) {
}
