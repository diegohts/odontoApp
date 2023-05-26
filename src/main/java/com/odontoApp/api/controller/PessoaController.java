package com.odontoApp.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import com.odontoApp.api.domain.pessoa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pessoas")
@SecurityRequirement(name = "bearer-key")
public class PessoaController {

	private final PessoaService pessoaService;

	@Autowired
	public PessoaController(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	@GetMapping("/{login}")
	public ResponseEntity<DadosDetalhamentoPessoa> detalhar(@PathVariable String login){
		DadosDetalhamentoPessoa dados = pessoaService.detalhar(login);

		return ResponseEntity.ok(dados);
	}

	@PutMapping
	public ResponseEntity<DadosDetalhamentoPessoa> atualizar(@RequestBody @Valid DadosAtualizacaoPessoa dadosAtualizacaoPessoa){
		DadosDetalhamentoPessoa dados = pessoaService.atualizar(dadosAtualizacaoPessoa);

		return ResponseEntity.ok(dados);
	}
}
