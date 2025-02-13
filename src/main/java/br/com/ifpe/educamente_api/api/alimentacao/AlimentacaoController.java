package br.com.ifpe.educamente_api.api.alimentacao;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifpe.educamente_api.modelo.alimentacao.Alimentacao;
import br.com.ifpe.educamente_api.modelo.alimentacao.AlimentacaoService;

@RestController //determina que essa classe e do tipo Rest
@RequestMapping("/api/alimentacao") //DETERMINA A URL para acesar as funçoes dessa classe
@CrossOrigin //recber requisiçoes javascript
public class AlimentacaoController {
    @Autowired
    private AlimentacaoService alimentacaoService;
 
        @GetMapping
        public List<Alimentacao> listarTodos() {
            return alimentacaoService.listarTodos();
     }
 
        @GetMapping("/{id}")
        public Alimentacao obterPorID(@PathVariable Long id) {
            return alimentacaoService.obterPorID(id);
     }
 
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
 
            alimentacaoService.delete(id);       
            return ResponseEntity.ok().build();
    }
 }