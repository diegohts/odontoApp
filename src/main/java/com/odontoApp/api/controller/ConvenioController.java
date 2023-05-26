package com.odontoApp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.odontoApp.api.domain.convenio.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/v1/convenios")
@SecurityRequirement(name = "bearer-key")
public class ConvenioController {

	private final ConvenioRepository convenioRepository;

	@Autowired
	public ConvenioController(ConvenioRepository convenioRepository) {
		this.convenioRepository = convenioRepository;
	}

	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroConvenio dados, UriComponentsBuilder uriBuilder) {
		var convenio = new Convenio(dados);
		convenioRepository.save(convenio);
		var uri = uriBuilder.path("/convenios/{id}").buildAndExpand(convenio.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoConvenio(convenio));
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemConvenio>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		var page = convenioRepository.findAllByAtivoTrue(paginacao).map(DadosListagemConvenio::new);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity detalhar(@PathVariable Long id) {
		var convenio = convenioRepository.getReferenceById(id);

		return ResponseEntity.ok(new DadosDetalhamentoConvenio(convenio));
	}

	@PutMapping
	@Transactional
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoConvenio dados) {
		var convenio = convenioRepository.getReferenceById(dados.id());
		convenio.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosDetalhamentoConvenio(convenio));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {
		var convenio = convenioRepository.getReferenceById(id);
		convenio.excluir();
		return ResponseEntity.noContent().build();
	}
}
