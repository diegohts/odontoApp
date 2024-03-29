package com.odontoApp.api.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import com.odontoApp.api.domain.dentista.Dentista;
import com.odontoApp.api.domain.paciente.Paciente;
import com.odontoApp.api.domain.procedimento.Procedimento;
import jakarta.validation.constraints.Future;
import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "consultas")
@Entity(name = "Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dentista_id")
	private Dentista dentista;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paciente_id")
	private Paciente paciente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "procedimento_id")
	private Procedimento procedimento;

	@Future
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime data;

	@Column(name = "motivo_cancelamento")
	@Enumerated(EnumType.STRING)
	private MotivoCancelamento motivoCancelamento;

	public void cancelar(MotivoCancelamento motivo) {
		this.motivoCancelamento = motivo;
	}

}
