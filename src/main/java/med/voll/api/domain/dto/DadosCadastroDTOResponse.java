package med.voll.api.domain.dto;

import med.voll.api.domain.model.Especialidade;
import med.voll.api.domain.model.Medico;

public record DadosCadastroDTOResponse(
        Long id,
        String nome,
        String email,
        String crm,
        Especialidade especialidade
) {
    public DadosCadastroDTOResponse(Medico medico){
        this(medico.getId(),medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }

    public static DadosCadastroDTOResponse valueOf (Medico medico){
        return new DadosCadastroDTOResponse(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
