package com.odontoApp.api.domain.pessoa;

import com.odontoApp.api.domain.ValidacaoException;
import com.odontoApp.api.domain.pessoa.validacoes.atualizacao.ValidadorAtualizacaoPessoa;
import com.odontoApp.api.domain.pessoa.validacoes.cadastro.ValidadorCadastroPessoa;
import com.odontoApp.api.domain.usuario.Usuario;
import com.odontoApp.api.domain.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PessoaService {

	private final PessoaRepository pessoaRepository;
	private final UsuarioService usuarioService;
	private final List<ValidadorAtualizacaoPessoa> validadoresAtualizacaoPessoa;
	private final List<ValidadorCadastroPessoa> validadoresCadastroPessoa;

	@Autowired
	public PessoaService(PessoaRepository pessoaRepository, UsuarioService usuarioService,
			List<ValidadorAtualizacaoPessoa> validadoresAtualizacaoPessoa,
			List<ValidadorCadastroPessoa> validadoresCadastroPessoa) {
		this.pessoaRepository = pessoaRepository;
		this.usuarioService = usuarioService;
		this.validadoresAtualizacaoPessoa = validadoresAtualizacaoPessoa;
		this.validadoresCadastroPessoa = validadoresCadastroPessoa;
	}

	public DadosDetalhamentoPessoa detalhar(String login) {
		Usuario usuario = usuarioService.findByLogin(login);
		Pessoa pessoa = pessoaRepository.getReferenceById(usuario.getId());

		return new DadosDetalhamentoPessoa(pessoa);
	}

	public DadosDetalhamentoPessoa cadastrar(DadosCadastroPessoa dadosCadastroPessoa) {
		for (var validador : validadoresCadastroPessoa) {
			validador.validar(dadosCadastroPessoa);
		}

		var usuario = usuarioService.novoUsuario(dadosCadastroPessoa.dadosCadastroUsuario());
		var pessoa = new Pessoa(
				null,
				dadosCadastroPessoa.nome(),
				null,
				usuario,
				dadosCadastroPessoa.genero(),
				dadosCadastroPessoa.dataNascimento());

		var pessoaCadastrada = pessoaRepository.save(pessoa);

		return new DadosDetalhamentoPessoa(pessoaCadastrada);
	}

	@Transactional
	public DadosDetalhamentoPessoa atualizar(DadosAtualizacaoPessoa dados) {

		if (!pessoaRepository.existsById(dados.id()))
			throw new ValidacaoException("Pessoa com o id " + dados.id() + " não existe");

		for (var validador : validadoresAtualizacaoPessoa) {
			validador.validar(dados);
		}

		if (dados.senha() != null) {
			dados = atualizarSenha(dados);
		}

		Pessoa pessoa = pessoaRepository.findById(dados.id()).get();
		pessoa.atualizarDados(dados);

		return new DadosDetalhamentoPessoa(pessoa);
	}

	private DadosAtualizacaoPessoa atualizarSenha(DadosAtualizacaoPessoa dados) {
		var novaSenha = usuarioService.encodePassword(dados.senha());

		return new DadosAtualizacaoPessoa(
				dados.id(),
				dados.nome(),
				dados.imagemUrl(),
				dados.login(),
				novaSenha,
				dados.genero(),
				dados.dataNascimento());
	}
}
