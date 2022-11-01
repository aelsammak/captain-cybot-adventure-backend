package captain.cybot.adventure.backend.repository.question;

import captain.cybot.adventure.backend.model.question.GuessTheImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuessTheImageRepository extends JpaRepository<GuessTheImage, Long> {
}