package br.com.ifpe.educamente_api.api.saudemental;

import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMental;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Mesma coisa que getters e setters juntos.
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaudeMentalRequest {
    private String conteudo;

    public SaudeMental build() {

        return SaudeMental.builder()
                .conteudo(conteudo)
                .build();
    }
    

}
