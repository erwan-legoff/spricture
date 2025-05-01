package fr.erwil.Spricture.Application.User;

import fr.erwil.Spricture.Application.Medium.Medium;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString(exclude = {"password", "media"})
@NoArgsConstructor
public class User {

    @Builder
    public User(String pseudo, String name, String lastName, String email, String password, UserRole role) {
        this.pseudo = pseudo;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @PrePersist
    public void prePersist() {
        if (this.role == null) {
            this.role = UserRole.ROLE_USER;
        }
        if (this.status == null) {
            this.status = UserStatus.CREATED;
        }
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String pseudo;

    private String name;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(updatable = false)
    @CreatedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime modifiedAt;

    @Setter(AccessLevel.NONE)
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "ownerId")
    private List<Medium> media;
}
