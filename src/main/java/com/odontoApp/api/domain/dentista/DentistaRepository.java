package com.odontoApp.api.domain.dentista;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {
	Page<Dentista> findAllByAtivoTrue(Pageable paginacao);

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
			and
			c.motivoCancelamento is null
		)
		order by rand()
		limit 1
		""")
	Dentista escolherDentistaAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

	@Query("""
		select d.ativo
		from Dentista d
		where
		d.id = :id
		""")
	Boolean findAtivoById(Long id);

	@Query("SELECT COUNT(d) > 0 FROM Dentista d WHERE d.cro = :cro")
	Boolean existisByCro(String cro);

	@Query("SELECT COUNT(d) > 0 FROM Dentista d WHERE d.email = :email")
	Boolean existisByEmail(String email);
}
