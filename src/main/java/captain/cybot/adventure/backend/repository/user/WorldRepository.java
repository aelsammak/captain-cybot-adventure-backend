package captain.cybot.adventure.backend.repository.user;

import captain.cybot.adventure.backend.model.user.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldRepository extends JpaRepository<World, Long> {
}
