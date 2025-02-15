package br.com.ifpe.educamente_api.modelo.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ifpe.educamente_api.modelo.acesso.Conta;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
     Funcionario findByConta(Conta conta);
}
