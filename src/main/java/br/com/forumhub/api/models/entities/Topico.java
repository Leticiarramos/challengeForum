package br.com.forumhub.api.models.entities;

import br.com.forumhub.api.dto.topico.AtualizarTopicoDto;
import br.com.forumhub.api.dto.topico.CadastroTopicoDto;
import br.com.forumhub.api.models.status.Status;
import br.com.forumhub.api.repositories.TopicoRepository;
import br.com.forumhub.api.repositories.UsuarioRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topico")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    private String curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resposta> respostas = new ArrayList<>();

    public Topico(){}

    public Topico(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, Status status, Usuario autor, String curso, List<Resposta> respostas) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.autor = autor;
        this.curso = curso;
        this.respostas = respostas;
    }

    public Topico(CadastroTopicoDto dados, UsuarioRepository usuarioRepository) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.autor = usuarioRepository.findById(dados.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        this.curso = dados.curso();
        this.dataCriacao = LocalDateTime.now();
        this.status = Status.NAO_RESPONDIDO;
    }


    public void atualizarInfo(AtualizarTopicoDto dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
        if (dados.curso() != null) {
            this.curso = dados.curso();
        }
    }
}
