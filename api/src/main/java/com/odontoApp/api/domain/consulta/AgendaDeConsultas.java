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

	public void agendar(DadosAgendamentoConsulta dados) {

		if (!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("ID do paciente informado não existe!");
		}

		if (dados.idDentista() != null && !dentistaRepository.existsById(dados.idDentista())) {
			throw new ValidacaoException("ID do dentista informado não existe!");
		}

		// Sao varias classes que tem o mesmo metodo em comum que se chama validar, um cenario de interface
		//e a assinatura é similar para todas as classes que usam o metodo.
		//Porem cada um, tem um tratamento e implementacao diferente
		validadores.forEach(v -> v.validar(dados));

		var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
		var dentista = escolherDentista(dados);

		var consulta = new Consulta(null, dentista, paciente, dados.data());
		consultaRepository.save(consulta);
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
