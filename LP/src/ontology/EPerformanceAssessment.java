/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontology;

/**
 *
 * @author t
 */
public class EPerformanceAssessment {
    //难点理解, 概念掌握,实践能力,扩展学习,作业评估,创新培养

    public String a1;
    public String a2;
    public String a3;
    public String a4;
    public String a5;
    public String a6;

    public EPerformanceAssessment() {
        this.a1 = "-1";
        this.a2 = "-1";
        this.a3 = "-1";
        this.a4 = "-1";
        this.a5 = "-1";
        this.a6 = "-1";
    }

    public EPerformanceAssessment(String a1, String a2, String a3, String a4, String a5, String a6) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.a6 = a6;
    }

    public int getValue() {
        int a = Integer.parseInt(a1);
        int b = Integer.parseInt(a2);
        int c = Integer.parseInt(a3);
        int d = Integer.parseInt(a4);
        int e = Integer.parseInt(a5);
        int f = Integer.parseInt(a6);
        return (a + b + c + d + e + f) / 6;
    }

    public String toString() {
        return a1 + "\t" + a2 + "\t" + a3 + "\t" + a4 + "\t" + a5 + "\t" + a6;
    }
}
