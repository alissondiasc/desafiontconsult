package br.com.julgamento.resource;

import br.com.julgamento.model.response.UsuarioResponse;
import br.com.julgamento.service.UsuarioSevice;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@SuppressWarnings({"java:S4834", "squid:S4834"})
@RequestMapping("usuario")
public class UsuarioResource {

    private UsuarioSevice usuarioSevice;

    @ApiOperation(value = "Criar associado")
    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody @Valid UsuarioResponse usuarioResponse) {
        try {
            return ResponseEntity.ok(usuarioSevice.cadastrar(usuarioResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ApiOperation(value = "Verificar validade de CPF")
    @GetMapping(value = "valida-cpf/{cpf}")
    public ResponseEntity validaCPF(@PathVariable String cpf) {
        try {
            return ResponseEntity.ok(usuarioSevice.validarCpf(cpf));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ApiOperation(value = "Obter associados cadastrados")
    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> obterUsuario(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "conut", defaultValue = "10") Integer count,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
    ) {
        Pageable pageable = PageRequest.of(page, count, Sort.Direction.valueOf(direction), orderBy);
        return ResponseEntity.ok(usuarioSevice.obterUsuario(pageable));
    }
}
