package com.odontoApp.api.controller;

import jakarta.validation.Valid;
import com.odontoApp.api.domain.pessoa.DadosCadastroPessoa;
import com.odontoApp.api.domain.pessoa.DadosDetalhamentoPessoa;
import com.odontoApp.api.domain.pessoa.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/user/signup")
public class CadastroPessoaController {

	private static final Logger logger = LogManager.getLogger(CadastroPessoaController.class);

	private final PessoaService pessoaService;

	@Autowired
	public CadastroPessoaController(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	@PostMapping
	public ResponseEntity<DadosDetalhamentoPessoa> cadastrarPessoa(@RequestBody @Valid DadosCadastroPessoa dadosCadastroPessoa, UriComponentsBuilder uriBuilder){
		var dadosDetalhamentoPessoa = pessoaService.cadastrar(dadosCadastroPessoa);
		URI uri = uriBuilder.path("/pessoas/{id}")
				.buildAndExpand(dadosDetalhamentoPessoa.id())
				.toUri();

		logger.info("A pessoa " + dadosDetalhamentoPessoa.nome() + " foi cadastrada com sucesso");

		return ResponseEntity.created(uri).body(dadosDetalhamentoPessoa);
	}
}
