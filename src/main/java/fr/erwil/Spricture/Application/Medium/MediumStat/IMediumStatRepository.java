package fr.erwil.Spricture.Application.Medium.MediumStat;


import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface IMediumStatRepository extends JpaRepository<MediumStat, Long> {
    Optional<MediumStat> findByUser_Id(Long userId);

}
