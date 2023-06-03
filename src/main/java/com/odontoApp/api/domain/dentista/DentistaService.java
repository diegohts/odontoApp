package com.odontoApp.api.domain.dentista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DentistaService {

	private final DentistaRepository dentistaRepository;

	@Autowired
	public DentistaService(DentistaRepository dentistaRepository) {
		this.dentistaRepository = dentistaRepository;
	}

	@Transactional
	public DadosDetalhamentoDentista cadastrar(DadosCadastroDentista dados){
		Dentista dentista = this.dentistaRepository.save(new Dentista(dados));

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
