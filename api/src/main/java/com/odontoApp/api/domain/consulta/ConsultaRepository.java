package com.odontoApp.api.domain.consulta;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

	boolean existsByDentistaIdAndData(Long idDentista, LocalDateTime data);
}
