package br.com.julgamento.service;

import br.com.julgamento.model.entity.SessaoJulgamento;
import br.com.julgamento.model.entity.Usuario;
import br.com.julgamento.model.entity.VotoParticipacao;
import br.com.julgamento.model.entity.enums.Indicador;
import br.com.julgamento.model.entity.enums.ResultadoVotacao;
import br.com.julgamento.model.response.VotoParticipacaoResponse;
import br.com.julgamento.repository.VotoParticipacaoRepository;
import br.com.julgamento.service.impl.VotoParticipacaoServiceImpl;
import br.com.julgamento.service.mapper.VotoParticipacaoMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class VotoParticipacaoImplTest {

    @InjectMocks
    private VotoParticipacaoServiceImpl votoParticipacaoService;
    @Mock
    private SessaoJulgamentoService sessaoJulgamentoService;
    @Mock
    private VotoParticipacaoRepository votoParticipacaoRepository;
    @Mock
    private VotoParticipacaoMapper votoParticipacaoMapper;
    @Mock
    private VotoParticipacaoResponse votoParticipacaoResponse;
    @Mock
    private Pageable pageable;

    @Before
    public void setUp() {
        pageable = PageRequest.of(0, 10, Sort.Direction.valueOf("ASC"), "voto");
        votoParticipacaoResponse = VotoParticipacaoResponse.builder()
                .idJulgamento("1")
                .idAssociado("2").build();


    }

    @Test
    public void realizarVotoSessaoJulgamentoTest() throws Exception {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        LocalDateTime dataFim = dataHoraAtual.plusDays(1);

        when(sessaoJulgamentoService.obterSessaoJulgamentoPorId(votoParticipacaoResponse.getIdJulgamento())).thenReturn(SessaoJulgamento.builder().indSessaoAberta(Indicador.S).dataFim(dataFim).build());
        when(votoParticipacaoRepository
                .findByAssociadoAndSessaoJulgamento(Usuario.builder().id(votoParticipacaoResponse.getIdAssociado()).build(), SessaoJulgamento.builder().id(votoParticipacaoResponse.getIdJulgamento()).build()))
                .thenReturn(Optional.empty());

        String retorno = votoParticipacaoService.realizarVotoSessaoJulgamento(this.votoParticipacaoResponse);
        assertThat(retorno).isNotNull();
        assertEquals(retorno, "Operação Relaizada com sucesso.");
    }

    @Test
    public void realizarVotoSessaoJulgamentoTestValidationException() throws Exception {
        LocalDateTime dataFim = LocalDateTime.now();

        when(sessaoJulgamentoService.obterSessaoJulgamentoPorId(votoParticipacaoResponse.getIdJulgamento())).thenReturn(SessaoJulgamento.builder().indSessaoAberta(Indicador.S).dataFim(dataFim).build());
        when(votoParticipacaoRepository
                .findByAssociadoAndSessaoJulgamento(Usuario.builder().id(votoParticipacaoResponse.getIdAssociado()).build(), SessaoJulgamento.builder().id(votoParticipacaoResponse.getIdJulgamento()).build()))
                .thenReturn(Optional.empty());
        String retorno = votoParticipacaoService.realizarVotoSessaoJulgamento(this.votoParticipacaoResponse);
        assertEquals(retorno, "Sessão de julgamento já encerrada.");
    }

    @Test
    public void apurarVotosEmpateTest() {
        when(votoParticipacaoRepository.findBySessaoJulgamento(SessaoJulgamento.builder().id(votoParticipacaoResponse.getIdJulgamento()).build()))
                .thenReturn(Arrays.asList(VotoParticipacao.builder().voto(Indicador.S).build(), VotoParticipacao.builder().voto(Indicador.N).build()));
        ResultadoVotacao retorno = votoParticipacaoService.apurarVotosSessaoJulgamento(this.votoParticipacaoResponse.getIdJulgamento());
        assertEquals(retorno, ResultadoVotacao.E);
    }

    @Test
    public void apurarVotosVencedorTest() {
        when(votoParticipacaoRepository.findBySessaoJulgamento(SessaoJulgamento.builder().id(votoParticipacaoResponse.getIdJulgamento()).build()))
                .thenReturn(Arrays.asList(VotoParticipacao.builder().voto(Indicador.S).build()));
        ResultadoVotacao retorno = votoParticipacaoService.apurarVotosSessaoJulgamento(this.votoParticipacaoResponse.getIdJulgamento());
        assertEquals(retorno, ResultadoVotacao.V);
    }

    @Test
    public void apurarVotosPerdedorTest() {
        when(votoParticipacaoRepository.findBySessaoJulgamento(SessaoJulgamento.builder().id(votoParticipacaoResponse.getIdJulgamento()).build()))
                .thenReturn(Arrays.asList(VotoParticipacao.builder().voto(Indicador.N).build()));
        ResultadoVotacao retorno = votoParticipacaoService.apurarVotosSessaoJulgamento(this.votoParticipacaoResponse.getIdJulgamento());
        assertEquals(retorno, ResultadoVotacao.P);
    }

    @Test
    public void obterVotosTest() {
        when(votoParticipacaoRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(Arrays.asList(VotoParticipacao.builder().build()), pageable, 0));
        when(votoParticipacaoMapper.pageEntidadeParaPageDTO(new PageImpl<>(Arrays.asList(VotoParticipacao.builder().build()), pageable, 0)))
                .thenReturn(new PageImpl<>(Arrays.asList(VotoParticipacaoResponse.builder().build()), pageable, 0));
        Page<VotoParticipacaoResponse> retorno = votoParticipacaoService.obterVotos(this.pageable);
        assertEquals(retorno.getContent().size(), 1);
    }
}
