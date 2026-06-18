import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {

    char m[][];

    Position p;
    Position d;
    Position e;

    int size = 45;

    int score = 0;

    long starttime;

    boolean timerstarted = false;

    boolean gameover = false;

    String result = "";

    // enemy moves independently every 700ms using this timer
    Timer enemyTimer;

    GamePanel()
    {
        // 15 cols * 45 px + 90 offset = 765, 15 rows * 45 px + 80 top bar = 755
        setPreferredSize(new Dimension(765, 755));

        m = new char[map.ROWS][map.COLS];

        initGame();

        setFocusable(true);

        // player movement on key press
        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent k)
            {
                if(gameover)
                {
                    if(k.getKeyCode() == KeyEvent.VK_R)
                    {
                        initGame();
                        repaint();
                    }
                    return;
                }

                move(k);
            }
        });

        // enemy moves on its own every 500ms 
        enemyTimer = new Timer(500, new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if(gameover) return;

                moveenemy();

                // check lose after enemy moves
                if(player.lose(p, e))
                {
                    gameover = true;
                    result   = "lose";
                }

                repaint();
            }
        });

        enemyTimer.start();
    }

    void initGame()
    {
        // keep regenerating map until player can reach destination (BFS check)
        do
        {
            map.createmap(m);

            p = map.randompos(m);
            d = map.randompos(m);
            e = map.randompos(m);

        }
        while(!DAA.bfs(m, p, d));

        // make sure no two spawns are on the same cell
        while(d.row == p.row && d.col == p.col)
        {
            d = map.randompos(m);
        }

        while((e.row == p.row && e.col == p.col) ||
              (e.row == d.row  && e.col == d.col))
        {
            e = map.randompos(m);
        }

        m[p.row][p.col] = 'P';
        m[d.row][d.col] = 'D';
        m[e.row][e.col] = 'E';

        score        = 0;
        timerstarted = false;
        gameover     = false;
        result       = "";

        // restart enemy timer for new game
        if(enemyTimer != null)
        {
            enemyTimer.restart();
        }
    }

    void moveenemy()
    {
        // BFS check: can enemy even reach player?
        if(!DAA.bfs(m, e, p)) return;

        // Dijkstra: find shortest path from enemy to player
        java.util.ArrayList<Position> path = DAA.dijkstra(m, e, p);

        if(path.size() > 1)
        {
            // clear enemy's old cell
            if(m[e.row][e.col] != 'D')
                m[e.row][e.col] = '.';

            // move enemy one step along the shortest path
            Position next = path.get(1);
            e = next;

            // don't overwrite D or P tile
            if(m[e.row][e.col] != 'D' &&
               m[e.row][e.col] != 'P')
            {
                m[e.row][e.col] = 'E';
            }
        }

        // always restore destination marker
        m[d.row][d.col] = 'D';
    }

    void move(KeyEvent k)
    {
        int oldr = p.row;
        int oldc = p.col;

        if(k.getKeyCode() == KeyEvent.VK_W) player.move(m, p, -1,  0);
        if(k.getKeyCode() == KeyEvent.VK_S) player.move(m, p,  1,  0);
        if(k.getKeyCode() == KeyEvent.VK_A) player.move(m, p,  0, -1);
        if(k.getKeyCode() == KeyEvent.VK_D) player.move(m, p,  0,  1);

        boolean moved = (oldr != p.row || oldc != p.col);

        if(moved)
        {
            score++;

            if(!timerstarted)
            {
                starttime    = System.currentTimeMillis();
                timerstarted = true;
            }
        }

        // check win - player reached destination
        if(player.win(p, d))
        {
            gameover = true;
            result   = "win";
            repaint();
            return;
        }

        // check lose - player walked into enemy
        if(player.lose(p, e))
        {
            gameover = true;
            result   = "lose";
            repaint();
            return;
        }

        repaint();
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // background
        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // top bar
        g2.setColor(new Color(20, 20, 20));
        g2.fillRect(0, 0, getWidth(), 50);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Score : " + score, 20, 30);

        double t = 0;
        if(timerstarted)
            t = (System.currentTimeMillis() - starttime) / 1000.0;

        g2.drawString("Time : " + String.format("%.1f", t), 200, 30);
        g2.drawString("W A S D To Move", 380, 30);

        if(gameover)
        {
            g2.setColor(new Color(255, 200, 0));
            g2.drawString("Press R to restart", 570, 30);
        }

        // draw grid
        int startx = 45;
        int starty = 65;

        for(int i = 0; i < map.ROWS; i++)
        {
            for(int j = 0; j < map.COLS; j++)
            {
                int x = startx + j * size;
                int y = starty + i * size;

                // wall tile
                if(m[i][j] == '#')
                {
                    g2.setColor(new Color(70, 70, 70));
                    g2.fillRect(x, y, size, size);
                    g2.setColor(new Color(110, 110, 110));
                    g2.drawRect(x, y, size, size);
                }
                // open tile
                else
                {
                    g2.setColor(new Color(220, 220, 220));
                    g2.fillRect(x, y, size, size);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(x, y, size, size);
                }

                // player - green circle
                if(m[i][j] == 'P')
                {
                    g2.setColor(new Color(0, 200, 0));
                    g2.fillOval(x + 6, y + 6, size - 12, size - 12);
                    g2.setColor(Color.WHITE);
                    g2.fillOval(x + 12, y + 13, 6, 6);
                    g2.fillOval(x + 24, y + 13, 6, 6);
                }

                // enemy - red circle
                if(m[i][j] == 'E')
                {
                    g2.setColor(new Color(210, 0, 0));
                    g2.fillOval(x + 6, y + 6, size - 12, size - 12);
                    g2.setColor(Color.BLACK);
                    g2.fillOval(x + 12, y + 13, 6, 6);
                    g2.fillOval(x + 24, y + 13, 6, 6);
                }

                // destination - blue square
                if(m[i][j] == 'D')
                {
                    g2.setColor(new Color(0, 100, 255));
                    g2.fillRect(x + 8, y + 8, size - 16, size - 16);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Arial", Font.BOLD, 16));
                    g2.drawString("D", x + 16, y + 28);
                    g2.setFont(new Font("Arial", Font.BOLD, 18));
                }
            }
        }

        // win overlay
        if(result.equals("win"))
        {
            g2.setColor(new Color(0, 0, 0, 160));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(0, 220, 0));
            g2.setFont(new Font("Arial", Font.BOLD, 52));
            g2.drawString("YOU WIN!", 230, 340);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 22));
            g2.drawString("Score: " + score + "   Press R to play again", 195, 400);
        }

        // lose overlay
        if(result.equals("lose"))
        {
            g2.setColor(new Color(0, 0, 0, 160));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(220, 0, 0));
            g2.setFont(new Font("Arial", Font.BOLD, 52));
            g2.drawString("GAME OVER", 195, 340);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 22));
            g2.drawString("Press R to play again", 250, 400);
        }
    }
}
