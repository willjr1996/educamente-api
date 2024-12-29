package br.com.ifpe.educamente_api.modelo.comportamento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class ComportamentoService {

    @Autowired // cria instâncias automaticamente
    private ComportamentoRepository repository;


    @Transactional // orgazina 
    public Comportamento save(Comportamento comportamento) {

        comportamento.setHabilitado(Boolean.TRUE);
        return repository.save(comportamento);
    }

    public List<Comportamento> listarTodos() {
  
        return repository.findAll();
    }

    public Comportamento obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Comportamento comportamentoAlterado) {
 
        Comportamento comportamento = repository.findById(id).get();
       comportamento.setConteudo(comportamentoAlterado.getConteudo());
 
       repository.save(comportamento);
   }
 
   @Transactional
   public void delete(Long id) {

    Comportamento comportamento = repository.findById(id).get();
       comportamento.setHabilitado(Boolean.FALSE);

       repository.save(comportamento);
   }


}
