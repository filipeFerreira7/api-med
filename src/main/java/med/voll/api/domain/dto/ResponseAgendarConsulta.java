package med.voll.api.domain.dto;

import med.voll.api.domain.model.Consulta;

import java.time.LocalDateTime;

public record ResponseAgendarConsulta (
        Long id,
        Long idMedico,
        Long idPaciente,
        LocalDateTime dateTime
) {

    public ResponseAgendarConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(),consulta.getPaciente().getId(),consulta.getDateTime());
    }
}
