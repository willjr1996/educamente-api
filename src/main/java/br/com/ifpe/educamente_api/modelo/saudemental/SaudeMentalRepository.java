package br.com.ifpe.educamente_api.modelo.saudemental;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaudeMentalRepository extends JpaRepository<SaudeMental, Long> {
    Optional<SaudeMental> findTopByOrderByIdDesc();
    List<SaudeMental> findByUsuario_Id(Long usuarioId);
}
