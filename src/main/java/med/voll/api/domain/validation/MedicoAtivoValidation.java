package med.voll.api.domain.validation;

import med.voll.api.domain.dto.AgendarConsultaDTO;
import med.voll.api.domain.repository.MedicoRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoAtivoValidation implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicoRepository repository;

    public  void validar(AgendarConsultaDTO data){
        if(data.idMedico() == null){
            return;

        }
        var medicoIsAtivo = repository.findAtivoById(data.idMedico());
        if(!medicoIsAtivo){
            throw new ValidacaoException("médico indisponivel","Consulta não pode ser agendada com médico excluído. ");
        }
    }
}
