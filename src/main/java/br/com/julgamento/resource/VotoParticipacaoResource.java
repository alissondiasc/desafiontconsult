package br.com.julgamento.resource;

import br.com.julgamento.model.response.VotoParticipacaoResponse;
import br.com.julgamento.service.VotoParticipacaoService;
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
@RequestMapping("voto-participacao")
public class VotoParticipacaoResource {

    private VotoParticipacaoService votoParticipacaoService;

    @ApiOperation(value = "Lançar voto associado")
    @PostMapping
    public ResponseEntity<String> realizarVoto(@RequestBody @Valid VotoParticipacaoResponse votoParticipacaoResponse) {
        try {
            return ResponseEntity.ok(votoParticipacaoService.realizarVotoSessaoJulgamento(votoParticipacaoResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @ApiOperation(value = "Obter votos efetuados")
    @GetMapping
    public ResponseEntity<Page<VotoParticipacaoResponse>> obterVotos(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "conut", defaultValue = "10") Integer count,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "voto") String orderBy
    ) {
        Pageable pageable = PageRequest.of(page, count, Sort.Direction.valueOf(direction), orderBy);
        return ResponseEntity.ok(votoParticipacaoService.obterVotos(pageable));
    }
}
