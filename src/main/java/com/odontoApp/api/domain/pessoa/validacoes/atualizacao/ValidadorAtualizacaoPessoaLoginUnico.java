package com.odontoApp.api.domain.pessoa.validacoes.atualizacao;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.pessoa.DadosAtualizacaoPessoa;
import com.odontoApp.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAtualizacaoPessoaLoginUnico implements ValidadorAtualizacaoPessoa {

	private final UsuarioRepository usuarioRepository;

	@Autowired
	public ValidadorAtualizacaoPessoaLoginUnico(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public void validar(DadosAtualizacaoPessoa dados) {
		String login = dados.login();
		if (dados.login() == null) {
			return;
		}

		var usuario = usuarioRepository.getReferenceUsuarioByLogin(login);
		if (usuario.getId().equals(dados.id())) {
			throw new ValidacaoException("Email já cadastrado");
		}
	}
}
