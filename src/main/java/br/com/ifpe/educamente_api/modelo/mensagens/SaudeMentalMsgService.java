package br.com.ifpe.educamente_api.modelo.mensagens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMental;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class SaudeMentalMsgService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(Conta conta, SaudeMental saudeMental) throws MessagingException {
    String email = conta.getUsername(); // Usa o e-mail armazenado no username

    Context context = new Context();
    context.setVariable("conteudo", saudeMental.getConteudo());

    String htmlContent = templateEngine.process("mensagem_saudemental", context);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setTo(email); // Define o destinatário usando o username
    helper.setSubject("Dicas de Saúde Mental");
    helper.setText(htmlContent, true);
    
    mailSender.send(message);
    }
}