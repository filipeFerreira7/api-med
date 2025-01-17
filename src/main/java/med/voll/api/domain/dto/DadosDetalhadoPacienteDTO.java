package med.voll.api.domain.dto;

import med.voll.api.domain.model.Endereco;
import med.voll.api.domain.model.Paciente;

public record DadosDetalhadoPacienteDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco)
{

public DadosDetalhadoPacienteDTO(Paciente paciente){
    this(
            paciente.getId(),
            paciente.getNome(),
            paciente.getEmail(),
            paciente.getTelefone(),
            paciente.getCpf(),
            paciente.getEndereco());
}
}
