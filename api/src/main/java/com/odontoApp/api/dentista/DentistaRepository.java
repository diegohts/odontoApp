package com.odontoApp.api.dentista;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {
	Page<Dentista> findAllByAtivoTrue(Pageable paginacao);
}
