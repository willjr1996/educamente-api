package br.com.ifpe.educamente_api.api.comportamento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifpe.educamente_api.modelo.comportamento.Comportamento;
import br.com.ifpe.educamente_api.modelo.comportamento.ComportamentoService;
import jakarta.validation.Valid;

@RestController // determina que essa classe e do tipo Rest
@RequestMapping("/api/comportamento") // DETERMINA A URL para acesar as funçoes dessa classe
@CrossOrigin // recber requisiçoes javascript
public class ComportamentoController {
    @Autowired
    private ComportamentoService comportamentoService;

    // @PostMapping // pra acessar essa funçao tem que fazer requisiçoes POST
    // public ResponseEntity<Comportamento> save(@RequestBody @Valid ComportamentoRequest request) {

    //     Comportamento comportamento = comportamentoService.save(request);
    //     return new ResponseEntity<>(comportamento, HttpStatus.CREATED);
    // }

    @GetMapping
    public List<Comportamento> listarTodos() {
        return comportamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Comportamento obterPorID(@PathVariable Long id) {
        return comportamentoService.obterPorID(id);
    }

        @PutMapping("/{id}")
    public ResponseEntity<Comportamento> update(@PathVariable("id") Long id, @RequestBody ComportamentoRequest request) {
        // Chama o serviço de atualização, passando o id e a requisição
        Comportamento comportamentoAtualizado = comportamentoService.update(id, request);
    
        // Retorna o status 200 (OK) com o objeto atualizado no corpo da resposta
        return ResponseEntity.ok(comportamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        comportamentoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
