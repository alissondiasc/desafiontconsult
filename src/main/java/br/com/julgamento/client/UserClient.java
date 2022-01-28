package br.com.julgamento.client;

import br.com.julgamento.model.response.UserClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "valida-cpf", url = "${usuario.client.url}")
public interface UserClient {

    @GetMapping(value = "/{cpf}")
    UserClientResponse validarCPF(@PathVariable String cpf);
}
