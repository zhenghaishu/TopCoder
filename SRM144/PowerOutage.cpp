#include <iostream>
#include <vector>
using namespace std;

class PowerOutage 
{
private:
    int getmax(vector<int> &from, vector<int> &to, vector<int> &length, int start)
    {
        int len = from.size();
        vector<int> tmp;
        int max = 0;
        
        // 计算有几个分支 
        for(int i = 0; i < len; i++)
        {
            if(from[i] == start)
            {
                tmp.push_back(i);
            }
        }
        
        // 递归结束的条件 
        if(tmp.empty())
        {
            return 0;
        }
    
        // 计算用时最长的分支所用的时间 
        for(int i = 0; i < tmp.size(); i++)
        {
            int node = tmp[i];
            int t = length[node] + getmax(from, to, length, to[node]);
            if(max < t)  
            {
                max = t;
            }       
        }
        
        return max;
    }
    
public:
    int estimateTimeOut(vector<int> fromJunction, vector<int> toJunction, vector<int> ductLength)
    {
        int sum = 0;
        int max = 0;
        int len = ductLength.size();
        for(int i = 0; i < len; i++)
        {
            sum += ductLength[i];
        }
        sum *= 2;
        
        max = getmax(fromJunction, toJunction, ductLength, 0);
        
        return sum - max;
    }
};

int main()
{
    vector<int> from;
    from.push_back(0);
    from.push_back(1);
    from.push_back(0);
    
    vector<int> to;
    to.push_back(1);
    to.push_back(2);
    to.push_back(3);
    
    vector<int> len;
    len.push_back(10);
    len.push_back(10);
    len.push_back(10);
    
    PowerOutage p;
    cout << p.estimateTimeOut(from, to, len);
    
    return 0;
}
