package br.com.ifpe.educamente_api.modelo.comentario;
import java.time.LocalDate;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.educamente_api.modelo.alimentacao.Alimentacao;
import br.com.ifpe.educamente_api.modelo.comportamento.Comportamento;
import br.com.ifpe.educamente_api.modelo.funcionario.Funcionario;
import br.com.ifpe.educamente_api.modelo.saudemental.SaudeMental;
import br.com.ifpe.educamente_api.modelo.usuario.Usuario;
import br.com.ifpe.educamente_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity // Entidade do banco de dados.
@Table(name = "Sugestao") // Nome da tabela no banco de dados.
@SQLRestriction("habilitado = true") // Restrição para mostrar apenas os Usuarios habilitados.
@Builder // Cria um objeto Usuario.
@Getter
@Setter
@AllArgsConstructor // Cria um construtor com todos os atributos.
@NoArgsConstructor // Cria um construtor vazio.
public class Comentario extends EntidadeAuditavel {

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Alimentacao alimentacao;

    @ManyToOne
    private SaudeMental saudeMental;

    @ManyToOne
    private Comportamento comportamento;

    @Column 
    private String mensagem;
 
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataRegistro;

 }
