package br.com.ifpe.educamente_api;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.com.ifpe.educamente_api.api.acesso.AuthenticationRequest;
import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.modelo.acesso.ContaRepository;
import br.com.ifpe.educamente_api.modelo.acesso.ContaService;
import jakarta.transaction.Transactional;

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

    // @Test
    // void testAuthenticate_ValidCredentials() {
    // // Criando e salvando usuário real no banco com username único
    // Conta conta = Conta.builder()
    // .username("usuario" + System.currentTimeMillis())
    // .password(passwordEncoder.encode("senha")) // Senha codificada
    // .build();
    // contaRepository.save(conta);

    // // Autenticação real
    // Conta result = contaService.authenticate(conta.getUsername(), "senha");

    // // Verificação
    // assertNotNull(result);
    // assertEquals(conta.getUsername(), result.getUsername());
    // }

    @Test
    void testAuthenticate_InvalidCredentials() {
        // Criando e salvando usuário real no banco
        Conta conta = Conta.builder()
                .username("usuario" + System.currentTimeMillis()) // Garantir username único
                .password(passwordEncoder.encode("senha")) // Senha codificada
                .build();
        contaRepository.save(conta);

        // Tentativa de login com senha errada
        assertThrows(BadCredentialsException.class,
                () -> contaService.authenticate(conta.getUsername(), "senhaerrada"));
    }

    @Test
    void testSave_NewUser() {
        // Criando conta
        Conta conta = Conta.builder().username("novousuario").password("novasenha").build();

        // Salvando a conta (o próprio serviço codifica a senha)
        Conta result = contaService.save(conta);

        // Verificando se foi salvo corretamente
        assertNotNull(result);
        assertEquals("novousuario", result.getUsername());

        // Verificando se a senha foi codificada corretamente
        Optional<Conta> savedConta = contaRepository.findByUsername("novousuario");
        assertTrue(savedConta.isPresent());
        assertNotEquals("novasenha", savedConta.get().getPassword());
    }

    // @Test
    // void testFindByUserName_UserExists() {
    // // Criando e salvando usuário
    // Conta conta = Conta.builder()
    // .username("usuarioexistente")
    // .password(passwordEncoder.encode("senha"))
    // .build();
    // contaRepository.save(conta);

    // // Buscando o usuário pelo id
    // Conta result =
    // contaRepository.findByUsername(conta.getUsername()).orElse(null);

    // // Verificando se o usuário foi encontrado corretamente
    // assertEquals(conta.getUsername(), result.getUsername());
    // }

    @Test
    void testFindByUsername_UserNotFound() {
        // Tentativa de buscar usuário inexistente
        assertThrows(Exception.class, () -> contaService.findByUsername("usuarionaoexistente"));
    }

    // teste request
    @Test
    public void testBuilder() {
        // Criando uma instância via builder
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("user123")
                .password("password123")
                .build();

        // Verificando se os valores estão sendo corretamente atribuídos
        assertThat(request.getUsername()).isEqualTo("user123");
        assertThat(request.getPassword()).isEqualTo("password123");
    }

    @Test
    public void testNoArgsConstructor() {
        // Criando uma instância via construtor sem argumentos
        AuthenticationRequest request = new AuthenticationRequest();

        // Verificando se os valores estão null inicialmente
        assertThat(request.getUsername()).isNull();
        assertThat(request.getPassword()).isNull();
    }

    @Test
    public void testAllArgsConstructor() {
        // Criando uma instância via construtor com todos os argumentos
        AuthenticationRequest request = new AuthenticationRequest("user123", "password123");

        // Verificando se os valores estão sendo corretamente atribuídos
        assertThat(request.getUsername()).isEqualTo("user123");
        assertThat(request.getPassword()).isEqualTo("password123");
    }
}