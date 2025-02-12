package br.com.ifpe.educamente_api.modelo.saudemental;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.educamente_api.api.saudemental.SaudeMentalRequest;
import br.com.ifpe.educamente_api.modelo.usuario.Usuario;
import br.com.ifpe.educamente_api.modelo.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class SaudeMentalService {

    @Autowired
    private SaudeMentalRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositório do usuário

@Transactional
public SaudeMental save(SaudeMental saudeMental) {
    // Buscar o usuário no banco de dados usando o ID
    // Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
    //                                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + request.getUsuarioId()));
    // // Construir o objeto SaudeMental com o usuário encontrado
    // SaudeMental saudeMental = request.build(usuario);
    saudeMental.setHabilitado(Boolean.TRUE);
    return repository.save(saudeMental);
}

    public List<SaudeMental> listarTodos() {
        return repository.findAll();
    }

    public SaudeMental obterPorID(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Saúde Mental não encontrada com id: " + id));
    }

    public List<SaudeMental> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuario_Id(usuarioId);
    }

    @Transactional
    public SaudeMental update(Long id, SaudeMentalRequest request) {
        SaudeMental saudeMental = repository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("Mensagem de Saúde mental não encontrada com id: " + id));
        saudeMental.setConteudo(request.getConteudo());
            Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                                               .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + request.getUsuarioId()));
        saudeMental.setUsuario(usuario);
        saudeMental.setHabilitado(Boolean.TRUE);
        return repository.save(saudeMental);
    }

    @Transactional
    public void delete(Long id) {
        SaudeMental saudeMental = obterPorID(id);
        saudeMental.setHabilitado(Boolean.FALSE);
        repository.save(saudeMental);
    }

}
