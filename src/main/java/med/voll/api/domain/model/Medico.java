package med.voll.api.domain.model;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.dto.DadosAtualizacaoMedicoDTO;
import med.voll.api.domain.dto.DadosCadastroDTO;

@Table(name = "Medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String crm;
    private String telefone;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;


    public Medico(DadosCadastroDTO data) {
        this.ativo = true;
    this.nome = data.nome();
    this.email = data.email();
    this.crm = data.crm();
    this.telefone = data.telefone();
    this.especialidade = data.especialidade();
    this.endereco = new Endereco(data.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoMedicoDTO data) {
        if(data.nome() != null) {
            this.nome = data.nome();
        }
        if(data.telefone() != null){
            this.telefone = data.telefone();
        }
        if(data.endereco() != null){
            this.endereco.atualizarInformacoes(data.endereco());
        }

    }

    public void excluir() {
        this.ativo = false;
    }
}
