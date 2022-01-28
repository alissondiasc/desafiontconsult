package br.com.julgamento.service;

import br.com.julgamento.model.entity.enums.ResultadoVotacao;
import br.com.julgamento.model.response.VotoParticipacaoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface VotoParticipacaoService {

    String realizarVotoSessaoJulgamento(VotoParticipacaoResponse votoParticipacaoResponse);

    ResultadoVotacao apurarVotosSessaoJulgamento(String idSessaoJulgamento);

    Page<VotoParticipacaoResponse> obterVotos(Pageable pageable);
}
