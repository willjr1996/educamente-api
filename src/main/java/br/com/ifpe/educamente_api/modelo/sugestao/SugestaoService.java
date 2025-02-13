package br.com.ifpe.educamente_api.modelo.sugestao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SugestaoService {
    @Autowired // cria instâncias automaticamente
    private SugestaoRepository repository;


    @Transactional // orgazina 
    public Sugestao save(Sugestao sugestao) {

        sugestao.setHabilitado(Boolean.TRUE);
        return repository.save(sugestao);
    }

    public List<Sugestao> listarTodos() {
  
        return repository.findAll();
    }

    public Sugestao obterPorID(Long id) {

        return repository.findById(id).get();
    }

 
   @Transactional
   public void delete(Long id) {

       Sugestao sugestao = repository.findById(id).get();
       sugestao.setHabilitado(Boolean.FALSE);

       repository.save(sugestao);
   }


}