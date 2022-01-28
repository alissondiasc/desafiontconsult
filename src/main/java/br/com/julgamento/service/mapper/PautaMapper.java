package br.com.julgamento.service.mapper;

import br.com.julgamento.model.entity.Pauta;
import br.com.julgamento.model.response.PautaResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static br.com.julgamento.util.TextoUtil.convertStringToByte;

@AllArgsConstructor
@Component
public class PautaMapper implements AbstractMapper<Pauta, PautaResponse> {

    @Override
    public PautaResponse entidadeParaDTO(Pauta entidade) {
        return PautaResponse.builder()
                .id(entidade.getId())
                .tema(entidade.getTema())
                .assunto(convertStringToByte(entidade.getAssunto()))
                .build();
    }

    @Override
    public Pauta dtoParaEntidade(PautaResponse pautaResponse) {
        return Pauta.builder()
                .tema(pautaResponse.getTema())
                .assunto(pautaResponse.getAssunto().getBytes())
                .build();
    }
}
