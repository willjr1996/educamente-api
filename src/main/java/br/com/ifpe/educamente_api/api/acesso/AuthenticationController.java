package br.com.ifpe.educamente_api.api.acesso;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.modelo.acesso.ContaService;
import br.com.ifpe.educamente_api.modelo.acesso.Perfil;
import br.com.ifpe.educamente_api.modelo.funcionario.Funcionario;
import br.com.ifpe.educamente_api.modelo.funcionario.FuncionarioRepository;
import br.com.ifpe.educamente_api.modelo.usuario.Usuario;
import br.com.ifpe.educamente_api.modelo.usuario.UsuarioRepository;
import br.com.ifpe.educamente_api.modelo.seguranca.JwtService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {

    private final JwtService jwtService;
    private final ContaService contaService;
    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AuthenticationController(JwtService jwtService, ContaService contaService,
            FuncionarioRepository funcionarioRepository,
            UsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.contaService = contaService;
        this.funcionarioRepository = funcionarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public Map<Object, Object> signin(@RequestBody AuthenticationRequest data) {
        Conta authenticatedUser = contaService.authenticate(data.getUsername(), data.getPassword());

        Long funcionarioId = null;
        Long usuarioId = null;
        String nome = "";

        // Verifique os papéis de forma robusta
        for (Perfil role : authenticatedUser.getRoles()) {
            if (role.getAuthority().equals(Perfil.ROLE_FUNCIONARIO_ADMIN)) {
                Funcionario funcionario = funcionarioRepository.findByConta(authenticatedUser);
                if (funcionario != null) {
                    funcionarioId = funcionario.getId();
                    nome = funcionario.getNome();
                }
            }

            if (role.getAuthority().equals(Perfil.ROLE_USUARIO)) {
                Usuario usuario = usuarioRepository.findByConta(authenticatedUser);
                if (usuario != null) {
                    usuarioId = usuario.getId();
                    nome = usuario.getNome();
                }
            }
        }

        // Gerar o token JWT
        String jwtToken = jwtService.generateTokenWithFuncionarioId(authenticatedUser, funcionarioId);

        Map<Object, Object> loginResponse = new HashMap<>();
        loginResponse.put("username", authenticatedUser.getUsername());
        loginResponse.put("token", jwtToken);
        loginResponse.put("tokenExpiresIn", jwtService.getExpirationTime());
        loginResponse.put("role", authenticatedUser.getRole());
        loginResponse.put("id", authenticatedUser.getId());
        loginResponse.put("nome", nome); // Agora retorna o nome correto

        // Se o Funcionario for encontrado, adicionar o 'funcionarioId' ao retorno
        if (funcionarioId != null) {
            loginResponse.put("funcionarioId", funcionarioId);
        }

        // Se o Usuario for encontrado, adicionar o 'usuarioId' ao retorno
        if (usuarioId != null) {
            loginResponse.put("usuarioId", usuarioId);
        }

        return loginResponse;
    }
}