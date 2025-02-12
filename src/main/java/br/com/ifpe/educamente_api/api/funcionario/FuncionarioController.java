package br.com.ifpe.educamente_api.api.funcionario;
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

import br.com.ifpe.educamente_api.modelo.acesso.Perfil;
import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.modelo.funcionario.Funcionario;
import br.com.ifpe.educamente_api.modelo.funcionario.FuncionarioService;
import jakarta.validation.Valid;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/funcionario")
@CrossOrigin
// @Tag(
//     name = "API Funcionario",
//     description = "API responsável pelos servidos de Funcionario no sistema"
// )
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    // @Operation(
    //     summary = "Cadastrar Funcionario",
    //     description = "Realiza o cadastro de um novo Funcionario"
    // )
    @PostMapping
    public ResponseEntity<Funcionario> save(@RequestBody @Valid FuncionarioRequest request) {

        Funcionario funcionarioNovo = request.build();
          
                funcionarioNovo.getConta().getRoles().add(new Perfil(Perfil.ROLE_FUNCIONARIO_ADMIN));
           
        Funcionario funcionario = funcionarioService.save(funcionarioNovo);
        return new ResponseEntity<Funcionario>(funcionario, HttpStatus.CREATED);
    }

    // @Operation(
    //     summary = "Listar todos os Funcionarios",
    //     description = "Retorna uma lista com todos os Funcionarios cadastrados"
    // )
    @GetMapping
    public List<Funcionario> listarTodos() {

        return funcionarioService.listarTodos();
    }

    // @Operation(
    //     summary = "Obter Funcionario por ID",
    //     description = "Retorna o Funcionario cadastrado com o ID informado"
    // )
    @GetMapping("/{id}")
    public Funcionario obterPorID(@PathVariable Long id) {

        return funcionarioService.obterPorID(id);
    }

    // @Operation(
    //     summary = "Atualizar Funcionario",
    //     description = "Realiza a atualização do Funcionario cadastrado com o ID informado"
    // )
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> update(@PathVariable("id") Long id, @RequestBody FuncionarioRequest request) {

        funcionarioService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    // @Operation(
    //     summary = "Deletar Funcionario",
    //     description = "Deleta o Funcionario cadastrado com o ID informado"
    // )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        funcionarioService.delete(id);
        return ResponseEntity.ok().build();
    }

}
