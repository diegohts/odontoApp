package com.odontoApp.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.odontoApp.api.domain.dentista.DadosCadastroDentista;
import com.odontoApp.api.domain.dentista.DadosDetalhamentoDentista;
import com.odontoApp.api.domain.dentista.Dentista;
import com.odontoApp.api.domain.dentista.DentistaRepository;
import com.odontoApp.api.domain.dentista.Especialidade;
import com.odontoApp.api.domain.endereco.DadosEndereco;
import com.odontoApp.api.domain.endereco.Endereco;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DentistaControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<DadosCadastroDentista> dadosCadastroDentistaJson;

	@Autowired
	private JacksonTester<DadosDetalhamentoDentista> dadosDetalhamentoDentistaJson;

	@MockBean
	private DentistaRepository dentistaRepository;

	@Test
	@DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
	@WithMockUser
	void cadastrar_cenario1() throws Exception {
		var response = mvc
				.perform(post("/dentistas"))
				.andReturn().getResponse();

		assertThat(response.getStatus())
				.isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	@DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
	@WithMockUser
	void cadastrar_cenario2() throws Exception {
		var dadosCadastro = new DadosCadastroDentista(
				"Dentista",
				"dentista@odontoApp.com",
				"31999999999",
				"123456",
				Especialidade.ODONTOPEDIATRIA,
				dadosEndereco());

		when(dentistaRepository.save(any())).thenReturn(new Dentista(dadosCadastro));

		var response = mvc
				.perform(post("/dentistas")
						.contentType(MediaType.APPLICATION_JSON)
						.content(dadosCadastroDentistaJson.write(dadosCadastro).getJson()))
				.andReturn().getResponse();

		var dadosDetalhamento = new DadosDetalhamentoDentista(
				null,
				dadosCadastro.nome(),
				dadosCadastro.email(),
				dadosCadastro.cro(),
				dadosCadastro.telefone(),
				dadosCadastro.especialidade(),
				new Endereco(dadosCadastro.endereco())
		);
		var jsonEsperado = dadosDetalhamentoDentistaJson.write(dadosDetalhamento).getJson();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
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
