package br.com.ifpe.educamente_api.api.alimentacao;

import br.com.ifpe.educamente_api.modelo.alimentacao.Alimentacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Mesma coisa que getters e setters juntos.
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlimentacaoRequest {
    
    private String conteudo;

    public Alimentacao build() {

        return Alimentacao.builder()
                .conteudo(conteudo)
                .build();
    }
    

}