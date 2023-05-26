package com.odontoApp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.odontoApp.api.domain.procedimento.DadosAtualizacaoProcedimento;
import com.odontoApp.api.domain.procedimento.DadosCadastroProcedimento;
import com.odontoApp.api.domain.procedimento.DadosDetalhamentoProcedimento;
import com.odontoApp.api.domain.procedimento.DadosListagemProcedimento;
import com.odontoApp.api.domain.procedimento.Procedimento;
import com.odontoApp.api.domain.procedimento.ProcedimentoRepository;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/v1/procedimentos")
@SecurityRequirement(name = "bearer-key")
public class ProcedimentoController {

	private final ProcedimentoRepository procedimentoRepository;

	@Autowired
	public ProcedimentoController(ProcedimentoRepository procedimentoRepository) {
		this.procedimentoRepository = procedimentoRepository;
	}

	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroProcedimento dados, UriComponentsBuilder uriBuilder) {
		var procedimento = new Procedimento(dados);
		procedimentoRepository.save(procedimento);
		var uri = uriBuilder.path("/procedimentos/{id}").buildAndExpand(procedimento.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoProcedimento(procedimento));
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemProcedimento>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		var page = procedimentoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemProcedimento::new);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity detalhar(@PathVariable Long id) {
		var procedimento = procedimentoRepository.getReferenceById(id);

		return ResponseEntity.ok(new DadosDetalhamentoProcedimento(procedimento));
	}

	@PutMapping
	@Transactional
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoProcedimento dados) {
		var procedimento = procedimentoRepository.getReferenceById(dados.id());
		procedimento.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosDetalhamentoProcedimento(procedimento));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {
		var procedimento = procedimentoRepository.getReferenceById(id);
		procedimento.excluir();
		return ResponseEntity.noContent().build();
	}

}
