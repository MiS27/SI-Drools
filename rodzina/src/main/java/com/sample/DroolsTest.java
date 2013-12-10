package com.sample;

import java.awt.HeadlessException;

import javax.swing.JFrame;

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

public class DroolsTest extends JFrame {
	
    public DroolsTest() {
        super("MusicAI");
        setUndecorated(true);
        setVisible(true);
        setLocationRelativeTo(null);
	}

	public static final void main(String[] args) {
        try {
            // load up the knowledge base
        	DroolsTest frame = new DroolsTest();
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
            ksession.fireUntilHalt();
            System.out.println("test");
            logger.close();
            System.out.println("test2");
            frame.dispose();
            System.out.println("test3");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("asking.drl"), ResourceType.DRL);
        kbuilder.add(ResourceFactory.newClassPathResource("artists.drl"), ResourceType.DRL);
        kbuilder.add(ResourceFactory.newClassPathResource("questions.drl"), ResourceType.DRL);
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
    	ksession.insert(new Fact("start", true));
    }

}
