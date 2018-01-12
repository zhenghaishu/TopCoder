#include <iostream>
#include <vector>
#include <map>
#include <set>
using namespace std;

class TwoDiagonals
{
public:
    int maxPoints(vector<int> x, vector<int> y)
    {
        map<int, int> b1; // y = -x + b1 ==> b1 = y + x
        map<int, int> b2; // y = x + b2 ==> b2 = y - x
        set<pair<int, int> > points;

        int maxCities = 0;
        int cityCnt;
        double crossX;
        double crossY;

        for(unsigned int i = 0; i < x.size(); i++)
        {
            b1[y[i] + x[i]]++;
            b2[y[i] - x[i]]++;

            points.insert(make_pair(x[i], y[i]));
        }

        for(map<int, int>::iterator iter1 = b1.begin(); iter1 != b1.end(); iter1++)
        {
            for(map<int, int>::iterator iter2 = b2.begin(); iter2 != b2.end(); iter2++)
            {
                cityCnt = (*iter1).second + (*iter2).second;
                crossX = (double)((*iter1).first - (*iter2).first)/2;
                crossY = (double)((*iter1).first + (*iter2).first)/2;
                pair<double, double> crossPoint = make_pair(crossX, crossY);
                if(points.count(crossPoint))
                {
                    cityCnt--;
                }

                maxCities = max(maxCities, cityCnt);
            }
        }

        return maxCities;
    }
};

int main()
{
    TwoDiagonals t;

    vector<int> x;
    x.push_back(1);
    x.push_back(4);
    x.push_back(4);
    x.push_back(5);
//    x.push_back(0);
//    x.push_back(1);
//    x.push_back(2);
//    x.push_back(3);
//    x.push_back(4);
//    x.push_back(5);

    vector<int> y;
    y.push_back(3);
    y.push_back(0);
    y.push_back(2);
    y.push_back(3);
//    y.push_back(2);
//    y.push_back(2);
//    y.push_back(2);
//    y.push_back(2);
//    y.push_back(2);
//    y.push_back(2);

    cout << t.maxPoints(x, y) << endl;

    return 0;
}
