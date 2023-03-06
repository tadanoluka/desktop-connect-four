package four;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ConnectFour extends JFrame {

    final private String letters = "abcdefghijklmnopqrstuvwxyz".toUpperCase();

    final private String oneSideSymbol = "X";
    final private String otherSideSymbol = "O";
    final private Color defaultBGColor = Color.DARK_GRAY;
    final private Color defaultTextColor = Color.LIGHT_GRAY;
    final private Font defaultFont = new Font(Font.MONOSPACED, Font.BOLD, 42);

    final private int sizeX = 7;
    final private int sizeY = 6;

    private boolean xTurn = true;

    private JPanel boardPanel;
    private JPanel buttonsPanel;

    private JButton[][] gameBoard;
    private JButton resetButton;


    public ConnectFour() {
        setTitle("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        initButtonsPanel();

        boardPanel = initBoardPanel();
        gameBoard = initGameBoard();
        buttonsPanel = initButtonsPanel();
        resetButton = initResetButton();


        setVisible(true);
    }

    private JPanel initBoardPanel() {
        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(sizeY,sizeX));
        panel.setVisible(true);
        return panel;
    }

    private JButton[][] initGameBoard() {
        JButton[][] gameBoard = new JButton[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                char letter = letters.charAt(x);
                int digit = sizeY - y;
                JButton button = new JButton();
                button.setName("Button" + letter + digit);
                button.setText(" ");
                button.setBackground(defaultBGColor);
                button.setForeground(defaultTextColor);
                button.setFont(defaultFont);
                button.setFocusPainted(false);
                button.addActionListener(e -> {
                    makeAMove(button);
                });
                gameBoard[y][x] = button;
                boardPanel.add(button);
            }
        }
        return gameBoard;
    }

    private JPanel initButtonsPanel() {
        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panel.setVisible(true);
        return panel;
    }

    private JButton initResetButton(){
        JButton buttonReset = new JButton();
        buttonReset.setName("ButtonReset" + "");
        buttonReset.setText("Reset");
        buttonReset.addActionListener(e -> gameReset());
        buttonsPanel.add(buttonReset);
        return buttonReset;
    }

    protected void gameReset() {
        boardPanel.removeAll();
        gameBoard = initGameBoard();
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    void changeTurn() {
        xTurn = !xTurn;
    }

    String changeText() {
        return xTurn ? oneSideSymbol : otherSideSymbol;
    }

    void makeAMove(JButton button) {
        String cords = button.getName().substring(6,8);

        int xCord = letters.indexOf(button.getName().charAt(6));

        int yCordInWeirdWay  = Character.getNumericValue(button.getName().charAt(7));
        int yCord = sizeY - yCordInWeirdWay;

        for (int y = sizeY - 1; y >= 0; y--) {
            if (Objects.equals(gameBoard[y][xCord].getText(), " ")) {
                gameBoard[y][xCord].setText(changeText());
                checkWin();
                changeTurn();
                break;
            }
        }
    }

    void checkWin() {
        // Horizontal Check
        for (int y = 0; y < sizeY; y++) {
            for (int x = 3; x < sizeX; x++) {
                if (gameBoard[y][x - 3].getText() != " " &&
                        gameBoard[y][x - 3].getText() == gameBoard[y][x - 2].getText() &&
                        gameBoard[y][x - 2].getText() == gameBoard[y][x - 1].getText() &&
                        gameBoard[y][x - 1].getText() == gameBoard[y][x].getText()
                ) {
                    win();
                }
            }
        }

        // Vertical Check
        for (int y = 3; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                if (gameBoard[y - 3][x].getText() != " " &&
                        gameBoard[y - 3][x].getText() == gameBoard[y - 2][x].getText() &&
                        gameBoard[y - 2][x].getText() == gameBoard[y - 1][x].getText() &&
                        gameBoard[y - 1][x].getText() == gameBoard[y][x].getText()
                ) {
                    win();
                }
            }
        }

        // Diagonal Check \
        for (int y = 3; y < sizeY; y++) {
            for (int x = 3; x < sizeX; x++) {
                if (gameBoard[y - 3][x - 3].getText() != " " &&
                        gameBoard[y - 3][x - 3].getText() == gameBoard[y - 2][x - 2].getText() &&
                        gameBoard[y - 2][x - 2].getText() == gameBoard[y - 1][x - 1].getText() &&
                        gameBoard[y - 1][x - 1].getText() == gameBoard[y][x].getText()
                ) {
                    win();
                }
            }
        }
    }

    void win() {
        System.out.println("WIN");
    }

}