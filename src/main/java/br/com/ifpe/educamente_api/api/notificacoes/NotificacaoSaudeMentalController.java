package br.com.ifpe.educamente_api.api.notificacoes;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifpe.educamente_api.modelo.acesso.Conta;

import br.com.ifpe.educamente_api.modelo.acesso.ContaRepository;
import br.com.ifpe.educamente_api.modelo.mensagens.SaudeMentalMsgService;
import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMental;
import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMentalRepository;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/notificacao")
@CrossOrigin
public class NotificacaoSaudeMentalController {

    @Autowired
    private SaudeMentalMsgService saudeMentalMsgService;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private SaudeMentalRepository saudeMentalRepository;

    // //Nessa rota, deve-se colocar o email para procurar se existe no banco. Se existir o email será enviado o email da notificação.
    @PostMapping("/saudemental")
    public ResponseEntity<String> enviarMsg(@RequestParam String email) throws MessagingException {
        Optional<Conta> contaOptional = contaRepository.findByUsername(email);
        Optional<SaudeMental> saudeMentalOptional = saudeMentalRepository.findTopByOrderByIdDesc();
        if (contaOptional.isPresent()) {
            SaudeMental saudeMental = saudeMentalOptional.get();
            saudeMentalMsgService.sendEmail(contaOptional.get(), saudeMental);
            return ResponseEntity.ok("E-mail de notificação enviado.");
        }
        return ResponseEntity.badRequest().body("Conta não encontrada.");
    }
}