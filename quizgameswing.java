package quizswing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizGameSwing extends JFrame implements ActionListener {

    Question[] questions;
    int currentQuestion = 0;
    int reward = 0;

    boolean fiftyUsed = false;
    boolean skipUsed = false;

    JLabel questionLabel, rewardLabel;
    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup bg;
    JButton nextBtn, fiftyBtn, skipBtn;

    QuizGameSwing() {

        // Questions
        questions = new Question[]{
            new Question("Which language is platform independent?",
                    new String[]{"C", "C++", "Java", "Python"}, 3),

            new Question("Which keyword is used to inherit a class in Java?",
                    new String[]{"this", "super", "extends", "implements"}, 3),

            new Question("Which company originally developed Java?",
                    new String[]{"Microsoft", "Sun Microsystems", "Google", "Oracle"}, 2),

            new Question("Which of these is not an OOP concept?",
                    new String[]{"Inheritance", "Polymorphism", "Compilation", "Encapsulation"}, 3),

            new Question("Which collection does not allow duplicates?",
                    new String[]{"List", "Set", "Queue", "ArrayList"}, 2),

            new Question("Which method is entry point of Java?",
                    new String[]{"start()", "main()", "run()", "init()"}, 2),

            new Question("Highest visibility access modifier?",
                    new String[]{"private", "protected", "default", "public"}, 4),

            new Question("Keyword used to create object?",
                    new String[]{"class", "new", "this", "object"}, 2),

            new Question("Which is checked exception?",
                    new String[]{"NullPointerException", "ArithmeticException", "IOException", "ArrayIndexOutOfBoundsException"}, 3),

            new Question("Which collection stores key-value pairs?",
                    new String[]{"List", "Set", "Map", "Queue"}, 3)
        };

        // Frame
        setTitle("Java Quiz Game");
        setSize(500, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Top Panel
        JPanel top = new JPanel(new GridLayout(2, 1));
        questionLabel = new JLabel("", JLabel.CENTER);
        rewardLabel = new JLabel("Reward: ₹0", JLabel.CENTER);
        top.add(questionLabel);
        top.add(rewardLabel);

        // Center Panel
        JPanel center = new JPanel(new GridLayout(4, 1));
        bg = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            bg.add(options[i]);
            center.add(options[i]);
        }

        // Bottom Panel
        JPanel bottom = new JPanel();
        nextBtn = new JButton("Next");
        fiftyBtn = new JButton("50-50");
        skipBtn = new JButton("Skip");

        bottom.add(nextBtn);
        bottom.add(fiftyBtn);
        bottom.add(skipBtn);

        nextBtn.addActionListener(this);
        fiftyBtn.addActionListener(this);
        skipBtn.addActionListener(this);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        loadQuestion();
        setVisible(true);
    }

    void loadQuestion() {

        if (currentQuestion == 9) {
            fiftyBtn.setEnabled(false);
            skipBtn.setEnabled(false);
        }

        bg.clearSelection();
        Question q = questions[currentQuestion];
        questionLabel.setText("Q" + (currentQuestion + 1) + ": " + q.question);

        for (int i = 0; i < 4; i++) {
            options[i].setText(q.options[i]);
            options[i].setVisible(true);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == nextBtn) {
            checkAnswer();
        }

        if (e.getSource() == fiftyBtn && !fiftyUsed) {
            fiftyUsed = true;
            useFiftyFifty();
            fiftyBtn.setEnabled(false);
        }

        if (e.getSource() == skipBtn && !skipUsed) {
            skipUsed = true;
            currentQuestion++;
            skipBtn.setEnabled(false);
            loadQuestion();
        }
    }

    void checkAnswer() {
        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selected = i + 1;
            }
        }

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select an option!");
            return;
        }

        if (questions[currentQuestion].isCorrect(selected)) {
            reward += 1000;
            rewardLabel.setText("Reward: ₹" + reward);
            currentQuestion++;

            if (currentQuestion == questions.length) {
                JOptionPane.showMessageDialog(this,
                        "🎉 Quiz Completed!\nTotal Reward: ₹" + reward);
                System.exit(0);
            }

            loadQuestion();
        } else {
            JOptionPane.showMessageDialog(this,
                    "❌ Wrong Answer!\nGame Over\nTotal Reward: ₹" + reward);
            System.exit(0);
        }
    }

    void useFiftyFifty() {
        int correct = questions[currentQuestion].correctAnswer;
        for (int i = 0; i < 4; i++) {
            if (i + 1 != correct && i != 0) {
                options[i].setVisible(false);
            }
        }
    }

    public static void main(String[] args) {
        new QuizGameSwing();
    }
}

// Question class (same file)
class Question {
    String question;
    String[] options;
    int correctAnswer;

    Question(String q, String[] o, int c) {
        question = q;
        options = o;
        correctAnswer = c;
    }

    boolean isCorrect(int ans) {
        return ans == correctAnswer;
    }
}


