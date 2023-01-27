package captain.cybot.adventure.backend.UnitTests.model.quiz;

import captain.cybot.adventure.backend.model.quiz.Quiz;
import captain.cybot.adventure.backend.model.quiz.QuizQuestion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuizQuestionTest {

    @Test
    public void setQuizBackReference() {
        Quiz quiz = new Quiz("Planet1");

        String[] options = {"Software that protects your computer from viruses",
                "Software that protects your computer from ransomware",
                "Software that updates your computers operating system",
                "Software that may harm your computer"};
        QuizQuestion q1 = new QuizQuestion("What is Malware?",options, "Software that may harm your computer",1);
        q1.setQuiz(quiz);
        quiz.addQuestion(q1);

        assertEquals(q1.getQuiz(), quiz);
    }

    @Test
    public void setQuestionText() {
        String[] options = {"Software that protects your computer from viruses",
                "Software that protects your computer from ransomware",
                "Software that updates your computers operating system",
                "Software that may harm your computer"};
        QuizQuestion q1 = new QuizQuestion("What is Malware?",options, "Software that may harm your computer",1);
        assertEquals(q1.getQuestion(), "What is Malware?");
    }

    @Test
    public void setOptions() {
        String[] options = {"Software that protects your computer from viruses",
                "Software that protects your computer from ransomware",
                "Software that updates your computers operating system",
                "Software that may harm your computer"};
        QuizQuestion q1 = new QuizQuestion("What is Malware?",options, "Software that may harm your computer",1);
        assertEquals(q1.getOptions(), options);
    }

    @Test
    public void setAnswer() {
        String[] options = {"Software that protects your computer from viruses",
                "Software that protects your computer from ransomware",
                "Software that updates your computers operating system",
                "Software that may harm your computer"};
        QuizQuestion q1 = new QuizQuestion("What is Malware?",options, "Software that may harm your computer",1);
        assertEquals(q1.getAnswer(), "Software that may harm your computer");
    }

    @Test
    public void setQuestionNumber() {
        String[] options = {"Software that protects your computer from viruses",
                "Software that protects your computer from ransomware",
                "Software that updates your computers operating system",
                "Software that may harm your computer"};
        QuizQuestion q1 = new QuizQuestion("What is Malware?",options, "Software that may harm your computer",1);
        assertEquals(q1.getQuestionNumber(), 1);
    }
}
