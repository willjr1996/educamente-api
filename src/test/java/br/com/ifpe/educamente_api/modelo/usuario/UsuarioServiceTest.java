package br.com.ifpe.educamente_api.modelo.usuario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.ifpe.educamente_api.modelo.acesso.ContaService;
import br.com.ifpe.educamente_api.modelo.acesso.Perfil;
import br.com.ifpe.educamente_api.modelo.acesso.PerfilRepository;
import br.com.ifpe.educamente_api.modelo.mensagens.EmailService;
import br.com.ifpe.educamente_api.modelo.usuario.Usuario;
import br.com.ifpe.educamente_api.modelo.usuario.UsuarioRepository;
import br.com.ifpe.educamente_api.modelo.usuario.UsuarioService;
import br.com.ifpe.educamente_api.util.exception.UsuarioException;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PerfilRepository perfilRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @InjectMocks
    private ContaService contaService;

    @InjectMocks
    private EmailService emailService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuração inicial do usuário
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Carlos");
        usuario.setCpf("12345678901");
        usuario.setFoneCelular("(81)99999-9999");
    }


    
    



    @Test
    void testSave_InvalidPhoneNumber() {
        // Configura um telefone inválido
        usuario.setFoneCelular("(82)98765-4321");

        // Mock do comportamento do repositório para evitar NullPointerException
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Verifica que a exceção é lançada devido ao número de telefone inválido

        UsuarioException exception = assertThrows(UsuarioException.class, () -> usuarioService.save(usuario));

        // Valida a mensagem da exceção
        assertNotNull(exception, "A exceção não deveria ser nula");
        assertTrue(exception.getMessage().contains(UsuarioException.MSG_PREFIXO_USUARIO),
                   "A mensagem da exceção deveria conter 'Só é permitido usuários com um número de Pernambuco'");
    }
    
    
    


    @Test
    void testUpdate_ExistingUser() {
        // Mock do comportamento do repositório
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Configuração do usuário atualizado
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("Maria Silva");
        usuarioAtualizado.setFoneCelular("(81)98765-4321");

        // Executa o método
        usuarioService.update(1L, usuarioAtualizado);

        // Verifica as alterações no objeto
        assertEquals("Maria Silva", usuario.getNome());
        assertEquals("(81)98765-4321", usuario.getFoneCelular());

        // Verifica interações com o mock
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testUpdate_UserNotFound() {
        // Mock do comportamento do repositório
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Configuração do usuário atualizado
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("Maria Silva");

        // Verifica que a exceção é lançada
        assertThrows(Exception.class, () -> usuarioService.update(1L, usuarioAtualizado));
    }

    @Test
    void testDelete_ExistingUser() {
        // Mock do comportamento do repositório
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Executa o método
        usuarioService.delete(1L);

        // Verifica que o usuário foi desabilitado
        assertFalse(usuario.getHabilitado());

        // Verifica interações com o mock
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testDelete_UserNotFound() {
        // Mock do comportamento do repositório
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica que a exceção é lançada
        assertThrows(Exception.class, () -> usuarioService.delete(1L));
    }
}
