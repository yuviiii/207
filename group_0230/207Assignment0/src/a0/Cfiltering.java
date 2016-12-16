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
package a0;

// import DecimalFormat
import java.text.DecimalFormat;

public class Cfiltering {
  // this is a 2d matrix i.e. user*movie
  private int userMovieMatrix[][];
  // this is a 2d matrix i.e. user*movie
  private float userUserMatrix[][];

  /**
   * Default Constructor.
   */
  public Cfiltering() {
    // this is 2d matrix of size 1*1
    userMovieMatrix = new int[1][1];
    // this is 2d matrix of size 1*1
    userUserMatrix = new float[1][1];
  }

  /*
   * TODO:COMPLETE THIS I.E. APPROPRIATELY CREATE THE userMovieMatrix AND
   * userUserMatrix WITH CORRECT DIMENSIONS.
   */
  /**
   * Constructs an object which contains two 2d matrices, one of size
   * users*movies which will store integer movie ratings and one of size
   * users*users which will store float similarity scores between pairs of
   * users.
   * 
   * @param numberOfUsers Determines size of matrix variables.
   * @param numberOfMovies Determines size of matrix variables.
   */
  public Cfiltering(int numberOfUsers, int numberOfMovies) {
    // create the userMovieMatrix of size numberOfUsers*numberOfMovies
    userMovieMatrix = new int[numberOfUsers][numberOfMovies];
    // create the userUserMatrix of size numberOfUsers*numberOfUsers
    userUserMatrix = new float[numberOfUsers][numberOfUsers];

  }

  /**
   * The purpose of this method is to populate the UserMovieMatrix. As input
   * parameters it takes in a rowNumber, columnNumber and a rating value. The
   * rating value is then inserted in the UserMovieMatrix at the specified
   * rowNumber and the columnNumber.
   * 
   * @param rowNumber The row number of the userMovieMatrix.
   * @param columnNumber The column number of the userMovieMatrix.
   * @param ratingValue The ratingValue to be inserted in the userMovieMatrix
   */
  public void populateUserMovieMatrix(int rowNumber, int columnNumber,
      int ratingValue) {

    userMovieMatrix[rowNumber][columnNumber] = ratingValue;
  }

  /*
   * TODO:COMPLETE THIS YOU ARE FREE TO CHANGE THE FUNCTION SIGNATURE BUT DO NOT
   * CHANGE THE FUNCTION NAME AND DO NOT MAKE THIS FUNCTION STATIC. Add/remove
   * 
   * @param AND
   * 
   * @return as required below.
   */
  /**
   * Determines how similar each pair of users is based on their ratings. This
   * similarity value is represented with with a float value between 0 and 1,
   * where 1 is perfect/identical similarity. Stores these values in the
   * userUserMatrix.
   * 
   * @param COMPLETE THIS IF NEEDED
   * @param COMPLETE THIS IF NEEDED
   * @return COMPLETE THIS IF NEEDED
   */
  public void calculateSimilarityScore() {
    // create integers for loops
    int i, j, k;
    // loop for the users
    for (i = 0; i < userMovieMatrix.length; i++) {
      // set the cell with similarity of a same person
      userUserMatrix[i][i] = 1;
      // loop for other users to be compared with the person with index i
      for (j = i + 1; j < userMovieMatrix.length; j++) {
        int sum = 0;
        for (k = 0; k < userMovieMatrix[0].length; k++) {
          sum += Math.pow((userMovieMatrix[i][k] - userMovieMatrix[j][k]), 2);
          // calculate the distance
          float distance = (float) Math.sqrt(sum);
          // calculate the similarity
          float similarity = 1 / (1 + distance);
          // set the cell with similarity of two different people
          userUserMatrix[i][j] = similarity;
          userUserMatrix[j][i] = similarity;
        }
      }
    }
  }

  /*
   * TODO:COMPLETE THIS YOU ARE FREE TO CHANGE THE FUNCTION SIGNATURE BUT DO NOT
   * CHANGE THE FUNCTION NAME AND DO NOT MAKE THIS FUNCTION STATIC
   */
  /**
   * Prints out the similarity scores of the userUserMatrix, with each row and
   * column representing each/single user and the cell position (i,j)
   * representing the similarity score between user i and user j.
   * 
   * @param COMPLETE THIS IF NEEDED
   * @param COMPLETE THIS IF NEEDED
   * @return COMPLETE THIS IF NEEDED
   */

