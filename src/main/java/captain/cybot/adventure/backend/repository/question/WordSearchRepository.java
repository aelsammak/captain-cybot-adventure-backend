package captain.cybot.adventure.backend.repository.question;

import captain.cybot.adventure.backend.model.question.WordSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordSearchRepository extends JpaRepository<WordSearch, Long> {
}