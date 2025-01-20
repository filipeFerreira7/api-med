package med.voll.api.domain.validation;

import med.voll.api.domain.dto.AgendarConsultaDTO;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoComOutraConsultaValidation implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(AgendarConsultaDTO data){
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndDateTimeAndMotivoCancelamentoIsNull(data.idMedico(),data.dateTime());

        if(medicoPossuiOutraConsultaNoMesmoHorario){
            throw new ValidacaoException("médico indisponível","Médico já possui outra consulta agendada nesse mesmo horário. ");
        }
    }
}
