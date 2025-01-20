package med.voll.api.domain.validation;

import med.voll.api.domain.dto.AgendarConsultaDTO;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class HorarioFuncionamentoValidation implements ValidadorAgendamentoDeConsulta {

    public void validar(AgendarConsultaDTO data){
        var dataConsulta = data.dateTime();
        var checkSunday = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var checkHourBefore = dataConsulta.getHour() < 7 ;
        var checkHourAfter = dataConsulta.getHour() > 18 ;

        if(checkSunday || checkHourAfter || checkHourBefore) {
            throw new ValidacaoException("fora do horário","Consulta fora do horário de funcionamento. ");
        }
    }
}
