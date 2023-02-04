package com.odontoApp.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<DadosListagemDentista> listar() {
		// Dessa forma converto uma lista de Medico para uma lista DadosListagemMedico
		return repository.findAll().stream().map(DadosListagemDentista::new).toList();
	}
}
