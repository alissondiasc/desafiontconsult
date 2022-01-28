package br.com.julgamento.service.mapper;

import br.com.julgamento.model.entity.SessaoJulgamento;
import br.com.julgamento.model.entity.Usuario;
import br.com.julgamento.model.entity.VotoParticipacao;
import br.com.julgamento.model.response.VotoParticipacaoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Component
public class VotoParticipacaoMapper implements AbstractMapper<VotoParticipacao, VotoParticipacaoResponse> {
    @Override
    public VotoParticipacaoResponse entidadeParaDTO(VotoParticipacao entidade) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return VotoParticipacaoResponse.builder()
                .idAssociado(entidade.getAssociado().getId())
                .nomeAssociado(entidade.getAssociado().getNome())
                .votoParticipacao(entidade.getVoto())
                .dataHoraSessaoJulgamento(entidade.getSessaoJulgamento().getDataInicio().format(formatter))
                .pautaJulgada(entidade.getSessaoJulgamento().getPauta().getTema())
                .idJulgamento(entidade.getSessaoJulgamento().getId())
                .build();
    }

    @Override
    public VotoParticipacao dtoParaEntidade(VotoParticipacaoResponse votoParticipacaoResponse) {
        return VotoParticipacao.builder()
                .associado(Usuario.builder().id(votoParticipacaoResponse.getIdAssociado()).build())
                .sessaoJulgamento(SessaoJulgamento.builder().id(votoParticipacaoResponse.getIdJulgamento()).build())
                .voto(votoParticipacaoResponse.getVotoParticipacao())
                .build();
    }
}
