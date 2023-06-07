package com.odontoApp.api.domain.paciente;

import com.odontoApp.api.domain.endereco.Endereco;
import com.odontoApp.api.domain.convenio.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String cpf;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "convenio_id")
	private Convenio convenio;

	@Embedded
	private Endereco endereco;

	private boolean ativo;

	public Paciente(DadosCadastroPaciente dados, Convenio convenio) {
		this.ativo = true;
		this.nome = dados.nome();
		this.email = dados.email();
		this.telefone = dados.telefone();
		this.cpf = dados.cpf();
		this.convenio = convenio;
		this.endereco = new Endereco(dados.endereco());
	}

	public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
		if(dados.nome() != null){
			this.nome = dados.nome();
		}

		if(dados.telefone() != null){
			this.telefone = dados.telefone();
		}

		if(dados.endereco() != null){
			this.endereco.atualizarInformacoes(dados.endereco());
		}
	}

	public void excluir() {
		this.ativo = false;
	}
}
