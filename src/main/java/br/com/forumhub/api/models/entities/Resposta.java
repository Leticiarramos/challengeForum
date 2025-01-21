package br.com.forumhub.api.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "resposta")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "topico", nullable = false)
    private Topico topico;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "autor", nullable = false)
    private Usuario autor;

    private boolean solucao;

    public Resposta(){}

    public Resposta(Long id, String mensagem, Topico topico, LocalDateTime dataCriacao, Usuario autor, boolean solucao) {
        this.id = id;
        this.mensagem = mensagem;
        this.topico = topico;
        this.dataCriacao = dataCriacao;
        this.autor = autor;
        this.solucao = solucao;
    }

}
