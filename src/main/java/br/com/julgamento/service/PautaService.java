package br.com.julgamento.service;

import br.com.julgamento.model.response.PautaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PautaService {
    String cadastrar(PautaResponse pautaResponse);

    Page<PautaResponse> obterPautas(Pageable page);
}
