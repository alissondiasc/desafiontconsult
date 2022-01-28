package br.com.julgamento.service.impl;

import br.com.julgamento.client.UserClient;
import br.com.julgamento.model.response.UserClientResponse;
import br.com.julgamento.model.response.UsuarioResponse;
import br.com.julgamento.repository.UsuarioRepository;
import br.com.julgamento.service.UsuarioSevice;
import br.com.julgamento.service.mapper.UsuarioMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@AllArgsConstructor
@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioSevice {

    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;
    private UserClient userClient;

    @Override
    public String cadastrar(UsuarioResponse usuarioResponse) {

        usuarioResponse.setCPF(usuarioResponse.getCPF().replaceAll("[^0-9]", ""));
        UserClientResponse userClientResponse = userClient.validarCPF(usuarioResponse.getCPF());
        if (userClientResponse.isValido()) {
            usuarioRepository.save(usuarioMapper.dtoParaEntidade(usuarioResponse));
            return "Usuario criado com sucesso.";
        }
        return "CPF inválido.";
    }

    @Override
    public UserClientResponse validarCpf(String cpf) {
        String novoCpf = cpf.replaceAll("[^0-9]", "");
        if (novoCpf.length() == 11) {
            return userClient.validarCPF(novoCpf);
        }
        throw new ValidationException("CPF inválido");

    }

    @Override
    public Page<UsuarioResponse> obterUsuario(Pageable pageable) {
        return usuarioMapper.pageEntidadeParaPageDTO(usuarioRepository.findAll(pageable));
    }
}
