package med.voll.api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.dto.AgendarConsultaDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "Consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Column(name = "data")
    private LocalDateTime dateTime;

    @Column(name = "motivo_cancelamento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivoCancelamento;

    private Boolean ativo;

    public Consulta(Long id, Medico medico, Paciente paciente, LocalDateTime dateTime) {
        this.id = id;
        this.medico = medico;
        this.paciente = paciente;
        this.dateTime = dateTime;
        this.ativo = true;
    }

    public void cancelar(MotivoCancelamento motivo){
        this.motivoCancelamento = motivo;
        this.ativo = false;
    }


}
