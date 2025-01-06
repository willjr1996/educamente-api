package br.com.ifpe.educamente_api.modelo.comentarios;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentariosRepository extends JpaRepository<Comentarios, Long> {
    
}
