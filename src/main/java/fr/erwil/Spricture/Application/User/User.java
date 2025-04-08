package fr.erwil.Spricture.Application.User;

import fr.erwil.Spricture.Application.Medium.Medium;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User {

    public User() {}

    @PrePersist
    public void prePersist() {
        if (this.role == null) this.role = UserRole.USER;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String pseudo;

    private String name;

    private  String lastName;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "ownerId")
    private List<Medium> media;


    public String getEmail() {
        return email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public Long getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    public List<Medium> getMedia() {
        return media;
    }

    public String getPassword() {
        return this.password;
    }

}
