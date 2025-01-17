package med.voll.api.domain.dto;

import med.voll.api.domain.model.Paciente;

public record DadosCadastroPacienteDTOResponse(
        Long id,
        String nome,
        String email,
        String cpf
) {
    public DadosCadastroPacienteDTOResponse(Paciente paciente){
        this(paciente.getId(),paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
