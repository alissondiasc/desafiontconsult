package br.com.julgamento.service;

import br.com.julgamento.client.UserClient;
import br.com.julgamento.model.entity.Usuario;
import br.com.julgamento.model.response.UserClientResponse;
import br.com.julgamento.model.response.UsuarioResponse;
import br.com.julgamento.repository.UsuarioRepository;
import br.com.julgamento.service.impl.UsuarioServiceImpl;
import br.com.julgamento.service.mapper.UsuarioMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioSevice;
    @Mock
    private UserClient userClient;
    @Mock
    private UsuarioMapper usuarioMapper;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioResponse usuarioResponse;
    @Mock
    private Pageable pageable;
    @Mock
    private UserClientResponse userClientResponseValid;
    @Mock
    private UserClientResponse userClientResponseInvalid;


    @Before
    public void setUp() {
        pageable = PageRequest.of(0, 10, Sort.Direction.valueOf("ASC"), "nome");
        userClientResponseValid = UserClientResponse.builder()
                .status("ABLE_TO_VOTE")
                .build();
        userClientResponseInvalid = UserClientResponse.builder()
                .status("UNABLE_TO_VOTE")
                .build();

        usuarioResponse = UsuarioResponse.builder()
                .CPF("037823336098")
                .nome("Teste Sucesso")
                .build();

    }

    @Test
    public void cadastrarTest() {

        when(userClient.validarCPF(usuarioResponse.getCPF())).thenReturn(userClientResponseInvalid);
        String retorno = usuarioSevice.cadastrar(this.usuarioResponse);
        assertThat(retorno).isNotNull();
        assertEquals(retorno, "CPF inválido.");
    }

    @Test
    public void cadastrarCPFInvalidoTest() {

        when(userClient.validarCPF(usuarioResponse.getCPF())).thenReturn(userClientResponseValid);
        String retorno = usuarioSevice.cadastrar(this.usuarioResponse);
        assertThat(retorno).isNotNull();
        assertEquals(retorno, "Usuario criado com sucesso.");
    }

    @Test
    public void obterUsuarioTest() {

        when(usuarioRepository.findAll(pageable)).thenReturn(new PageImpl<>(Arrays.asList(Usuario.builder().build()), pageable, 1));
        when(usuarioMapper.pageEntidadeParaPageDTO(new PageImpl<>(Arrays.asList(Usuario.builder().build()), pageable, 1))).thenReturn(new PageImpl<>(Arrays.asList(UsuarioResponse.builder().build()), pageable, 1));
        Page<UsuarioResponse> retorno = usuarioSevice.obterUsuario(pageable);
        assertThat(retorno).isNotNull();
        assertEquals(retorno.getContent().size(), 1);
    }


}
