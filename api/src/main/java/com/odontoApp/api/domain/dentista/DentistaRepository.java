package com.odontoApp.api.domain.dentista;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {
	Page<Dentista> findAllByAtivoTrue(Pageable paginacao);

	// como o metodo abaixo escreveu em portugues, nao esta seguindo o padrao de nomenclatura em ingles. Entao vamos digitar a consulta SQL
	// A consulta abaixo e para trazer um dentista de maneira randomica cujo sejam ativos de uma determinada especialidade.
	// E que o id não seja de um dentista que tenha consulta em uma determinada data marcada
	@Query("""
		select d from Dentista d
		where
		d.ativo = 1
		and
		d.especialidade = :especialidade
		and
		d.id not in(
			select c.dentista.id from Consulta c
			where
			c.data = :data
		)
		order by rand()
		limit 1
		""")
	Dentista escolherDentistaAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);
}
