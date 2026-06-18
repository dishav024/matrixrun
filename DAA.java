import java.util.*;

public class DAA {

    static int dr[] = {-1, 1, 0, 0};
    static int dc[] = {0, 0, -1, 1};

    static boolean bfs(char m[][],
                       Position s,
                       Position t)
    {
        boolean vis[][] =
        new boolean[map.ROWS][map.COLS];

        Queue<Position> q = new LinkedList<>();

        q.add(new Position(s.row, s.col));
        vis[s.row][s.col] = true;

        while(!q.isEmpty())
        {
            Position cur = q.poll();

            if(cur.row == t.row &&
               cur.col == t.col)
            {
                return true;
            }

            for(int i = 0; i < 4; i++)
            {
                int nr = cur.row + dr[i];
                int nc = cur.col + dc[i];

                if(nr >= 0 && nc >= 0 &&
                   nr < map.ROWS &&
                   nc < map.COLS &&
                   !vis[nr][nc] &&
                   m[nr][nc] != '#')
                {
                    vis[nr][nc] = true;
                    q.add(new Position(nr, nc));
                }
            }
        }

        return false;
    }

    static ArrayList<Position> dijkstra(
            char m[][],
            Position s,
            Position t)
    {
        int dist[][] =
        new int[map.ROWS][map.COLS];

        Position par[][] =
        new Position[map.ROWS][map.COLS];

        for(int i = 0; i < map.ROWS; i++)
        {
            Arrays.fill(dist[i], 99999);
        }

        dist[s.row][s.col] = 0;

        PriorityQueue<Position> pq =
        new PriorityQueue<>(
        (a, b) ->
        dist[a.row][a.col] -
        dist[b.row][b.col]);

        pq.add(new Position(s.row, s.col));

        while(!pq.isEmpty())
        {
            Position cur = pq.poll();

            for(int i = 0; i < 4; i++)
            {
                int nr = cur.row + dr[i];
                int nc = cur.col + dc[i];

                if(nr >= 0 && nc >= 0 &&
                   nr < map.ROWS &&
                   nc < map.COLS &&
                   m[nr][nc] != '#')
                {
                    if(dist[nr][nc] >
                       dist[cur.row][cur.col] + 1)
                    {
                        dist[nr][nc] =
                        dist[cur.row][cur.col] + 1;

                        par[nr][nc] = cur;

                        pq.add(new Position(nr, nc));
                    }
                }
            }
        }

        ArrayList<Position> path = new ArrayList<>();

        Position cur = t;

        // FIX: added null check on cur to prevent NullPointerException
        // if target is unreachable (par entry stays null)
        while(cur != null)
        {
            path.add(cur);
            cur = par[cur.row][cur.col];
        }

        Collections.reverse(path);

        return path;
    }
}
