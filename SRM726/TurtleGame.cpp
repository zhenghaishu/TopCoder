#include <iostream>
#include <vector>
using namespace std;

class TurtleGame
{
public:
    string getwinner(vector<string> board)
    {
        const int N = board.size();
        const int M = board[0].size();
        int cnt  = 0;

        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < M; j++)
            {
                cnt += board[i][j] == '.';
            }
        }

        // Got the total remove count
        // N+M-1 means the minimum turtle-friendly board
        cnt -= (N + M - 1);

        // Hero win if remove count is odd, else he lose
        return cnt % 2 ? "Win" : "Lose";
    }
};

int main()
{
    TurtleGame t;
    vector<string> board;
    board.push_back("..");
    board.push_back("..");
    cout << t.getwinner(board) << endl;

    return 0;
}
