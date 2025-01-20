package med.voll.api.domain.service;

import med.voll.api.domain.dto.DadosAtualizacaoMedicoDTO;
import med.voll.api.domain.dto.DadosCadastroDTO;
import med.voll.api.domain.dto.DadosCadastroDTOResponse;
import med.voll.api.domain.dto.DadosDetalhadoMedicoDTO;
import med.voll.api.domain.model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface MedicoService {

    public Medico cadastrar(DadosCadastroDTO data, UriComponentsBuilder uriBuilder);

    public Page<DadosCadastroDTOResponse> list(Pageable pag);

    public Medico put(DadosAtualizacaoMedicoDTO data);

    public void delete(Long id);

    public DadosDetalhadoMedicoDTO detail(Long id);
}
