package com.odontoApp.api.domain.paciente;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

	@Query("""
		select p.ativo
		from Paciente p
		where
		p.id = :id
		""")
	Boolean findAtivoById(Long id);

	@Query("SELECT COUNT(p) > 0 FROM Paciente p WHERE p.email = :email")
	Boolean existsByEmail(String email);

	@Query("SELECT COUNT(p) > 0 FROM Paciente p WHERE p.cpf = :cpf")
	Boolean existisByCpf(String cpf);
}
