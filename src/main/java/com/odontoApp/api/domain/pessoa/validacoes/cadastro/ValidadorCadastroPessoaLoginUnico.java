package com.odontoApp.api.domain.pessoa.validacoes.cadastro;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.pessoa.DadosCadastroPessoa;
import com.odontoApp.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorCadastroPessoaLoginUnico implements ValidadorCadastroPessoa {

	private final UsuarioRepository usuarioRepository;

	@Autowired
	public ValidadorCadastroPessoaLoginUnico(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public void validar(DadosCadastroPessoa dados) {

		if (usuarioRepository.existsByLogin(dados.dadosCadastroUsuario().login())) {
			throw new ValidacaoException("Email já cadastrado");
		}
	}
}
