package br.com.ifpe.educamente_api.modelo.acesso;

import java.time.LocalDateTime;

import br.com.ifpe.educamente_api.util.entity.EntidadeNegocio;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "password_reset_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetToken extends EntidadeNegocio {

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "conta_id", referencedColumnName = "id", nullable = false)
    private Conta conta;
    
    @Column(nullable = false)
    private LocalDateTime expiryDate;
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
