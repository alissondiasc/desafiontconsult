package br.com.julgamento.service;

import br.com.julgamento.model.entity.SessaoJulgamento;
import br.com.julgamento.model.entity.enums.Indicador;
import br.com.julgamento.model.request.SessaoJulgamentoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SessaoJulgamentoService {

    String cadastrar(SessaoJulgamentoRequest sessaoJulgamentoRequest);

    SessaoJulgamento obterSessaoJulgamentoPorId(String idSessaoJulgamento) throws Exception;

    void atualizarIndicador(SessaoJulgamento sessaoJulgamento, Indicador n);

    Page<SessaoJulgamentoRequest> obterSessoesJulgamentos(Pageable pageable);
}
