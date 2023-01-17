package captain.cybot.adventure.backend.repository.quiz;

import captain.cybot.adventure.backend.model.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Quiz findByPlanet(String planet);
}
