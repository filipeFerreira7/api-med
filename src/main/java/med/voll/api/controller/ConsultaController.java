package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.AgendarConsultaDTO;
import med.voll.api.domain.dto.CancelarConsultaDTO;
import med.voll.api.domain.dto.ResponseAgendarConsulta;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.domain.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ConsultaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid AgendarConsultaDTO data){
       var response = consultaService.agendar(data);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public  ResponseEntity cancelar(@RequestBody @Valid CancelarConsultaDTO data){
        consultaService.cancelar(data);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ResponseAgendarConsulta>>list(Pageable pag){

        var page =  repository.findAllByAtivoTrue(pag).map(ResponseAgendarConsulta::new);
        return ResponseEntity.ok(page);
    }
}
