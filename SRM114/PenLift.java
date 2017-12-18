import java.util.ArrayList;
import java.util.Collections;

public class PenLift {	
   
    // ���Է���
	public static void main(String[] args) {
		int num;
		
		// // example 0
		// String[] segs = {"-10 0 10 0", "0 -10 0 10"};
		// num = 1;
		
		// // example 1,2 
		// String[] segs = {"-10 0 0 0", "0 0 10 0", "0 -10 0 0", "0 0 0 10"};
		// num = 1;
		// num = 4;
		
		// // example 3
		// String[] segs = {"0 0 1 0",   "2 0 4 0",   "5 0 8 0",   "9 0 13 0",
						// "0 1 1 1",   "2 1 4 1",   "5 1 8 1",   "9 1 13 1",
						// "0 0 0 1",   "1 0 1 1",   "2 0 2 1",   "3 0 3 1",
						// "4 0 4 1",   "5 0 5 1",   "6 0 6 1",   "7 0 7 1",
						// "8 0 8 1",   "9 0 9 1",   "10 0 10 1", "11 0 11 1",
						// "12 0 12 1", "13 0 13 1"};
		// num = 1;
		
		// example 4
		String[] segs = {"-2 6 -2 1",  "2 6 2 1",  "6 -2 1 -2",  "6 2 1 2",
						"-2 5 -2 -1", "2 5 2 -1", "5 -2 -1 -2", "5 2 -1 2",
						"-2 1 -2 -5", "2 1 2 -5", "1 -2 -5 -2", "1 2 -5 2",
						"-2 -1 -2 -6","2 -1 2 -6","-1 -2 -6 -2","-1 2 -6 2"};
		num = 5;
		
		System.out.println(numTimes(segs, num));
	}
	
	static ArrayList<Point> points;

