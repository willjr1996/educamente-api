package br.com.ifpe.educamente_api.api.comentario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.educamente_api.api.alimentacao.AlimentacaoRequest;
import br.com.ifpe.educamente_api.api.comportamento.ComportamentoRequest;
import br.com.ifpe.educamente_api.api.saudemental.SaudeMentalRequest;
import br.com.ifpe.educamente_api.modelo.alimentacao.Alimentacao;
import br.com.ifpe.educamente_api.modelo.alimentacao.AlimentacaoService;
import br.com.ifpe.educamente_api.modelo.comportamento.Comportamento;
import br.com.ifpe.educamente_api.modelo.comportamento.ComportamentoService;
import br.com.ifpe.educamente_api.modelo.usuario.Usuario;
import br.com.ifpe.educamente_api.modelo.usuario.UsuarioRepository;
import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMental;
import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMentalService;
import br.com.ifpe.educamente_api.modelo.comentario.Comentario;
import br.com.ifpe.educamente_api.modelo.comentario.ComentarioService;
import jakarta.validation.Valid;

@RestController // determina que essa classe e do tipo Rest
@RequestMapping("/api/comentario") // DETERMINA A URL para acessar as funções dessa classe
@CrossOrigin // receber requisições javascript
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ComportamentoService comportamentoService;

    @Autowired
    private SaudeMentalService saudeMentalService;

    @Autowired
    private AlimentacaoService alimentacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

  
    @PostMapping("/comportamento")
    public ResponseEntity<Comentario> saveComportamento(@RequestBody @Valid ComentarioRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Comportamento comportamento = new Comportamento();
        comportamento.setComentario(request.getMensagem());
        comportamento.setUsuario(usuario);

        comportamento = comportamentoService.save(comportamento);

        Comentario comentarioNovo = new Comentario();
        comentarioNovo.setMensagem(request.getMensagem());
        comentarioNovo.setDataRegistro(request.getDataRegistro());
        comentarioNovo.setUsuario(usuario);
        comentarioNovo.setComportamento(comportamento);

        Comentario comentario = comentarioService.save(comentarioNovo);
        return new ResponseEntity<>(comentario, HttpStatus.CREATED);
    }

    @PostMapping("/saudemental")
    public ResponseEntity<Comentario> saveSaudeMental(@RequestBody @Valid ComentarioRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        SaudeMental saudeMental = new SaudeMental();
        saudeMental.setComentario(request.getMensagem());
        saudeMental.setUsuario(usuario);

        saudeMental = saudeMentalService.save(saudeMental);

        Comentario comentarioNovo = new Comentario();
        comentarioNovo.setMensagem(request.getMensagem());
        comentarioNovo.setDataRegistro(request.getDataRegistro());
        comentarioNovo.setSaudeMental(saudeMental);

        Comentario comentario = comentarioService.save(comentarioNovo);
        return new ResponseEntity<>(comentario, HttpStatus.CREATED);
    }

    @PostMapping("/alimentacao")
    public ResponseEntity<Comentario> saveAlimentacao(@RequestBody @Valid ComentarioRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Alimentacao alimentacao = new Alimentacao();
        alimentacao.setComentario(request.getMensagem());
        alimentacao.setUsuario(usuario);

        alimentacao = alimentacaoService.save(alimentacao);

        Comentario comentarioNovo = new Comentario();
        comentarioNovo.setMensagem(request.getMensagem());
        comentarioNovo.setDataRegistro(request.getDataRegistro());
        comentarioNovo.setAlimentacao(alimentacao);

        Comentario comentario = comentarioService.save(comentarioNovo);
        return new ResponseEntity<>(comentario, HttpStatus.CREATED);
    }


    @GetMapping
    public List<Comentario> listarTodos() {
        return comentarioService.listarTodos();
    }


    @GetMapping("/{id}")
    public Comentario obterPorID(@PathVariable Long id) {
        return comentarioService.obterPorID(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comentarioService.delete(id);
        return ResponseEntity.ok().build();
    }
}
