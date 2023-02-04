package com.odontoApp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.odontoApp.api.dentista.DadosAtualizacaoDentista;
import com.odontoApp.api.dentista.DadosCadastroDentista;
import com.odontoApp.api.dentista.DadosListagemDentista;
import com.odontoApp.api.dentista.Dentista;
import com.odontoApp.api.dentista.DentistaRepository;

@RestController
@RequestMapping("dentistas")
public class DentistaController {

	@Autowired
	private DentistaRepository repository;

	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastroDentista dados) {
		repository.save(new Dentista(dados));
	}

	@GetMapping
	public Page<DadosListagemDentista> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return repository.findAll(paginacao).map(DadosListagemDentista::new);
	}

	@PutMapping
	@Transactional
	public void atualizar(@RequestBody @Valid DadosAtualizacaoDentista dados) {
		var dentista = repository.getReferenceById(dados.id());
		dentista.atualizarInformacoes(dados);
	}
}
