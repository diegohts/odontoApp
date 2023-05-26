package com.odontoApp.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	private final PasswordEncoder passwordEncoder;
	private final UsuarioRepository usuarioRepository;

	@Autowired
	public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
		this.passwordEncoder = passwordEncoder;
		this.usuarioRepository = usuarioRepository;
	}

	public String encodePassword(String senha){
		return this.passwordEncoder.encode(senha);
	}

	public Usuario findByLogin(String login){
		return usuarioRepository.getReferenceUsuarioByLogin(login);
	}

	public Usuario novoUsuario(DadosCadastroUsuario dadosCadastroUsuario){
		var usuario = new Usuario();
		usuario.setLogin(dadosCadastroUsuario.login());
		usuario.setSenha(encodePassword(dadosCadastroUsuario.senha()));

		return usuario;
	}
}
