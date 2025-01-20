package med.voll.api.domain.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.DadosAtualizacaoPacienteDTO;
import med.voll.api.domain.dto.DadosCadastroPacienteDTOResponse;
import med.voll.api.domain.dto.DadosDetalhadoPacienteDTO;
import med.voll.api.domain.dto.PacienteDadosCadastroDTO;
import med.voll.api.domain.model.Paciente;
import med.voll.api.domain.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public Paciente cadastrar(PacienteDadosCadastroDTO data, UriComponentsBuilder uriBuilder){
        var paciente = new Paciente(data);
        repository.save(paciente);
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return paciente;
    }

    public Page<DadosCadastroPacienteDTOResponse>list(Pageable pag){
        var page =  repository.findAllByAtivoTrue(pag).map(DadosCadastroPacienteDTOResponse::new);
        return page;
    }

    public Paciente put(DadosAtualizacaoPacienteDTO data){
        var paciente = repository.getReferenceById(data.id());
        paciente.atualizarInformacoes(data);

        return paciente;
    }

    public void delete( Long id){
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
    }

    public DadosDetalhadoPacienteDTO detail(Long id){
        var paciente = repository.getReferenceById(id);
            return DadosDetalhadoPacienteDTO.valueOf(paciente);
    }
}
