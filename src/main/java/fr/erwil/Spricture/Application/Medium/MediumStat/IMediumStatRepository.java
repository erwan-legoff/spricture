package fr.erwil.Spricture.Application.Medium.MediumStat;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface IMediumStatRepository extends JpaRepository<MediumStat, Long> {
    Optional<MediumStat> findByUser_Id(Long userId);
    @Query("SELECT SUM(m.storageUsage) FROM MediumStat m WHERE m.user.id = :userId")
    Optional<Long> sumStorageUsageByUserId(@Param("userId") Long userId);

}
