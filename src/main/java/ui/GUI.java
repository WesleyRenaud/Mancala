package ui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Box;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

import mancala.GameNotOverException;
import mancala.InvalidMoveException;
import mancala.MancalaGame;
import mancala.Player;
import mancala.Saver;
import mancala.UserProfile;

public class GUI extends JFrame {
    private MancalaGame game;
    private Saver saver;
    /*For checking the input of the user */
    private InputAnalyzer analyzer;
    private JPanel gameContainer;
    private JPanel boardPanel;
    private PositionAwareButton[][] pitButtons;
    private JButton store1;
    private JButton store2;
    /*Labels for the information of the user profiles */
    private JPanel userInfo;
    private JLabel p1Name;
    private JLabel p1Played;
    private JLabel p1Won;
    private JLabel p2Name;
    private JLabel p2Played;
    private JLabel p2Won;
    private JMenuBar menubar;
    private JFileChooser fileChooser;
    private Path assetsFolderPath;
    private UserProfile profile1 = null;
    private UserProfile profile2 = null;
    private boolean profile1Loaded = false;
    private boolean profile2Loaded = false;    
    private String ruleSet = null;

    public GUI() {
        super();
        gameContainer = new JPanel();
        game = new MancalaGame();
        analyzer = new InputAnalyzer();
        saver = new Saver();
        basicSetUp();
        add(makeBoard(), BorderLayout.CENTER);
        add(makeUserInfo(), BorderLayout.EAST);
        add(makeMenuBar(), BorderLayout.NORTH);
        setJMenuBar(menubar);
        disableAllButtons();
        pack();
    }

