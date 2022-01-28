package br.com.julgamento.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "USUARIO")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    private String id;
    private String nome;
    private String cpf;

}
