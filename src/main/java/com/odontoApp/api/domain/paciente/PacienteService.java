package com.odontoApp.api.domain.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteService {

	private final PacienteRepository pacienteRepository;

	@Autowired
	public PacienteService(PacienteRepository pacienteRepository) {
		this.pacienteRepository = pacienteRepository;
	}

	@Transactional
	public DadosDetalhamentoPaciente cadastrar(DadosCadastroPaciente dados){

		Paciente paciente = this.pacienteRepository.save(new Paciente(dados));

		return new DadosDetalhamentoPaciente(paciente);
	}

	public Page<DadosListagemPaciente> listar(Pageable paginacao){
		return this.pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
	}

	@Transactional
	public DadosDetalhamentoPaciente atualizarInformacoes(DadosAtualizacaoPaciente dados) {

		Paciente paciente = this.pacienteRepository.getReferenceById(dados.id());
		paciente.atualizarInformacoes(dados);

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
