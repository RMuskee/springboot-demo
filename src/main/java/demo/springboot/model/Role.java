package demo.springboot.model;

import javax.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    // Username with unique constraint
    @Column(name = "role", nullable = false, unique = true)
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}