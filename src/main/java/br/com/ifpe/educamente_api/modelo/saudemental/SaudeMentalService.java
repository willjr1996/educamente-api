package br.com.ifpe.educamente_api.modelo.saudemental;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class SaudeMentalService {
    @Autowired // cria instâncias automaticamente
    private SaudeMentalRepository repository;


    @Transactional // orgazina 
    public SaudeMental save(SaudeMental saudeMental) {

        saudeMental.setHabilitado(Boolean.TRUE);
        return repository.save(saudeMental);
    }

    public List<SaudeMental> listarTodos() {
  
        return repository.findAll();
    }

    public SaudeMental obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, SaudeMental saudeMentalAlterado) {
 
        SaudeMental saudeMental = repository.findById(id).get();
       saudeMental.setConteudo(saudeMentalAlterado.getConteudo());
 
       repository.save(saudeMental);
   }
 
   @Transactional
   public void delete(Long id) {

    SaudeMental saudeMental = repository.findById(id).get();
       saudeMental.setHabilitado(Boolean.FALSE);

       repository.save(saudeMental);
   }


}