    public static int numTimes(String[] segments, int n) {
        ArrayList<Segment> segs = new ArrayList<Segment>();
        for (int i = 0; i < segments.length; i++) {
            String str[] = segments[i].split(" ");
            int x1 = Integer.parseInt(str[0]);
            int y1 = Integer.parseInt(str[1]);
            int x2 = Integer.parseInt(str[2]);
            int y2 = Integer.parseInt(str[3]);
            segs.add(new Segment(x1, y1, x2, y2));
        }
        
        for (int i = 0; i < segs.size(); i++) {
            Segment seg1 = segs.get(i);
            for (int j = i + 1; j<segs.size(); j++) {
                Segment seg2 = segs.get(j);
                if (Segment.isOverlap(seg1, seg2) == true) {
                	// �������غϵ��߶Σ��ϲ���һ��
                    segs.set(i, Segment.combine(seg1, seg2));
                    segs.remove(j);
                    i--;
                    break;
                }
            }
        }

        points = new ArrayList<Point>();
        // �������߶εĶ˵㡢������ӵ�points��
        for (int i = 0; i < segs.size(); i++) {
            Segment seg = segs.get(i);
            Point p1 = new Point(seg.x1, seg.y1);
            if (points.indexOf(p1) == -1) {
                points.add(p1);
            }
            
            Point p2 = new Point(seg.x2, seg.y2);
            if (points.indexOf(p2) == -1) {
                points.add(p2);
            }
            
            for (int j = 0; j < i; j++) {
                Segment seg1 = segs.get(j);
                Point cross = Segment.intersect(seg, seg1);
                if (cross != null) {
                    if (points.indexOf(cross) == -1) {
                        points.add(cross);
                    }
                }
            }
        }
        
        // ��points�е����н�㣬������ͨ��ϵ�ŵ���ͬ��line��
        for (int i = 0; i < segs.size(); i++) {
            Segment seg = segs.get(i);
            ArrayList<Point> line = new ArrayList<Point>();
            for (int j = 0; j < points.size(); j++) {
            	// ͬһ����������Щ��
                Point point = points.get(j);
                if (point.x >= seg.x1 && point.x <= seg.x2 && point.y >= seg.y1 && point.y <= seg.y2) {
                    line.add(point);
                }
            }
            
            Collections.sort(line);
            
            for (int j = 0; j < line.size() - 1; j++) {
                line.get(j).adjacents.add(points.indexOf(line.get(j + 1)));
                ArrayList<Integer> ids = line.get(j).adjacents;
                line.get(j + 1).adjacents.add(points.indexOf(line.get(j)));
                ArrayList<Integer> ids2 = line.get(j+1).adjacents;
            }
        }
        
        int count = 0;
        // ��������
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).visited == false) {
                points.get(i).visited = true;
                int odd = DFS(i);
				// ��nΪż����points.get(i)��Ȼ������㣬����һ�ʻ�
                if (n % 2 == 0 || odd == 0) {
                    count++;
                } else {
                    count += odd / 2;
                }
            }
        }
        
        return count - 1;
    }

    // �����������
    private static int DFS(int index) {
        Point point = points.get(index);
        int odd;
        if (point.adjacents.size() % 2 == 0) {
            odd = 0;
        } else {
            odd = 1;
        }
        
		// �ۼ�points.get(index)�������ڵ���������
        for (int i = 0; i < point.adjacents.size(); i++) {
            int next = point.adjacents.get(i);
            if (points.get(next).visited == false) {
                points.get(next).visited = true;
                odd += DFS(next);
            }
        }
        
        return odd;
    }

    
    // �ڲ��ࣺ�߶�
    static class Segment {
        int x1;
        int y1;
        int x2;
        int y2;
        boolean horizontal;

        public Segment(int X1, int Y1, int X2, int Y2) {
            this.x1 = Math.min(X1, X2);
            this.y1 = Math.min(Y1, Y2);
            this.x2 = Math.max(X1, X2);
            this.y2 = Math.max(Y1, Y2);
            
            if (this.y1 == this.y2) {
                this.horizontal = true;
            } else {
                this.horizontal = false;
            }
        }
        
        // �ж������߶��Ƿ��ص�
        static boolean isOverlap(Segment a, Segment b) {
            if (a.horizontal == true && b.horizontal == true && a.y1 == b.y1) {
            	// 1. ��������ˮƽ�ߣ�����������ͬ
                if ((a.x1 >= b.x1 && a.x1 <= b.x2) || (a.x2 >= b.x1 && a.x2 <= b.x2) || (b.x1 >= a.x1 && b.x1 <= a.x2) || (b.x2 >= a.x1 && b.x2 <= a.x2)) {
                    return true;
                }
            } else if (a.horizontal == false && b.horizontal == false && a.x1 == b.x2) {
            	// 2. �������Ǵ�ֱ�ߣ��Һ�������ͬ
                if ((a.y1 >= b.y1 && a.y1 <= b.y2) || (a.y2 >= b.y1 && a.y2 <= b.y2) || (b.y1 >= a.y1 && b.y1 <= a.y2) || (b.y2 >= a.y1 && b.y2 <= a.y2)) {
                    return true;
                }
            }
            
            return false;
        }

        // ���������߶Σ����һ�������߶�
        static Segment combine(Segment a, Segment b) {
            if (a.horizontal == true && b.horizontal == true) {
                int x1 = Math.min(a.x1, b.x1);
                int x2 = Math.max(a.x2, b.x2);
                int y = a.y1;
                return new Segment(x1, y, x2, y);
            } else {
                int x = a.x1;
                int y1 = Math.min(a.y1, b.y1);
                int y2 = Math.max(a.y2, b.y2);
                return new Segment(x, y1, x, y2);
            }
        }

        // �������ཻ�ߵĽ���
        static Point intersect(Segment a, Segment b) {
        	// �������߶ζ�Ϊˮƽ��ֱ���򲻿����ཻ
            if (a.horizontal == b.horizontal) {
                return null;
            }
            
            // ��aΪ��ֱ��bΪˮƽ�ߣ����������ߣ�ʹ��aΪˮƽ��bΪ��ֱ��
            if (a.horizontal == false) {
                Segment temp = a;
                a = b;
                b = temp;
            }
            
            // aΪˮƽ��bΪ��ֱ�ߣ�ֻ���ཻ������£����н���
            if (a.y1 >= b.y1 && a.y1 <= b.y2 && b.x1 >= a.x1 && b.x1 <= a.x2) {
                return new Point(b.x1,a.y1);
            }
            
            return null;
        }
    }

    
    // �ڲ��ࣺ��
    static class Point implements Comparable<Point> {
        int x;
        int y;
        ArrayList<Integer> adjacents;
        boolean visited;

        public Point(int X, int Y) {
            this.x = X;
            this.y = Y;
            this.adjacents = new ArrayList<Integer>();
            this.visited = false;
        }

        public boolean equals(Object obj) {
            Point another = (Point) obj;
            if (this.x == another.x && this.y == another.y) {
                return true;
            } else {
                return false;
            }
        }

        public int compareTo(Point another) {
        	// �Ȱ���������������������ͬ��������������
            if (this.x != another.x) {
                return this.x - another.x;
            } else {
                return this.y - another.y;
            }
        }
    }
}
