package med.voll.api.domain.dto;

import med.voll.api.domain.model.Endereco;
import med.voll.api.domain.model.Especialidade;
import med.voll.api.domain.model.Medico;

public record DadosDetalhadoMedicoDTO(
        Long id,
        String nome,
        String email,
        String crm,
        String telefone,
        Especialidade especialidade,
        Endereco endereco)
{

public DadosDetalhadoMedicoDTO(Medico medico){
    this(
            medico.getId(),
            medico.getNome(),
            medico.getEmail(),
            medico.getCrm(),
            medico.getTelefone(),
            medico.getEspecialidade(),
            medico.getEndereco());
}
}
