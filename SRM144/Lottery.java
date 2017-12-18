import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Lottery {
    public String[] sortByOdds(String[] rules) {
        List<Rule> listOfRules = new ArrayList<Rule>();
        for(String ruleDesc: rules) {
            listOfRules.add(new Rule(ruleDesc));
        }
        
        // 按正序（升序）排序，会调用Rule的compareTo方法
        Collections.sort(listOfRules);
        
        // 把排序后的名字放到sortedRules，即是最终所求的结果
        String[] sortedRules = new String[rules.length];
        int i = 0;
        for(Rule rule : listOfRules) {
            sortedRules[i++] = rule.name;
        }
        
        return sortedRules;
    }
    
    // 内部类
    class Rule implements Comparable<Rule> {
        // Java四种访问权限：public, protected, private, default
        // 如果不显式写出，则为default,同一个包内都可以访问
        // 又因为这是个内部类，所以下面的属性只有Lottery才能访问
        String name;
        byte choices;
        byte blanks;
        boolean sorted;         // 是否排序
        boolean unique;         // 是否不重复
        long totalTickets;      // 有多少种票
        double winningOdds;     // 中奖的概率
        
        // 构造函数
        Rule (String description) {
            String[] nameAndRules = description.split(": ");
            name = nameAndRules[0];
            String[] rules = nameAndRules[1].split(" ");
            choices = (byte)Integer.parseInt(rules[0]);
            blanks = (byte)Integer.parseInt(rules[1]);
            sorted = rules[2].equals("T") ? true : false;
            unique = rules[3].equals("T") ? true : false;
            totalTickets =  getTotalTickets();
            winningOdds = 1.0 / totalTickets;
        }
        
        // 共有多少种票
        private long getTotalTickets() {
            if(!sorted) {
                if(unique) {
                    return permutation(choices, blanks);
                } else {
                    return (long)Math.pow(choices, blanks);
                }
            } else {
                if(unique) {
                    return combination(choices, blanks);
                } else {
                    return combination(choices + blanks - 1, blanks);
                }
            }
        }
        
        // 递归求排列数
        private long permutation(int n, int k) {
            if(0 == k) {
                return 1;
            } else {
                return permutation(n, k - 1) * (n - k + 1);
            }
        }
        
        // 递归求组合数
        private long combination(int n, int k) {
            if(0 == k) {
                return 1;
            } else {
                return combination(n, k - 1) * (n - k + 1) / k;
            } 
        }
        
        // 题目要求，赢率大（总票数小）的排在前，赢率小（总票数大）的排在后
        // 相同赢率，按字母升序排列（见例1）
        public int compareTo(Rule that) {
            if(this.totalTickets < that.totalTickets) {
                return -1;
            } else if(this.totalTickets > that.totalTickets) {
                return 1;
            } else {
                return this.name.compareTo(that.name);
            }
        }
    }
    
    // 测试结果，可不提交到TopCoder Arena中
    public static void main(String[] args) {
        Lottery lot = new Lottery();
        String[] rules ={"PICK ANY TWO: 10 2 F F", 
                        "PICK TWO IN ORDER: 10 2 T F", 
                        "PICK TWO DIFFERENT: 10 2 F T", 
                        "PICK TWO LIMITED: 10 2 T T"};
        // String[] rules = {"INDIGO: 93 8 T F",
                        // "ORANGE: 29 8 F T",
                        // "VIOLET: 76 6 F F",
                        // "BLUE: 100 8 T T",
                        // "RED: 99 8 T T",
                        // "GREEN: 78 6 F T",
                        // "YELLOW: 75 6 F F"};
        // String[] rules = {};
        String[] result = lot.sortByOdds(rules);
        
        for(int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}