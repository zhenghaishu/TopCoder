import java.util.*;

public class ManageSubsequences {

	int [][][]dp = new int[302][302][302];
	int [][]dp2 = new int[302][302];	
	
	public int minAdded(String S, String A, String B) {
		for(int d[][] : dp) {
			for(int d2[] : d) {
				Arrays.fill(d2, -1);
			}
		}
		
		for(int d[] : dp2) {
			Arrays.fill(d, -1);
		}
		
		int min = countMin(S, A, B, 0, 0, 0);
		if(min > 1000) {
			return -1;
		} else {
			return min;
		}
	}
	
	private int countMin(String s, String a, String b, int si, int ai, int bi) {
		if(bi >= b.length()) {
			return 10000;
		}
		
		if(s.length() == si && ai == a.length() && bi < b.length()) {
			return 0;
		} else {
			if(dp[si][ai][bi] != -1) {
				return dp[si][ai][bi];
			}
			
			int min = Integer.MAX_VALUE;
			if(si < s.length()) {
				min = countMin(s, a, b, si + 1, ai , bi + (s.charAt(si) == b.charAt(bi) ? 1 : 0));
				System.out.println(si + "," + ai + "," + bi);
				System.out.println("si < s.len, min = " + min);
			}
			
			if(ai != a.length()) {
				min = Math.min(min, countMin(s, a, b, si, ai + 1, bi + (a.charAt(ai) == b.charAt(bi) ? 1 : 0)) + 1);
				System.out.println(si + "," + ai + "," + bi);
				System.out.println(" ai != a.len, min = " + min);
			}
			
			if( si < s.length() && ai < a.length() && s.charAt(si) == a.charAt(ai)) {
				min = Math.min(min, countMin(s, a, b, si + 1, ai + 1, bi + (a.charAt(ai) == b.charAt(bi) ? 1 : 0)));
				System.out.println(si + "," + ai + "," + bi);
				System.out.println("s.si == s.ai, min = " + min);
			}
			
			return dp[si][ai][bi] = min;
		}
	}
				
	
	public static void main(String[] args) {
		String s = "A";
		String a = "AB";
		String b = "X";
		ManageSubsequences ms = new ManageSubsequences();
		System.out.println(ms.minAdded(s, a, b));
	}
}
