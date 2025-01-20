package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.DadosAtualizacaoMedicoDTO;
import med.voll.api.domain.dto.DadosCadastroDTO;
import med.voll.api.domain.dto.DadosCadastroDTOResponse;
import med.voll.api.domain.dto.DadosDetalhadoMedicoDTO;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.repository.MedicoRepository;
import med.voll.api.domain.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @Autowired
    private MedicoService medicoService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroDTO data, UriComponentsBuilder uriBuilder){
        Medico medico = medicoService.cadastrar(data,uriBuilder);
       return ResponseEntity.ok(DadosCadastroDTOResponse.valueOf(medico));
    }


    @GetMapping
    public ResponseEntity<Page<DadosCadastroDTOResponse>>list(@PageableDefault(size = 10, sort = {"nome"}) Pageable pag){
        var page =  medicoService.list(pag);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity put(@RequestBody @Valid DadosAtualizacaoMedicoDTO data){
        var medico = medicoService.put(data);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){


        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id){
        var medico = medicoService.detail(id);
        return ResponseEntity.ok(medico);
    }
}
