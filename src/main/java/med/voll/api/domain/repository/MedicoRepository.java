package med.voll.api.domain.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.model.Especialidade;
import med.voll.api.domain.model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
@Repository
public interface MedicoRepository extends JpaRepository<Medico,Long> {

    Page<Medico> findAllByAtivoTrue(Pageable pag);

    @Query(value = "select m from Medico m where m.ativo = true and m.especialidade = :especialidade and m.id not in(select c.medico.id from Consulta c where c.dateTime = :dateTime and c.motivoCancelamento is null) order by rand() limit 1")
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime dateTime);

    @Query(value = "select m.ativo from Medico m where m.id = :id")
    Boolean findAtivoById(Long id);

    boolean existsByCrm(String crm);

    boolean existsByEmail(String email);
}
