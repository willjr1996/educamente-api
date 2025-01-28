package br.com.ifpe.educamente_api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.modelo.acesso.ContaRepository;
import br.com.ifpe.educamente_api.modelo.acesso.ContaService;

class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ContaService contaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticate_ValidCredentials() {
        // Dados de teste
        String username = "user";
        String password = "password";
        Conta conta = Conta.builder().username(username).password(password).build();

        // Mock das dependências
        when(contaRepository.findByUsername(username)).thenReturn(Optional.of(conta));
        doNothing().when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Executa o teste
        Conta result = contaService.authenticate(username, password);

        // Verifica o resultado
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void testAuthenticate_InvalidCredentials() {
        String username = "user";
        String password = "wrongpassword";

        // Mock do comportamento do authenticationManager
        doThrow(new BadCredentialsException("Invalid credentials"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Verifica que a exceção é lançada
        assertThrows(BadCredentialsException.class, () -> contaService.authenticate(username, password));
    }

    @Test
    void testSave_NewUser() {
        // Dados de teste
        Conta conta = Conta.builder().username("newuser").password("rawpassword").build();
        String encodedPassword = "encodedpassword";

        // Mock das dependências
        when(passwordEncoder.encode("rawpassword")).thenReturn(encodedPassword);
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        // Executa o teste
        Conta result = contaService.save(conta);

        // Verifica o resultado
        assertNotNull(result);
        assertEquals("newuser", result.getUsername());
        assertEquals(encodedPassword, result.getPassword());

        // Verifica as interações com os mocks
        verify(passwordEncoder).encode("rawpassword");
        verify(contaRepository).save(conta);
    }

    @Test
    void testFindByUsername_UserExists() {
        String username = "existinguser";
        Conta conta = Conta.builder().username(username).build();

        // Mock do repositório
        when(contaRepository.findByUsername(username)).thenReturn(Optional.of(conta));

        // Executa o teste
        Conta result = contaService.findByUsername(username);

        // Verifica o resultado
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void testFindByUsername_UserNotFound() {
        String username = "nonexistentuser";

        // Mock do repositório
        when(contaRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Verifica que a exceção é lançada
        assertThrows(Exception.class, () -> contaService.findByUsername(username));
    }
}
