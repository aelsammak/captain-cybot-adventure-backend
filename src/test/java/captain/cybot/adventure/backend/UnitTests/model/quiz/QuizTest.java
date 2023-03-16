package captain.cybot.adventure.backend.UnitTests.model.quiz;

import captain.cybot.adventure.backend.model.quiz.Quiz;
import captain.cybot.adventure.backend.model.quiz.QuizQuestion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {

    @Test
    public void addQuestions() {
        Quiz quiz = new Quiz("Planet1");

        assertTrue(quiz.getQuestions().isEmpty());

        String[] options = {"Software that protects your computer from viruses",
                "Software that protects your computer from ransomware",
                "Software that updates your computers operating system",
                "Software that may harm your computer"};
        QuizQuestion q1 = new QuizQuestion("What is Malware?",options, "Software that may harm your computer",1);
        q1.setQuiz(quiz);
        quiz.addQuestion(q1);
        List<QuizQuestion> questions = new ArrayList<>();
        questions.add(q1);

        assertEquals(q1.getQuiz(), quiz);
        assertEquals(quiz.getQuestions(), questions);

        QuizQuestion q2 = new QuizQuestion("What is Malware?",options, "Software that may harm your computer",2);
        q2.setQuiz(quiz);
        quiz.addQuestion(q2);
        questions.add(q2);

        assertEquals(q2.getQuiz(), quiz);
        assertEquals(quiz.getQuestions(), questions);
    }

    @Test
    public void SetPlanet() {
        Quiz quiz = new Quiz("Planet1");

        assertEquals(quiz.getPlanet(), "Planet1");
    }
}
