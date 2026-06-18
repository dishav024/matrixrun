public class player {

    static void move(char m[][],
                     Position p,
                     int dr,
                     int dc)
    {
        int nr = p.row + dr;
        int nc = p.col + dc;

        if(nr < 0 || nc < 0 ||
           nr >= map.ROWS ||
           nc >= map.COLS)
        {
            return;
        }

        if(m[nr][nc] == '#')
        {
            return;
        }

        // FIX: don't erase D or E when player leaves a cell
        if(m[p.row][p.col] != 'D' &&
           m[p.row][p.col] != 'E')
        {
            m[p.row][p.col] = '.';
        }

        p.row = nr;
        p.col = nc;

        m[p.row][p.col] = 'P';
    }

    static boolean win(Position p,
                       Position d)
    {
        return p.row == d.row &&
               p.col == d.col;
    }

    static boolean lose(Position p,
                        Position e)
    {
        return p.row == e.row &&
               p.col == e.col;
    }
}
