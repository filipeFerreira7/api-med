package med.voll.api.domain.repository;

import med.voll.api.domain.dto.EnderecoDTO;
import med.voll.api.domain.dto.PacienteDadosCadastroDTO;
import med.voll.api.domain.model.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando o unico medico cadastrado não está disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        

        var nextMonday10Am = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var freeDoctor = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,nextMonday10Am);
        assertThat(freeDoctor).isNull();

    }

    private PacienteDadosCadastroDTO dataPaciente(String nome, String email, String cpf){
        return new PacienteDadosCadastroDTO(nome,email,"6199999999",cpf, dadosEndereco());
    }

    private EnderecoDTO dadosEndereco(){
        return new EnderecoDTO("rua xpto", "bairro","000000000","Brasillia","DF",null,null);
    }
}