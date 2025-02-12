package br.com.ifpe.educamente_api.modelo.comportamento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ifpe.educamente_api.api.comportamento.ComportamentoRequest;
import br.com.ifpe.educamente_api.modelo.usuario.Usuario;
import br.com.ifpe.educamente_api.modelo.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class ComportamentoService {

    @Autowired // cria instâncias automaticamente
    private ComportamentoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositório do usuário

    @Transactional
    public Comportamento save(Comportamento comportamento) {
        
        // Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
        //         .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + request.getUsuarioId()));
        
        // Comportamento comportamento = request.build(usuario);
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
    public Comportamento update(Long id, ComportamentoRequest request) {
        Comportamento comportamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensagem de Saúde mental não encontrada com id: " + id));
        comportamento.setConteudo(request.getConteudo());
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + request.getUsuarioId()));
        comportamento.setUsuario(usuario);
        comportamento.setHabilitado(Boolean.TRUE);
        return repository.save(comportamento);
    }

    @Transactional
    public void delete(Long id) {

        Comportamento comportamento = repository.findById(id).get();
        comportamento.setHabilitado(Boolean.FALSE);

        repository.save(comportamento);
    }

}
