package com.odontoApp.api.domain.consulta;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import com.odontoApp.api.domain.dentista.Dentista;
import com.odontoApp.api.domain.dentista.DentistaRepository;
import com.odontoApp.api.domain.paciente.PacienteRepository;

@Service
public class AgendaDeConsultas {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private DentistaRepository dentistaRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private List<ValidadorAgendamentoDeConsulta> validadores;

	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {

		if (!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("ID do paciente informado não existe!");
		}

		if (dados.idDentista() != null && !dentistaRepository.existsById(dados.idDentista())) {
			throw new ValidacaoException("ID do dentista informado não existe!");
		}

		validadores.forEach(v -> v.validar(dados));

		var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
		var dentista = escolherDentista(dados);

		if(dentista == null) {
			throw new ValidacaoException("Não existe dentista disponível nessa data");
		}

		var consulta = new Consulta(null, dentista, paciente, dados.data(), null);
		consultaRepository.save(consulta);

		return new DadosDetalhamentoConsulta(consulta);
	}

	public void cancelar(DadosCancelamentoConsulta dados) {
		if (!consultaRepository.existsById(dados.idConsulta())) {
			throw new ValidacaoException("ID da consulta informado não existe!");
		}

		var consulta = consultaRepository.getReferenceById(dados.idConsulta());
		consulta.cancelar(dados.motivo());
	}

	private Dentista escolherDentista(DadosAgendamentoConsulta dados) {
		if (dados.idDentista() != null) {
			return dentistaRepository.getReferenceById(dados.idDentista());
		}

		if (dados.especialidade() == null) {
			throw new ValidacaoException("Especialidade é obrigatória quando dentista não for escolhido!");
		}

		return dentistaRepository.escolherDentistaAleatorioLivreNaData(dados.especialidade(), dados.data());
	}

}
