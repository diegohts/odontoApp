package com.odontoApp.api.domain.convenio;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConvenioRepository extends JpaRepository<Convenio, Long> {
	Page<Convenio> findAllByAtivoTrue(Pageable paginacao);
}
