package br.com.ifpe.educamente_api.api.comportamento;
 
import br.com.ifpe.educamente_api.modelo.comportamento.Comportamento;
import br.com.ifpe.educamente_api.modelo.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Mesma coisa que getters e setters juntos.
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComportamentoRequest {
    
    @NotBlank
    private String conteudo;

    private Long usuarioId;

    public Comportamento build(Usuario usuario) {

        return Comportamento.builder()
                .conteudo(conteudo)
                .usuario(usuario)
                .build();
    }
    

}