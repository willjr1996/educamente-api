package br.com.ifpe.educamente_api.api.notificacao;

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

import br.com.ifpe.educamente_api.modelo.notificacao.Notificacao;
import br.com.ifpe.educamente_api.modelo.notificacao.NotificacaoService;
import jakarta.validation.Valid;

@RestController //determina que essa classe e do tipo Rest
@RequestMapping("/api/notificacao") //DETERMINA A URL para acesar as funçoes dessa classe
@CrossOrigin //recber requisiçoes javascript
public class NotificacaoController {

    @Autowired
   private NotificacaoService notificacaoService;

   @PostMapping //pra acessar essa funçao tem que fazer requisiçoes POST
   public ResponseEntity<Notificacao> save(@RequestBody @Valid NotificacaoRequest request) {

    Notificacao notificacao = notificacaoService.save(request.build());
       return new ResponseEntity<Notificacao>(notificacao, HttpStatus.CREATED);
   }

       @GetMapping
    public List<Notificacao> listarTodos() {
        return notificacaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Notificacao obterPorID(@PathVariable Long id) {
        return notificacaoService.obterPorID(id);
    }

     @PutMapping("/{id}") 
 public ResponseEntity<Notificacao> update(@PathVariable("id") Long id, @RequestBody NotificacaoRequest request) {

       notificacaoService.update(id, request.build());
       return ResponseEntity.ok().build();
 }

 @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {

       notificacaoService.delete(id);       
       return ResponseEntity.ok().build();
   }
}