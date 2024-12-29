package br.com.ifpe.educamente_api.modelo.alimentacao;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.educamente_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // Entidade do banco de dados.
@Table(name = "Alimentacao") // Nome da tabela no banco de dados.
@SQLRestriction("habilitado = true") // Restrição para mostrar apenas os habilitados.
@Builder // Cria um objeto.
@Getter
@Setter
@AllArgsConstructor // Cria um construtor com todos os atributos.
@NoArgsConstructor // Cria um construtor vazio.
public class Alimentacao extends EntidadeAuditavel {
    
    @Column 
    private String conteudo;
 
 }