package br.com.ifpe.educamente_api.modelo.alimentacao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.educamente_api.modelo.alimentacao.AlimentacaoService;
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
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada para o ID: " + id));
    }

 
    @Transactional
    public void delete(Long id) {
        Alimentacao alimentacao = obterPorID(id);
        alimentacao.setHabilitado(Boolean.FALSE);
        repository.save(alimentacao);
    }

}