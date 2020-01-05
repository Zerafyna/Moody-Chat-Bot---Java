/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NLP;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class SentimentAnalyzer {
    StanfordCoreNLP tokenizer;
    StanfordCoreNLP pipeline;
 
    /**
     * Library code to analyze text
     */
    public SentimentAnalyzer() {
        Properties pipelineProps = new Properties();
        Properties tokenizerProps = new Properties();
        pipelineProps.setProperty("annotators", "parse, sentiment");
        pipelineProps.setProperty("parse.binaryTrees", "true");
        pipelineProps.setProperty("enforceRequirements", "false");
        tokenizerProps.setProperty("annotators", "tokenize ssplit");
        tokenizer = new StanfordCoreNLP(tokenizerProps);
        pipeline = new StanfordCoreNLP(pipelineProps);
    }
 
    public List<String> analyze(String line) {
        Annotation annotation = tokenizer.process(line);
        pipeline.annotate(annotation);
        
        //adding all the responses to List of strings
        List<String> results = new ArrayList();
        
        //analyses each sentace separetly, storing results in an array to find overall
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            results.add(sentence.get(SentimentCoreAnnotations.ClassName.class));
        }
        //returning the results
        return results;
    }
}
