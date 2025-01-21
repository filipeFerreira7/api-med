package med.voll.api.domain.repository;

import med.voll.api.domain.dto.DadosCadastroDTO;
import med.voll.api.domain.dto.EnderecoDTO;
import med.voll.api.domain.dto.PacienteDadosCadastroDTO;
import med.voll.api.domain.model.Consulta;
import med.voll.api.domain.model.Especialidade;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.model.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        //given or arrange
        var nextMonday10Am = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);


        var medico = cadastrarMedico("Medico 1", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@gmail.com","12345678910");
        cadastrarConsulta(medico,paciente,nextMonday10Am);

        //when or act
        var freeDoctor = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,nextMonday10Am);

        //then or assert
        assertThat(freeDoctor).isNull();

    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {

        var nextMonday10Am = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = cadastrarMedico("Medico 1", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        var freeDoctor = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,nextMonday10Am);
        assertThat(freeDoctor).isEqualTo(medico);

    }


    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime dateTime){
        em.persist(new Consulta(null,medico,paciente,dateTime));
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf){
        var paciente = new Paciente(dataPaciente(nome,email,cpf));
        em.persist(paciente);
        return paciente;
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade){
        var medico = new Medico(dadosMedico(nome,email,crm,especialidade));
        em.persist(medico);
        return medico;
    }

    private DadosCadastroDTO dadosMedico(String nome, String email, String crm, Especialidade especialidade){
        return new DadosCadastroDTO(nome,email,"123456",especialidade,crm,dadosEndereco());
    }

    private PacienteDadosCadastroDTO dataPaciente(String nome, String email, String cpf){
        return new PacienteDadosCadastroDTO(nome,email,"6199999999",cpf, dadosEndereco());
    }

    private EnderecoDTO dadosEndereco(){
        return new EnderecoDTO("rua xpto", "bairro","000000000","Brasillia","DF",null,null);
    }
}