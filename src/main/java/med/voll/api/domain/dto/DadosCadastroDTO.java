package med.voll.api.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.model.Especialidade;

public record DadosCadastroDTO(
        @NotBlank(message = "O nome não pode ser branco")
        String nome,
        @NotBlank
        @Email(message = "O gmail não pode ser branco")
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}",message = "O crm não pode ser em branco e tem que haver de 4 a 6 dígitos")
        String crm,
        @NotNull(message = "Especialidade não pode ser nula")
        Especialidade especialidade,
        @NotBlank(message = "Telefone não pode ser nulo")
        String telefone,
        @NotNull
        @Valid
        EnderecoDTO endereco
) {
}
