package br.com.ifpe.educamente_api.api.saudemental;

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

import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMental;
import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMentalService;
import jakarta.validation.Valid;

@RestController // determina que essa classe e do tipo Rest
@RequestMapping("/api/saudemental") // DETERMINA A URL para acesar as funçoes dessa classe
@CrossOrigin // receber requisiçoes javascript

public class SaudeMentalController {

    @Autowired
    private SaudeMentalService saudeMentalService;


    @GetMapping
    public List<SaudeMental> listarTodos() {
        return saudeMentalService.listarTodos();
    }

    @GetMapping("/{id}")
    public SaudeMental obterPorID(@PathVariable Long id) {
        return saudeMentalService.obterPorID(id);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        saudeMentalService.delete(id);
        return ResponseEntity.ok().build();
    }

}
