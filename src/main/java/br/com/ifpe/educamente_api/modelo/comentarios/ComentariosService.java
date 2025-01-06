package br.com.ifpe.educamente_api.modelo.comentarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ComentariosService {

    @Autowired // cria instâncias automaticamente
    private ComentariosRepository repository;


    @Transactional // orgazina 
    public Comentarios save(Comentarios comentarios) {

        comentarios.setHabilitado(Boolean.TRUE);
        return repository.save(comentarios);
    }

    public List<Comentarios> listarTodos() {
  
        return repository.findAll();
    }

    public Comentarios obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Comentarios comentariosAlterado) {

 
        Comentarios comentarios = repository.findById(id).get();
       comentarios.setMensagem(comentariosAlterado.getMensagem());
       comentarios.setDataRegistro(comentariosAlterado.getDataRegistro());
 
       repository.save(comentarios);
   }
 
   @Transactional
   public void delete(Long id) {

    Comentarios comentarios = repository.findById(id).get();
       comentarios.setHabilitado(Boolean.FALSE);

       repository.save(comentarios);
   }


}