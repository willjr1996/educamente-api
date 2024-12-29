package br.com.ifpe.educamente_api.modelo.alimentacao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentacaoRepository extends JpaRepository<Alimentacao, Long> {
    
}
