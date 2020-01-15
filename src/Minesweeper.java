
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.*;
import javafx.scene.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.border.*;
import javax.swing.Timer;

/*
 * @author NAF-FLY
 */
public class Minesweeper {

    /*
    The following code initializes the 2d array's fields for
    positions, with its rows, columns, and # of mines.
     */
    ImageIcon MINE;
    ImageIcon TILE;
    ImageIcon TILE1;
    ImageIcon FLAG;
    ImageIcon FLAG2;
    ImageIcon FACE;
    ArrayList<Integer> minenums;
    int mineNum;
    int rows;
    int cols;
    int[][] grid;
    int tilesExposed;
    Button[][] buttons;
    boolean gameOver;
    static int difficulty;
    int minesLeft;
    Label unflagged;
    int timePassed;
    Label clock;
    JFrame frame;
    JPanel panel;
    int buttonSize = 20;
    boolean firstClick;
    public javax.swing.Timer timer;
    
    public static void main(String[] args) {
        /*
        the three parameters given in new Minesweeper() set the rows, columns, and number of mines 
         */
        Minesweeper sweep = new Minesweeper(8, 8, 10);
        // prints out the 2D array 
        System.out.println(sweep);
        //starts timer

    }
 
    public Minesweeper(int r, int c, int num) {
        // contructor initializes fields
        difficulty = 1;
        rows = r;
        cols = c;
        mineNum = num;
        minesLeft = mineNum;

        int height;
        int width;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel topestPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        MINE = new ImageIcon(Minesweeper.class.getResource("Images/Mine Karl.jpg"));
        TILE1 = new ImageIcon(Minesweeper.class.getResource("Images/black tile block.png"));
        TILE = new ImageIcon(Minesweeper.class.getResource("Images/white tile block.png"));
        FLAG = new ImageIcon(Minesweeper.class.getResource("Images/Manifest Destiny.png"));
        FLAG2 = new ImageIcon(Minesweeper.class.getResource("Images/Commie.png"));
        FACE = new ImageIcon(Minesweeper.class.getResource("Images/georgie.jpg"));
        Scale(MINE);
        Scale(TILE1);
        Scale(TILE);
        ScaleFlag(FLAG);
        Scale(FLAG2);
        Scale(FACE);

       /* if (60 * cols < 960) {
            height = 60 * rows;
            width = 60 * cols;
        } else {
            height = 960;
            width = 960;
        }*/

        JButton easy = new JButton("Easy");
        easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                newGame(8, 8, 10);

            }
        });
        JButton medium = new JButton("Medium");
        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
              
                newGame(16, 16, 40);

               
            }
        });
        JButton hard = new JButton("Hard");
        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
              
                newGame(16,30, 99 );

               
            }
        });
        JButton restart = new JButton("");
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               newGame(rows, cols, mineNum);
            }
        });
        
        clock = new Label("Time Elapsed: " + timePassed);
        unflagged = new Label("Unflagged Mines: " + minesLeft);
        clock.setAlignment(Label.LEFT);
        unflagged.setAlignment(Label.RIGHT);
        restart.setAlignmentY(cols / 2);
        restart.setIcon(FACE);

     //   topPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
       // topestPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
       // panel.setBorder(new EmptyBorder(10, 0, 10, 0));
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(new TitledBorder("Space Race to the Moon"));
        
        topPanel.setLayout(new FlowLayout());
        topestPanel.setLayout(new FlowLayout());
        topPanel.add(easy);
        topPanel.add(medium);
        topPanel.add(hard);
        topestPanel.add(clock);
        topestPanel.add(restart);
        topestPanel.add(unflagged);
        //frame.setSize(width, height);
        frame.setResizable(false);
        main.add(panel, BorderLayout.SOUTH);
        main.add(topPanel, BorderLayout.CENTER);
        main.add(topestPanel, BorderLayout.NORTH);
        frame.add(main);
    
        frame.setVisible(true);
        tilesExposed = 0;
timer = new javax.swing.Timer(1000, null);
ActionListener timerAction = new ActionListener(){
@Override
public void actionPerformed(ActionEvent e){
    timePassed++;
            clock.setText("Time Elapsed: " + timePassed);
}
};

