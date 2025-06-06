package fr.erwil.Spricture.Application.Medium.MediumStat;

import fr.erwil.Spricture.Application.AbstractEntity;
import fr.erwil.Spricture.Application.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class MediumStat extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Setter
    @Getter
    private long storageUsage;
    @Setter
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
