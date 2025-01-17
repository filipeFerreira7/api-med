package med.voll.api.domain.validation;

import med.voll.api.domain.dto.AgendarConsultaDTO;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class HorarioAntecedenciaValidation implements ValidadorAgendamentoDeConsulta {

    public void validar(AgendarConsultaDTO data){
        var dataConsulta = data.dateTime();

        var now = LocalDateTime.now();

        var diff = Duration.between(now,dataConsulta).toMinutes();

        if(diff < 30 ){
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
