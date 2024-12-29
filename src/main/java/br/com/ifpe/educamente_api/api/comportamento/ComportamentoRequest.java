package br.com.ifpe.educamente_api.api.comportamento;
 
import br.com.ifpe.educamente_api.modelo.comportamento.Comportamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Mesma coisa que getters e setters juntos.
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComportamentoRequest {
    
    private String conteudo;

    public Comportamento build() {

        return Comportamento.builder()
                .conteudo(conteudo)
                .build();
    }
    

}