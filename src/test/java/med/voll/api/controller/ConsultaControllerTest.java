package med.voll.api.controller;

import med.voll.api.domain.dto.AgendarConsultaDTO;
import med.voll.api.domain.dto.ResponseAgendarConsulta;
import med.voll.api.domain.model.Especialidade;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.domain.service.ConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AgendarConsultaDTO> agendarConsultaJson;

    @Autowired
    private JacksonTester<ResponseAgendarConsulta> responseAgendarConsultaJson;

    @MockitoBean
    private ConsultaService service;


    @Test
    @DisplayName("Deveria devolver 400 quando informações estão inválidas")
    @WithMockUser
    void agendarCenario1() throws Exception {
        //disparar requisição para o controller e guardei uma response
       var response =  mvc.perform(MockMvcRequestBuilders.post("/consultas"))
                .andReturn().getResponse();

       assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver 200 quando informações estão válidas")
    @WithMockUser
    void agendarCenario2() throws Exception {
        var dateTime = LocalDateTime.now().plusHours(2);

        var especialidade = Especialidade.CARDIOLOGIA;
        //disparar requisição para o controller e guardei uma response

        var dataDetail = new ResponseAgendarConsulta(null,2l,5l,dateTime);

                when(service.agendar(any())).thenReturn(dataDetail);

        var response =  mvc.perform(MockMvcRequestBuilders
                                    .post("/consultas")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(agendarConsultaJson.write(
                                                    new AgendarConsultaDTO(2l,5l,dateTime,especialidade)
                                            ).getJson())
                                     )
                                    .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = responseAgendarConsultaJson.write(dataDetail).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

}

