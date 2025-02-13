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
import br.com.ifpe.educamente_api.modelo.mensagens.AlimentacaoMsgService;
import br.com.ifpe.educamente_api.modelo.acesso.ContaRepository;
import br.com.ifpe.educamente_api.modelo.alimentacao.Alimentacao;
import br.com.ifpe.educamente_api.modelo.alimentacao.AlimentacaoRepository;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/notificacao")
@CrossOrigin

public class NotificacaoAlimentacaoController {

    @Autowired
    private AlimentacaoMsgService alimentacaoMsgService;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private AlimentacaoRepository alimentacaoRepository;

    @PostMapping("/alimentacao")
    public ResponseEntity<String> enviarMsg(@RequestParam String email) throws MessagingException {
        Optional<Conta> contaOptional = contaRepository.findByUsername(email);
        Optional<Alimentacao> alimentacaoOptional = alimentacaoRepository.findTopByOrderByIdDesc();
        if (contaOptional.isPresent()) {
            Alimentacao alimentacao = alimentacaoOptional.get();
            alimentacaoMsgService.sendEmail(contaOptional.get(), alimentacao);
            return ResponseEntity.ok("E-mail de notificação enviado.");
        }
        return ResponseEntity.badRequest().body("Conta não encontrada.");
    }
}