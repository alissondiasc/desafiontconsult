package br.com.julgamento.service;

import br.com.julgamento.model.response.UserClientResponse;
import br.com.julgamento.model.response.UsuarioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioSevice {

    String cadastrar(UsuarioResponse usuarioResponse);

    UserClientResponse validarCpf(String cpf);

    Page<UsuarioResponse> obterUsuario(Pageable pageable);
}
