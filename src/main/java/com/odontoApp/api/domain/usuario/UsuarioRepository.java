package com.odontoApp.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	UserDetails findBylogin(String login);
	Usuario getReferenceUsuarioByLogin(String login);

	@Query("SELECT COUNT(c) > 0 FROM Usuario c WHERE c.login = :login")
	boolean existsByLogin(String login);
}
