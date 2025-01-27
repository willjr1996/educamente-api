package br.com.ifpe.educamente_api.modelo.usuario;

import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.modelo.acesso.ContaService;
import br.com.ifpe.educamente_api.modelo.acesso.Perfil;
import br.com.ifpe.educamente_api.modelo.acesso.PerfilRepository;
import br.com.ifpe.educamente_api.modelo.mensagens.EmailService;
import br.com.ifpe.educamente_api.util.exception.UsuarioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ContaService contaService;

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private EmailService emailService;

    private Usuario usuario;
    private Perfil perfil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Configurar um usuário de teste
        usuario = new Usuario();
        usuario.setNome("João da Silva");
        usuario.setFoneCelular("(81)99999-9999");
        usuario.setCpf("12345678901");
        
        perfil = new Perfil();
        perfil.setNome("USER");
        perfil.setHabilitado(Boolean.TRUE);

        // Setando uma conta com um perfil
        Conta conta = new Conta();
        conta.setRoles(List.of(perfil));
        usuario.setConta(conta);
    }

    @Test
    public void testSaveUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(contaService.save(any(Conta.class))).thenReturn(null);
        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfil);

        Usuario savedUsuario = usuarioService.save(usuario);

     
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(contaService, times(1)).save(any(Conta.class));
        verify(perfilRepository, times(1)).save(any(Perfil.class));
        verify(emailService, times(1)).enviarEmailConfirmacaoCadastroCliente(any(Usuario.class));

       
        assert(savedUsuario != null);
        assert(savedUsuario.getNome().equals("João da Silva"));
    }

    @Test
    public void testSaveUsuario_InvalidPhone() {
        usuario.setFoneCelular("(82)99999-9999"); 

        UsuarioException exception = null;
        try {
            usuarioService.save(usuario);
        } catch (UsuarioException e) {
            exception = e;
        }


        assert(exception != null);
        assert(exception.getMessage().contains("Usuário"));
    }

    @Test
    public void testUpdateUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioAlterado = new Usuario();
        usuarioAlterado.setNome("Maria Silva");
        usuarioAlterado.setFoneCelular("(81)98765-4321");
        usuarioAlterado.setCpf("12345678902");

        usuarioService.update(1L, usuarioAlterado);

        // Verifica se o método de salvar foi chamado
        verify(usuarioRepository, times(1)).save(any(Usuario.class));

        // Verifica se o nome e o telefone foram alterados
        assert(usuario.getNome().equals("Maria Silva"));
        assert(usuario.getFoneCelular().equals("(81)98765-4321"));
    }

    @Test
    public void testDeleteUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.delete(1L);

        // Verifica se o método de salvar foi chamado
        verify(usuarioRepository, times(1)).save(any(Usuario.class));

        // Verifica se o usuário foi desabilitado
        assert(!usuario.getHabilitado());
    }

}
