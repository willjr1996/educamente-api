package br.com.ifpe.educamente_api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.modelo.acesso.ContaRepository;
import br.com.ifpe.educamente_api.modelo.acesso.ContaService;

import java.util.Optional;

@SpringBootTest // Carrega o contexto do Spring Boot
public class ContaServiceComponentTest {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ContaService contaService;

    @BeforeEach
    void setUp() {
        contaRepository.deleteAll(); // Limpa o banco de dados antes de cada teste
    }

    @Test
    void testAuthenticate_ValidCredentials() {
    // Criando e salvando usuário real no banco com username único
    Conta conta = Conta.builder()
    .username("user" + System.currentTimeMillis()) // Garantir username único
    .password(passwordEncoder.encode("password")) // Senha codificada
    .build();
    contaRepository.save(conta);

    // Autenticação real
    Conta result = contaService.authenticate(conta.getUsername(), "password");

    // Verificação
    assertNotNull(result);
    assertEquals(conta.getUsername(), result.getUsername());
    }

    @Test
    void testAuthenticate_InvalidCredentials() {
    // Criando e salvando usuário real no banco
    Conta conta = Conta.builder()
    .username("user" + System.currentTimeMillis()) // Garantir username único
    .password(passwordEncoder.encode("password")) // Senha codificada
    .build();
    contaRepository.save(conta);

    // Tentativa de login com senha errada
    assertThrows(BadCredentialsException.class, () ->
    contaService.authenticate(conta.getUsername(), "wrongpassword"));
    }

    @Test
    void testSave_NewUser() {
        // Criando conta
        Conta conta = Conta.builder().username("newuser").password("rawpassword").build();

        // Salvando a conta (o próprio serviço codifica a senha)
        Conta result = contaService.save(conta);

        // Verificando se foi salvo corretamente
        assertNotNull(result);
        assertEquals("newuser", result.getUsername());

        // Verificando se a senha foi codificada corretamente
        Optional<Conta> savedConta = contaRepository.findByUsername("newuser");
        assertTrue(savedConta.isPresent());
        assertNotEquals("rawpassword", savedConta.get().getPassword());
    }

    @Test
    void testFindByUsername_UserExists() {
        // Criando e salvando usuário real com username único
        Conta conta = Conta.builder().username("existinguser" + System.currentTimeMillis()) // Garantir username único
                .password(passwordEncoder.encode("password"))
                .build();
        contaRepository.save(conta);

        // Buscando usuário
        Conta result = contaService.findByUsername(conta.getUsername());

        // Verificando resultado
        assertNotNull(result);
        assertEquals(conta.getUsername(), result.getUsername());
        System.out.println(conta.getUsername() + result);
    }

    @Test
    void testFindByUsername_UserNotFound() {
        // Tentativa de buscar usuário inexistente
        assertThrows(Exception.class, () -> contaService.findByUsername("nonexistentuser"));
    }
}
