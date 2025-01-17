package med.voll.api.domain.service;

import med.voll.api.domain.dto.DadosAtualizacaoMedicoDTO;
import med.voll.api.domain.dto.DadosCadastroDTO;
import med.voll.api.domain.dto.DadosCadastroDTOResponse;
import med.voll.api.domain.dto.DadosDetalhadoMedicoDTO;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;
    @Override
    public Medico cadastrar(DadosCadastroDTO data, UriComponentsBuilder uriBuilder) {
        Medico medico = new Medico(data);
        medicoRepository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return medico;
    }

    @Override
    public ResponseEntity<Page<DadosCadastroDTOResponse>> list(Pageable pag) {
        return null;
    }

    @Override
    public ResponseEntity put(DadosAtualizacaoMedicoDTO data) {
        return null;
    }

    @Override
    public ResponseEntity delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity detail(Long id) {
        return null;
    }
}
