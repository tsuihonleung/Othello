/**
 * 
 */
package org.ivan.project.starter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.ivan.project.Othello;

/**
 * @author ivantsui
 *
 */
public class Part1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String INPUT_CSV_FILE_PATH = "./data/input.csv";
		//final String INPUT_CSV_FILE_PATH = "./data/input_2.csv";

		BufferedReader bufferedReader = null;
		FileReader fileReader = null;

		Othello othello = new Othello();
		try {
			bufferedReader = new BufferedReader(new FileReader(INPUT_CSV_FILE_PATH));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.print(othello.playGame(line.trim()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
