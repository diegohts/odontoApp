package com.odontoApp.api.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.odontoApp.api.domain.dentista.DadosCadastroDentista;
import com.odontoApp.api.domain.dentista.DadosDetalhamentoDentista;
import com.odontoApp.api.domain.dentista.DadosAtualizacaoDentista;
import com.odontoApp.api.domain.dentista.Dentista;
import com.odontoApp.api.domain.dentista.DentistaRepository;
import com.odontoApp.api.domain.dentista.DentistaService;
import com.odontoApp.api.domain.dentista.Especialidade;
import com.odontoApp.api.domain.endereco.DadosEndereco;
import com.odontoApp.api.domain.endereco.Endereco;

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

	@Autowired
	private JacksonTester<DadosAtualizacaoDentista> dadosAtualizacaoDentistaJson;

	@MockBean
	private DentistaService dentistaService;
	private final String baseUrl = "/api/v1/dentistas";
	private DadosDetalhamentoDentista dadosDetalhamentoDentista;
	private DadosDetalhamentoDentista dadosDetalhamentoDentistaComID;
	private final Long idDentistaMock = 1L;
	private DadosCadastroDentista dadosCadastroDentista;
	private DadosAtualizacaoDentista dadosAtualizacaoDentista;

	@BeforeEach
	public void beforeAll(){
		this.dadosCadastroDentista = criarDadosCadastro();
		this.dadosDetalhamentoDentista = criarDadosDetalhamento();
		this.dadosDetalhamentoDentistaComID = criarDadosDetalhamentoComId(idDentistaMock);
		this.dadosAtualizacaoDentista = criarDadosAtualizacao(idDentistaMock);
	}

	private DadosEndereco criarEndereco(){
		return new DadosEndereco("Rua Exemplo", "Bairro", "12345123", "Cidade", "MG", null, null);
	}

	private DadosCadastroDentista criarDadosCadastro(){
		var dadosEspecialidade = Especialidade.ORTODONTISTA;
		var dadosEndereco = this.criarEndereco();
		var dadosCadastro = new DadosCadastroDentista("Diego", "diego@email.com", "31123451234", "123456", dadosEspecialidade, dadosEndereco);

		return dadosCadastro;
	}

	private DadosDetalhamentoDentista criarDadosDetalhamento(){
		var dadosCadastro = criarDadosCadastro();
		var dadosDetalhamento = new DadosDetalhamentoDentista(null, "Diego", "diego@email.com", "123456", "31123451234", dadosCadastro.especialidade(), new Endereco(dadosCadastro.endereco()));

		return dadosDetalhamento;
	}

	private DadosDetalhamentoDentista criarDadosDetalhamentoComId(Long id){
		var dadosCadastro = criarDadosCadastro();
		var dadosDetalhamento = new DadosDetalhamentoDentista(id, "Diego", "diego@email.com", "123456", "31123451234", dadosCadastro.especialidade(), new Endereco(dadosCadastro.endereco()));

		return dadosDetalhamento;
	}

	private DadosAtualizacaoDentista criarDadosAtualizacao(Long idDentistaMock) {
		return new DadosAtualizacaoDentista(idDentistaMock, "Diego", "31123451234", criarEndereco());
	}

	@Test
	@DisplayName("cadastrar - Deve devolver codigo HTTP 400 quando informacoes estao invalidas")
	@WithMockUser
	public void cadastrarTest400() throws Exception {
		var response = mvc.perform(MockMvcRequestBuilders.post(baseUrl)).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	@DisplayName("cadastrar - Deve devolver codigo HTTP 201 quando informacoes estao validas")
	@WithMockUser
	public void cadastrarTest201() throws Exception {
		Mockito.when(this.dentistaService.cadastrar(Mockito.any())).thenReturn(this.dadosDetalhamentoDentista);

		var response = mvc.perform(
				MockMvcRequestBuilders.post(baseUrl)
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.dadosCadastroDentistaJson.write(this.dadosCadastroDentista).getJson())
		).andReturn().getResponse();
		var jsonEsperado = dadosDetalhamentoDentistaJson.write(this.dadosDetalhamentoDentista).getJson();

		Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		Assertions.assertEquals(jsonEsperado, response.getContentAsString());
	}

	@Test
	@DisplayName("detalhar - Deve devolver codigo HTTP 200 quando o id passado é válido")
	@WithMockUser
	public void detalharTest200() throws Exception {
		Mockito.when(this.dentistaService.detalhar(idDentistaMock)).thenReturn(dadosDetalhamentoDentistaComID);

		var response = mvc.perform(
				MockMvcRequestBuilders.get(baseUrl + "/" + idDentistaMock).contentType(MediaType.APPLICATION_JSON)
		).andReturn().getResponse();
		var jsonEsperado = dadosDetalhamentoDentistaJson.write(dadosDetalhamentoDentistaComID).getJson();

		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertEquals(jsonEsperado, response.getContentAsString());
	}

	@Test
	@DisplayName("excluir - Deve retornar Http codigo 204 quando passado um id válido")
	@WithMockUser
	public void excluirTest204() throws Exception {
		Mockito.when(dentistaService.excluir(idDentistaMock)).thenReturn(dadosDetalhamentoDentistaComID);

		var response = mvc.perform(
				MockMvcRequestBuilders.delete(baseUrl + "/" + idDentistaMock).contentType(MediaType.APPLICATION_JSON)
		).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}

	@Test
	@DisplayName("atualizar - Deve retornar Http codigo 400 quando informacoes passadas estão invalidas")
	@WithMockUser
	public void atualizarTest400() throws Exception {
		var response = mvc.perform(
				MockMvcRequestBuilders.put(baseUrl).contentType(MediaType.APPLICATION_JSON)
		).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	@DisplayName("atualizar - Deve retornar Http codigo 200 quando informações passadas estão válidas")
	@WithMockUser
	public void atualizarTest200() throws Exception {
		Mockito.when(this.dentistaService.atualizarInformacoes(dadosAtualizacaoDentista)).thenReturn(dadosDetalhamentoDentistaComID);
		var jsonEsperado = dadosDetalhamentoDentistaJson.write(dadosDetalhamentoDentistaComID).getJson();

		var response = mvc.perform(
				MockMvcRequestBuilders.put(baseUrl)
						.contentType(MediaType.APPLICATION_JSON)
						.content(dadosAtualizacaoDentistaJson.write(dadosAtualizacaoDentista).getJson())
		).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertEquals(jsonEsperado, response.getContentAsString());
	}

}
