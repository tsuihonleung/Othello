/**
 * 
 */
package org.ivan.project.starter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.ivan.project.Othello;

/**
 * @author ivantsui
 *
 */
public class Part2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Othello othello = new Othello();
        BufferedReader bufferedReader = null;

        try {
        	System.out.println(othello.displayBoard());
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while (!othello.isGameEnd()) {
				System.out.print("Player '" + othello.getCurrentPlayer() + "' move: ");
                String input = bufferedReader.readLine();
                System.out.println(othello.playGame(input.trim()));
            }
			System.out.println(othello.gameResult());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}

}
