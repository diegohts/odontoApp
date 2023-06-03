package com.odontoApp.api.domain.dentista;

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
public class DentistaService {

	private static final Logger logger = LogManager.getLogger(DentistaService.class);

	private final DentistaRepository dentistaRepository;
	private final TipoRepository tipoRepository;
	private final UsuarioRepository usuarioRepository;

	@Autowired
	public DentistaService(DentistaRepository dentistaRepository, TipoRepository tipoRepository, UsuarioRepository usuarioRepository) {
		this.dentistaRepository = dentistaRepository;
		this.tipoRepository = tipoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	@Transactional
	public DadosDetalhamentoDentista cadastrar(DadosCadastroDentista dados){
		Dentista dentista = this.dentistaRepository.save(new Dentista(dados));

		Usuario usuarioDentista = usuarioRepository.getReferenceUsuarioByLogin(dentista.getEmail());
		if (usuarioDentista == null) {
			logger.info("Usuario do dentista nulo atraves da busca por login");
			throw new ValidacaoException("Busca do usuario do dentista não encontrado");
		}

		Tipo dentistaPerfil = new Tipo();
		dentistaPerfil.setUsuario(usuarioDentista);
		dentistaPerfil.setPerfil(new Perfil(2L, "ROLE_DENTISTA"));

		tipoRepository.save(dentistaPerfil);

		return new DadosDetalhamentoDentista(dentista);
	}

	@Transactional
	public DadosDetalhamentoDentista atualizarInformacoes(DadosAtualizacaoDentista dados){
		Dentista dentista = this.dentistaRepository.getReferenceById(dados.id());
		dentista.atualizarInformacoes(dados);

		return new DadosDetalhamentoDentista(dentista);
	}

	public Page<DadosListagemDentista> listar(Pageable paginacao){
		return this.dentistaRepository.findAllByAtivoTrue(paginacao).map(DadosListagemDentista::new);
	}

	@Transactional
	public DadosDetalhamentoDentista excluir(Long id) {
		// forma destrutiva, nao usar: this.dentistaRepository.deleteById(id);
		Dentista dentista = dentistaRepository.getReferenceById(id);
		dentista.excluir();

		return new DadosDetalhamentoDentista(dentista);
	}

	public DadosDetalhamentoDentista detalhar(Long id){
		Dentista dentista =  this.dentistaRepository.getReferenceById(id);

		return new DadosDetalhamentoDentista(dentista);
	}

	public Boolean croDentistaJaCadastrado(String cro) {
		return this.dentistaRepository.existisByCro(cro);
	}

	public Boolean emailDentistaJaCadastrado(String email) {
		return this.dentistaRepository.existisByEmail(email);
	}
}
