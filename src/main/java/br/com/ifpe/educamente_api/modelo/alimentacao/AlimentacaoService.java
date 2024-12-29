package br.com.ifpe.educamente_api.modelo.alimentacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class AlimentacaoService {

    @Autowired // cria instâncias automaticamente
    private AlimentacaoRepository repository;


    @Transactional // orgazina 
    public Alimentacao save(Alimentacao alimentacao) {

        alimentacao.setHabilitado(Boolean.TRUE);
        return repository.save(alimentacao);
    }

    public List<Alimentacao> listarTodos() {
  
        return repository.findAll();
    }

    public Alimentacao obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Alimentacao alimentacaoAlterado) {
 
        Alimentacao alimentacao = repository.findById(id).get();
       alimentacao.setConteudo(alimentacaoAlterado.getConteudo());
 
       repository.save(alimentacao);
   }
 
   @Transactional
   public void delete(Long id) {

    Alimentacao alimentacao = repository.findById(id).get();
       alimentacao.setHabilitado(Boolean.FALSE);

       repository.save(alimentacao);
   }


}
