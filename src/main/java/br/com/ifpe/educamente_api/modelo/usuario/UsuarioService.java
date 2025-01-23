package br.com.ifpe.educamente_api.modelo.usuario;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.educamente_api.modelo.acesso.ContaService;
import br.com.ifpe.educamente_api.modelo.acesso.Perfil;
import br.com.ifpe.educamente_api.modelo.acesso.PerfilRepository;
import br.com.ifpe.educamente_api.modelo.mensagens.EmailService;
import br.com.ifpe.educamente_api.util.exception.UsuarioException;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    @Autowired // cria instâncias automaticamente
    private UsuarioRepository repository;

    @Autowired
    private ContaService contaService;

    @Autowired
    private PerfilRepository perfilContaRepository;

    @Autowired
    private EmailService emailService;

    @Transactional // orgazina 
    public Usuario save(Usuario usuario) {
        
        contaService.save(usuario.getConta());

        for (Perfil perfil : usuario.getConta().getRoles()) {
           perfil.setHabilitado(Boolean.TRUE);
           perfilContaRepository.save(perfil);
        }

        if (!isValidFoneCelular(usuario.getFoneCelular())){
            throw new UsuarioException(UsuarioException.MSG_PREFIXO_USUARIO);
        }

        usuario.setHabilitado(Boolean.TRUE);

        usuario = repository.save(usuario);
        
        emailService.enviarEmailConfirmacaoCadastroCliente(usuario);
        
        return usuario;
    }

    public List<Usuario> listarTodos() {
  
        return repository.findAll();
    }

    public Usuario obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Usuario usuarioAlterado) {

        if (!isValidFoneCelular(usuarioAlterado.getFoneCelular())){
            throw new UsuarioException(UsuarioException.MSG_PREFIXO_USUARIO);
        }
 
        Usuario usuario = repository.findById(id).get();
       usuario.setNome(usuarioAlterado.getNome());
       usuario.setDataNascimento(usuarioAlterado.getDataNascimento());
       usuario.setCpf(usuarioAlterado.getCpf());
       usuario.setFoneCelular(usuarioAlterado.getFoneCelular());
 
       repository.save(usuario);
   }
 
   @Transactional
   public void delete(Long id) {

       Usuario usuario = repository.findById(id).get();
       usuario.setHabilitado(Boolean.FALSE);

       repository.save(usuario);
   }

   private boolean isValidFoneCelular(String foneCelular) {
    // Verifica se o número de celular começa com "81"
    return foneCelular != null && foneCelular.startsWith("(81");
}


}