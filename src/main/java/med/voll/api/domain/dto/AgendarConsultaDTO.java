package med.voll.api.domain.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.model.Especialidade;

import java.time.LocalDateTime;

public record AgendarConsultaDTO(

         Long idMedico,

         @NotNull
         Long idPaciente,

        @NotNull
        @Future
        LocalDateTime dateTime,

        Especialidade especialidade
) { }
