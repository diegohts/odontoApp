package com.odontoApp.api.domain.dentista;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import com.odontoApp.api.domain.consulta.Consulta;
import com.odontoApp.api.domain.convenio.Convenio;
import com.odontoApp.api.domain.convenio.DadosCadastroConvenio;
import com.odontoApp.api.domain.dentista.DadosCadastroDentista;
import com.odontoApp.api.domain.dentista.DentistaRepository;
import com.odontoApp.api.domain.dentista.Especialidade;
import com.odontoApp.api.domain.endereco.DadosEndereco;
import com.odontoApp.api.domain.paciente.DadosCadastroPaciente;
import com.odontoApp.api.domain.paciente.Paciente;
import com.odontoApp.api.domain.procedimento.DadosCadastroProcedimento;
import com.odontoApp.api.domain.procedimento.Procedimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.DisplayName;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest// utilizada para teste de classes repository
// manda o spring boot não substituir o SGBD utilizado
// na aplicação por um em memória
// (melhor por ser mais fiel ao ambiente da aplicação porém é mais demorado paa efetuar os testes)
// Utilizar um banco de dados exclusivo para teste
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // diz que o prefixo do bandco é teste(ex: banco). O spring boot vai procurar por um bd chamado banco-test
@ActiveProfiles("test")
public class DentistaRepositoryTest {

	@Autowired
	private DentistaRepository dentistaRepository;

	@Autowired
	private TestEntityManager em;

	@Test
	@DisplayName("Deveria devolver null quando unico dentista cadastrado nao esta disponivel na data")
	void escolherDentistaAleatorioLivreNaDataCenario1() {

		var proximaSegundaAs10 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10, 0);

		var convenio = cadastrarConvenio("Convenio XPTO", "Contato do Convenio", "Cobertura Especial", "Observações importantes");

		var dentista = cadastrarDentista("Dentista", "dentista@odontoApp.com", "123456", Especialidade.CLINICO_GERAL);
		var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "12345678901", convenio);

		var procedimento = cadastrarProcedimento("Limpeza", "Limpeza dentária", new BigDecimal("20.00"));
		cadastrarConsulta(dentista, paciente, procedimento, proximaSegundaAs10);

		var dentistaLivre = dentistaRepository.escolherDentistaAleatorioLivreNaData(Especialidade.CLINICO_GERAL, proximaSegundaAs10);

		Assertions.assertEquals(null, dentistaLivre);
	}

	@Test
	@DisplayName("Deveria devolver dentista quando ele estiver disponivel na data")
	void escolherDentistaAleatorioLivreNaDataCenario2() {

		var proximaSegundaAs10 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10, 0);
		var dentista = cadastrarDentista("Dentista", "dentista@odontoApp.com", "123456", Especialidade.CLINICO_GERAL);

		var dentistaLivre = dentistaRepository.escolherDentistaAleatorioLivreNaData(Especialidade.CLINICO_GERAL, proximaSegundaAs10);

		Assertions.assertEquals(dentista, dentistaLivre);
	}

	private void cadastrarConsulta(Dentista dentista, Paciente paciente, Procedimento procedimento, LocalDateTime data) {
		em.persist(new Consulta(null, dentista, paciente, procedimento, data, null));
	}

	private Dentista cadastrarDentista(String nome, String email, String cro, Especialidade especialidade) {
		var dentista = new Dentista(dadosDentista(nome, email, cro, especialidade));
		em.persist(dentista);
		return dentista;
	}

	private Paciente cadastrarPaciente(String nome, String email, String cpf, Convenio convenio) {
		var paciente = new Paciente(dadosPaciente(nome, email, cpf, convenio), convenio);
		em.persist(paciente);
		return paciente;
	}

	private Procedimento cadastrarProcedimento(String nome, String descricao, BigDecimal preco) {
		var procedimento = new Procedimento(dadosProcedimento(nome, descricao, preco));
		em.persist(procedimento);
		return procedimento;
	}

	private Convenio cadastrarConvenio(String nome, String contato, String cobertura, String observacao) {
		var dadosConvenio = new DadosCadastroConvenio(nome, contato, cobertura, observacao);
		var convenio = new Convenio(dadosConvenio);
		em.persist(convenio);
		return convenio;
	}


	private DadosCadastroDentista dadosDentista(String nome, String email, String cro, Especialidade especialidade) {
		return new DadosCadastroDentista(
				nome,
				email,
				"31999999991",
				cro,
				especialidade,
				dadosEndereco()
		);
	}

	private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf, Convenio convenio) {
		return new DadosCadastroPaciente(
				nome,
				email,
				"31999999999",
				cpf,
				convenio.getId(),
				dadosEndereco()
		);
	}

	private DadosCadastroProcedimento dadosProcedimento(String nome, String descricao, BigDecimal preco) {
		return new DadosCadastroProcedimento(
				nome,
				descricao,
				preco
		);
	}

	private DadosEndereco dadosEndereco() {
		return new DadosEndereco(
				"rua xxx",
				"bairro",
				"00000000",
				"Belo Horizonte",
				"MG",
				null,
				null
		);
	}

}
