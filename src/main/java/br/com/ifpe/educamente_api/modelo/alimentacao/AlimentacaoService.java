package br.com.ifpe.educamente_api.modelo.alimentacao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.educamente_api.api.alimentacao.AlimentacaoRequest;
import br.com.ifpe.educamente_api.modelo.alimentacao.AlimentacaoService;
import br.com.ifpe.educamente_api.modelo.usuario.Usuario;
import br.com.ifpe.educamente_api.modelo.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class AlimentacaoService {

    @Autowired // cria instâncias automaticamente
    private AlimentacaoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional // orgazina 
    public Alimentacao save(Alimentacao alimentacao) {

    // Usuario usuario = usuarioRepository.findById(alimentacao.getUsuarioId())
    //                                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + request.getUsuarioId()));
  
    // alimentacao.build(usuario);
    // alimentacao.setHabilitado(Boolean.TRUE);
    return repository.save(alimentacao);
    }

    public List<Alimentacao> listarTodos() {
  
        return repository.findAll();
    }

    public Alimentacao obterPorID(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada para o ID: " + id));
    }

    // @Transactional
    // public void update(Long id, Alimentacao alimentacaoAlterado) {
    //     Alimentacao alimentacao = repository.findById(id)
    //             .orElseThrow(() -> new RuntimeException("Alimentação não encontrada para o ID: " + id));
    //     alimentacao.setConteudo(alimentacaoAlterado.getConteudo());
    //     repository.save(alimentacao);
    // }

        // @Transactional
        // public Alimentacao update(Long id, AlimentacaoRequest request) {
        // Alimentacao alimentacao = repository.findById(id)
        //                                    .orElseThrow(() -> new RuntimeException("Mensagem de alimentação não encontrada com id: " + id));
        // alimentacao.setConteudo(request.getConteudo());
        //     Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
        //                                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + request.getUsuarioId()));
        // alimentacao.setUsuario(usuario);
        // alimentacao.setHabilitado(Boolean.TRUE);
        // return repository.save(alimentacao);
    //}
 
    @Transactional
    public void delete(Long id) {
        Alimentacao alimentacao = obterPorID(id);
        alimentacao.setHabilitado(Boolean.FALSE);
        repository.save(alimentacao);
    }

}