package com.odontoApp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.odontoApp.api.domain.dentista.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("api/v1/dentistas")
@SecurityRequirement(name = "bearer-key")
public class DentistaController {

	private static final Logger logger = LogManager.getLogger(DentistaController.class);

	private final DentistaService dentistaService;
	private final DentistaRepository dentistaRepository;

	@Autowired
	public DentistaController(DentistaService dentistaService, DentistaRepository dentistaRepository) {
		this.dentistaService = dentistaService;
		this.dentistaRepository = dentistaRepository;
	}

	@PostMapping
	public ResponseEntity<DadosDetalhamentoDentista> cadastrar(@RequestBody @Valid DadosCadastroDentista dados, UriComponentsBuilder uriBuilder) {

		DadosDetalhamentoDentista dentista = this.dentistaService.cadastrar(dados);
		URI uri = uriBuilder.path("/dentistas/{id}").buildAndExpand(dentista.id()).toUri();

		logger.info("O dentista " + dentista.nome() + " foi cadastrado com sucesso");

		return ResponseEntity.created(uri).body(dentista);
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemDentista>> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable paginacao) {

		Page<DadosListagemDentista> page = this.dentistaService.listar(paginacao);

		logger.info("Listando informacoes dos dentistas");

		return ResponseEntity.ok(page);
	}

	@PutMapping
	public ResponseEntity<DadosDetalhamentoDentista> atualizar(@RequestBody @Valid DadosAtualizacaoDentista dados) {

		DadosDetalhamentoDentista dentista = this.dentistaService.atualizarInformacoes(dados);

		logger.info("O dentista " + dentista.nome() + " foi atualizado com sucesso");

		return ResponseEntity.ok(dentista);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity excluir(@PathVariable Long id) {

		this.dentistaService.excluir(id);

		logger.info("O dentista de codigo " + id + " foi inativado com sucesso");

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoDentista> detalhar(@PathVariable Long id) {
		DadosDetalhamentoDentista dentista = dentistaService.detalhar(id);

		logger.info("Obtendo informacoes do dentista " + dentista.nome());

		return ResponseEntity.ok(dentista);
	}

	@GetMapping("/existe")
	public ResponseEntity<Boolean> verificarExistencia(
			@RequestParam(name = "tipo") Optional<String> tipoInformado,
			@RequestParam(name = "email", required = false) Optional<String> emailInformado,
			@RequestParam(name = "cro", required = false) Optional<String> croInformado) {
		Boolean resultado = true;
		String tipo = tipoInformado.orElseThrow();

		if (Objects.equals(tipo, "email")) {
			resultado = dentistaService.emailDentistaJaCadastrado(emailInformado.orElseThrow());
		}
		if (Objects.equals(tipo, "cro")) {
			resultado = dentistaService.croDentistaJaCadastrado(croInformado.orElseThrow());
		}

		logger.info("Verificando a existencia do dentista no sistema");

		return ResponseEntity.ok(resultado);
	}
}
