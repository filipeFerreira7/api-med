package med.voll.api.domain.validation;

import jakarta.validation.ValidationException;
import med.voll.api.domain.dto.CancelarConsultaDTO;
import med.voll.api.domain.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CancelamentoHorarioAntecedenciaValidation implements ValidadorCancelamentoConsulta{
    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(CancelarConsultaDTO data) {
        var consulta = repository.getReferenceById(data.idConsulta());
        var now = LocalDateTime.now();
        var diffHours = Duration.between(now,consulta.getDateTime()).toHours();

        if(diffHours < 24 ){
            throw new ValidationException("Consulta pode ser cancelada apenas com 24h de antecedência! ");
        }
    }
}
