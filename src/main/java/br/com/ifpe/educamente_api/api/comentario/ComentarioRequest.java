package br.com.ifpe.educamente_api.api.comentario;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.educamente_api.modelo.comentario.Comentario;
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
public class ComentarioRequest {

    private Long idComportamento;
    private Long idSaudeMental;
    private Long idAlimentacao;
    private Long idUsuario;

    @NotNull(message = "A mensagem é de preenchimento obrigatório")
    @NotEmpty(message = "A mensagem é de preenchimento obrigatório")
    private String mensagem;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataRegistro;

    public Comentario build() {
        return Comentario.builder()
                .mensagem(mensagem)
                .dataRegistro(dataRegistro)
                .build();
    }
}
