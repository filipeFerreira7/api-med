package med.voll.api.domain.validation;

import med.voll.api.domain.dto.AgendarConsultaDTO;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSemConsultaNoDiaValidation implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(AgendarConsultaDTO data){
        var primeiroHorario = data.dateTime().withHour(7);
        var ultimoHorario = data.dateTime().withHour(18);
        var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDateTimeBetween(data.idPaciente(), primeiroHorario, ultimoHorario);

        if(pacientePossuiOutraConsultaNoDia){
            throw new ValidacaoException("conflito de consultas","Paciente já possui uma consulta agendada nesse dia. ");
        }
    }
}
