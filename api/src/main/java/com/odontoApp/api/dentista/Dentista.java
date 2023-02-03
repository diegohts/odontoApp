package com.odontoApp.api.dentista;

import com.odontoApp.api.endereco.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "dentistas")
@Entity(name = "Dentista")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Dentista {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String cro;

	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;

	@Embedded
	private Endereco endereco;

	public Dentista(DadosCadastroDentista dados) {
		this.nome = dados.nome();
		this.email = dados.email();
		this.cro = dados.cro();
		this.especialidade = dados.especialidade();
		this.endereco = new Endereco(dados.endereco());
	}
}
