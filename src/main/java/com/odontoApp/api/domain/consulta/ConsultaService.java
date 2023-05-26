package com.odontoApp.api.domain.consulta;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import com.odontoApp.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import com.odontoApp.api.domain.dentista.Dentista;
import com.odontoApp.api.domain.dentista.DentistaRepository;
import com.odontoApp.api.domain.paciente.PacienteRepository;
import com.odontoApp.api.domain.procedimento.ProcedimentoRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultaService {

	private ConsultaRepository consultaRepository;
	private DentistaRepository dentistaRepository;
	private PacienteRepository pacienteRepository;
	private ProcedimentoRepository procedimentoRepository;
	private List<ValidadorAgendamentoDeConsulta> validadoresConsulta;
	private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

	@Autowired
	public ConsultaService(
		ConsultaRepository consultaRepository,
		DentistaRepository dentistaRepository,
		PacienteRepository pacienteRepository,
		ProcedimentoRepository procedimentoRepository,
		List<ValidadorAgendamentoDeConsulta> validadoresConsulta,
		List<ValidadorCancelamentoDeConsulta> validadoresCancelamento) {

			this.consultaRepository = consultaRepository;
			this.dentistaRepository = dentistaRepository;
			this.pacienteRepository = pacienteRepository;
			this.procedimentoRepository = procedimentoRepository;
			this.validadoresConsulta = validadoresConsulta;
			this.validadoresCancelamento = validadoresCancelamento;
	}

	@Transactional
	public DadosDetalhamentoConsulta agendarConsulta(DadosAgendamentoConsulta dados) {

		if (!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("ID do paciente informado não existe");
		}

		if (dados.idDentista() != null && !dentistaRepository.existsById(dados.idDentista())) {
			throw new ValidacaoException("ID do dentista informado não existe");
		}

		validadoresConsulta.forEach(v -> v.validar(dados));

		var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
		var procedimento = procedimentoRepository.getReferenceById(dados.idProcedimento());
		var dentista = escolherDentista(dados);

		if(dentista == null) {
			throw new ValidacaoException("Não existe dentista disponível nessa data");
		}

		var consulta = new Consulta(null, dentista, paciente, procedimento, dados.data(), null);
		consulta = consultaRepository.save(consulta);

		return new DadosDetalhamentoConsulta(consulta);
	}

	@Transactional
	public Consulta cancelamentoConsulta(DadosCancelamentoConsulta dados) {

		if (!consultaRepository.existsById(dados.idConsulta())) {
			throw new ValidacaoException("ID da consulta informada não existe");
		}

		validadoresCancelamento.forEach(v -> v.validar(dados));

		var consulta = consultaRepository.getReferenceById(dados.idConsulta());
		consulta.cancelar(dados.motivo());

		return consulta;
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
