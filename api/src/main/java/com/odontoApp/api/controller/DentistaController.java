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

@RestController
@RequestMapping("dentistas")
public class DentistaController {

	@Autowired
	private DentistaRepository dentistaRepository;

	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroDentista dados, UriComponentsBuilder uriBuilder) {
		var dentista = new Dentista(dados);
		dentistaRepository.save(dentista);
		var uri = uriBuilder.path("/dentistas/{id}").buildAndExpand(dentista.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoDentista(dentista));
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemDentista>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		var page = dentistaRepository.findAllByAtivoTrue(paginacao).map(DadosListagemDentista::new);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity detalhar(@PathVariable Long id) {
		var dentista = dentistaRepository.getReferenceById(id);

		return ResponseEntity.ok(new DadosDetalhamentoDentista(dentista));
	}

	@PutMapping
	@Transactional
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoDentista dados) {
		var dentista = dentistaRepository.getReferenceById(dados.id());
		dentista.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosDetalhamentoDentista(dentista));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {
		var dentista = dentistaRepository.getReferenceById(id);
		dentista.excluir();
		return ResponseEntity.noContent().build();
	}
}
