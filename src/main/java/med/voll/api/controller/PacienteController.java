package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.*;
import med.voll.api.domain.model.Paciente;
import med.voll.api.domain.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid PacienteDadosCadastroDTO data, UriComponentsBuilder uriBuilder){
       var paciente = new Paciente(data);
        repository.save(paciente);

       var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
       return ResponseEntity.created(uri).body(new DadosDetalhadoPacienteDTO(paciente));
    }


    @GetMapping
    public ResponseEntity<Page<DadosCadastroPacienteDTOResponse>>list(@PageableDefault(size = 10, sort = {"nome"}) Pageable pag){
        var page =  repository.findAllByAtivoTrue(pag).map(DadosCadastroPacienteDTOResponse::new);

    return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity put(@RequestBody @Valid DadosAtualizacaoPacienteDTO data){
        var paciente = repository.getReferenceById(data.id());
        paciente.atualizarInformacoes(data);

        return ResponseEntity.ok(new DadosDetalhadoPacienteDTO(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhadoPacienteDTO(paciente));
    }
}
