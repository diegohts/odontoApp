package com.odontoApp.api.domain.pagamento;

import java.math.BigDecimal;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.odontoApp.api.domain.consulta.Consulta;
import com.odontoApp.api.domain.pagamento.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "pagamentos")
@Entity(name = "Pagamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consulta_id")
	private Consulta consulta;

    @Column(name = "data_pagamento")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataPagamento;

	@Column(name = "forma_pagamento")
	@Enumerated(EnumType.STRING)
	private FormaPagamento formaPagamento;

	@Column(name = "valor_pago")
	private BigDecimal valorPago;

	@Column(name = "quem_pagou")
	private String quemPagou;

	@Column(name = "observacao")
	private String observacao;

	public String getValorPagoFormatted() {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		return currencyFormat.format(valorPago);
	}
}
