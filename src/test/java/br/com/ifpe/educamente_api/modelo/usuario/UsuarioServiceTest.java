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

import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.modelo.acesso.ContaService;
import br.com.ifpe.educamente_api.modelo.acesso.Perfil;
import br.com.ifpe.educamente_api.modelo.acesso.PerfilRepository;
import br.com.ifpe.educamente_api.modelo.mensagens.EmailService;
import br.com.ifpe.educamente_api.util.exception.UsuarioException;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private ContaService contaService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Conta conta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando um mock de Conta (evita NullPointerException)
        conta = new Conta();
        conta.setUsername("carlos@email.com");
        conta.setPassword("senha123"); // Define um valor inicial para evitar null
        conta.setHabilitado(true);

        // Criando um mock de Usuario
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Carlos");
        usuario.setCpf("12345678901");
        usuario.setFoneCelular("(81)99999-9999");
        usuario.setConta(conta); // Associa a conta ao usuário

        // Criando um perfil válido para a conta
        Perfil perfil = new Perfil();
        perfil.setHabilitado(true);

        // **MOCKANDO OS SERVIÇOS PARA SEGUIR A ORDEM CORRETA DO SAVE**
        
        // 1️⃣ Primeiro, salvamos a Conta
        when(contaService.save(any(Conta.class))).thenAnswer(invocation -> {
            Conta contaMock = invocation.getArgument(0);
            contaMock.setPassword("senhaCodificada"); // Simula criptografia da senha
            return contaMock;
        });

        // 2️⃣ Depois, salvamos os perfis
        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfil);

        // 3️⃣ Por fim, salvamos o usuário
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuarioMock = invocation.getArgument(0);
            usuarioMock.setId(1L); // Simula ID gerado pelo banco
            return usuarioMock;
        });
    }

    @Test
    void testSave_ShouldThrowException_WhenPhoneNumberIsInvalid() {
        // Configura um telefone inválido
        usuario.setFoneCelular("(82)98765-4321");

        // Executa e verifica a exceção
        UsuarioException exception = assertThrows(UsuarioException.class, () -> usuarioService.save(usuario));

        assertNotNull(exception, "A exceção não deveria ser nula");
        assertTrue(exception.getMessage().contains("Só é permitido usuários com um número de Pernambuco"),
                   "A mensagem da exceção deveria conter a restrição do número de telefone");
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
    void testUpdate_ShouldThrowException_WhenPhoneNumberIsInvalid() {
        // Mock do comportamento do repositório
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Configuração do usuário atualizado com telefone inválido
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setFoneCelular("(82)98765-4321");

        // Verifica que a exceção é lançada
        UsuarioException exception = assertThrows(UsuarioException.class, () -> usuarioService.update(1L, usuarioAtualizado));

        assertNotNull(exception, "A exceção não deveria ser nula");
        assertTrue(exception.getMessage().contains("Só é permitido usuários com um número de Pernambuco"),
                   "A mensagem da exceção deveria conter a restrição do número de telefone");
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
