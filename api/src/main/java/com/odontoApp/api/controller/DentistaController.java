package com.odontoApp.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.odontoApp.api.dentista.DadosCadastroDentista;

@RestController
@RequestMapping("dentistas")
public class DentistaController {

	@PostMapping
	public void cadastrar(@RequestBody DadosCadastroDentista dados) {
		System.out.println(dados);
	}
}
