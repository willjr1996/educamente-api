package br.com.ifpe.educamente_api.modelo.comportamento;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComportamentoRepository extends JpaRepository<Comportamento, Long> {
    Optional<Comportamento> findTopByOrderByIdDesc();
    List<Comportamento> findByUsuario_Id(Long usuarioId);
}