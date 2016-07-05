import java.util.Scanner;
import java.util.Random;

public class MastermindGame {
	public static void main(String[] args) {
		new Mastermind((args.length == 1 && args[0].equals("true")));
	}
}

class Mastermind {
	private char[] colors = {'r', 'b', 'j', 'v', 'n', 'g'};
	private String code = null;
	
	public Mastermind(boolean displayCode) {
		boolean win = false;
		String response = null;
		int[] validation = null;
		
		generateCode();
		
		if (displayCode) {
			code = "rbjv";
			System.out.println("Code : " + code);
		}
		
		while (!win) {
			response = ask();
			validation = checkResponse(response);
			
			if (true == (win = isWinner(validation))) {
				System.out.println("Gagne !!!");
			}
			else {
				System.out.println("Pas encore gagne : ");
				System.out.println("\t" + validation[0] + " bonnes pieces mais dans mauvaise position");
				System.out.println("\t" + validation[1] + " bonnes pieces dans la bonne position");
			}
		}
	}
	
	/**
	 * Génération du code.
	 */
	void generateCode() {
		int i = 0;
		char[] codeChr = new char[4];
		Random rand = new Random();
		
		for (i = 0; i < codeChr.length; i++) {
			codeChr[i] = colors[rand.nextInt(colors.length - 1)];
		}
		code = new String(codeChr);
	}	
	
	/**
	 * Lecture ligne de l'utilisateur.
	 */
	String ask() {
		boolean goodLine = false;
		String inputString = null;
		
		while (!goodLine) {
			System.out.println("Votre code (" + code.length() + " caractères, couleurs : " + new String(colors) + ") : ");
		
			Scanner scanCode = new Scanner(System.in);
			inputString = scanCode.nextLine();
			
			goodLine = validateResponse(inputString);
		}
 
		return inputString;
	}
	
	/**
	 * Validation de l'entrée utilisateur.
	 */
	boolean validateResponse(String response) {
		int i = 0;
		String strColors = new String(colors);
		
		if (response.length() != code.length()) {
			return false;
		}
		
		for (i = 0; i < response.length(); i++) {
			if (-1 == strColors.indexOf((int)response.charAt(i))) {
				return false;
			}
		}
		
		return true;
	}	
	
	/**
	 * Validation de la réponse utilisateur.
	 */
	int[] checkResponse(String response) {
		int i = 0;
		int index = -1;
		int[] validation = {0, 0}; // 0 : bonnes pièces & mauvaise position, 1 : bonnes pièces & bonne position
		String codeCp = code;
		
		if (response.equals(code)) {
			validation[1] = code.length();
			return validation;
		}
		
		for (i = 0; i < codeCp.length(); i++) {
			if (response.charAt(i) == code.charAt(i)) {
				validation[1]++;
				
				char[] responseTmp = response.toCharArray();
				responseTmp[i] = ' ';
				response = String.valueOf(responseTmp);
				
				char[] codeTmp = codeCp.toCharArray();
				codeTmp[i] = ' ';
				codeCp = String.valueOf(codeTmp);
			}
			else if (-1 != (index = codeCp.indexOf((int)response.charAt(i)))) {
				validation[0]++;
				
				char[] codeTmp = codeCp.toCharArray();
				codeTmp[index] = ' ';
				codeCp = String.valueOf(codeTmp);
			}
		}
		
		return validation;
	}
	
	/**
	 * Indique si l'utilisateur a gagné.
	 */
	boolean isWinner(int [] validation) {
		return validation[1] == code.length();
	}
}

