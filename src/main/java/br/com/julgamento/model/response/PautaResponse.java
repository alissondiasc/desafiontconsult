package br.com.julgamento.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PautaResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(hidden = true)
    private String id;
    @NotBlank(message = "Campo tema é de preenchimento obrigatório")
    private String tema;
    @NotBlank(message = "Campo assunto é de preenchimento obrigatório")
    private String assunto;
}
