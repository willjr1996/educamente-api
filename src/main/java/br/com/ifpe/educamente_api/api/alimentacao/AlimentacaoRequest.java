package br.com.ifpe.educamente_api.api.alimentacao;

import br.com.ifpe.educamente_api.modelo.alimentacao.Alimentacao;
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
public class AlimentacaoRequest {
    
    @NotBlank
    private String comentario;
    
    private long usuarioId;
    
        public Alimentacao build(Usuario usuario) {
            return Alimentacao.builder()
                .comentario(comentario)
                .usuario(usuario)  // Associando o usuário ao SaudeMental
                .build();
    }
}