timer.addActionListener(timerAction);
        newGame(rows, cols, mineNum);
        //  Media hit = new Media(new File(getClass().getResource("usa.mp3").toURI()));
        //MediaPlayer mediaPlayer = new MediaPlayer(hit);
        //  mediaPlayer.play();

        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(Minesweeper.class.getResource("Sounds/usa.mp3"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
        }

    }
    
    
  

    public void newGame(int rows, int cols, int mines) {
       
        panel.removeAll();
        this.rows = rows;
        this.cols = cols;
        minenums = new ArrayList<Integer>();
        timePassed = 0;
        minesLeft = mines;
        mineNum = mines;
        int minesSet = 0;
        tilesExposed = 0;
        gameOver = false;
        grid = new int[rows][cols];
        clock.setText("Time Elapsed: " + timePassed);
        unflagged.setText("Unflagged Mines: " + minesLeft);
        mineNum = mines;
        buttons = new Button[rows][cols];
        panel.setLayout(new GridLayout(rows, cols));
        setgrid();
        setnumber();
        initButtons();
             frame.pack();
         timer.stop();
         firstClick = false;
        System.out.println(minesSet + " " + mineNum + " " + minenums.size());

    }

    public void initButtons() {
        buttons = new Button[rows][cols];
        int n = 0;
        for (int i = 0; i < rows; i++) {
            if (i < rows / 2) {
                n++;
            }
            if (i > rows / 2) {
                n--;
            }
            for (int j = 0; j < cols; j++) {
                buttons[i][j] = new Button(grid[i][j]);
                buttons[i][j].setFont(new Font("Arial", Font.ITALIC, 20));
                if (rows > 20 || cols > 20) {
                    buttons[i][j].setFont(new Font("Arial", Font.ITALIC, 15));
                }
                panel.add(buttons[i][j]);
                buttons[i][j].setPosition(i, j);
                buttons[i][j].addMouseListener(new MouseWhisperer(this));

                if (rows * cols % 2 == 0) {

                    if (j >= (cols / 2) - n && j < (cols / 2) + n) {
                        buttons[i][j].setIcon(TILE);
                        buttons[i][j].white = true;
                    } else {
                        buttons[i][j].setIcon(TILE1);
                        buttons[i][j].white = false;
                    }
                }

                if (buttons[i][j].exposed) {
                    buttons[i][j].setIcon(null);
                }
            }
        }
    }

   

    public void Scale(ImageIcon icon) {
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        //Image new_img = img;
        icon.setImage(img);
    }

    public void ScaleFlag(ImageIcon icon) {
        Image img = icon.getImage().getScaledInstance(40, 45, Image.SCALE_SMOOTH);
        //Image new_img = img;
        icon.setImage(img);
    }

    public void setgrid() {
        Random rand = new Random();
        int minesSet = 0;
        while (minesSet < mineNum) {
            // randomly generates a number from 0 to total positions on grid. 
            int mines = rand.nextInt(rows * cols);
            // adds one to the corresponding index postion in minenums arraylist.  
            if (!(minenums.contains(mines))) {
                minenums.add(mines);
                minesSet++;
            }

        }
        for (int i = 0; i < minenums.size(); i++) {
            // takes mines from arraylist and finds corresponding position on grid, sets it to -1.
            grid[minenums.get(i) / cols][minenums.get(i) % cols] = -1;

        }

        System.out.println(minesSet + " " + mineNum + " " + minenums.size());
    }

    public void setnumber() {
        for (int i = 0; i < minenums.size(); i++) {
            int row = minenums.get(i) / cols;
            int col = minenums.get(i) % cols;
            // loops through all the mine positions on the grid
            for (int j = col - 1; j < col + 2; j++) {
                for (int k = row - 1; k < row + 2; k++) {
                    //loops around all positions surrounding mine
                    if (!(j == -1 || k == -1 || j == cols || k == rows)) {
                        if (grid[k][j] != -1) {
                            //adds one to the value of the surrounding position if 
                            // it is within the grid and not another mine
                            grid[k][j]++;
                        }
                    }
                }
            }
        }
    }

    public void ReCurse(int row, int col) {
        for (int j = row - 1; j < row + 2; j++) {
            for (int i = col - 1; i < col + 2; i++) {
                if (i >= 0 && i < cols && j >= 0 && j < rows) {
                    if (buttons[j][i].flagged) {
                    } else if (!buttons[j][i].exposed) {
                        if (!(grid[j][i] == -1)) {
                            tilesExposed++;
                            buttons[j][i].setIcon(null);
                            buttons[j][i].expose();
                            if (buttons[j][i].getvalue() == 0) {

                                ReCurse(j, i);
                            }
                        }
                    }

                }
            }
        }
    }

    /*
    
    count how many mines is around firxst clcick
remove all mind around fc
    
    add the mines back in, somehow, but not at or arouind first click
    
    refill the numbers of the empty grids
     */
    public void SpawnProtection(Button click) {

        if (tilesExposed <= 6) {
            if (click.getvalue() == -1) {
                click.value = 0;
                int newValue = 0;
                buttons[click.location[0]][click.location[1]].setIcon(null);
                for (int i = click.location[0] - 1; i < click.location[0] + 2; i++) {
                    for (int j = click.location[1] - 1; j < click.location[1] + 2; j++) {
                        if (!(i == -1 || j == -1 || j == cols || i == rows)) {
                            if (buttons[i][j].value == -1) {
                                click.value++;
                                //grid[click.location[0]][click.location[1]]++;   
                            }
                            if (grid[i][j] != grid[click.location[0]][click.location[1]] && grid[i][j] != -1) {
                                if (!(buttons[i][j].value == -1)) {
                                    //grid[i][j]--;
                                    buttons[i][j].value--;
                                }

                            }
                        }
                    }
                }

                // buttons[click.location[0]][click.location[1]].value = newValue;
                //  click.value = newValue; 
                if (newValue == 0) {
                    ReCurse(click.location[0], click.location[1]);
                }
                buttons[click.location[0]][click.location[1]].expose();

                if (grid[0][0] != -1) {
                    grid[0][0] = -1;
                    buttons[0][0].value = -1;
                    mineCheck(0, 0);
                    System.out.println("A");
                } else if (grid[0][cols - 1] != -1) {
                    grid[0][cols - 1] = -1;
                    mineCheck(0, cols - 1);
                    System.out.println("B");
                } else if (grid[rows - 1][0] != -1) {
                    grid[rows - 1][0] = -1;
                    mineCheck(rows - 1, 0);
                    System.out.println("C");
                } else if (grid[rows - 1][cols - 1] != -1) {
                    grid[rows - 1][cols - 1] = -1;
                    mineCheck(rows - 1, cols - 1);
                    System.out.println("D");
                }
            }
        }
    }

    /*   
    The following code adds the 2D array to a string so 
    that it is able to be printed as a static variable in main
     */
    public void mineCheck(int x, int y) {
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (!(i == -1 || j == -1 || j == cols || i == rows)) {
                    if (buttons[i][j].value != -1) {
                        buttons[i][j].value++;
                    }
                }
            }
        }
    }

    public void exposeAll(Button mine) {
        timer.stop();
        if (mine.value == -1) {
            mine.setIcon(MINE);
            System.out.println("GG EZ");
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (buttons[i][j].flagged) {
                    buttons[i][j].setIcon(FLAG2);
                }
                if (buttons[i][j].getvalue() == -1 && !buttons[i][j].flagged) {
                    buttons[i][j].setIcon(MINE);
                }
                if (buttons[i][j].getvalue() != -1 && !buttons[i][j].flagged) {
                    buttons[i][j].setIcon(null);
                    buttons[i][j].expose();

                }
            }
        }
        gameOver = true;
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                //this loops through the 2D array 
                str += grid[i][j] + " ";
                //adding the value of each position to each line
            }
            //until it reaches the end of a row, and starts again on next row
            str += "\n";
        }
        return str;
    }

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            @Override
            public void run() {
                try {
                    /*            Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                  Minesweeper.class.getResourceAsStream("/path/to/sounds/" + url));
                clip.open(inputStream);
                clip.start(); */
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    public void playSound() {

    }
}
