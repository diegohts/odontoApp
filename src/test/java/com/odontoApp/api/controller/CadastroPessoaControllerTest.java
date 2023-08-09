package com.odontoApp.api.controller;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.pessoa.DadosCadastroPessoa;
import com.odontoApp.api.domain.pessoa.DadosDetalhamentoPessoa;
import com.odontoApp.api.domain.pessoa.Genero;
import com.odontoApp.api.domain.pessoa.PessoaService;
import com.odontoApp.api.domain.usuario.DadosCadastroUsuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CadastroPessoaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JacksonTester<DadosCadastroPessoa> dadosCadastroPessoaJson;

	@Autowired
	private JacksonTester<DadosDetalhamentoPessoa> dadosDetalhamentoPessoaJson;

	@MockBean
	private PessoaService pessoaService;

	private final String baseUrl = "/user/signup";

	@Test
	@DisplayName("cadastrarPessoa - Deve devolver codigo HTTP 400 quando informações estão inválidas")
	@WithMockUser // Anotação fornece um usuário simulado com papéis de autorização atribuídos
	public void cadastrarPessoaTest400() throws Exception {
		var response = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	@DisplayName("cadastrarPessoa - Deve retornar codigo HTTP 201 quando os dados estão válidos")
	@WithMockUser
	public void cadastrarPessoaTest201() throws Exception{
		var dadosCadastroPessoa = new DadosCadastroPessoa(
				"Enzo Silva",
				LocalDate.of(1997, 12, 21),
				Genero.MASCULINO,
				new DadosCadastroUsuario("email@email.com", "123456789")
		);
		var dadosDetalhamentoPessoa = new DadosDetalhamentoPessoa(1L, "Enzo Silva","", "email@email.com", Genero.MASCULINO, LocalDate.of(1997, 12, 21));
		var jsonEsperado = dadosDetalhamentoPessoaJson.write(dadosDetalhamentoPessoa).getJson();

		Mockito.when(this.pessoaService.cadastrar(Mockito.any(DadosCadastroPessoa.class))).thenReturn(dadosDetalhamentoPessoa);

		var response = mockMvc.perform(
				MockMvcRequestBuilders
						.post(baseUrl)
						.contentType(MediaType.APPLICATION_JSON)
						.content(dadosCadastroPessoaJson.write(dadosCadastroPessoa).getJson())
		).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		Assertions.assertEquals(jsonEsperado, response.getContentAsString());
	}

	@Test
	@DisplayName("cadastrarPessoa - Deve devolver codigo HTTP 400 ao passar um email já cadastrado")
	@WithMockUser
	public void cadastrarPessoaTestEmail400() throws Exception{
		var dadosCadastroPessoa = new DadosCadastroPessoa(
				"Carina Laureano",
				LocalDate.of(1997, 12, 21),
				Genero.FEMININO,
				new DadosCadastroUsuario("email@email.com", "123456789")
		);

		Mockito.when(this.pessoaService.cadastrar(Mockito.any(DadosCadastroPessoa.class))).thenThrow(new ValidacaoException("Email já cadastrado"));

		var response = mockMvc.perform(
				MockMvcRequestBuilders
						.post(baseUrl)
						.contentType(MediaType.APPLICATION_JSON)
						.content(dadosCadastroPessoaJson.write(dadosCadastroPessoa).getJson())
		).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		Assertions.assertEquals("Email já cadastrado", response.getContentAsString());
	}

}
