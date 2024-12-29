package br.com.ifpe.educamente_api.modelo.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.educamente_api.util.exception.UsuarioException;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    @Autowired // cria instâncias automaticamente
    private UsuarioRepository repository;


    @Transactional // orgazina 
    public Usuario save(Usuario usuario) {

        if (!isValidFoneCelular(usuario.getFoneCelular())){
            throw new UsuarioException(UsuarioException.MSG_PREFIXO_USUARIO);
        }

        usuario.setHabilitado(Boolean.TRUE);
        return repository.save(usuario);
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