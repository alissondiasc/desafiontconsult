package br.com.julgamento.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class DetalhesResultadoJulgamentoResponse {

    private String idJulgamento;

    private PautaResponse pautaResponse;

    private long votosContras;

    private long votosFavoraveis;

    private String resultadojulamento;

    private String dataJulgamento;

}
