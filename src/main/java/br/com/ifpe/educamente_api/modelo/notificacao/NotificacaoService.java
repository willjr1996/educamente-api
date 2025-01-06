package br.com.ifpe.educamente_api.modelo.notificacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class NotificacaoService {
    @Autowired // cria instâncias automaticamente
    private NotificacaoRepository repository;


    @Transactional // orgazina 
    public Notificacao save(Notificacao notificacao) {

        notificacao.setHabilitado(Boolean.TRUE);
        return repository.save(notificacao);
    }

    public List<Notificacao> listarTodos() {
  
        return repository.findAll();
    }

    public Notificacao obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Notificacao notificacaoAlterado) {

 
        Notificacao notificacao = repository.findById(id).get();
       notificacao.setMensagem(notificacaoAlterado.getMensagem());
       notificacao.setDataRegistro(notificacaoAlterado.getDataRegistro());
 
       repository.save(notificacao);
   }
 
   @Transactional
   public void delete(Long id) {

       Notificacao notificacao = repository.findById(id).get();
       notificacao.setHabilitado(Boolean.FALSE);

       repository.save(notificacao);
   }


}