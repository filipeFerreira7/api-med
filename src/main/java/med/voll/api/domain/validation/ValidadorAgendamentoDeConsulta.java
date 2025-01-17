package med.voll.api.domain.validation;

import med.voll.api.domain.dto.AgendarConsultaDTO;

public interface ValidadorAgendamentoDeConsulta {

    void validar(AgendarConsultaDTO data);
}
