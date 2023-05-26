package com.odontoApp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
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

	@Autowired
	public PacienteController(PacienteRepository pacienteRepository) {
		this.pacienteRepository = pacienteRepository;
	}

	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
		var paciente = new Paciente(dados);
		pacienteRepository.save(paciente);

		var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

		logger.info("O paciente " + paciente.getNome() + " foi cadastrado com sucesso");

		return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		var page = pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);

		logger.info("Listando informacoes dos pacientes");

		return ResponseEntity.ok(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity detalhar(@PathVariable Long id) {
		var paciente = pacienteRepository.getReferenceById(id);

		logger.info("Obtendo informacoes do paciente " + paciente.getNome());

		return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
	}

	@PutMapping
	@Transactional
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
		var paciente = pacienteRepository.getReferenceById(dados.id());
		paciente.atualizarInformacoes(dados);

		logger.info("O paciente " + paciente.getNome() + " foi atualizado com sucesso");

		return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {
		var paciente = pacienteRepository.getReferenceById(id);
		paciente.excluir();

		logger.info("O paciente " + paciente.getNome() + " foi inativado com sucesso");

		return ResponseEntity.noContent().build();
	}
}
