#include <iostream>
using namespace std;

class Time
{
public:
    string whatTime(int Time)
    {
        int hour = Time / 3600;
        int minute = Time % 3600 / 60;
        int second = Time % 3600 % 60;
        
        char *res = new char[8];
        sprintf(res, "%d:%d:%d", hour, minute, second);
        
        return string(res); 
    }   
};

int main() 
{
	Time t;
	cout << t.whatTime(3661) << endl;
}
