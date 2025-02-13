package br.com.ifpe.educamente_api.modelo.comentario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ComentarioService {
    @Autowired // cria instâncias automaticamente
    private ComentarioRepository repository;

    @Transactional // organiza 
    public Comentario save(Comentario comentario) {
        comentario.setHabilitado(Boolean.TRUE);
        return repository.save(comentario);
    }

    public List<Comentario> listarTodos() {
        return repository.findAll();
    }

    public Comentario obterPorID(Long id) {
        return repository.findById(id).get();
    }


    @Transactional
    public void delete(Long id) {
        Comentario comentario = repository.findById(id).get();
        comentario.setHabilitado(Boolean.FALSE);
        repository.save(comentario);
    }
}
