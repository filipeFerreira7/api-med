package med.voll.api.domain.service;

import med.voll.api.domain.dto.ResponseAgendarConsulta;
import med.voll.api.domain.validation.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.validation.ValidadorCancelamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.domain.dto.AgendarConsultaDTO;
import med.voll.api.domain.dto.CancelarConsultaDTO;
import med.voll.api.domain.model.Consulta;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.domain.repository.MedicoRepository;
import med.voll.api.domain.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoConsulta> validadorCancelamento;

    public ResponseAgendarConsulta agendar(AgendarConsultaDTO data) {
        if(!pacienteRepository.existsById(data.idPaciente())){
            throw new ValidacaoException("ID do paciente informado não existe");
        }
        if(data.idMedico() != null && !medicoRepository.existsById(data.idMedico())){
            throw new ValidacaoException("ID do médico informado não existe");
        }

        validadores.forEach(v -> v.validar(data));

        var paciente = pacienteRepository.getReferenceById(data.idPaciente());
        var medico = escolherMedico(data);
        if(medico == null){
            throw new ValidacaoException("Não existe médico disponível na data. ");
        }
        var consulta = new Consulta(null,medico,paciente,data.dateTime(),null,true);

        repository.save(consulta);

        return new ResponseAgendarConsulta(consulta);
    }

    private Medico escolherMedico(AgendarConsultaDTO data) {
        if(data.idMedico() != null){
            return medicoRepository.getReferenceById(data.idMedico());
        }
        if(data.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(data.especialidade(),data.dateTime());
    }

    public void cancelar(CancelarConsultaDTO data){
        if(!repository.existsById(data.idConsulta())){
            throw new ValidacaoException("Id da consulta informado não existe!");
        }
        validadorCancelamento.forEach(v -> v.validar(data));

        var consulta = repository.getReferenceById(data.idConsulta());
        consulta.cancelar(data.motivo());
    }
}
