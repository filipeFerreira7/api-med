package med.voll.api.domain.validation;

import med.voll.api.domain.dto.CancelarConsultaDTO;

public interface ValidadorCancelamentoConsulta {
    void validar(CancelarConsultaDTO data);
}
