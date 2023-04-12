package com.odontoApp.api.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.odontoApp.api.domain.consulta.AgendaDeConsultas;
import com.odontoApp.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

	@Autowired
	private AgendaDeConsultas agenda;

	@PostMapping
	@Transactional
	public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
		var dto = agenda.agendar(dados);
		return ResponseEntity.ok(dto);
	}
}
