/**
 * Created by Home on 21.09.2015.
 */
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class swing {
    static JFrame frame = new JFrame();
    static JPanel panel = new JPanel();

    static JLabel answerLabel = new JLabel();
    static JLabel statisticsLabel = new JLabel();
    static JLabel imageLabel = null;

    static JButton getKnown;
    static JButton nextExcercise;
    static JButton end;

    static String path = ".\\img\\";
    static String wordsPath = path + "words.txt";

    static ArrayList<String> words = getWords();

    static JTextField field = new JTextField();

    static int x;

    static String word = "";

    static int countOfAll = 0;
    static int countOfCorrect = 0;
    static int numOfTests = 10;

    static boolean isChecked = false;
    static byte mode = -1;

    public static void main(String[] a) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        JFrame frame = initializeFrame();
    }

    public static JFrame initializeFrame() {
        panel = initializeMainMenu();

        frame.add(panel);
        frame.setSize(800, 700);
        frame.setLocationRelativeTo((Component) null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);

        return frame;
    }

    public static JPanel initializeMainMenu() {
        JPanel panel = new JPanel();
        panel.setSize(800, 700);
        panel.setBackground(new Color(255, 198, 169));
        panel.setLayout(null);

        JLabel picLabel = new JLabel(new ImageIcon(path + "theme.png"));
        picLabel.setBounds(170, 40, 500, 300);
        panel.add(picLabel);

        JLabel slogan = slogan();
        panel.add(slogan);

        JButton testsButton = getTestsButton(), studyButton = getStudyButton(), learningButton = getLearningButton();

        panel.add(testsButton);
        panel.add(studyButton);
        panel.add(learningButton);

        return panel;
    }

    public static JButton patternButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(400, 100));
        button.setBackground(new Color(59, 89, 182));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", 1, 12));

        button.addMouseListener(new CustomListener());

        return button;
    }

    public static JButton getTestsButton() {
        JButton button = patternButton();
        button.setBounds(270, 260, 250, 50);
        button.setText("Start");
        button.setName("Start");


        return button;
    }
    public static JButton getStudyButton() {
        JButton button = patternButton();
        button.setBounds(270, 330, 250, 50);
        button.setName("Training");
        button.setText("Training");
        return button;
    }
    public static JButton getLearningButton() {
        JButton button = patternButton();
        button.setBounds(270, 400, 250, 50);
        button.setName("Learning");
        button.setText("Learn words");
        return button;
    }
    public static JLabel slogan() {
        JLabel slogan = new JLabel();
        slogan.setText(phrase());
        slogan.setFont(new Font("MV boli", Font.PLAIN, 17));
        slogan.setBounds(350, 100, 400, 200);

        return slogan;
    }
    public static String phrase() {
        String[] phrases = new String[]{
                "Good luck!",
                "It\'s a good day for tests!",
                "Yes, I\'ve stolen the idea from Minecraft",
                "London is the capital of Great Britain",
                "4 8 15 16 23 42",
                "SAY WHAT AGAIN!",
                "They call it a Royale with cheese",
                "Good boy, Chop!",
                "Hey, Niko, my little brother!",
                "This is my destiny! Don\'t tell me \nwhat I can\'t do!",
                "See you in another life, brother",
                "Buy an elephant!",
                "while (true) { do(nothing); }",
                "I have a dream",
                "No, Harry. The wand has chosen you!",
                "Keep your chin up, guy!",
                "Sweeeeeeeeeeeet",
                "Just do it!" };
        return phrases[ (int) (Math.random() * 100) % phrases.length];
    }
    public static String getResultText(double p) {
        String s = "<html>";
        s += (int) (p * 100) + " % of answers are right. ";
        s += "\nRecommended mark: ";

        if (p < 0.85) {
            if (p < 0.65) {
                if (p < 0.51) {
                    s += 2;

                    s += "<br>";
                    s += "Main thing is to never give up!";
                }
                else {
                    s += 3;

                    s += "<br>";
                    s += "Work! You have a big potential!";
                }
            }
            else {
                s += 4;

                s += "<br>";
                s += "A little effort and you'll be a master!";
            }
        }
        else {
            s += 5;

            s += "<br>";
            s += "Keep going!";
        }
        s += "</html>";
        return s;
    }

    public static ArrayList<String> getWords()  {
        try {
            ArrayList<String> arr = new ArrayList<>();
            Scanner in = new Scanner(new File(".\\img\\words.txt"));


            while (in.hasNextLine()) {
                arr.add(in.nextLine());
            }
            return arr;
        }
        catch (Exception ex) {
            System.out.println("Error");
        }
        return null;
    }
    public static JLabel getImageLabel(int i) {
        JLabel picLabel = new JLabel();


        if (new File(".\\img\\" + Integer.toString(i) + ".jpg").exists()) {
            picLabel = new JLabel(new ImageIcon(".\\img\\" + Integer.toString(i) + ".jpg"));
        }
        else if (new File(".\\img\\" + Integer.toString(i) + ".jpeg").exists()) {
            picLabel = new JLabel(new ImageIcon(".\\img\\" + Integer.toString(i) + ".jpeg"));
        }
        else if (new File(".\\img\\" + Integer.toString(i) + ".png").exists()) {
            picLabel = new JLabel(new ImageIcon(".\\img\\" + Integer.toString(i) + ".png"));
        }
        picLabel.setLayout(null);
        picLabel.setBounds(235, 60, 320, 320);

        return picLabel;
    }
    static class CustomListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            String name = e.getComponent().getName();
             if (name.equals("Start") || name.equals("Training") || name.equals("Learning")) {
                 switch( name ) {
                     case "Start": mode = 0; break;
                     case "Training": mode = 1; break;
                     case "Learning": mode = 2; break;
                 }
                 panel.removeAll();
                 startTest();

                 //frame.repaint();
             }
             else if (name.equals("Next")){
                 if (mode == 0) {
                     if (field.getText().equals(word)) {
                             answerLabel.setText("Correct!");
                             countOfCorrect++;
                     }
                     countOfAll ++;
                     if (countOfAll >= numOfTests && mode == 0) {
                         endTest();
                     }
                     else {
                         setNewExcercise();
                     }
                 }
                 else {
                     setNewExcercise();
                 }
             }
             else if (name.equals("Check answer") ) {
                if (field.getText().equals(word)) {
                    if (!isChecked) {
                        countOfCorrect++;
                    }
                    answerLabel.setText("Correct!");
                }
                 else {
                    answerLabel.setText("Incorrect");
                }
                 if (!isChecked) {
                     countOfAll++;
                     int percent = (int) (((double) countOfCorrect / (double) countOfAll) * 100);

                     statisticsLabel.setText(Integer.toString(percent) + " % were correct");

                     isChecked = true;
                     System.out.println(percent);
                 }
             }
             else if (name.equals("End")) {
                 if (mode == 0) {
                     endTest();
                 }
                 else {
                     countOfAll = 0;
                     countOfCorrect = 0;
                     numOfTests = 10;

                     isChecked = false;
                     mode = 0;

                     panel.removeAll();
                     panel = initializeMainMenu();

                     panel.repaint();
                     frame.repaint();
                 }
             }
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
        }

    public static void startTest() {
        panel.removeAll();
        setNewExcercise();

        System.out.println(mode);

        if (mode == 0 || mode == 1) {
            field = new JTextField();
            field.setBounds(300, 450, 200, 40);
            field.setFont(new Font("Tahoma", Font.PLAIN, 17));
        }

        if (mode == 1) {
            getKnown = patternButton();
            getKnown.setBounds(300, 490, 200, 50);
            getKnown.setText("Check answer");
            getKnown.setName("Check answer");

            panel.add(getKnown);
        }

        if (mode == 1) {
            answerLabel = slogan();
            answerLabel.setBounds(300, 580, 200, 50);
            answerLabel.setText("Your answer is ...");

            panel.add(answerLabel);
        }

        if (mode == 1) {
            statisticsLabel = slogan();
            statisticsLabel.setBounds(300, 610, 200, 50);
            statisticsLabel.setText("It's no info yet");

            panel.add(statisticsLabel);
        }

        nextExcercise = patternButton();
        nextExcercise.setBounds(300, 540, 200, 50);
        nextExcercise.setText("Next");
        nextExcercise.setName("Next");

        end = patternButton();
        end.setBounds(300, 40, 200, 50);
        end.setText("End");
        end.setName("End");
        if (mode == 0) { end.setName("EndTest"); }

        panel.add(nextExcercise);
        panel.add(field);
        panel.add(end);

        panel.validate();
        panel.repaint();
    }
    public static void setNewExcercise() {
        isChecked = false;

        if (imageLabel != null) { panel.remove(imageLabel); }
        answerLabel.setText("Your answer is ...");

        x = (int) (Math.random() * 100) % 51;

        imageLabel = getImageLabel(x);
        word = words.get(x);

    //    panel.removeAll();

        panel.add(imageLabel);

        panel.validate();
        panel.repaint();

        panel.setVisible(true);

        frame.validate();
        frame.repaint();
    }
    public static void endTest() {
        mode = -1;

        panel.removeAll();
        panel.repaint();

        JLabel resultText = new JLabel();
        resultText.setBounds(300, 150, 300, 280);
        resultText.setFont(new Font("Tahoma", Font.PLAIN, 17));
        resultText.setText(getResultText((double) countOfCorrect / (double) numOfTests));

        JButton button = patternButton();
        button.setBounds(300, 360, 200, 50);
        button.setName("ToMenu");
        button.setText("To menu");

        panel.add(button);
        panel.add(resultText);
    }
}