   //*Set up methods */
    private void basicSetUp() {
        this.setTitle("Mancala");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void disableAllButtons() {
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 6; x++) {
                pitButtons[y][x].setEnabled(false);
            }
        }
    }

    private JPanel makeBoard() {
        boardPanel = new JPanel();
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.X_AXIS));
        makeStore2();
        makePits();
        makeStore1();

        return boardPanel;
    }
    
    private void makeStore1() {
        store1 = new JButton("0");
        store1.setEnabled(false);
        boardPanel.add(store1);
    }

    private void makePits() {
        JPanel pitPanel = new JPanel();
        pitPanel.setLayout(new GridLayout(2, 6));
        pitButtons = new PositionAwareButton[2][6];
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 6; x++) {
                pitButtons[y][x] = new PositionAwareButton("4");
                pitButtons[y][x].setAcross(x);;
                pitButtons[y][x].setDown(y);
                pitPanel.add(pitButtons[y][x]);
                pitButtons[y][x].addActionListener (e -> {
                    makeMove(e);
                    checkGameState();
                });
            }
        }
        boardPanel.add(pitPanel);
    }

    private void makeStore2() {
        store2 = new JButton("0");
        store2.setEnabled(false);
        boardPanel.add(store2);
    }

    private JPanel makeUserInfo() {
        userInfo = new JPanel();
        userInfo.add(Box.createVerticalStrut(20));
        userInfo.add(Box.createHorizontalStrut(50));
        userInfo.setAlignmentX((float)0.5);
        userInfo.setAlignmentY((float)0.5);
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));
        //*Make the labels for the users' games played and wins */
        p1Name = new JLabel("User 1");
        userInfo.add(p1Name);
        userInfo.add(Box.createVerticalStrut(10));
        p1Played = new JLabel("Games Played: 0");
        userInfo.add(p1Played);
        userInfo.add(Box.createVerticalStrut(10));
        p1Won = new JLabel("Games Won: 0");
        userInfo.add(p1Won);
        userInfo.add(Box.createVerticalStrut(10));
        JButton p1NameButton = new JButton("Set User 1 Name");
        p1NameButton.setEnabled(true);
        p1NameButton.addActionListener(e -> setUserName(1));
        userInfo.add(p1NameButton);
        userInfo.add(Box.createVerticalStrut(10));
        JButton p1StatsButton = new JButton("Get User 1 Stats");
        p1StatsButton.setEnabled(true);
        p1StatsButton.addActionListener(e -> displayUserStats(1));
        userInfo.add(p1StatsButton);
        userInfo.add(Box.createVerticalStrut(20));

        p2Name = new JLabel("User 2");
        userInfo.add(p2Name);
        userInfo.add(Box.createVerticalStrut(10));
        p2Played = new JLabel("Games Played: 0");
        userInfo.add(p2Played);
        userInfo.add(Box.createVerticalStrut(10));
        p2Won = new JLabel("Games Won: 0");
        userInfo.add(p2Won);
        userInfo.add(Box.createVerticalStrut(10));
        JButton p2NameButton = new JButton("Set User 2 Name");
        p2NameButton.setEnabled(true);
        p2NameButton.addActionListener(e -> setUserName(2));
        userInfo.add(p2NameButton);
        userInfo.add(Box.createVerticalStrut(10));
        JButton p2StatsButton = new JButton("Get User 2 Stats");
        p2StatsButton.setEnabled(true);
        p2StatsButton.addActionListener(e -> displayUserStats(2));
        userInfo.add(p2StatsButton);
        userInfo.add(Box.createVerticalStrut(50));
        userInfo.add(Box.createHorizontalStrut(50));

        userInfo.add(makeStartNewGameButton());

        return userInfo;
    }

    private JButton makeStartNewGameButton() {
        JButton button = new JButton("Start New Game");
        button.addActionListener(e -> startNewGame());
        return button;
    }

    private void startNewGame() {        
        String gameType = getGameType();
        if (!(analyzer.checkSpecificInput("*no game selected*", gameType))) {
            ruleSet = gameType;
            startCorrectGame(gameType);
            initializePlayers();
        }
    }

    private String getGameType() {
        String gameType = "*no game selected*";
        boolean cancelled = false;
        while (!(analyzer.checkSpecificInput("Kalah", gameType) || analyzer.checkSpecificInput("Ayo", gameType)) && !cancelled) {
            gameType = JOptionPane.showInputDialog("Enter 'Kalah' or 'Ayo' to pick the rule set");
            if (gameType == null) {
                gameType = "*no game selected*";
                cancelled = true;
            }
        }
        return gameType;
    }

    private void startCorrectGame(String gameType) {
        if (analyzer.checkSpecificInput("Kalah", gameType)) {
            game.startKalahGame();
        } else {
            game.startAyoGame();
        }
        game.startNewGame();
        updateBoard();
    }

    private void initializePlayers() {
        if (!(profile1Loaded && profile2Loaded)) {
            /**Make new profiles if the user has not loaded any in */
            game.setPlayers(new Player("Player One"), new Player("Player Two"));
            profile1 = new UserProfile("Player One");
            profile2 = new UserProfile("Player Two");
            profile1Loaded = true;
            profile2Loaded = true;
        } else if (!profile1Loaded) {
            game.setOnePlayer(new Player("Player One"), 1);
            profile1 = new UserProfile("Player One");
        }
         else if (!profile2Loaded) {
            game.setOnePlayer(new Player("Player Two"), 2);
            profile2 = new UserProfile("Player Two");
        }
    }


   //*Methods for updating the display */
    private void updateBoard() {
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 6; x++) {
                pitButtons[y][x].setText(game.getPitString(getPitNum(pitButtons[y][x].getDown(), pitButtons[y][x].getAcross())));
            }
        }
        store1.setText("" + game.getStoreCount(1));
        store2.setText("" + game.getStoreCount(2));
        
        enablePlayerButtons();
    }

    private void displayUserStats(int userNum) {
        String message = "";
        if (userNum == 1) {
            if (!profile1Loaded) {
                message = "Error - Profie 1 Not Loaded";
            } else {
                message += "Kalah Played: " + profile1.getKalahPlayed() + " | Kalah Won: " + profile1.getKalahWon() + " | Percentage won " + profile1.percentageKalahWon() + "\n";
                message += "Ayo Played: " + profile1.getAyoPlayed() + " | Ayo Won: " + profile1.getAyoWon() + " | Percentage won " + profile1.percentageAyoWon();
            }
        } else {
            if (!profile2Loaded) {
                message = "Error - Profie 2 Not Loaded";
            } else {
                message += "Kalah Played: " + profile2.getKalahPlayed() + " | Kalah Won: " + profile2.getKalahWon() + " | Percentage won " + profile2.percentageKalahWon() + "\n";
                message += "Ayo Played: " + profile2.getAyoPlayed() + " | Ayo Won: " + profile2.getAyoWon() + " | Percentage won " + profile2.percentageAyoWon();
            }
        }
        JOptionPane.showMessageDialog(null, message);
    }

    private void enablePlayerButtons() {
        if (game.getCurrPlayerNum()==1) {
            setPlayerButtons(1, true);
            setPlayerButtons(2, false);
        } else {
            setPlayerButtons(1, false);
            setPlayerButtons(2, true);
        }
    }

    private void setPlayerButtons(int playerNum, boolean enable) {
        int playerRowNum = getPlayerRowNum(playerNum);
        for (int x = 0; x < 6; x++) {
            pitButtons[playerRowNum][x].setEnabled(enable);
        }
    }

    private void updateProfileInfo(int userNum) {
        if (userNum == 1) {
            p1Name.setText(profile1.getName());
            p1Played.setText("Games Played: " + (profile1.getKalahPlayed() + profile1.getAyoPlayed()));
            p1Won.setText("Games Won: " + (profile1.getKalahWon() + profile1.getAyoWon()));
        } else {
            p2Name.setText(profile2.getName());
            p2Played.setText("Games Played: " + (profile2.getKalahPlayed() + profile2.getAyoPlayed()));
            p2Won.setText("Games Won: " + (profile2.getKalahWon() + profile2.getAyoWon()));
        }
    }

    private void addUserWin(String winnerName) {
        if (analyzer.checkSpecificInput("Kalah", ruleSet)) {
            if (winnerName != null) {
                if (analyzer.checkSpecificInput(profile1.getName(), winnerName)) {
                    profile1.addKalahWon();
                } else {
                    profile2.addKalahWon();
                }
            }
        } else if (analyzer.checkSpecificInput("Ayo", ruleSet)) {
            if (winnerName != null) {
                if (analyzer.checkSpecificInput(profile1.getName(), winnerName)) {
                    profile1.addAyoWon();
                } else {
                    profile2.addAyoWon();
                }
            }
        }
    }

    private void addUserGamesPlayed() {
        if (analyzer.checkSpecificInput("Kalah", ruleSet)) {
            profile1.addKalahPlayed();
            profile2.addKalahPlayed();
        } else {
            profile1.addAyoPlayed();
            profile2.addAyoPlayed();
        }
    }

    private void setUserName(int userNum) {
        if (userNum == 1) {
            String user1Name = JOptionPane.showInputDialog("Enter a Name for User 1");
            profile1.setName(user1Name);
        } else {
            String user2Name = JOptionPane.showInputDialog("Enter a Name for User 2");
            profile2.setName(user2Name);
        }
        updateProfileInfo(userNum);
    }


   //*Helper methods */
    private int getPlayerRowNum(int playerNum) {
        if (playerNum == 1) {
            return 1;
        }
        return 0;
    }

    private int getPitNum(int y, int x) {
        if (y == 0) {
            return 6 + (6 - x);
        }

        return x + 1;
    }

    
   //*Methods for running the game */
    
    private void makeMove(ActionEvent e) {
        PositionAwareButton clicked = (PositionAwareButton) (e.getSource());
        try {
            game.move(getPitNum(clicked.getDown(), clicked.getAcross()));
            updateBoard();
        } catch (InvalidMoveException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    private void checkGameState() {
        if (game.isGameOver()) {
            String winnerName = null;
            try {
                winnerName = game.getWinnerName();
                if (winnerName != null) {
                    JOptionPane.showMessageDialog(null, winnerName + " has won the game!");
                } else {
                    JOptionPane.showMessageDialog(null, "The game has ended in a tie");
                }
            } catch (GameNotOverException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            addUserWin(winnerName);
            addUserGamesPlayed();
            updateProfileInfo(1);
            updateProfileInfo(2);
            
            updateBoard();
            disableAllButtons();
        }
    }

    
   //*Methods for the menu (saving and loading games/profiles) */
    private JMenuBar makeMenuBar() {
        menubar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem saveProfile1 = new JMenuItem("Save Profile 1");
        saveProfile1.addActionListener(e -> saveProfile1());
        JMenuItem saveProfile2 = new JMenuItem("Save Profile 2");
        saveProfile2.addActionListener(e -> saveProfile2());
        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(e -> saveGame());
        JMenuItem openProfile1 = new JMenuItem("Open Profile 1");
        openProfile1.addActionListener(e -> openProfile1());
        JMenuItem openProfile2 = new JMenuItem("Open Profile 2");
        openProfile2.addActionListener(e -> openProfile2());
        JMenuItem openGame = new JMenuItem("Open Game");
        openGame.addActionListener(e -> openGame());
        menu.add(saveProfile1);
        menu.add(saveProfile2);
        menu.add(saveGame);
        menu.add(openProfile1);
        menu.add(openProfile2);
        menu.add(openGame);

        makeFileChooser();
        menubar.add(menu);
        return menubar;
    }

    private void makeFileChooser() {
        Path currentDirectory = Paths.get(System.getProperty("user.dir"));
        String folderName = "assets";
        assetsFolderPath = currentDirectory.resolve(folderName);
        File file = new File(folderName);

        if (!file.exists()){
            file.mkdir();
        }
        fileChooser = new JFileChooser(file.getPath());
    }

    private File getFileChoice() {
        File selectedFile = null;
        try {
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return selectedFile;
    }

    private void saveObject(Serializable serializable, String filename) {
        try {
            saver.saveObject(serializable, "assets/" + filename);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private Serializable loadObject() {
        Serializable serializable = null;
        try {
            File file = getFileChoice();
            if (file != null) {
                serializable = saver.loadObject(file.getPath());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return serializable;
    }

    private void saveProfile1() {
        String filename = getFilename();
        saveObject(profile1, filename);
    }

    private void saveProfile2() {
        String filename = getFilename();
        saveObject(profile2, filename);
    }

    private void saveGame() {
        String filename = getFilename();
        saveObject(game, filename);
    }

    private String getFilename() {
        return JOptionPane.showInputDialog(null, "Enter Filename");
    }

    private void openProfile1() {
        try {
            Object o = loadObject();
            if (o != null) {
                profile1 = (UserProfile)o;
                profile1Loaded = true;
                updateProfileInfo(1);
                startNewGame();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void openProfile2() {
        try {
            Object o = loadObject();
            if (o != null) {
                profile2 = (UserProfile)o;
                profile2Loaded = true;
                updateProfileInfo(2);
                startNewGame();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void openGame() {
        try {
            Object o = loadObject();
            if (o != null) {
                game = (MancalaGame)o;
                updateBoard();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());    
        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);
    }
}