#include <iostream>
#include <vector>
using namespace std;

class BinaryCode 
{
public:
    vector<string> decode(string message)
    {
        vector<string> result;
        result.push_back(decodeBy(message, '0'));
        result.push_back(decodeBy(message, '1'));
        
        return result;      
    }

private:
    string decodeBy(string message, char begin)
    {
        string res;
        int left = 0;
        int mid = begin - '0';
        int right = 0;
        
        int i = 0;
        for(; i < message.length(); i++)    
        {
            right = message[i] - '0' - left - mid;  
            if(0 == right || 1 == right)
            {
                res += (mid + '0');
                left = mid;
                mid = right;
            }
            else
            {
                res = "NONE";
                break;
            }
        }
            
        if(0 != right)
        {
            res = "NONE";
        }
        
        return res;     
    }           
};

int main(int argc, char** argv) 
{
    BinaryCode bc;
    
    vector<string> v;
    //v = bc.decode("123210122");
    //v = bc.decode("11");
    v = bc.decode("22111");
    //v = bc.decode("123210120");
    //v = bc.decode("3");
    //v = bc.decode("12221112222221112221111111112221111");

    for(vector<string>::iterator iter = v.begin(); iter != v.end(); iter++)
    {
        cout << *iter << endl;
    }
    cout << endl;
    
    return 0;
}
