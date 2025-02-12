package br.com.ifpe.educamente_api.modelo.funcionario;
import java.time.LocalDate;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Administrador")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario extends EntidadeAuditavel {
    
    @OneToOne
   @JoinColumn(nullable = false)
   private Conta conta;

//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private TipoFuncionario tipo;

   @Column
   private String nome;

   @Column
   private String cpf;

   @Column
   @JsonFormat(pattern = "dd/MM/yyyy")
   private LocalDate dataNascimento;

   @Column
   private String foneCelular;

}
