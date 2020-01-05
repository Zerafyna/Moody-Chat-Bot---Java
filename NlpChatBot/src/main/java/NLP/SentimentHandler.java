/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NLP;

import java.util.List;

/**
 *
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class SentimentHandler {

    private SentimentAnalyzer analyzer; 

    public SentimentHandler() {
        analyzer = new SentimentAnalyzer();
    }

    public List<String> sentimentAnalyze(String sentences) {
        System.out.println("got: " + sentences);
        
        //storing the results in the list
        List<String> sentiments = analyzer.analyze(sentences);
        
        return sentiments;
    }
}
