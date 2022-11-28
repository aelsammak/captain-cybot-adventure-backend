package captain.cybot.adventure.backend.repository.user;

import captain.cybot.adventure.backend.model.user.Level;
import captain.cybot.adventure.backend.model.user.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

    Level findByLevelNumberAndWorld(int levelNumber, World world);
}
