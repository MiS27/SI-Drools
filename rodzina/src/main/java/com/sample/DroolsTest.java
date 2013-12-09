package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class DroolsTest {
	public static List<Question> questions = new ArrayList<Question>();

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            Comm.setSession(ksession);
            // go !
            // Message message = new Message();
            // message.setMessage("Hello World");
            // message.setStatus(Message.HELLO);
            // ksession.insert(message);
            init(ksession);
            ksession.fireAllRules();
            logger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("asking.drl"), ResourceType.DRL);
        kbuilder.add(ResourceFactory.newClassPathResource("artists.drl"), ResourceType.DRL);
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase;
    }

    private static void init(StatefulKnowledgeSession ksession) {
    	ksession.insert(new Fact("start"));
    	/*int i = 1;
    	questions.add(new Question(0, "Może metal?", "boolean", new Option[] {new Option("metal", new Fact("metal"))}, false));
    	questions.add(new Question(i++, "Wybierz lubiane instrumenty", "multiple", new Option[] {new Option("bas"),new Option("gitara"),new Option("perkusja"),new Option("fortepian"),new Option("pianino"), new Option("syntezator")}, true));
    	questions.add(new Question(i++, "Wybierz lubiane gitary", "multiple", new Option[] {new Option("gitara akustyczna"),new Option("gitara elektryczna"),new Option("gitara klasyczna")}, false));
    	questions.add(new Question(i++, "Wybierz lubiane syntezatory", "multiple", new Option[] {new Option("moog"),new Option("syth pad"),new Option("saw lead")}, false));
    	questions.add(new Question(i++, "Na co masz ochotę?", "boolean", new Option[] {new Option("instrumental"),new Option("tekst")}, true));
    	questions.add(new Question(i++, "Wokal męski czy żeński?", "boolean", new Option[] {new Option("męski"),new Option("żeński")}, false));
    	
    	for (Question q : questions)
    		ksession.insert(q);*/
    }

}
