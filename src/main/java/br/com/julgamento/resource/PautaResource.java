package br.com.julgamento.resource;

import br.com.julgamento.model.response.PautaResponse;
import br.com.julgamento.service.PautaService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@SuppressWarnings({"java:S4834", "squid:S4834"})
@RequestMapping("pauta")
public class PautaResource {

    private PautaService pautaService;

    @ApiOperation(value = "Criar pauta")
    @PostMapping
    public ResponseEntity<String> criarPauta(@RequestBody @Valid PautaResponse pautaResponse) {
        return ResponseEntity.ok(pautaService.cadastrar(pautaResponse));
    }

    @ApiOperation(value = "Obter pautas cadastrados")
    @GetMapping
    public ResponseEntity<Page<PautaResponse>> obterPautas(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "conut", defaultValue = "10") Integer count,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "tema") String orderBy
    ) {
        Pageable pageable = PageRequest.of(page, count, Sort.Direction.valueOf(direction), orderBy);
        return ResponseEntity.ok(pautaService.obterPautas(pageable));
    }
}
