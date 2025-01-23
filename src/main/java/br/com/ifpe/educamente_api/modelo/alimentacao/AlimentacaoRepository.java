package br.com.ifpe.educamente_api.modelo.alimentacao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentacaoRepository extends JpaRepository<Alimentacao, Long> {
    Optional<Alimentacao> findTopByOrderByIdDesc();
    List<Alimentacao> findByUsuario_Id(Long usuarioId);
}
