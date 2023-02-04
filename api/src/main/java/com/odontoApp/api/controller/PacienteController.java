package com.odontoApp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.odontoApp.api.paciente.DadosCadastroPaciente;
import com.odontoApp.api.paciente.Paciente;
import com.odontoApp.api.paciente.PacienteRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

	@Autowired
	private PacienteRepository pacienteRepository;

	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dados) {
		pacienteRepository.save(new Paciente(dados));
	}
}
