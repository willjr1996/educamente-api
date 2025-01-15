package br.com.ifpe.educamente_api.modelo.usuario;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.educamente_api.modelo.sugestao.Sugestao;
import br.com.ifpe.educamente_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity // Entidade do banco de dados.
@Table(name = "Usuario") // Nome da tabela no banco de dados.
@SQLRestriction("habilitado = true") // Restrição para mostrar apenas os Usuarios habilitados.
@Builder // Cria um objeto Usuario.
@Getter
@Setter
@AllArgsConstructor // Cria um construtor com todos os atributos.
@NoArgsConstructor // Cria um construtor vazio.
public class Usuario extends EntidadeAuditavel {

    @OneToMany(mappedBy="Sugestao")
    private List<Sugestao> sugestao;
    
    @Column (nullable = false, length = 100)
    private String nome;
 
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
 
    @Column (unique = true)
    private String cpf;
 
    @Column
    private String foneCelular;

 
 }
