package fr.erwil.Spricture.Application.Medium.MediumStat;

import fr.erwil.Spricture.Application.AbstractEntity;
import fr.erwil.Spricture.Application.User.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public class MediumStat extends AbstractEntity {

    @Setter
    @Getter
    private long storageUsage;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
