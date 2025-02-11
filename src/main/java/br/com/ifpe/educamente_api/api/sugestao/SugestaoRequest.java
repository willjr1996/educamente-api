package br.com.ifpe.educamente_api.api.sugestao;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.educamente_api.modelo.sugestao.Sugestao;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Mesma coisa que getters e setters juntos.
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SugestaoRequest {

    private Long idComportamento;

    private Long idSaudeMental;

    private Long idAlimentacao;

    private Long idUsuario;

    @NotNull(message = "A mensagem é de preenchimento obrigatório")
    @NotEmpty(message = "A mensagem é de preenchimento obrigatório")
    private String mensagem;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataRegistro;

    public Sugestao build() {

        return Sugestao.builder()
                .mensagem(mensagem)
                .dataRegistro(dataRegistro)           
                .build();
    }
    

}
