package br.com.ifpe.educamente_api.modelo.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ifpe.educamente_api.modelo.acesso.Conta;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
     Usuario findByConta(Conta conta);
}