package med.voll.api.domain.repository;

import med.voll.api.domain.model.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente,Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable pag);

    @Query(value = "select p.ativo from Paciente p where p.id = :id")
    Boolean findAtivoById(Long id);
}
