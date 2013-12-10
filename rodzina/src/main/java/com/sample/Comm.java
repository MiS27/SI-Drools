package com.sample;

import java.util.Map;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

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

public class Comm {
	private static StatefulKnowledgeSession ksession;
	public static void setSession(StatefulKnowledgeSession session) {
		ksession = session;
	}
	public static void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public static void askQuestion(Question question) {
		if (question.type.equals("boolean") || question.type.equals("choice")) {
			askBooleanQuestion(question);
		}
		// else if (question.type.equals("numeric")) {
		// 	askNumericQuestion(question);
		// }
		/*else if (question.type.equals("multiple")) {
			askMultipleQuestion(question);
		}*/
		else {
			askMultipleQuestion(question);
		}
	}

	static void askBooleanQuestion(Question question) {
		int data = JOptionPane.showConfirmDialog(null, question.text, "Choose an answer", JOptionPane.YES_NO_OPTION);
		boolean result = data == JOptionPane.YES_OPTION;
		if(result && question.type.equals("choice")) {
			ksession.insert(new Fact("chosen", true));
		}
		else {
			ksession.insert(new Fact(question.possibleAnswers[0].toString(), result));
		}
	}

	// static void askNumericQuestion(Question question) {
	// 	String data = JOptionPane.showInputDialog(null, question.text);
	// 	try {
	// 		int result = Integer.parseInt(data);
	// 		ksession.insert(new Fact(question.possibleAnswers[0], result));
	// 	} catch (java.lang.NumberFormatException t) {
	// 		askNumericQuestion(question);
	// 	}
	// }

	static void askMultipleQuestion(Question question) {
		String[] opt = new String[question.possibleAnswers.length];

		for (int i = 0; i<question.possibleAnswers.length; i++) {
			opt[i] = question.possibleAnswers[i].toString();
		}

		JList<String> list = new JList<String>(opt);
		if (question.type == question.MULTIPLE)
			list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		else
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		Object[] options = {question.text, list};
		JOptionPane.showMessageDialog(null, options, "Choose an answer", JOptionPane.QUESTION_MESSAGE);
		try {
			int[] test = list.getSelectedIndices();
			for (int i : test)
				ksession.insert(question.possibleAnswers[i].fact);
		}
		catch (java.lang.ArrayIndexOutOfBoundsException t) {
			askMultipleQuestion(question);
		}
	}
}
