package com.odontoApp.api.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.odontoApp.api.domain.consulta.ConsultaService;
import com.odontoApp.api.domain.consulta.Consulta;
import com.odontoApp.api.domain.consulta.ConsultaRepository;
import com.odontoApp.api.domain.consulta.DadosAgendamentoConsulta;
import com.odontoApp.api.domain.consulta.DadosCancelamentoConsulta;
import com.odontoApp.api.domain.consulta.DadosListagemConsulta;
import com.odontoApp.api.domain.consulta.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("api/v1/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

	private static final Logger logger = LogManager.getLogger(ConsultaController.class);

	private final ConsultaService consultaService;
	private final ConsultaRepository consultaRepository;

	@Autowired
	public ConsultaController(ConsultaService consultaService, ConsultaRepository consultaRepository) {
		this.consultaService = consultaService;
		this.consultaRepository = consultaRepository;
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemConsulta>> listar(@PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {
		Page<Consulta> consultas = consultaRepository.findAll(paginacao);
		Page<DadosListagemConsulta> page = consultas.map(DadosListagemConsulta::new);

		logger.info("Listando informacoes das consultas");

		return ResponseEntity.ok(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity detalhar(@PathVariable Long id) {
		var consulta = consultaRepository.getReferenceById(id);

		logger.info("Obtendo informacoes da consulta de codigo " + id);

		return ResponseEntity.ok(new DadosListagemConsulta(consulta));
	}

	@PostMapping
	public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		var dto = consultaService.agendarConsulta(dadosAgendamentoConsulta);

		logger.info("A consulta de codigo " + dto.id() + " foi agendada com sucesso");

		return ResponseEntity.ok(dto);
	}

	@DeleteMapping
	public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dadosCancelamentoConsulta) {
		consultaService.cancelamentoConsulta(dadosCancelamentoConsulta);

		logger.info("A consulta de codigo " + dadosCancelamentoConsulta.idConsulta() + " foi cancelada com sucesso");

		return ResponseEntity.noContent().build();
	}

}
