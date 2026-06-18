import java.util.*;

public class map {

    static int ROWS = 15;
    static int COLS = 15;

    static Random r = new Random();

    static void createmap(char m[][])
    {
        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                // border walls
                if(i == 0 || j == 0 || i == ROWS-1 || j == COLS-1)
                {
                    m[i][j] = '#';
                }
                // random inner walls - 30% chance
                else if(r.nextInt(100) < 30)
                {
                    m[i][j] = '#';
                }
                else
                {
                    m[i][j] = '.';
                }
            }
        }
    }

    static Position randompos(char m[][])
    {
        while(true)
        {
            int x = r.nextInt(ROWS - 2) + 1;
            int y = r.nextInt(COLS - 2) + 1;

            if(m[x][y] == '.')
            {
                return new Position(x, y);
            }
        }
    }
}
