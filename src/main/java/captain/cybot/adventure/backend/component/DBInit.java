package captain.cybot.adventure.backend.component;

import captain.cybot.adventure.backend.constants.COSMETICS;
import captain.cybot.adventure.backend.constants.ROLES;
import captain.cybot.adventure.backend.model.question.*;
import captain.cybot.adventure.backend.model.quiz.Quiz;
import captain.cybot.adventure.backend.model.quiz.QuizQuestion;
import captain.cybot.adventure.backend.model.user.Cosmetic;
import captain.cybot.adventure.backend.model.user.Role;
import captain.cybot.adventure.backend.model.user.User;
import captain.cybot.adventure.backend.repository.question.*;
import captain.cybot.adventure.backend.repository.quiz.QuizQuestionRepository;
import captain.cybot.adventure.backend.repository.quiz.QuizRepository;
import captain.cybot.adventure.backend.repository.user.CosmeticRepository;
import captain.cybot.adventure.backend.repository.user.RoleRepository;
import captain.cybot.adventure.backend.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DBInit implements ApplicationListener<ContextRefreshedEvent> {

    private RoleRepository roleRepository;
    private CosmeticRepository cosmeticRepository;
    private QuestionOrderRepository questionOrderRepository;
    private WordScrambleRepository wordScrambleRepository;
    private WordSearchRepository wordSearchRepository;
    private GuessTheImageRepository guessTheImageRepository;
    private CrosswordRepository crosswordRepository;

    private QuizQuestionRepository quizQuestionRepository;

    private QuizRepository quizRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initDefaultRoles();
        initDefaultCosmetics();
        initDefaultQuestions();
        initDefaultQuizzes();
    }

    private void initDefaultRoles() {
        Role userRole = roleRepository.findByName(ROLES.ROLE_USER.toString());
        Role adminRole = roleRepository.findByName(ROLES.ROLE_ADMIN.toString());
        if (userRole == null) {
            userRole = new Role(1L, ROLES.ROLE_USER.toString());
            roleRepository.save(userRole);
        }
        if (adminRole == null) {
            adminRole = new Role(2L, ROLES.ROLE_ADMIN.toString());
            roleRepository.save(adminRole);
        }
    }

    private void initDefaultCosmetics() {
        Cosmetic defaultShield = cosmeticRepository.findByFileName(COSMETICS.DEFAULT_SHIELD.toString());
        Cosmetic world1Shield = cosmeticRepository.findByFileName(COSMETICS.WORLD_1_SHIELD.toString());
        Cosmetic world2Shield = cosmeticRepository.findByFileName(COSMETICS.WORLD_2_SHIELD.toString());
        Cosmetic world3Shield = cosmeticRepository.findByFileName(COSMETICS.WORLD_3_SHIELD.toString());
        Cosmetic world4Shield = cosmeticRepository.findByFileName(COSMETICS.WORLD_4_SHIELD.toString());

        if (defaultShield == null) {
            defaultShield = new Cosmetic(1L, COSMETICS.DEFAULT_SHIELD.toString(), 0);
            cosmeticRepository.save(defaultShield);
        }

        if (world1Shield == null) {
            world1Shield = new Cosmetic(2L, COSMETICS.WORLD_1_SHIELD.toString(), 1);
            cosmeticRepository.save(world1Shield);
        }

        if (world2Shield == null) {
            world2Shield = new Cosmetic(3L, COSMETICS.WORLD_2_SHIELD.toString(), 2);
            cosmeticRepository.save(world2Shield);
        }

        if (world3Shield == null) {
            world3Shield = new Cosmetic(4L, COSMETICS.WORLD_3_SHIELD.toString(), 3);
            cosmeticRepository.save(world3Shield);
        }

        if (world4Shield == null) {
            world4Shield = new Cosmetic(5L, COSMETICS.WORLD_4_SHIELD.toString(), 4);
            cosmeticRepository.save(world4Shield);
        }
    }

    private void initDefaultQuestions() {
        QuestionOrder w1Q1 = questionOrderRepository.findByPlanetAndQuestionNumber("EARTH", 1);
        QuestionOrder w1Q2 = questionOrderRepository.findByPlanetAndQuestionNumber("EARTH", 2);
        QuestionOrder w1Q3 = questionOrderRepository.findByPlanetAndQuestionNumber("EARTH", 3);
        QuestionOrder w1Q4 = questionOrderRepository.findByPlanetAndQuestionNumber("EARTH", 4);
        QuestionOrder w2Q1 = questionOrderRepository.findByPlanetAndQuestionNumber("MARS", 1);
        QuestionOrder w2Q2 = questionOrderRepository.findByPlanetAndQuestionNumber("MARS", 2);
        QuestionOrder w2Q3 = questionOrderRepository.findByPlanetAndQuestionNumber("MARS", 3);
        QuestionOrder w2Q4 = questionOrderRepository.findByPlanetAndQuestionNumber("MARS", 4);
        QuestionOrder w3Q1 = questionOrderRepository.findByPlanetAndQuestionNumber("NEPTUNE", 1);
        QuestionOrder w3Q2 = questionOrderRepository.findByPlanetAndQuestionNumber("NEPTUNE", 2);
        QuestionOrder w3Q3 = questionOrderRepository.findByPlanetAndQuestionNumber("NEPTUNE", 3);
        QuestionOrder w3Q4 = questionOrderRepository.findByPlanetAndQuestionNumber("NEPTUNE", 4);
        QuestionOrder w4Q1 = questionOrderRepository.findByPlanetAndQuestionNumber("JUPITER", 1);
        QuestionOrder w4Q2 = questionOrderRepository.findByPlanetAndQuestionNumber("JUPITER", 2);
        QuestionOrder w4Q3 = questionOrderRepository.findByPlanetAndQuestionNumber("JUPITER", 3);
        QuestionOrder w4Q4 = questionOrderRepository.findByPlanetAndQuestionNumber("JUPITER", 4);

        if (w1Q1 == null) {
            WordScramble w1Q1Question =  wordScrambleRepository.save(new WordScramble("RRECEPE",
                                                                                            "CREEPER"));
            w1Q1 = new QuestionOrder(w1Q1Question, 1, "EARTH");
            questionOrderRepository.save(w1Q1);
        }

        if (w1Q2 == null) {
            GuessTheImage w1Q2Question = guessTheImageRepository.save(new GuessTheImage("World1Question2.png",
                                                                                "Replicate"));
            w1Q2 = new QuestionOrder(w1Q2Question, 2, "EARTH");
            questionOrderRepository.save(w1Q2);
        }

        if (w1Q3 == null) {
            String[][] searchBlock = {
                    {"P","A","N","J","H","J","F","C","I","X"},
                    {"R","R","N","S","Y","K","N","M","L","Y"},
                    {"C","E","O","T","L","I","K","T","M","S"},
                    {"R","D","P","T","I","O","D","J","I","V"},
                    {"E","A","E","L","E","V","W","V","F","I"},
                    {"E","M","N","P","I","C","I","J","U","R"},
                    {"P","A","K","Q","Q","C","T","R","K","U"},
                    {"E","G","X","X","N","S","A","I","U","S"},
                    {"R","E","L","B","G","C","C","T","O","S"},
                    {"K","M","A","L","W","A","R","E","E","N"}
            };
            String[] answers = {"protection", "antivirus", "virus", "replicate", "slow", "malware", "creeper", "damage"};
            WordSearch w1Q3Question = wordSearchRepository.save(new WordSearch(searchBlock, answers));
            w1Q3 = new QuestionOrder(w1Q3Question, 3, "EARTH");
            questionOrderRepository.save(w1Q3);
        }

        if (w1Q4 == null) {
            String[][] crosswordBlock = {
                    {"|", "|", "|", "|", "|", "|", "|", "|", "|", "1", "|", "|"},
                    {"|", "|", "|", "|", "|", "2", "X", "X", "X", "X", "X", "|"},
                    {"|", "|", "|", "|", "|", "|", "|", "|", "|", "X", "|", "3"},
                    {"|", "|", "|", "|", "|", "|", "|", "4", "|", "X", "|", "X"},
                    {"5", "X", "X", "X", "X", "X", "X", "X", "|", "X", "|", "X"},
                    {"|", "|", "|", "|", "|", "|", "|", "6", "X", "X", "X", "X"},
                    {"|", "7", "X", "X", "X", "X", "X", "X", "|", "X", "|", "X"},
                    {"|", "|", "|", "|", "|", "|", "|", "X", "|", "X", "|", "X"},
                    {"|", "|", "8", "X", "X", "X", "X", "X", "X", "|", "|", "X"},
                    {"|", "|", "|", "|", "|", "|", "|", "X", "|", "|", "|", "X"},
                    {"|", "|", "|", "|", "|", "|", "|", "|", "|", "|", "|", "X"}
            };
            String[] hints = {"The programs that tell the hardware what to do.",
                    "A common anti-virus software.",
                    "Make an exact copy of; reproduce.",
                    "The first ever computer virus.",
                    "A device for working with information.",
                    "A message sent through the internet.",
                    "Software that may harm your computer.",
                    "Malicious self-reproducing programs that change how a computer works."};
            String[] answers = {"software", "norton", "replicate", "creeper", "computer", "email", "malware", "viruses"};
            Crossword w1Q4Question = crosswordRepository.save(new Crossword(crosswordBlock, hints, answers));
            w1Q4 = new QuestionOrder(w1Q4Question, 4, "EARTH");
            questionOrderRepository.save(w1Q4);
        }



        if (w2Q1 == null) {
            /* TODO: Change to real question */
            WordScramble w2Q1Question =  wordScrambleRepository.save(new WordScramble("RRECEPE2",
                    "CREEPER2"));
            w2Q1 = new QuestionOrder(w2Q1Question, 1, "MARS");
            questionOrderRepository.save(w2Q1);
        }
    }


    private void initDefaultQuizzes() {
        Quiz w1Quiz = quizRepository.findByPlanet("EARTH");
        Quiz w2Quiz = quizRepository.findByPlanet("MARS");
        Quiz w3Quiz = quizRepository.findByPlanet("NEPTUNE");
        Quiz w4Quiz = quizRepository.findByPlanet("JUPITER");

        if (w1Quiz == null) {
            w1Quiz = new Quiz("EARTH");
            quizRepository.save(w1Quiz);

            String[] options1 = {"Software that protects your computer from viruses",
                    "Software that protects your computer from ransomware",
                    "Software that updates your computers operating system",
                    "Software that may harm your computer"};
            QuizQuestion q1 = new QuizQuestion("What is Malware?",options1, "Software that may harm your computer",1);

            q1.setQuiz(w1Quiz);

            q1 = quizQuestionRepository.save(q1);

            w1Quiz.addQuestion(q1);

            String[] options2 = {"Computer runs very slowly",
                    "Increase in spam email",
                    "Random operating system updates",
                    "YouTube is not loading"};
            QuizQuestion q2 = new QuizQuestion("Which of the following may be an indication that a computer has" +
                    " been infected by a virus or other malware?",options2, "Computer runs very slowly",2);

            q2.setQuiz(w1Quiz);

            q2 = quizQuestionRepository.save(q2);

            w1Quiz.addQuestion(q2);

            quizRepository.save(w1Quiz);
        }
    }
}
