package com.odontoApp.api.domain.procedimento;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
	Page<Procedimento> findAllByAtivoTrue(Pageable paginacao);

}