  public void printUserUserMatrix() {
    // initialize the result to be printed
    String result = "";
    int i, j;
    // loop for each row
    for (i = 0; i < userUserMatrix.length; i++) {
      result = result + "[";
      // loop for each column
      for (j = 0; j < userUserMatrix.length; j++) {
        // round the similarity to four decimal places
        DecimalFormat df = new DecimalFormat("0.0000");
        String similarity = df.format(userUserMatrix[i][j]);
        // check whether this row of score are all printed
        if (j == userUserMatrix.length - 1) {
          result = result + similarity + "]" + "\n";
        } else {
          result = result + similarity + ", ";
        }
      }
    }
    System.out.println("userUserMatrix is:" + "\n" + result + "\n");
  }

  /*
   * TODO:COMPLETE THIS YOU ARE FREE TO CHANGE THE FUNCTION SIGNATURE BUT DO NOT
   * CHANGE THE FUNCTION NAME AND DO NOT MAKE THIS FUNCTION STATIC
   */
  /**
   * This function finds and prints the most similar pair of users in the
   * userUserMatrix.
   * 
   * @param COMPLETE THIS IF NEEDED
   * @param COMPLETE THIS IF NEEDED
   * @return COMPLETE THIS IF NEEDED
   */

  public void findAndprintMostSimilarPairOfUsers() {
    // initialize the highestScore to zero
    float highestScore = 0;
    // initialize the users to be printed
    String Users = "";
    for (int i = 0; i < userUserMatrix.length; i++) {
      for (int j = i + 1; j < userUserMatrix.length; j++) {
        String myUser = "User" + Integer.toString(i + 1) + " and " + "User"
            + Integer.toString(j + 1);
        // check if this pair of users have new highest score
        if (highestScore < userUserMatrix[i][j]) {
          // replace the Users with new pair of users with new highest score
          Users = myUser;
          // update the highest score
          highestScore = userUserMatrix[i][j];
        }
        // check whether this pair of users has the same score with current
        // highest score
        else if (highestScore == userUserMatrix[i][j]) {
          // add this pair of users to Users
          Users += "," + "\n" + myUser;
        }
      }
    }
    // round the similarity to four decimal places
    DecimalFormat df = new DecimalFormat("0.0000");
    String myScore = df.format(highestScore);
    System.out.println(
        "The most similar pairs of users from above " + "userUserMatrix are:"
            + "\n" + Users + "\n" + "with similarity score of " + myScore);
  }

  /*
   * TODO:COMPLETE THIS YOU ARE FREE TO CHANGE THE FUNCTION SIGNATURE BUT DO NOT
   * CHANGE THE FUNCTION NAME AND DO NOT MAKE THIS FUNCTION STATIC
   */
  /**
   * This function finds and prints the most dissimilar pair of users in the
   * userUserMatrix.
   * 
   * @param COMPLETE THIS IF NEEDED
   * @param COMPLETE THIS IF NEEDED
   * @return COMPLETE THIS IF NEEDED
   */
  public void findAndprintMostDissimilarPairOfUsers() {
    // initialize the lowest score to 2, which is a number that is higher than
    // all possible similarity scores
    float lowestScore = 2;
    // initialize the users to be printed
    String Users = "";
    for (int i = 0; i < userUserMatrix.length; i++) {
      for (int j = i + 1; j < userUserMatrix.length; j++) {
        String myUser = "User" + Integer.toString(i + 1) + " and " + "User"
            + Integer.toString(j + 1);
        // check if this pair of users have new lowest score
        if (lowestScore > userUserMatrix[i][j]) {
          // replace the Users with new pair of users with new lowest score
          Users = myUser;
          // update the lowest score
          lowestScore = userUserMatrix[i][j];
        }
        // check whether this pair of users has the same score with current
        // lowest score
        else if (lowestScore == userUserMatrix[i][j]) {
          Users += "," + "\n" + myUser;
        }
      }
    }
    // round the similarity score to four decimal places
    DecimalFormat df = new DecimalFormat("0.0000");
    String myScore = df.format(lowestScore);
    System.out.println(
        "The most dissimilar pairs of users from above " + "userUserMatrix are:"
            + "\n" + Users + "\n" + "with similarity score of " + myScore);
  }
}
