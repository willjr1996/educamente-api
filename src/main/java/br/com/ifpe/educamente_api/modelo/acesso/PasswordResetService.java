package br.com.ifpe.educamente_api.modelo.acesso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    //método que gera o token aleatório a ser enviado para o email do usuário
    public void sendResetPasswordEmail(Conta conta, String email) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .conta(conta)
                .expiryDate(LocalDateTime.now().plusMinutes(30))
                .build();
        
        tokenRepository.save(resetToken);

        String resetUrl = "http://localhost:8080/api/auth/resetar-senha?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Redefinição de Senha");
        message.setText("Clique no link para redefinir sua senha: " + resetUrl);

        mailSender.send(message);
    }
}
