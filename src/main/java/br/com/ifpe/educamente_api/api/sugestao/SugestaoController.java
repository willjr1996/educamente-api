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
import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMental;
import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMentalService;
import br.com.ifpe.educamente_api.modelo.sugestao.Sugestao;
import br.com.ifpe.educamente_api.modelo.sugestao.SugestaoService;
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

    // @PostMapping //pra acessar essa funçao tem que fazer requisiçoes POST
    // public ResponseEntity<Sugestao> save(@RequestBody @Valid SugestaoRequest
    // request) {

    // Sugestao sugestaoNova = request.build();
    // sugestaoNova.setComportamento(comportamentoService.obterPorID(request.getIdComportamento()));
    // sugestaoNova.setSaudeMental(saudeMentalService.obterPorID(request.getIdSaudeMental()));
    // sugestaoNova.setAlimentacao(alimentacaoService.obterPorID(request.getIdAlimentacao()));
    // Sugestao sugestao = sugestaoService.save(sugestaoNova);

    // return new ResponseEntity<Sugestao>(sugestao, HttpStatus.CREATED);
    // }

    @PostMapping("/comportamento")
public ResponseEntity<Sugestao> saveComportamento(@RequestBody @Valid SugestaoRequest request) {
    ComportamentoRequest comportamentoRequest = new ComportamentoRequest();
    comportamentoRequest.setConteudo(request.getMensagem());
    comportamentoRequest.setUsuarioId(request.getIdUsuario());

    Comportamento comportamento = comportamentoService.save(comportamentoRequest);

    Sugestao sugestaoNova = new Sugestao();
    sugestaoNova.setMensagem(request.getMensagem());
    sugestaoNova.setDataRegistro(request.getDataRegistro());
    sugestaoNova.setComportamento(comportamento);

    Sugestao sugestao = sugestaoService.save(sugestaoNova);
    return new ResponseEntity<>(sugestao, HttpStatus.CREATED);
}


@PostMapping("/saudemental")
public ResponseEntity<Sugestao> saveSaudeMental(@RequestBody @Valid SugestaoRequest request) {
    SaudeMentalRequest saudeMentalRequest = new SaudeMentalRequest();
    saudeMentalRequest.setConteudo(request.getMensagem());
    saudeMentalRequest.setUsuarioId(request.getIdUsuario());

    SaudeMental saudeMental = saudeMentalService.save(saudeMentalRequest);

    Sugestao sugestaoNova = new Sugestao();
    sugestaoNova.setMensagem(request.getMensagem());
    sugestaoNova.setDataRegistro(request.getDataRegistro());
    sugestaoNova.setSaudeMental(saudeMental);

    Sugestao sugestao = sugestaoService.save(sugestaoNova);
    return new ResponseEntity<>(sugestao, HttpStatus.CREATED);
}


@PostMapping("/alimentacao")
public ResponseEntity<Sugestao> saveAlimentacao(@RequestBody @Valid SugestaoRequest request) {
    // Criar o AlimentacaoRequest com o conteúdo vindo do SugestaoRequest
    AlimentacaoRequest alimentacaoRequest = new AlimentacaoRequest();
    alimentacaoRequest.setConteudo(request.getMensagem());  // Definir o conteúdo da alimentação
    alimentacaoRequest.setUsuarioId(request.getIdUsuario());  // Definir o usuário associado

    // Salvar a alimentação com o método correto do serviço
    Alimentacao alimentacao = alimentacaoService.save(alimentacaoRequest);  // Passar o AlimentacaoRequest para o serviço

    // Criar a sugestão associada à alimentação salva
    Sugestao sugestaoNova = new Sugestao();
    sugestaoNova.setMensagem(request.getMensagem());
    sugestaoNova.setDataRegistro(request.getDataRegistro());
    sugestaoNova.setAlimentacao(alimentacao);

    // Salvar a sugestão
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

    @PutMapping("/{id}")
    public ResponseEntity<Sugestao> update(@PathVariable("id") Long id, @RequestBody SugestaoRequest request) {

        sugestaoService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        sugestaoService.delete(id);
        return ResponseEntity.ok().build();
    }
}