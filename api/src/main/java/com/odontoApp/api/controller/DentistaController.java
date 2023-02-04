package com.odontoApp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
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
	public Page<DadosListagemDentista> listar(Pageable paginacao) {
		/* Paginacao e o controle é realizado na chamada na url, sendo http://localhost:8080/medicos?size=1&page=2
		Detalhe a primeira página é representada por page=0
		Esses 2 parametros são usados para controlar a paginacao, size quantos registros quero carregar
		e o page qual pagina carrego os registros*/
		return repository.findAll(paginacao).map(DadosListagemDentista::new);
	}
}
