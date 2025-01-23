package br.com.ifpe.educamente_api.api.sugestao;

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

import br.com.ifpe.educamente_api.modelo.alimentacao.AlimentacaoService;
import br.com.ifpe.educamente_api.modelo.comportamento.ComportamentoService;
import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMentalService;
import br.com.ifpe.educamente_api.modelo.sugestao.Sugestao;
import br.com.ifpe.educamente_api.modelo.sugestao.SugestaoService;
import jakarta.validation.Valid;

@RestController //determina que essa classe e do tipo Rest
@RequestMapping("/api/sugestao") //DETERMINA A URL para acesar as funçoes dessa classe
@CrossOrigin //recber requisiçoes javascript
public class SugestaoController {

    @Autowired
   private SugestaoService sugestaoService;

   @Autowired
   private ComportamentoService comportamentoService;

   @Autowired
   private SaudeMentalService saudeMentalService;

   @Autowired
   private AlimentacaoService alimentacaoService;

   @PostMapping //pra acessar essa funçao tem que fazer requisiçoes POST
   public ResponseEntity<Sugestao> save(@RequestBody @Valid SugestaoRequest request) {

    Sugestao sugestaoNova = request.build();
    sugestaoNova.setComportamento(comportamentoService.obterPorID(request.getIdComportamento()));
    sugestaoNova.setSaudeMental(saudeMentalService.obterPorID(request.getIdSaudeMental()));
    sugestaoNova.setAlimentacao(alimentacaoService.obterPorID(request.getIdAlimentacao()));
    Sugestao sugestao = sugestaoService.save(sugestaoNova);


       return new ResponseEntity<Sugestao>(sugestao, HttpStatus.CREATED);
   }

    @GetMapping
    public List<Sugestao> listarTodos() {
        return sugestaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Sugestao obterPorID(@PathVariable Long id) {
        return sugestaoService.obterPorID(id);
    }

    @PutMapping("/{id}") 
    public ResponseEntity<Sugestao> update(@PathVariable("id") Long id, @RequestBody SugestaoRequest request) {

       sugestaoService.update(id, request.build());
       return ResponseEntity.ok().build();
 }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

       sugestaoService.delete(id);       
       return ResponseEntity.ok().build();
   }
}