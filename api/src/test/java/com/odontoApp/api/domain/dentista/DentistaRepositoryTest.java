package com.odontoApp.api.domain.dentista;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import com.odontoApp.api.domain.consulta.Consulta;
import com.odontoApp.api.domain.endereco.DadosEndereco;
import com.odontoApp.api.domain.paciente.DadosCadastroPaciente;
import com.odontoApp.api.domain.paciente.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.DisplayName;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class DentistaRepositoryTest {

	@Autowired
	private DentistaRepository dentistaRepository;

	@Autowired
	private TestEntityManager em;

	@Test
	@DisplayName("Deveria devolver null quando unico dentista cadastrado nao esta disponivel na data")
	void escolherDentistaAleatorioLivreNaDataCenario1() {
		//given ou arrange
		var proximaSegundaAs10 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10, 0);
		var dentista = cadastrarDentista("Dentista", "dentista@odontoApp.com", "123456", Especialidade.CLINICO);
		var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
		cadastrarConsulta(dentista, paciente, proximaSegundaAs10);

		//when ou act
		var dentistaLivre = dentistaRepository.escolherDentistaAleatorioLivreNaData(Especialidade.CLINICO, proximaSegundaAs10);

		//then ou assert
		assertThat(dentistaLivre).isNull();
	}

	@Test
	@DisplayName("Deveria devolver dentista quando ele estiver disponivel na data")
	void escolherDentistaAleatorioLivreNaDataCenario2() {
		//given ou arrange
		var proximaSegundaAs10 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10, 0);
		var dentista = cadastrarDentista("Dentista", "dentista@odontoApp.com", "123456", Especialidade.CLINICO);

		//when ou act
		var dentistaLivre = dentistaRepository.escolherDentistaAleatorioLivreNaData(Especialidade.CLINICO, proximaSegundaAs10);

		//then ou assert
		assertThat(dentistaLivre).isEqualTo(dentista);
	}

	private void cadastrarConsulta(Dentista dentista, Paciente paciente, LocalDateTime data) {
		em.persist(new Consulta(null, dentista, paciente, data));
	}

	private Dentista cadastrarDentista(String nome, String email, String crm, Especialidade especialidade) {
		var dentista = new Dentista(dadosDentista(nome, email, crm, especialidade));
		em.persist(dentista);
		return dentista;
	}

	private Paciente cadastrarPaciente(String nome, String email, String cpf) {
		var paciente = new Paciente(dadosPaciente(nome, email, cpf));
		em.persist(paciente);
		return paciente;
	}

	private DadosCadastroDentista dadosDentista(String nome, String email, String crm, Especialidade especialidade) {
		return new DadosCadastroDentista(
				nome,
				email,
				"31999999991",
				crm,
				especialidade,
				dadosEndereco()
		);
	}

	private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
		return new DadosCadastroPaciente(
				nome,
				email,
				"31999999999",
				cpf,
				dadosEndereco()
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
