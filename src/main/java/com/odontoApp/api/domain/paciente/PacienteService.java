package com.odontoApp.api.domain.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.usuario.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class PacienteService {

	private static final Logger logger = LogManager.getLogger(PacienteService.class);

	private final PacienteRepository pacienteRepository;
	private final TipoRepository tipoRepository;
	private final UsuarioRepository usuarioRepository;

	@Autowired
	public PacienteService(PacienteRepository pacienteRepository, TipoRepository tipoRepository, UsuarioRepository usuarioRepository) {
		this.pacienteRepository = pacienteRepository;
		this.tipoRepository = tipoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	@Transactional
	public DadosDetalhamentoPaciente cadastrar(DadosCadastroPaciente dados){
		Paciente paciente = this.pacienteRepository.save(new Paciente(dados));

		Usuario usuarioPaciente = usuarioRepository.getReferenceUsuarioByLogin(paciente.getEmail());
		if (usuarioPaciente == null) {
			logger.info("Usuario do paciente nulo atraves da busca por login");
			throw new ValidacaoException("Busca do usuario do paciente não encontrado");
		}

		Tipo pacientePerfil = new Tipo();
		pacientePerfil.setUsuario(usuarioPaciente);
		pacientePerfil.setPerfil(new Perfil(3L, "ROLE_PACIENTE"));

		tipoRepository.save(pacientePerfil);

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
