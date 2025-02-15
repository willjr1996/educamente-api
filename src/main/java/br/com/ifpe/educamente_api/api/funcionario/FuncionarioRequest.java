package br.com.ifpe.educamente_api.api.funcionario;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.modelo.funcionario.Funcionario;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioRequest {

    // @NotNull
    // @Enumerated(EnumType.STRING)
    // private TipoFuncionario tipo;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nome;

    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private String foneCelular;

    public Funcionario build() {

        return Funcionario.builder()
                .conta(buildConta())
                .nome(nome)
                .cpf(cpf)
                .dataNascimento(dataNascimento)
                .foneCelular(foneCelular)
                .build();
    }

    public Conta buildConta() {

        return Conta.builder()
                .username(email)
                .password(password)
                .build();
    }
}