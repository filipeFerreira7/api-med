package med.voll.api.domain.repository;

import med.voll.api.domain.model.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteIdAndDateTimeBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicoIdAndDateTimeAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime dateTime);

//    @Query("select * from Consulta c where c.ativo = true")
//    Page<Consulta> encontrarTodosAtivos(Pageable pag);

    Page<Consulta> findAllByAtivoTrue(Pageable pag);
}
