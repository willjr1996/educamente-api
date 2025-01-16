package br.com.ifpe.educamente_api.modelo.acesso;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByUsername(String username);
}