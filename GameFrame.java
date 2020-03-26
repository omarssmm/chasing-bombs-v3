import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.Random;
import java.util.Arrays;
import java.util.List;

/**
 * BTEC minesweeper 
 *
 * @author Omar Moharram
 * @version 26.03.2020
 */
public class GameFrame extends JFrame
{
    private static final int ROWS = 2;
    private static final int COLS = 5;
    private static final int EASY = 5;
    private static final int MEDIUM = 7;
    private static final int HARD = 9;
    
    private JButton [] bombs = new JButton[ROWS*COLS];
    
    private JLabel scoreLabel;
    private JLabel statusLabel;

    private int score;
    private int bombLocation;
    private int winningScore;
    /**
     * Constructor for objects of class GameFrame
     */
    public GameFrame()
    {
        super("Chasing Bombs");
        setSize(950,550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // default is easy
        winningScore = EASY;
        makeFrame();
    }
    
    /**
     * Create the main game frame along with all needed components.
     */
    private void makeFrame()
    {
        JPanel parentPanel = new JPanel(new GridLayout(1, 3));
        JPanel gamePanel = new JPanel(new GridLayout(ROWS, COLS));    
        JPanel levelPanel = new JPanel();
        JPanel startPanel = new JPanel();
        
        scoreLabel  = new JLabel();
        statusLabel = new JLabel();
        
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.PAGE_AXIS));
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.PAGE_AXIS));

        JButton play = new JButton("Play A Game");
        JButton exit = new JButton("Exit");
        
        startPanel.add(createArea());
        startPanel.add(play);
        startPanel.add(createArea());
        startPanel.add(exit);
        startPanel.add(createArea());
        startPanel.add(scoreLabel);
        startPanel.add(createArea());
        startPanel.add(statusLabel);
        
        centreComponent(play);
        centreComponent(exit);
        centreComponent(scoreLabel);
        centreComponent(statusLabel);

        JButton easy = new JButton("Easy");
        JButton intermediate = new JButton("Intermediate");
        JButton difficult = new JButton("Difficult");
        levelPanel.add(createArea());
        levelPanel.add(easy);
        levelPanel.add(createArea());
        levelPanel.add(intermediate);
        levelPanel.add(createArea());
        levelPanel.add(difficult);
        
        centreComponent(easy);
        centreComponent(intermediate);
        centreComponent(difficult);

        easy.addActionListener(e -> winningScore = EASY);
        intermediate.addActionListener(e -> winningScore = MEDIUM);
        difficult.addActionListener(e -> winningScore = HARD);
        
        play.addActionListener(e -> startGame(e, easy, intermediate, difficult));
        exit.addActionListener(e -> resetGame(e, easy, intermediate, difficult)); 

        startPanel.setBackground(Color.BLUE);
        levelPanel.setBackground(Color.GREEN);

        for(int i = 0; i < ROWS*COLS; i++) {
            bombs[i] = new JButton();
            bombs[i].setBackground(Color.RED);
            bombs[i].setBorder(new LineBorder(Color.WHITE));
            bombs[i].addActionListener(e -> tileClicked(e, easy, intermediate, difficult));
            bombs[i].setEnabled(false);
            gamePanel.add(bombs[i]);
        }

        parentPanel.add(gamePanel);
        parentPanel.add(startPanel);
        parentPanel.add(levelPanel);
        add(parentPanel);

        setVisible(true);
    }
    
    /**
     * Resets all tiles back to their default state, 
     * alongside reseting the score to zero.
     */
    private void resetGame(ActionEvent e, JButton easy, JButton intermediate, JButton difficult)
    {
        score = 0;
        setScore(scoreLabel);
        resetDifficulties(easy, intermediate, difficult);
        statusLabel.setText("");
        setTilesStatus(false, Color.RED);
    }

    private void tileClicked(ActionEvent e, JButton easy, JButton intermediate, JButton difficult)
    {
        JButton selectedButton = (JButton) e.getSource();
        int index = Arrays.asList(bombs).indexOf(selectedButton);

        if(index == bombLocation) {
            selectedButton.setBackground(Color.BLACK);
            gameStatus("You lost. Better luck next time.");
            resetDifficulties(easy, intermediate, difficult);
        }
        else 
        {
            selectedButton.setBackground(Color.YELLOW);
            selectedButton.setEnabled(false);
            score++;
            setScore(scoreLabel);
            if(score == winningScore) {
                gameStatus("You won! Congratulations!");
                resetDifficulties(easy, intermediate, difficult);
            }
        }
    }

    private void startGame(ActionEvent e, JButton easy, JButton intermediate, JButton difficult)
    {
        score = 0;
        Random rand = new Random();
        bombLocation = rand.nextInt(ROWS*COLS);
        setScore(scoreLabel);   
        setTextColor(scoreLabel);
        statusLabel.setText("");
        setTilesStatus(true, Color.RED);
        if(winningScore == EASY){
            intermediate.setEnabled(false);
            difficult.setEnabled(false);
        }
        if(winningScore == MEDIUM){
            easy.setEnabled(false);
            difficult.setEnabled(false);
        }
        if(winningScore == HARD){
            easy.setEnabled(false);
            intermediate.setEnabled(false);
        }
    }

    private void setScore(JLabel label)
    { 
        label.setText("Your score is " + score);
    }
    
    private void gameStatus(String status)
    {
        statusLabel.setText(status);
        setTextColor(statusLabel);
        setTilesStatus(false, Color.BLACK);
    }

    private void setTilesStatus(boolean enableState, Color bgColor)
    {
        for(JButton b : bombs) {
            b.setEnabled(enableState);
            if(bgColor == Color.RED){
                b.setBackground(bgColor);
            }
        }
    }

    private void setTextColor(JLabel label)
    {
        label.setForeground(Color.WHITE);
    }
    
    private void centreComponent(JComponent comp)
    {
        comp.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private Component createArea()
    {
         return Box.createRigidArea(new Dimension(40,25));
    }
    
    private void resetDifficulties(JButton easy, JButton intermediate, JButton difficult)
    {
        easy.setEnabled(true);
        intermediate.setEnabled(true);
        difficult.setEnabled(true);
    }
}
