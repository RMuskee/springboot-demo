package demo.springboot.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Size(max = 50)
    private String title;

    @NotNull
    @Size(max = 512)
    private String message;

    public Article() {
    }

    public Article(User user, @NotNull @Size(max = 50) String title, @NotNull @Size(max = 512) String message) {
        this.user = user;
        this.title = title;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Article setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Article setUser(User user) {
        this.user = user;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Article setMessage(String message) {
        this.message = message;
        return this;
    }
}