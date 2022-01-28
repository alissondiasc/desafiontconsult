package br.com.julgamento.service.impl;

import br.com.julgamento.model.entity.SessaoJulgamento;
import br.com.julgamento.model.entity.enums.Indicador;
import br.com.julgamento.model.request.SessaoJulgamentoRequest;
import br.com.julgamento.repository.SessaoJulgamentoRepository;
import br.com.julgamento.service.SessaoJulgamentoService;
import br.com.julgamento.service.mapper.SessaoJulgamentoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;


@AllArgsConstructor
@Service
@Slf4j
public class SessaoJulgamentoServiceImpl implements SessaoJulgamentoService {

    private SessaoJulgamentoRepository sessaoJulgamentoRepository;
    private SessaoJulgamentoMapper sessaoJulgamentoMapper;

    @Override
    public String cadastrar(SessaoJulgamentoRequest sessaoJulgamentoRequest) {
        validarData(sessaoJulgamentoRequest.getDataFim(), "Data fim não pode ser anterior a data e hora atual.");
        validarData(sessaoJulgamentoRequest.getDataInicio(), "Data de início não pode ser anterior a data e hora atual.");
        SessaoJulgamento sessaoJulgamento = sessaoJulgamentoMapper.dtoParaEntidade(sessaoJulgamentoRequest);
        atualizarIndicador(sessaoJulgamento, Indicador.S);
        return "Operação realizada com sucesso.";
    }

    public void validarData(LocalDateTime data, String msg) {
        if (nonNull(data)) {
            LocalDateTime now = LocalDateTime.now();
            if (data.isBefore(now)) {
                throw new ValidationException(msg);
            }
        }
    }

    @Override
    public SessaoJulgamento obterSessaoJulgamentoPorId(String idSessaoJulgamento) throws Exception {
        return sessaoJulgamentoRepository.findById(idSessaoJulgamento).orElseThrow(() -> new Exception("Não existe sessão julgamento com esse id"));
    }


    @Override
    public void atualizarIndicador(SessaoJulgamento sessaoJulgamento, Indicador n) {
        sessaoJulgamento.setIndSessaoAberta(n);
        sessaoJulgamentoRepository.save(sessaoJulgamento);
    }

    @Override
    public Page<SessaoJulgamentoRequest> obterSessoesJulgamentos(Pageable pageable) {
        return sessaoJulgamentoMapper.pageEntidadeParaPageDTO(sessaoJulgamentoRepository.findAll(pageable));
    }
}
