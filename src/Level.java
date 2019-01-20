/**
 * Author: Tony Huang
 * 
 * Description: Contains the level of the game, the level will determine the difficulty of the question
 * 
 * Parameter:String Letter, receives the letter of the answer to the question, int level, receives the level that the player is on 
 * 
 * Returns: Current Question, Current Answer, If the Level is complete
 * 
 * **/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Level {

	//number of the level
	private int level;

	//Arraylist of question and answer
	private ArrayList<String[]> qAndA = new ArrayList<String[]>();

	//the current question and answer that user is on
	private String[] currentQAndA;

	//what the user answers
	private String playerAnswer = "";

	//random
	private Random rand = new Random();

	//to check if question is complete
	public boolean isComplete = false;
	public boolean questionComplete = false;


	/**Constructor Method**/
	public Level(int level) {

		//call methods
		setLevel(level);
		loadLevel();
	}

	/**Answer Question Method**/
	public void answerQuestion(String letter) {

		// concatenate 2 strings
		playerAnswer += letter;

		//print player answer and current question
		System.out.println(playerAnswer);
		System.out.println(currentQAndA[1].substring(0, playerAnswer.length()));

		//if the player answers 
		if (playerAnswer.equals(currentQAndA[1].substring(0, playerAnswer.length()))) {

			//if the player answer is correct
			if (playerAnswer.equals(currentQAndA[1])) {

				//set questioncomplete to true
				questionComplete = true;

				//clear answer, move onto next question
				playerAnswer = "";
				setCurrentQAndA();
			}

		}

	}


	/**Setters and Getters**/
	public String getPlayerAnswer() {
		return playerAnswer;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String[] getCurrentQAndA() {
		return currentQAndA;
	}

	public void setCurrentQAndA() {

		//if there are no more questions
		if (qAndA.size() == 0) {

			//they are completed
			isComplete = true;

			//else
		} else {

			//next question
			int index = rand.nextInt(qAndA.size());
			currentQAndA = qAndA.get(index);
			qAndA.remove(index);

		}

	}


	/**Load Level Method**/
	public void loadLevel() {

		//player answer is blank
		playerAnswer = "";

		//set complete to false
		isComplete = false;
		questionComplete = false;


		try {

			//read the levelInfo.txt
			Scanner inputFile = new Scanner(new File("Data/levelInfo.txt")).useDelimiter(",");

			//check next line
			inputFile.nextLine();
			inputFile.nextLine();

			int numberOfQuestions = 0;

			//while inputFile has token
			while (inputFile.hasNext()) {

				//if the nextline equals level
				if (inputFile.next().equals(Integer.toString(level))) {

					//set # of questions to the int value
					numberOfQuestions = inputFile.nextInt();

					//for each question
					for (int i = 1; i <= numberOfQuestions; i++) {

						//add new string
						qAndA.add(new String[2]);

						//subtract the question and answer from txt
						qAndA.get(qAndA.size() - 1)[0] = inputFile.next();
						qAndA.get(qAndA.size() - 1)[1] = inputFile.next();

						//print qandA size
						System.out.print(qAndA.get(qAndA.size() - 1)[0] + "     " + qAndA.get(qAndA.size() - 1)[1]);
					}

					break;

					//else
				} else {
					
					
					numberOfQuestions = Integer.valueOf(inputFile.next());
					
					//check next line
					inputFile.nextLine();
					
					//print number of questions
					System.out.println(numberOfQuestions);
					
					//for the # of questions
					for (int i = 1; i <= numberOfQuestions; i++)
						
						//check next line
						inputFile.nextLine();
				}
			}

			//catch filenotfound
		} catch (FileNotFoundException error) {
			
			//error print
			System.err.println("File not found - check the file name");
		}

		//set the current question and answer
		setCurrentQAndA();
	}

}
