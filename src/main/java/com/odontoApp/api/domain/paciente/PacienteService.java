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
	public Paciente cadastrar(DadosCadastroPaciente dados){
		return this.pacienteRepository.save(new Paciente(dados));
	}

	public Page<DadosListagemPaciente> listar(Pageable paginacao){
		return this.pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
	}

	@Transactional
	public Paciente atualizarInformacoes(DadosAtualizacaoPaciente dados) {
		Paciente paciente = this.pacienteRepository.getReferenceById(dados.id());
		paciente.atualizarInformacoes(dados);

		return paciente;
	}

	@Transactional
	public void excluir(Long id){
		Paciente paciente = this.pacienteRepository.getReferenceById(id);
		paciente.excluir();
	}

	public Paciente detalhar(Long id) {
		return this.pacienteRepository.getReferenceById(id);
	}

	public Boolean emailPacienteJaCadastrado(String email) {
		return this.pacienteRepository.existsByEmail(email);
	}

	public Boolean cpfPacienteJaCadastrado(String cpf) {
		return this.pacienteRepository.existisByCpf(cpf);
	}
}
