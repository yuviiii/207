// **********************************************************
// Assignment0:
// CDF user_name:c5dingyi
// UT Student #:1001126186
// Author:Yitian Ding
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC 207 and understand the consequences.
// *********************************************************
package driver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import a0.Cfiltering;

public class CfilteringDriver {

  /**
   * Reads user movie ratings from a text file, calculates similarity scores and
   * prints a score matrix.
   */
  public static void main(String[] args) {
    try {

      // open file to read
      String fileName;
      Scanner in = new Scanner(System.in);
      System.out.println("Enter the name of input file? ");
      fileName = in.nextLine();
      FileInputStream fStream = new FileInputStream(fileName);
      BufferedReader br = new BufferedReader(new InputStreamReader(fStream));

      // Read dimensions: number of users and number of movies
      int numberOfUsers = Integer.parseInt(br.readLine());
      int numberOfMovies = Integer.parseInt(br.readLine());

      /*
       * create a new Cfiltering object that contains: a) 2d matrix
       * i.e.userMovieMatrix (#users*#movies) b) 2d matrix i.e. userUserMatrix
       * (#users*#users)
       */
      Cfiltering cfObject = new Cfiltering(numberOfUsers, numberOfMovies);

      // this is a blankline being read
      br.readLine();

      // read each line of movie ratings and populate the
      // userMovieMatrix
      String row;
      // set the index of rows of the userMovieMatrix
      int i = 0;
      while ((row = br.readLine()) != null) {
        // allRatings is a list of all String numbers on one row
        String allRatings[] = row.split(" ");
        // set the index of columns of the userMovieMatrix
        int j = 0;
        for (String singleRating : allRatings) {
          // make the String number into an integer
          // populate userMovieMatrix
          // TODO: COMPLETE THIS I.E. POPULATE THE USER_MOVIE MATRIX
          cfObject.populateUserMovieMatrix(i, j,
              Integer.parseInt(singleRating));
          j++;
        }
        i++;
      }
      // close the file
      fStream.close();

      /*
       * COMPLETE THIS ( I.E. CALL THE APPROPRIATE FUNCTIONS THAT DOES THE
       * FOLLOWING)
       */
      // TODO:1.) CALCULATE THE SIMILARITY SCORE BETWEEN USERS.
      // calculate similarity score by calling this method
      cfObject.calculateSimilarityScore();
      // TODO:2.) PRINT OUT THE userUserMatrix
      System.out.println("\n");
      // print userUserMatrix
      cfObject.printUserUserMatrix();
      // TODO:3.) PRINT OUT THE MOST SIMILAR PAIRS OF USER AND THE MOST
      // DISSIMILAR
      // PAIR OF USERS.
      // find and print most similar pair of users
      cfObject.findAndprintMostSimilarPairOfUsers();
      System.out.println("\n");
      // find and print most dissimilar pair of users
      cfObject.findAndprintMostDissimilarPairOfUsers();
    } catch (FileNotFoundException e) {
      System.err.println("Do you have the input file in the root folder "
          + "of your project?");
      System.err.print(e.getMessage());
    } catch (IOException e) {
      System.err.print(e.getMessage());
    }
  }
}
