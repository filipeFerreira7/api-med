package med.voll.api.domain.validation;

import med.voll.api.domain.dto.AgendarConsultaDTO;
import med.voll.api.domain.repository.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteAtivoValidation implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacienteRepository repository;

    public  void validar(AgendarConsultaDTO data){
        if(data.idPaciente() == null){
            return;

        }
        var medicoIsAtivo = repository.findAtivoById(data.idPaciente());
        if(!medicoIsAtivo){
            throw new ValidacaoException("paciente excluido","Consulta não pode ser agendada com paciente excluído. ");
        }
    }
}
