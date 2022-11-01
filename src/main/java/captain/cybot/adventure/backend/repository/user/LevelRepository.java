package captain.cybot.adventure.backend.repository.user;

import captain.cybot.adventure.backend.model.user.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
}
