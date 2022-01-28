package br.com.julgamento.service.impl;

import br.com.julgamento.model.response.PautaResponse;
import br.com.julgamento.repository.PautaRepository;
import br.com.julgamento.service.PautaService;
import br.com.julgamento.service.mapper.PautaMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class PautaServiceImpl implements PautaService {

    private PautaRepository pautaRepository;
    private PautaMapper pautaMapper;

    @Override
    public String cadastrar(PautaResponse pautaResponse) {
        pautaRepository.save(pautaMapper.dtoParaEntidade(pautaResponse));
        return "Puata criada com sucesso.";
    }

    @Override
    public Page<PautaResponse> obterPautas(Pageable pageable) {
        return pautaMapper.pageEntidadeParaPageDTO(pautaRepository.findAll(pageable));
    }
}
