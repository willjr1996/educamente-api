package br.com.ifpe.educamente_api.api.acesso;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.modelo.acesso.ContaRepository;
import br.com.ifpe.educamente_api.modelo.acesso.ContaService;
import br.com.ifpe.educamente_api.modelo.acesso.PasswordResetService;
import br.com.ifpe.educamente_api.modelo.acesso.PasswordResetToken;
import br.com.ifpe.educamente_api.modelo.acesso.PasswordResetTokenRepository;
import br.com.ifpe.educamente_api.modelo.seguranca.JwtService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {

    private final JwtService jwtService;
    
    private ContaService contaService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private ContaRepository contaRepository;

    public AuthenticationController(JwtService jwtService, ContaService contaService) {

        this.jwtService = jwtService;
        this.contaService = contaService;
    }

    @PostMapping
    public Map<Object, Object> signin(@RequestBody AuthenticationRequest data) {
    
        Conta authenticatedUser = contaService.authenticate(data.getUsername(), data.getPassword());

        String jwtToken = jwtService.generateToken(authenticatedUser);

        Map<Object, Object> loginResponse = new HashMap<>();
        loginResponse.put("username", authenticatedUser.getUsername());
        loginResponse.put("token", jwtToken);
        loginResponse.put("tokenExpiresIn", jwtService.getExpirationTime());

        return loginResponse;
    }    

    //Nessa rota, deve-se colocar o email para procurar se existe no banco. Se existir o email será enviado com o link para redefinição.
    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestParam String email) {
        Optional<Conta> contaOptional = contaRepository.findByUsername(email);
        if (contaOptional.isPresent()) {
            passwordResetService.sendResetPasswordEmail(contaOptional.get(), email);
            return ResponseEntity.ok("E-mail de redefinição enviado.");
        }
        return ResponseEntity.badRequest().body("Conta não encontrada.");
    }

    //Nessa rota, o token é colocado e a nova senha é trocada no banco. O token vem pela url e por json será colocada a nova senha
    @PostMapping("/resetar-senha")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody String newPassword) {
        Optional<PasswordResetToken> resetTokenOpt = tokenRepository.findByToken(token);

        if (resetTokenOpt.isPresent()) {
            PasswordResetToken resetToken = resetTokenOpt.get();

            if (resetToken.isExpired()) {
                return ResponseEntity.badRequest().body("Token expirado.");
            }

            Conta conta = resetToken.getConta();
            conta.setPassword(passwordEncoder.encode(newPassword));
            contaRepository.save(conta);
            
            tokenRepository.delete(resetToken); // Remove o token após o uso.

            return ResponseEntity.ok("Senha redefinida com sucesso.");
        }
        return ResponseEntity.badRequest().body("Token inválido.");
    }

}