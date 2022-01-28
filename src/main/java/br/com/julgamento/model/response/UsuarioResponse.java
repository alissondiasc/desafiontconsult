package br.com.julgamento.model.response;

import br.com.julgamento.util.annotations.UnicoCPF;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class UsuarioResponse {
    @ApiModelProperty(hidden = true)
    private String id;
    @NotBlank(message = "O campo nome é de preenchimento obrigatório.")
    private String nome;

    @NotEmpty(message = "O campo CPF é de preenchimento obrigatório.")
    @CPF(message = "Verifique seu CPF e tente novamente")
    @UnicoCPF(message = "Já existe um usuário com esse CPF.")
    private String CPF;
}
