package br.com.ifpe.educamente_api.api.usuario;

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

import br.com.ifpe.educamente_api.modelo.usuario.Usuario;
import br.com.ifpe.educamente_api.modelo.usuario.UsuarioService;
import jakarta.validation.Valid;

@RestController //determina que essa classe e do tipo Rest
@RequestMapping("/api/usuario") //DETERMINA A URL para acesar as funçoes dessa classe
@CrossOrigin //recber requisiçoes javascript

public class UsuarioController {
    
   @Autowired
   private UsuarioService usuarioService;

 
   @PostMapping //pra acessar essa funçao tem que fazer requisiçoes POST
   public ResponseEntity<Usuario> save(@RequestBody @Valid UsuarioRequest request) {

       Usuario usuario = usuarioService.save(request.build());
       return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
   }


       @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Usuario obterPorID(@PathVariable Long id) {
        return usuarioService.obterPorID(id);
    }

   
     @PutMapping("/{id}") 
 public ResponseEntity<Usuario> update(@PathVariable("id") Long id, @RequestBody UsuarioRequest request) {

       usuarioService.update(id, request.build());
       return ResponseEntity.ok().build();
 }

 
 @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {

       usuarioService.delete(id);       
       return ResponseEntity.ok().build();
   }
}