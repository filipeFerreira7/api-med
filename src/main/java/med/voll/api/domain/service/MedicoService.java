package med.voll.api.domain.service;

import med.voll.api.domain.dto.DadosAtualizacaoMedicoDTO;
import med.voll.api.domain.dto.DadosCadastroDTO;
import med.voll.api.domain.dto.DadosCadastroDTOResponse;
import med.voll.api.domain.dto.DadosDetalhadoMedicoDTO;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.repository.MedicoRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;


    public Medico cadastrar(DadosCadastroDTO data, UriComponentsBuilder uriBuilder) {

        if(medicoRepository.existsByCrm(data.crm())){
            throw new ValidacaoException("crm","CRM ja existente");
        }

        if(medicoRepository.existsByEmail(data.email())){
            throw new ValidacaoException("email","Email ja existente");
        }

        Medico medico = new Medico(data);
        medicoRepository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return medico;
    }

    public Page<DadosCadastroDTOResponse> list(Pageable pag) {
        var page =  medicoRepository.findAllByAtivoTrue(pag).map(DadosCadastroDTOResponse::new);
        return page;
    }

    public Medico put(DadosAtualizacaoMedicoDTO data) {
        var medico = medicoRepository.getReferenceById(data.id());
        if(medico == null){
            throw new ValidacaoException("id","medico inexistente, não pode ser atualizado! ");
        }

        medico.atualizarInformacoes(data);
        return medico;
    }


    public void delete(Long id) {
        var medico = medicoRepository.getReferenceById(id);

        if(medico == null){
            throw new ValidacaoException("id","ID inexistente, impossível excluir! ");
        }
        medico.excluir();
    }


    public DadosDetalhadoMedicoDTO detail(Long id) {
        var medico = medicoRepository.getReferenceById(id);
        if(medico == null){
            throw new ValidacaoException("id","ID inexistente, impossível detalhar! ");
        }

        return DadosDetalhadoMedicoDTO.valueOf(medico);
    }
}
