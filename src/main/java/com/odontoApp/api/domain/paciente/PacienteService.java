package com.odontoApp.api.domain.paciente;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.odontoApp.api.domain.convenio.*;
import com.odontoApp.api.domain.ValidacaoException;

@Service
public class PacienteService {

	private final PacienteRepository pacienteRepository;
	private final ConvenioRepository convenioRepository;

	@Autowired
	public PacienteService(PacienteRepository pacienteRepository, ConvenioRepository convenioRepository) {
		this.pacienteRepository = pacienteRepository;
		this.convenioRepository = convenioRepository;
	}

	@Transactional
	public DadosDetalhamentoPaciente cadastrar(DadosCadastroPaciente dados) {
		Convenio convenio = null;

		// Verifique se o ID do convênio foi fornecido e obtenha o objeto Convenio correspondente
		if (dados.idConvenio() != null) {
			Optional<Convenio> optionalConvenio = convenioRepository.findById(dados.idConvenio());

			if (optionalConvenio.isEmpty()) {
				throw new ValidacaoException("ID do convênio informado não existe");
			}

			convenio = optionalConvenio.get();
		}

		Paciente paciente = new Paciente(dados, convenio);
		paciente = this.pacienteRepository.save(paciente);

		return new DadosDetalhamentoPaciente(paciente);
	}


	public Page<DadosListagemPaciente> listar(Pageable paginacao){
		return this.pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
	}

	@Transactional
	public DadosDetalhamentoPaciente atualizarInformacoes(DadosAtualizacaoPaciente dados) {

		Paciente paciente = this.pacienteRepository.getReferenceById(dados.id());
		paciente.atualizarInformacoes(dados);

		// Verifique se o ID do convênio foi fornecido e associe-o ao paciente
		if (dados.idConvenio() != null) {
			Optional<Convenio> optionalConvenio = convenioRepository.findById(dados.idConvenio());

			if (optionalConvenio.isEmpty()) {
				throw new ValidacaoException("ID do convenio informado não existe");
			}
			Convenio convenio = optionalConvenio.get();
			paciente.setConvenio(convenio);
		} else {
			paciente.setConvenio(null); // Define o convênio como null
		}

		return new DadosDetalhamentoPaciente(paciente);
	}

	@Transactional
	public DadosDetalhamentoPaciente excluir(Long id){
		Paciente paciente = this.pacienteRepository.getReferenceById(id);
		paciente.excluir();

		return new DadosDetalhamentoPaciente(paciente);
	}

	public DadosDetalhamentoPaciente detalhar(Long id) {

		Paciente paciente = this.pacienteRepository.getReferenceById(id);

		return new DadosDetalhamentoPaciente(paciente);
	}

	public Boolean emailPacienteJaCadastrado(String email) {
		return this.pacienteRepository.existsByEmail(email);
	}

	public Boolean cpfPacienteJaCadastrado(String cpf) {
		return this.pacienteRepository.existisByCpf(cpf);
	}
}
