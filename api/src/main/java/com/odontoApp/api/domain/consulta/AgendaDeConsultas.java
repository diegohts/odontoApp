package com.odontoApp.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.odontoApp.api.domain.dentista.DentistaRepository;
import com.odontoApp.api.domain.paciente.PacienteRepository;

//Representa um servico, que serve para agendar consultas e consegue carregar posteriormente
// Classe Service executa as regras de negocio e validacoes da aplicacao
@Service
public class AgendaDeConsultas {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private DentistaRepository dentistaRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	public void agendar(DadosAgendamentoConsulta dados) {
		var paciente = pacienteRepository.findById(dados.idPaciente()).get();
		var dentista = dentistaRepository.findById(dados.idDentista()).get();
		var consulta = new Consulta(null, dentista, paciente, dados.data());
		consultaRepository.save(consulta);
	}

}
