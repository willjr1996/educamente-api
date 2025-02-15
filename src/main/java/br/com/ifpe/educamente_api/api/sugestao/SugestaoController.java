package br.com.ifpe.educamente_api.api.sugestao;

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
import br.com.ifpe.educamente_api.modelo.funcionario.Funcionario;
import br.com.ifpe.educamente_api.modelo.funcionario.FuncionarioRepository;
import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMental;
import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMentalService;
import br.com.ifpe.educamente_api.modelo.sugestao.Sugestao;
import br.com.ifpe.educamente_api.modelo.sugestao.SugestaoService;
import br.com.ifpe.educamente_api.modelo.usuario.Usuario;
import jakarta.validation.Valid;

@RestController // determina que essa classe e do tipo Rest
@RequestMapping("/api/sugestao") // DETERMINA A URL para acesar as funçoes dessa classe
@CrossOrigin // recber requisiçoes javascript

public class SugestaoController {

    @Autowired
    private SugestaoService sugestaoService;

    @Autowired
    private ComportamentoService comportamentoService;

    @Autowired
    private SaudeMentalService saudeMentalService;

    @Autowired
    private AlimentacaoService alimentacaoService;

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    @PostMapping("/comportamento")
    public ResponseEntity<Sugestao> saveComportamento(@RequestBody @Valid SugestaoRequest request) {
    
    Funcionario funcionario = funcionarioRepository.findById(request.getIdFuncionario())
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

    
    Comportamento comportamento = new Comportamento();
    comportamento.setConteudo(request.getMensagem());
    comportamento.setFuncionario(funcionario);

    comportamento = comportamentoService.save(comportamento);

    
    Sugestao sugestaoNova = new Sugestao();
    sugestaoNova.setMensagem(request.getMensagem());
    sugestaoNova.setDataRegistro(request.getDataRegistro());
    sugestaoNova.setComportamento(comportamento);

    Sugestao sugestao = sugestaoService.save(sugestaoNova);
    return new ResponseEntity<>(sugestao, HttpStatus.CREATED);
}


@PostMapping("/saudemental")
public ResponseEntity<Sugestao> saveSaudeMental(@RequestBody @Valid SugestaoRequest request) {
    Funcionario funcionario = funcionarioRepository.findById(request.getIdFuncionario())
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

    SaudeMental saudeMental = new SaudeMental();
    saudeMental.setConteudo(request.getMensagem());
    saudeMental.setFuncionario(funcionario);

    saudeMental = saudeMentalService.save(saudeMental);

    Sugestao sugestaoNova = new Sugestao();
    sugestaoNova.setMensagem(request.getMensagem());
    sugestaoNova.setDataRegistro(request.getDataRegistro());
    sugestaoNova.setSaudeMental(saudeMental);

    Sugestao sugestao = sugestaoService.save(sugestaoNova);
    return new ResponseEntity<>(sugestao, HttpStatus.CREATED);
}


@PostMapping("/alimentacao")
public ResponseEntity<Sugestao> saveAlimentacao(@RequestBody @Valid SugestaoRequest request) {
    Funcionario funcionario = funcionarioRepository.findById(request.getIdFuncionario())
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

    Alimentacao alimentacao = new Alimentacao();
    alimentacao.setConteudo(request.getMensagem());
    alimentacao.setFuncionario(funcionario);

    alimentacao = alimentacaoService.save(alimentacao);

    Sugestao sugestaoNova = new Sugestao();
    sugestaoNova.setMensagem(request.getMensagem());
    sugestaoNova.setDataRegistro(request.getDataRegistro());
    sugestaoNova.setAlimentacao(alimentacao);

    Sugestao sugestao = sugestaoService.save(sugestaoNova);
    return new ResponseEntity<>(sugestao, HttpStatus.CREATED);
}


    @GetMapping
    public List<Sugestao> listarTodos() {
        return sugestaoService.listarTodos();
    }


     @GetMapping("/{id}")
    public Sugestao obterPorID(@PathVariable Long id) {
        return sugestaoService.obterPorID(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        sugestaoService.delete(id);
        return ResponseEntity.ok().build();
    }
}