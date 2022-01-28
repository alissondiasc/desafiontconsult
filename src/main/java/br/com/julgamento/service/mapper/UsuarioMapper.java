package br.com.julgamento.service.mapper;

import br.com.julgamento.model.entity.Usuario;
import br.com.julgamento.model.response.UsuarioResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UsuarioMapper implements AbstractMapper<Usuario, UsuarioResponse> {

    @Override
    public UsuarioResponse entidadeParaDTO(Usuario entidade) {
        return UsuarioResponse.builder()
                .id(entidade.getId())
                .nome(entidade.getNome())
                .CPF(entidade.getCpf())
                .build();
    }

    @Override
    public Usuario dtoParaEntidade(UsuarioResponse entidade) {
        return Usuario.builder()
                .nome(entidade.getNome())
                .cpf(entidade.getCPF())
                .build();
    }

}
