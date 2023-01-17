package captain.cybot.adventure.backend.repository.user;

import captain.cybot.adventure.backend.model.user.Cosmetic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CosmeticRepository extends JpaRepository<Cosmetic, Long> {
    Cosmetic findByFileName(String fileName);
    Cosmetic findByUnlockWorld(int unlockWorld);
}
