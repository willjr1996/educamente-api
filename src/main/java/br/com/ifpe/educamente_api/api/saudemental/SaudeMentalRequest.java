package br.com.ifpe.educamente_api.api.saudemental;

import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMental;
import br.com.ifpe.educamente_api.modelo.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaudeMentalRequest {
    
    @NotBlank
    private String conteudo;
    
    private Long usuarioId;

    public SaudeMental build(Usuario usuario) {
        return SaudeMental.builder()
                .conteudo(conteudo)
                .usuario(usuario)
                .build();
    }
}
