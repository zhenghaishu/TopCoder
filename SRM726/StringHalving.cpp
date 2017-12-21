#include <iostream>
#include <set>
using namespace std;

class StringHalving
{
	set<char> deleted;
	
public:
	string lexsmallest(string s) 
	{
		for(int i = 0; i < s.length(); i++)
		{
			if(deleted.count(s[i]))
			{
				// string(int n, char c) 用n个字符c初始化 
				return string(1, *deleted.begin());
			}
			else
			{
				deleted.insert(s[i]);
			}
		}
	}	
};

int main() 
{
	StringHalving sh;
	string s;
	//s = sh.lexsmallest("baba");
	//  s = sh.lexsmallest("bbaa");
	// s = sh.lexsmallest("zyiggiyssz");
	// s = sh.lexsmallest("topcodertpcder");
 	s = sh.lexsmallest("rvofqorvfq");
	cout << s << endl;
	
	return 0;
}
