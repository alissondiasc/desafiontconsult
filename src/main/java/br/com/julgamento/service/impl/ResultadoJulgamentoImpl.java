package br.com.julgamento.service.impl;

import br.com.julgamento.kafkaService.Producer;
import br.com.julgamento.model.entity.ResultadoJulgamento;
import br.com.julgamento.model.entity.SessaoJulgamento;
import br.com.julgamento.model.entity.VotoParticipacao;
import br.com.julgamento.model.entity.enums.Indicador;
import br.com.julgamento.model.entity.enums.ResultadoVotacao;
import br.com.julgamento.model.response.DetalhesResultadoJulgamentoResponse;
import br.com.julgamento.model.response.ResultadoJulgamentoResponse;
import br.com.julgamento.repository.ResultadoJulgamentoRepository;
import br.com.julgamento.repository.SessaoJulgamentoRepository;
import br.com.julgamento.repository.VotoParticipacaoRepository;
import br.com.julgamento.service.ResultadoJulgamentoService;
import br.com.julgamento.service.VotoParticipacaoService;
import br.com.julgamento.service.mapper.PautaMapper;
import br.com.julgamento.service.mapper.ResultadoJulgamentoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.Objects.nonNull;

//import org.springframework.kafka

@AllArgsConstructor
@Service
@Slf4j
public class ResultadoJulgamentoImpl implements ResultadoJulgamentoService {

    private final Producer topicProducer;
    private ResultadoJulgamentoRepository resultadoJulgamentoRepository;
    private SessaoJulgamentoRepository sessaoJulgamentoRepository;
    private VotoParticipacaoRepository votoParticipacaoRepository;
    private VotoParticipacaoService votoParticipacaoService;
    private ResultadoJulgamentoMapper resultadoJulgamentoMapper;
    private PautaMapper pautaMapper;

    @Override
    public ResultadoJulgamentoResponse cadastra(String idJulgamentoSessao) {
        ResultadoVotacao resultadoVotacao = votoParticipacaoService.apurarVotosSessaoJulgamento(idJulgamentoSessao);
        ResultadoJulgamento resultadoJulgamento = ResultadoJulgamento.builder().sessaoJulgamento(SessaoJulgamento.builder().id(idJulgamentoSessao).build()).build();
        resultadoJulgamento.setResultadoVotacao(resultadoVotacao);
        resultadoJulgamentoRepository.save(resultadoJulgamento);
        return resultadoJulgamentoMapper.entidadeParaDTO(resultadoJulgamento);
    }

    @Override
    public ResultadoJulgamentoResponse encerrarAndCadastrarResultadoJulg(String idSessaoJulgamento) throws Exception {
        ResultadoJulgamento resultadoJulgamento = resultadoJulgamentoRepository.findBySessaoJulgamento(SessaoJulgamento.builder().id(idSessaoJulgamento).build());
        if (nonNull(resultadoJulgamento)) {
            return resultadoJulgamentoMapper.entidadeParaDTO(resultadoJulgamento);
        }
        SessaoJulgamento sessaoJulgamento = sessaoJulgamentoRepository.findById(idSessaoJulgamento).orElseThrow(() -> new Exception("Não existe sessão julgamento com esse ID: " + idSessaoJulgamento));
        ResultadoJulgamentoResponse resultadoJulgamentoResponse = cadastra(idSessaoJulgamento);
        sessaoJulgamento.setIndSessaoAberta(Indicador.N);
        sessaoJulgamentoRepository.save(sessaoJulgamento);
        topicProducer.send(resultadoJulgamentoResponse);
        return resultadoJulgamentoResponse;
    }

    @Override
    public DetalhesResultadoJulgamentoResponse obterDetalhesResultadoJulgamento(String idResultado) throws Exception {
        ResultadoJulgamento resultadoJulgamento = resultadoJulgamentoRepository.findById(idResultado).orElseThrow(() -> new Exception("Não existe resultado de julgamento com esse ID: " + idResultado));
        List<VotoParticipacao> votoParticipacaos = votoParticipacaoRepository.findBySessaoJulgamento(SessaoJulgamento.builder().id(resultadoJulgamento.getSessaoJulgamento().getId()).build());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return DetalhesResultadoJulgamentoResponse.builder()
                .idJulgamento(resultadoJulgamento.getSessaoJulgamento().getId())
                .dataJulgamento(resultadoJulgamento.getSessaoJulgamento().getDataInicio().format(formatter))
                .pautaResponse(pautaMapper.entidadeParaDTO(resultadoJulgamento.getSessaoJulgamento().getPauta()))
                .resultadojulamento(resultadoJulgamento.getResultadoVotacao().getValor())
                .votosContras(votoParticipacaos.stream().filter(votoParticipacao -> !votoParticipacao.getVoto().getValor()).count())
                .votosFavoraveis(votoParticipacaos.stream().filter(votoParticipacao -> votoParticipacao.getVoto().getValor()).count())
                .build();
    }

    @Override
    public Page<ResultadoJulgamentoResponse> obterResultadosJulgamento(Pageable pageable) {
        return resultadoJulgamentoMapper.pageEntidadeParaPageDTO(resultadoJulgamentoRepository.findAll(pageable));
    }
}
