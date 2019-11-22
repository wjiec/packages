package tests.reflection;

import inherit.company.staff.Manager;
import main.reflection.Analyzer;

public class AnalyzerTest {

    public static void main(String[] args) {
        Analyzer analyzer = new Analyzer();
        System.out.println(analyzer.analysis(new Manager[] {
            new Manager("Lisa", 3000),
            new Manager("Lisa", 3000),
            new Manager("Lisa", 3000)
        }));

        analyzer = new Analyzer();
        System.out.println(analyzer.analysis(new Manager("Lisa", 3000)));
    }

}
