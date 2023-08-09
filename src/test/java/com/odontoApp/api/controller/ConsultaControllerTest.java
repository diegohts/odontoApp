package com.odontoApp.api.controller;

import com.odontoApp.api.domain.consulta.Consulta;
import com.odontoApp.api.domain.consulta.ConsultaService;
import com.odontoApp.api.domain.consulta.DadosAgendamentoConsulta;
import com.odontoApp.api.domain.consulta.DadosCancelamentoConsulta;
import com.odontoApp.api.domain.consulta.DadosDetalhamentoConsulta;
import com.odontoApp.api.domain.consulta.MotivoCancelamento;
import com.odontoApp.api.domain.dentista.Especialidade;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;

@SpringBootTest(classes = com.odontoApp.api.ApiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

	@Autowired
	private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

	@Autowired
	private JacksonTester<DadosCancelamentoConsulta> dadosCancelamentoConsultaJson;

	@MockBean
	private ConsultaService consultaService;
	private final String baseUrl = "/api/v1/consultas";

	@Test
	@DisplayName("agendar - Deve devolver codigo HTTP 400 quando informacoes estao invalidas")
	@WithMockUser
	public void agendarTest400() throws Exception {
		var response = mvc.perform(MockMvcRequestBuilders.post(baseUrl)).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	@DisplayName("agendar - Deve devolver codigo HTTP 200 quando informações estao válidas")
	@WithMockUser
	public void agendarTest200() throws Exception {
		var data = LocalDateTime.now().plusHours(1);
		var especialidade = Especialidade.ORTODONTISTA;
		var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2L, "NomeDentista", 5L, "NomePaciente", 1L, "NomeProcedimento", data);
		Mockito.when(this.consultaService.agendarConsulta(Mockito.any())).thenReturn(dadosDetalhamento);

		var response = mvc.perform(
				MockMvcRequestBuilders
						.post(baseUrl)
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								dadosAgendamentoConsultaJson.write(
										new DadosAgendamentoConsulta(2L, 5L, 1L, data, especialidade)
								).getJson()
						)
		).andReturn().getResponse();
		var jsonEsperado = dadosDetalhamentoConsultaJson.write(dadosDetalhamento).getJson();

		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertEquals(jsonEsperado, response.getContentAsString());
	}

	@Test
	@DisplayName("cancelamento - Deve devolver codigo HTTP 400 quando informacoes estao invalidas")
	@WithMockUser
	public void cancelamentoTest400() throws Exception {
		var response = mvc.perform(MockMvcRequestBuilders.delete(baseUrl)).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	@DisplayName("cancelamento - Deve devolver codigo HTTP 204 quando informacoes estao validas")
	@WithMockUser
	public void cancelamentoTest204() throws Exception{
		var motivo = MotivoCancelamento.DENTISTA_CANCELOU;
		var dadosCancelamento = new DadosCancelamentoConsulta(1L, motivo);
		Mockito.when(this.consultaService.cancelamentoConsulta(Mockito.any())).thenReturn(new Consulta());

		var response = mvc.perform(
				MockMvcRequestBuilders.delete(baseUrl)
						.contentType(MediaType.APPLICATION_JSON)
						.content(dadosCancelamentoConsultaJson.write(dadosCancelamento).getJson())
		).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}

}
