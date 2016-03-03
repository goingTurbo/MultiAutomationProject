/**
 *   File Name: MSNReadFile.java<br>
 *
 *   LastName, FirstName<br>
 *   Java Boot Camp Exercise<br>
 *   Instructor: Jean-francois Nepton<br>
 *   Created: Feb 29, 2016
 *   
 */

package com.sqa.ts.multiautoproject;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.io.FileHandler;

/**
 * MSNReadFile //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 * 
 * @author LastName, FirstName
 * @version 1.0.0
 * @since 1.0
 *
 */
public class MSNReadFile {

	private String fileContent;
	private String itemOne;
	private String itemTwo;
	private String itemThree;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileContent = null;
		try {
			fileContent = FileHandler.readAsString(new File("C:/workspace/MultiAutomationProject/MSNFile.txt"));
			System.out.println(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] items = fileContent.split(",");
		String itemOne = items[0];
		String itemTwo = items[1];
		String itemThree = items[2];
	}

}
