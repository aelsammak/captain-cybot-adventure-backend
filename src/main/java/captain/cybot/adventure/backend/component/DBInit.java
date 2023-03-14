package captain.cybot.adventure.backend.component;

import captain.cybot.adventure.backend.constants.COSMETICS;
import captain.cybot.adventure.backend.constants.ROLES;
import captain.cybot.adventure.backend.model.question.*;
import captain.cybot.adventure.backend.model.quiz.Quiz;
import captain.cybot.adventure.backend.model.quiz.QuizQuestion;
import captain.cybot.adventure.backend.model.user.Cosmetic;
import captain.cybot.adventure.backend.model.user.Role;
import captain.cybot.adventure.backend.repository.question.*;
import captain.cybot.adventure.backend.repository.quiz.QuizQuestionRepository;
import captain.cybot.adventure.backend.repository.quiz.QuizRepository;
import captain.cybot.adventure.backend.repository.user.CosmeticRepository;
import captain.cybot.adventure.backend.repository.user.RoleRepository;
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
                    {"R","D","P","T","I","O","D","J","I","Z"},
                    {"E","A","E","L","E","V","W","V","F","Y"},
                    {"E","M","N","P","I","C","I","J","U","A"},
                    {"P","A","K","Q","Q","C","T","R","K","F"},
                    {"E","G","X","X","N","S","A","I","U","G"},
                    {"R","E","L","B","G","C","C","T","O","S"},
                    {"K","M","A","L","W","A","R","E","E","N"}
            };
            String[] answers = {"protection", "antivirus", "replicate", "slow", "malware", "creeper", "damage"};
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
            WordScramble w2Q1Question =  wordScrambleRepository.save(new WordScramble("LAWERAM",
                    "MALWARE"));
            w2Q1 = new QuestionOrder(w2Q1Question, 1, "MARS");
            questionOrderRepository.save(w2Q1);
        }

        if (w2Q2 == null) {
            GuessTheImage w2Q2Question = guessTheImageRepository.save(new GuessTheImage("World2Question2.png",
                    "Network"));
            w2Q2 = new QuestionOrder(w2Q2Question, 2, "MARS");
            questionOrderRepository.save(w2Q2);
        }

        if (w2Q3 == null) {
            String[][] searchBlock = {
                    {"A","P","P","L","I","C","A","T","I","O","N"},
                    {"N","H","S","A","H","C","J","V","E","A","M"},
                    {"E","D","T","H","J","N","V","A","E","T","P"},
                    {"T","H","A","R","D","W","A","R","E","L","U"},
                    {"W","Q","Z","G","H","K","L","Z","M","G","F"},
                    {"O","W","E","M","A","I","L","A","C","Y","I"},
                    {"R","O","R","F","Y","B","H","J","Q","N","L"},
                    {"K","R","T","H","V","U","Z","Q","P","I","E"},
                    {"O","M","M","R","A","H","A","C","K","E","R"},
                    {"I","N","T","E","R","N","E","T","H","T","K"}
            };
            String[] answers = {"application", "internet", "hardware", "file", "network", "hacker", "email", "worm"};
            WordSearch w2Q3Question = wordSearchRepository.save(new WordSearch(searchBlock, answers));
            w2Q3 = new QuestionOrder(w2Q3Question, 3, "MARS");
            questionOrderRepository.save(w2Q3);
        }

        if (w2Q4 == null) {
            String[][] crosswordBlock = {
                    {"|","|","|","|","|","1","|","|","|","|","|","|","|"},
                    {"|","|","2","|","|","X","|","|","|","|","|","|","|"},
                    {"|","|","X","|","|","X","|","3","|","|","|","|","|"},
                    {"|","|","X","|","|","X","|","X","|","|","|","|","|"},
                    {"4","X","X","5","X","X","X","X","|","|","6","|","|"},
                    {"|","|","|","X","|","X","|","7","X","X","X","X","X"},
                    {"|","|","|","X","|","X","|","X","|","|","X","|","|"},
                    {"|","|","|","X","|","X","|","X","|","|","X","|","|"},
                    {"|","|","|","X","|","|","|","|","|","|","X","|","|"},
                    {"|","8","X","X","X","|","|","|","|","|","X","|","|"},
                    {"|","|","|","X","|","|","|","|","|","|","X","|","|"},
                    {"|","|","|","|","|","|","|","|","|","|","X","|","|"}
            };
            String[] hints = {"Has keys, used to type letters and numbers onto a computer.",
                    "Not fast",
                    "To upgrade software",
                    "The act of copying data from one computer to another, typically over the internet.",
                    "A group of devices that are connected to each other that can exchange and share data with each other",
                    "The physical parts of a computer. Not Software",
                    "Attempt to cause harm.",
                    "A small animal that is use as fish bait"};
            String[] answers = {"keyboard", "slow", "update", "download", "network", "hardware", "attack", "worm"};
            Crossword w2Q4Question = crosswordRepository.save(new Crossword(crosswordBlock, hints, answers));
            w2Q4 = new QuestionOrder(w2Q4Question, 4, "MARS");
            questionOrderRepository.save(w2Q4);
        }

        if (w3Q1 == null) {
            WordScramble w3Q1Question =  wordScrambleRepository.save(new WordScramble("DHDIEN",
                    "HIDDEN"));
            w3Q1 = new QuestionOrder(w3Q1Question, 1, "NEPTUNE");
            questionOrderRepository.save(w3Q1);
        }

        if (w3Q2 == null) {
            GuessTheImage w3Q2Question = guessTheImageRepository.save(new GuessTheImage("World3Question2.png",
                    "Smartphone"));
            w3Q2 = new QuestionOrder(w3Q2Question, 2, "NEPTUNE");
            questionOrderRepository.save(w3Q2);
        }

        if (w3Q3 == null) {
            String[][] searchBlock = {
                    {"M","G","U","V","M","A","L","I","C","I","O","U","S","S","I"},
                    {"A","P","P","L","I","C","A","T","I","O","N","S","B","J","U"},
                    {"T","H","C","C","H","T","O","U","G","T","K","Y","Z","O","P"},
                    {"W","O","T","P","R","I","V","I","L","E","G","E","J","T","I"},
                    {"H","T","P","D","L","B","G","D","E","V","B","E","N","H","J"},
                    {"I","R","E","I","Z","I","Y","D","D","K","I","R","R","Z","A"},
                    {"D","O","J","Q","S","M","A","R","T","P","H","O","N","E","Z"},
                    {"D","J","Z","N","H","A","K","X","X","J","D","Q","U","T","N"},
                    {"E","A","P","R","O","G","R","A","M","S","E","T","U","S","N"},
                    {"N","N","A","D","M","I","N","I","S","T","R","A","T","O","R"},
            };
            String[] answers = {"administrator", "application", "smartphone", "malicious", "programs", "trojan", "privilege", "hidden"};
            WordSearch w3Q3Question = wordSearchRepository.save(new WordSearch(searchBlock, answers));
            w3Q3 = new QuestionOrder(w3Q3Question, 3, "NEPTUNE");
            questionOrderRepository.save(w3Q3);
        }

        if (w3Q4 == null) {
            String[][] crosswordBlock = {
                    {"|","|","|","|","|","|","|","|","1","|","2","|","|",},
                    {"|","|","|","|","|","|","|","|","X","|","X","|","|",},
                    {"|","|","|","|","|","|","|","|","X","|","X","|","|",},
                    {"|","|","|","|","|","|","|","|","X","|","X","|","|",},
                    {"3","X","X","X","X","X","4","|","X","|","X","|","5",},
                    {"|","|","|","|","|","|","6","X","X","X","X","X","X",},
                    {"|","|","|","|","|","|","X","|","|","|","X","|","X",},
                    {"|","|","|","|","|","|","X","|","|","|","X","|","X",},
                    {"|","7","X","X","X","X","X","X","X","|","X","|","X",},
                    {"|","|","|","|","|","|","X","|","|","|","|","|","X",},
                    {"|","|","|","|","|","|","X","|","|","|","|","|","X",},
                    {"|","|","|","|","|","8","X","X","X","|","|","|","|",},
                    {"|","|","|","|","|","|","X","|","|","|","|","|","|",},
                    {"|","|","|","|","|","|","X","|","|","|","|","|","|",},
            };
            String[] hints = {"A program or utility with malicious hidden code.",
                    "Remove an application or file from a computer",
                    "When a machine, system, or software fails suddenly",
                    "A mobile phone that performs many of the functions of a computer",
                    "When a computer screen becomes temporarily locked because of system problems",
                    "Another word for a computer screen",
                    "Affected with a virus",
                    "Lines of ____; Program Instructions."};
            String[] answers = {"trojan", "uninstall", "crashes", "smartphone", "freezes", "monitor", "infected", "code"};
            Crossword w3Q4Question = crosswordRepository.save(new Crossword(crosswordBlock, hints, answers));
            w3Q4 = new QuestionOrder(w3Q4Question, 4, "NEPTUNE");
            questionOrderRepository.save(w3Q4);
        }

        if (w4Q1 == null) {
            WordScramble w4Q1Question =  wordScrambleRepository.save(new WordScramble("SARNMO",
                    "Ransom"));
            w4Q1 = new QuestionOrder(w4Q1Question, 1, "JUPITER");
            questionOrderRepository.save(w4Q1);
        }

        if (w4Q2 == null) {
            GuessTheImage w4Q2Question = guessTheImageRepository.save(new GuessTheImage("World4Question2.png",
                    "Money"));
            w4Q2 = new QuestionOrder(w4Q2Question, 2, "JUPITER");
            questionOrderRepository.save(w4Q2);
        }

        if (w4Q3 == null) {
            String[][] searchBlock = {
                    {"E","G","V","M","J","B","A","C","K","U","P"},
                    {"X","T","F","G","B","I","T","C","O","I","N"},
                    {"T","Z","K","T","Z","L","W","D","R","J","U"},
                    {"E","Y","Q","M","A","L","W","A","R","E","V"},
                    {"R","R","A","N","S","O","M","W","A","R","E"},
                    {"N","S","A","W","H","O","S","T","A","G","E"},
                    {"A","H","S","N","L","J","X","C","E","W","N"},
                    {"L","D","Y","G","S","F","I","H","R","X","U"},
                    {"O","W","T","A","J","O","M","O","N","E","Y"},
                    {"S","P","A","Y","M","E","N","T","A","U","S"},
            };
            String[] answers = {"ransomware", "external", "bitcoin", "payment", "malware", "hostage", "backup", "money"};
            WordSearch w4Q3Question = wordSearchRepository.save(new WordSearch(searchBlock, answers));
            w4Q3 = new QuestionOrder(w4Q3Question, 3, "JUPITER");
            questionOrderRepository.save(w4Q3);
        }

        if (w4Q4 == null) {
            String[][] crosswordBlock = {
                    {"|","|","1","|","|","|","|","2","|","3","|","|","|","|","|",},
                    {"|","|","X","|","|","|","|","X","|","X","|","|","|","|","|",},
                    {"|","|","X","|","|","|","|","X","|","X","|","|","|","|","|",},
                    {"4","X","X","X","X","X","|","X","|","X","|","|","|","|","|",},
                    {"|","|","X","|","|","|","|","X","|","X","|","|","|","|","|",},
                    {"|","|","X","|","5","|","|","X","|","6","X","X","X","X","X",},
                    {"7","X","X","X","X","X","X","X","X","X","|","|","|","|","|",},
                    {"|","|","|","|","X","|","|","X","|","|","|","|","|","|","|",},
                    {"|","|","|","|","X","|","|","|","|","|","|","|","|","|","|",},
                    {"|","|","|","8","X","X","X","X","X","X","X","X","|","|","|",},
                    {"|","|","|","|","X","|","|","|","|","|","|","|","|","|","|",},
                    {"|","|","|","|","X","|","|","|","|","|","|","|","|","|","|",},
            };
            String[] hints = {"Ransomware payments are often demanded in the form of _____.",
                    "Opposite of internal.",
                    "Ransomware is type of ______.",
                    "An extra copy of data from a computer.",
                    "A prisoner taken by kidnappers and held until the kidnappers get whatever they're asking for..",
                    "To hold a hostage and demand money for their release.",
                    "A type of malware that takes your information hostage until you pay a fee to get it back..",
                    "A storage device built into a computer."};
            String[] answers = {"bitcoin", "external", "malware", "backup", "hostage", "ransom", "ransomware", "harddrive"};
            Crossword w4Q4Question = crosswordRepository.save(new Crossword(crosswordBlock, hints, answers));
            w4Q4 = new QuestionOrder(w4Q4Question, 4, "JUPITER");
            questionOrderRepository.save(w4Q4);
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

            String[] options3 = {"Damage data files",
                    "Increasing the size of files by attaching themselves to the files",
                    "Slowing down the system by occupying most of the memory space",
                    "All the options listed"};
            QuizQuestion q3 = new QuizQuestion("Which of these are the harmful effects of viruses?",options3, "All the options listed",3);

            q3.setQuiz(w1Quiz);

            q3 = quizQuestionRepository.save(q3);

            w1Quiz.addQuestion(q3);

            String[] options4 = {"Malicious self-reproducing programs that change how a computer works",
                    "Threatens user with stolen data unless ransom is paid",
                    "Self-installed software that monitors your online behaviour",
                    "Software that protects your computer from cyber attacks"};
            QuizQuestion q4 = new QuizQuestion("What is a computer virus?",options4, "Malicious self-reproducing programs that change how a computer works",4);

            q4.setQuiz(w1Quiz);

            q4 = quizQuestionRepository.save(q4);

            w1Quiz.addQuestion(q4);

            String[] options5 = {"Online downloads",
                    "Infected emails",
                    "External hardware",
                    "Anti-Virus Software"};
            QuizQuestion q5 = new QuizQuestion("What is an example of how computer viruses DON’T spread?",options5, "Anti-Virus Software",5);

            q5.setQuiz(w1Quiz);

            q5 = quizQuestionRepository.save(q5);

            w1Quiz.addQuestion(q5);

            String[] options6 = {"Never open emails",
                    "Anti-Virus Software",
                    "Uninstall and reinstall programs periodically",
                    "Use the same password for all your accounts"};
            QuizQuestion q6 = new QuizQuestion("How can you protect your computer from a virus?",options6, "Anti-Virus Software",6);

            q6.setQuiz(w1Quiz);

            q6 = quizQuestionRepository.save(q6);

            w1Quiz.addQuestion(q6);

            String[] options7 = {"Deletes every file it suspects is infected",
                    "Stops people gaining unauthorised access to your computer via the internet",
                    "Inspects computer files and email attachments for viruses and removes any that it finds",
                    "Disables the internet"};
            QuizQuestion q7 = new QuizQuestion("What does anti-virus software do?",options7, "Inspects computer files and email attachments for viruses and removes any that it finds",7);

            q7.setQuiz(w1Quiz);

            q7 = quizQuestionRepository.save(q7);

            w1Quiz.addQuestion(q7);

            String[] options8 = {"Antivirus",
                    "Virus",
                    "Malware",
                    "Trojans"};
            QuizQuestion q8 = new QuizQuestion("Quick Heal, Norton , AVG, Smart Dog, McAffee are all examples of _______?",options8, "Antivirus",8);

            q8.setQuiz(w1Quiz);

            q8 = quizQuestionRepository.save(q8);

            w1Quiz.addQuestion(q8);


            String[] options9 = {"Mydoom",
                    "Creeper",
                    "Code Red",
                    "Sobig"};
            QuizQuestion q9 = new QuizQuestion("What is the name of first computer virus?",options9, "Creeper",9);

            q9.setQuiz(w1Quiz);

            q9 = quizQuestionRepository.save(q9);

            w1Quiz.addQuestion(q9);

            String[] options10 = {"Identity Theft",
                    "Pranks",
                    "Research Purpose",
                    "Protection"};
            QuizQuestion q10 = new QuizQuestion("Why do people NOT create computer viruses?",options10, "Protection",10);

            q10.setQuiz(w1Quiz);

            q10 = quizQuestionRepository.save(q10);

            w1Quiz.addQuestion(q10);

            quizRepository.save(w1Quiz);
        }

        if (w2Quiz == null) {
            w2Quiz = new Quiz("MARS");
            quizRepository.save(w2Quiz);

            String[] options1 = {"Network Worm",
                    "Bozo Worm",
                    "Internet Worm",
                    "Ransom Worm"};
            QuizQuestion q1 = new QuizQuestion("A _______ is a computer worm which copies different segments of itself to computers linked together" +
                    "in a network.",options1, "Network Worm",1);

            q1.setQuiz(w2Quiz);

            q1 = quizQuestionRepository.save(q1);

            w2Quiz.addQuestion(q1);

            String[] options2 = {"Email Worms",
                    "Internet Worms",
                    "File-Sharing Worms",
                    "Earth Worms"};
            QuizQuestion q2 = new QuizQuestion("Which of the following is not a type of computer worm?",
                    options2, "Earth Worms",2);

            q2.setQuiz(w2Quiz);

            q2 = quizQuestionRepository.save(q2);

            w2Quiz.addQuestion(q2);

            String[] options3 = {"A type of malicious software that replicates while moving across computers",
                    "An organism used as bait for fishing",
                    "A worm that snuck into my computer",
                    "Software that protects your computer from cyber attacks."};
            QuizQuestion q3 = new QuizQuestion("What is a computer worm?",
                    options3, "A worm is a type of malicious software that replicates while moving across computers, leaving" +
                    "copies of itself in the memory of each computer in its path.",3);

            q3.setQuiz(w2Quiz);

            q3 = quizQuestionRepository.save(q3);

            w2Quiz.addQuestion(q3);

            String[] options4 = {"False", "True"};
            QuizQuestion q4 = new QuizQuestion("Can a worm be active dormant like a virus (True/False)",
                    options4, "False",4);

            q4.setQuiz(w2Quiz);

            q4 = quizQuestionRepository.save(q4);

            w2Quiz.addQuestion(q4);

            String[] options5 = {"Hackers",
                    "Human users launching the virus",
                    "Computer Networks/ The internet",
                    "Trojan Horses"};
            QuizQuestion q5 = new QuizQuestion("A worm replicates itself and spreads malicious code to other computers by using ____.",
                    options5, "Computer Networks/ The internet",5);

            q5.setQuiz(w2Quiz);

            q5 = quizQuestionRepository.save(q5);

            w2Quiz.addQuestion(q5);

            String[] options6 = {"Virus",
                    "Malware",
                    "Both Virus and Malware",
                    "Neither Virus nor Malware"};
            QuizQuestion q6 = new QuizQuestion("A worm is type of ___.",options6, "Both Virus and Malware",6);

            q6.setQuiz(w2Quiz);

            q6 = quizQuestionRepository.save(q6);

            w2Quiz.addQuestion(q6);

            String[] options7 = {"Find sensitive files on your machine and steal information",
                    "Log all your keystrokes",
                    "Be used to catch fish for dinner",
                    "Launch an attack on the computers on the Internet"};
            QuizQuestion q7 = new QuizQuestion("A computer worm CAN'T...",options7, "Be used to catch fish for dinner",7);

            q7.setQuiz(w2Quiz);

            q7 = quizQuestionRepository.save(q7);

            w2Quiz.addQuestion(q7);

            String[] options8 = {"Always update your software applications with the latest versions to eliminate possible security " +
                    "flaws",
                    "Avoid clicking on random links and opening emails from people that you do not know",
                    "Install an anti-virus",
                    "All the above"};
            QuizQuestion q8 = new QuizQuestion("What are ways of protecting your computer from worms?",options8, "All the above",8);

            q8.setQuiz(w2Quiz);

            q8 = quizQuestionRepository.save(q8);

            w2Quiz.addQuestion(q8);


            String[] options9 = {"Slow computer performance",
                    "Missing/modified files",
                    "Strange looking application icons",
                    "Emails sent to your contacts without your knowledge",
                    "All the above"};
            QuizQuestion q9 = new QuizQuestion("What are some symptoms of a computer worm?",options9, "All the above",9);

            q9.setQuiz(w2Quiz);

            q9 = quizQuestionRepository.save(q9);

            w2Quiz.addQuestion(q9);

            String[] options10 = {"A worm replicates itself and a virus does not",
                    "A virus replicates itself and a worm does not",
                    "A virus needs to be attached to another file and a worm does not",
                    "A worm needs to be attached to another file and a virus does not"};
            QuizQuestion q10 = new QuizQuestion("What is the difference between viruses and worms?",options10, "A virus needs to be attached to another file and a worm does not",10);

            q10.setQuiz(w2Quiz);

            q10 = quizQuestionRepository.save(q10);

            w2Quiz.addQuestion(q10);

            quizRepository.save(w2Quiz);
        }

        if (w3Quiz == null) {
            w3Quiz = new Quiz("NEPTUNE");
            quizRepository.save(w3Quiz);

            String[] options1 = {"A software that will send many unwanted emails",
                    "A program or utility with malicious hidden code",
                    "A software that captures the entered keys of the computer",
                    "A program to use an infected machine to attack other machines"};
            QuizQuestion q1 = new QuizQuestion("What is a trojan?",options1, "A program or utility with malicious hidden code",1);

            q1.setQuiz(w3Quiz);

            q1 = quizQuestionRepository.save(q1);

            w3Quiz.addQuestion(q1);

            String[] options2 = {"Yes","No"};
            QuizQuestion q2 = new QuizQuestion("If you installed a program and it runs as you would expect, could the program still contain a trojan?",
                    options2, "Yes",2);

            q2.setQuiz(w3Quiz);

            q2 = quizQuestionRepository.save(q2);

            w3Quiz.addQuestion(q2);

            String[] options3 = {"Running anti-virus scans often",
                    "Keeping applications up to date",
                    "Avoiding suspicious websites",
                    "All the above"};
            QuizQuestion q3 = new QuizQuestion("How can you prevent a trojan?",
                    options3, "All the above",3);

            q3.setQuiz(w3Quiz);

            q3 = quizQuestionRepository.save(q3);

            w3Quiz.addQuestion(q3);

            String[] options4 = {"Allow the attacker access to the data on the computer",
                    "Allow the attacker to watch online activities performed on the computer",
                    "Download other malware",
                    "All the above"};
            QuizQuestion q4 = new QuizQuestion("What can a trojan do on an infected system?",
                    options4, "All the above",4);

            q4.setQuiz(w3Quiz);

            q4 = quizQuestionRepository.save(q4);

            w3Quiz.addQuestion(q4);

            String[] options5 = {"The same as the program it is hidden in",
                    "Administrator privileges",
                    "The privileges assigned to current user",
                    "None of the above"};
            QuizQuestion q5 = new QuizQuestion("What level of privileges does a trojan have on the system?",
                    options5, "The same as the program it is hidden in",5);

            q5.setQuiz(w3Quiz);

            q5 = quizQuestionRepository.save(q5);

            w3Quiz.addQuestion(q5);

            String[] options6 = {"Yes",
                    "No"};
            QuizQuestion q6 = new QuizQuestion("Are trojans found on smartphones?",options6, "Yes",6);

            q6.setQuiz(w3Quiz);

            q6 = quizQuestionRepository.save(q6);

            w3Quiz.addQuestion(q6);

            String[] options7 = {"Yes",
                    "No"};
            QuizQuestion q7 = new QuizQuestion("Can a trojan self-replicate?",options7, "No",7);

            q7.setQuiz(w3Quiz);

            q7 = quizQuestionRepository.save(q7);

            w3Quiz.addQuestion(q7);

            String[] options8 = {"Yes","No"};
            QuizQuestion q8 = new QuizQuestion("Can a trojan be removed by uninstalling the host program?",options8, "Yes",8);

            q8.setQuiz(w3Quiz);

            q8 = quizQuestionRepository.save(q8);

            w3Quiz.addQuestion(q8);


            String[] options9 = {"Computer is running slow",
                    "Computer crashes or freezes",
                    "Increase in number of pop-ups",
                    "All the above"};
            QuizQuestion q9 = new QuizQuestion("What are symptoms of a trojan?",options9, "All the above",9);

            q9.setQuiz(w3Quiz);

            q9 = quizQuestionRepository.save(q9);

            w3Quiz.addQuestion(q9);

            String[] options10 = {"Yes",
                    "No"};
            QuizQuestion q10 = new QuizQuestion("Is a trojan a virus?",options10, "No",10);

            q10.setQuiz(w3Quiz);

            q10 = quizQuestionRepository.save(q10);

            w3Quiz.addQuestion(q10);

            quizRepository.save(w3Quiz);
        }

        if (w4Quiz == null) {
            w4Quiz = new Quiz("JUPITER");
            quizRepository.save(w4Quiz);

            String[] options1 = {"Ransomware",
                    "Phishing software",
                    "Hacking software",
                    "Kidnapping software"};
            QuizQuestion q1 = new QuizQuestion("_____ is a type of malware that takes your information hostage until you pay a fee to get it back.",options1, "Ransomware",1);

            q1.setQuiz(w4Quiz);

            q1 = quizQuestionRepository.save(q1);

            w4Quiz.addQuestion(q1);

            String[] options2 = {"Credit cards","Bitcoin", "Bank account numbers", "Subway coupons"};
            QuizQuestion q2 = new QuizQuestion("Ransomware payments are often demanded in the form of _____.",
                    options2, "Bitcoin",2);

            q2.setQuiz(w4Quiz);

            q2 = quizQuestionRepository.save(q2);

            w4Quiz.addQuestion(q2);

            String[] options3 = {"True",
                    "False"};
            QuizQuestion q3 = new QuizQuestion("Ransomware can’t affect you if you have an anti-virus software installed. True or False?",
                    options3, "False",3);

            q3.setQuiz(w4Quiz);

            q3 = quizQuestionRepository.save(q3);

            w4Quiz.addQuestion(q3);

            String[] options4 = {"True",
                    "False"};
            QuizQuestion q4 = new QuizQuestion("The best way to deal with ransomware is to pay the attacker. True or False?",
                    options4, "False",4);

            q4.setQuiz(w4Quiz);

            q4 = quizQuestionRepository.save(q4);

            w4Quiz.addQuestion(q4);

            String[] options5 = {"True",
                    "False"};
            QuizQuestion q5 = new QuizQuestion("Keeping a backup of your data on an external hard drive is a good way to protect against a" +
                    "ransomware attack. True or False",
                    options5, "True",5);

            q5.setQuiz(w4Quiz);

            q5 = quizQuestionRepository.save(q5);

            w4Quiz.addQuestion(q5);

            String[] options6 = {"Worm",
                    "Phishing",
                    "Malware",
                    "Trojan"};
            QuizQuestion q6 = new QuizQuestion("Ransomware is type of ______:",options6, "Malware",6);

            q6.setQuiz(w4Quiz);

            q6 = quizQuestionRepository.save(q6);

            w4Quiz.addQuestion(q6);

            String[] options7 = {"True",
                    "False"};
            QuizQuestion q7 = new QuizQuestion("You can also get ransomware on your phone. True or False?",options7, "True",7);

            q7.setQuiz(w4Quiz);

            q7 = quizQuestionRepository.save(q7);

            w4Quiz.addQuestion(q7);

            String[] options8 = {"Ransom","Kidnap","Victim","Robbery"};
            QuizQuestion q8 = new QuizQuestion("_______ means to hold a hostage and demand money for their release.",options8, "Ransom",8);

            q8.setQuiz(w4Quiz);

            q8 = quizQuestionRepository.save(q8);

            w4Quiz.addQuestion(q8);


            String[] options9 = {"True",
                    "False"};
            QuizQuestion q9 = new QuizQuestion("You can get ransomware from clicking a link on an email. True or False?",options9, "True",9);

            q9.setQuiz(w4Quiz);

            q9 = quizQuestionRepository.save(q9);

            w4Quiz.addQuestion(q9);

            String[] options10 = {"To steal your data",
                    "To crash your computer",
                    "To get money",
                    "To hack you"};
            QuizQuestion q10 = new QuizQuestion("What is the main reason an attacker performs an attack using ransomware?",options10, "To get money",10);

            q10.setQuiz(w4Quiz);

            q10 = quizQuestionRepository.save(q10);

            w4Quiz.addQuestion(q10);

            quizRepository.save(w4Quiz);
        }
    }
}
