package com.odontoApp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import com.odontoApp.api.domain.paciente.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("api/v1/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

	private static final Logger logger = LogManager.getLogger(PacienteController.class);

	private final PacienteRepository pacienteRepository;
	private final PacienteService pacienteService;

	@Autowired
	public PacienteController(PacienteService pacienteService, PacienteRepository pacienteRepository) {
		this.pacienteRepository = pacienteRepository;
		this.pacienteService = pacienteService;
	}

	@PostMapping
	public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {

		DadosDetalhamentoPaciente paciente = this.pacienteService.cadastrar(dados);

		URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.id()).toUri();

		logger.info("O paciente " + paciente.nome() + " foi cadastrado com sucesso");

		return ResponseEntity.created(uri).body(paciente);
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

		Page<DadosListagemPaciente> page = this.pacienteService.listar(paginacao);

		logger.info("Listando informacoes dos pacientes");

		return ResponseEntity.ok(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id) {
		DadosDetalhamentoPaciente paciente = pacienteService.detalhar(id);

		logger.info("Obtendo informacoes do paciente " + paciente.nome());

		return ResponseEntity.ok(paciente);
	}

	@PutMapping
	public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {

		DadosDetalhamentoPaciente paciente = this.pacienteService.atualizarInformacoes(dados);

		logger.info("O paciente " + paciente.nome() + " foi atualizado com sucesso");

		return ResponseEntity.ok(paciente);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity excluir(@PathVariable Long id) {

		this.pacienteService.excluir(id);

		logger.info("O paciente de codigo " + id + " foi inativado com sucesso");

		return ResponseEntity.noContent().build();
	}
}
