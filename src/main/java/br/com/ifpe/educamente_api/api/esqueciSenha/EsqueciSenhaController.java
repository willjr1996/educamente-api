package br.com.ifpe.educamente_api.api.esqueciSenha;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.modelo.acesso.PasswordResetService;
import br.com.ifpe.educamente_api.modelo.acesso.PasswordResetToken;
import br.com.ifpe.educamente_api.modelo.acesso.PasswordResetTokenRepository;
import br.com.ifpe.educamente_api.modelo.acesso.ContaRepository;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/redefinir")
@CrossOrigin
public class EsqueciSenhaController {
    
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private ContaRepository contaRepository;
    
    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestParam String email) throws MessagingException {
        Optional<Conta> contaOptional = contaRepository.findByUsername(email);
        if (contaOptional.isPresent()) {
            passwordResetService.sendResetPasswordEmail(contaOptional.get(), email);
            return ResponseEntity.ok("E-mail de redefinição enviado.");
        }
        return ResponseEntity.badRequest().body("Conta não encontrada.");
    }

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