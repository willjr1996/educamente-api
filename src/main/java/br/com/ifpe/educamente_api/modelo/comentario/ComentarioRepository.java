package br.com.ifpe.educamente_api.modelo.comentario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    
}
