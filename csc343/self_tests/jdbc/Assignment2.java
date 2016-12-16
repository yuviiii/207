

import java.sql.*;
import java.util.Date;

// If you are looking for Java data structures, these are highly useful.
// Remember that an important part of your mark is for doing as much in SQL (not Java) as you can.
// Solutions that use only or mostly Java will not received a high mark.  
import java.util.ArrayList; 
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Calendar;

public class Assignment2 {

   // A connection to the database
   Connection connection;

   Assignment2() throws SQLException {
      try {
         Class.forName("org.postgresql.Driver");
      } catch (ClassNotFoundException e) { 
         e.printStackTrace();
      }
   }

  /**
   * Connects and sets the search path.
   *
   * Establishes a connection to be used for this session, assigning it to
   * the instance variable 'connection'.  In addition, sets the search
   * path to bnb.
   *
   * @param  url       the url for the database
   * @param  username  the username to connect to the database
   * @param  password  the password to connect to the database
   * @return           true if connecting is successful, false otherwise
   */
   public boolean connectDB(String URL, String username, String password) {
      // Implement this method!
    try{
      connection = DriverManager.getConnection(URL, username, password);
      PreparedStatement st = 
                connection.prepareStatement("SET search_path TO bnb, public;");
      st.execute();
      return true;
    }
    catch (SQLException e) {
      return false;
    }
   }

  /**
   * Closes the database connection.
   *
   * @return true if the closing was successful, false otherwise
   */
   public boolean disconnectDB() {
      // Implement this method!
    if (connection != null) {
      try {
        connection.close();
        return true;
      }
      catch (SQLException e) {
        return false;
      }
    }
    return false;
  }

   /**
    * Returns the 10 most similar homeowners based on traveller reviews. 
    *
    * Does so by using Cosine Similarity: the dot product between the columns
    * representing different homeowners. If there is a tie for the 10th 
    * homeowner (only the 10th), more than 10 records may be returned. 
    *
    * @param  homeownerID   id of the homeowner
    * @return               a list of the 10 most similar homeowners
    */
   public ArrayList homeownerRecommendation(int homeownerID) {
      // Implement this method
    try {
      String sql = "CREATE VIEW htPair AS " + 
                    "SELECT travelerid, homeownerid " + 
                    "FROM Homeowner, Traveler ";
      PreparedStatement st = connection.prepareStatement(sql);
      st.execute();

      sql = "CREATE VIEW avgRatingEachPair AS " +
            "SELECT travelerid, owner AS homeownerid, avg(rating) AS rating " +
            "FROM TravelerRating NATURAL JOIN Listing NATURAL JOIN Booking " +
            "GROUP BY travelerid, owner ";
       st = connection.prepareStatement(sql);
      st.execute();

      sql = "CREATE VIEW allInfo AS " +
            "SELECT homeownerid, CAST(travelerid AS FLOAT), " + 
            "COALESCE(rating, 0) AS rating " +
            "FROM htPair NATURAL FULL JOIN avgRatingEachPair ";
       st = connection.prepareStatement(sql);
      st.execute();

      sql = "CREATE VIEW pairs AS " +
            "SELECT * " +
            "FROM (SELECT homeownerid AS h1, travelerid AS t1, rating AS r1 " +
              "FROM allInfo " +
              "WHERE homeownerid = " + homeownerID + ") t1, " +
              "(SELECT homeownerid AS h2, travelerid AS t2, rating AS r2 " +
              "FROM allInfo " +
              "WHERE homeownerid != " + homeownerID + ") t2 " +
            "WHERE t1 = t2 ";
       st = connection.prepareStatement(sql);
      st.execute();

      sql = "SELECT h2, sum(r1 * r2) AS score " +
                    "FROM pairs " +
                    "GROUP BY h2 " +
                    "HAVING sum(r1 * r2) >= 0 " +
                    "ORDER BY score DESC, h2 ";
      st = connection.prepareStatement(sql);
      ResultSet rs = st.executeQuery();
      ArrayList<Integer> result = new ArrayList<Integer>();
      int count = 10;
      while (rs.next() && count > 0) {
        Integer homeowner = rs.getInt("h2");
        result.add(homeowner);
        Float tenthScore = rs.getFloat("score");
        // check tie at the last
        if (count == 1) {
          boolean flag = true;
          while (rs.next() && flag) {
            System.out.println(rs.getInt("h2"));
            Float curScore = rs.getFloat("score");
            if (Float.compare(curScore, tenthScore) == 0) {
              Integer curOwner = rs.getInt("h2");
              result.add(curOwner);
            } else {
              flag = false;
            }
          }
          System.out.println("keek");
          // check the last item that does not have rs.next()
          // if (flag) {
          //   Float curScore = rs.getFloat("score");
          //   if (Float.compare(curScore, tenthScore) == 0) {
          //     Integer curOwner = rs.getInt("h2");
          //     result.add(curOwner);
          //   } else {
          //     flag = false;
          //   }
          // }
          Integer curOwner = rs.getInt("h2");
          System.out.println(curOwner);
        }
        count--;
      }
      return result;

    } catch (SQLException e) {
        System.err.println("SQL Exception." +
                        "<Message>: " + e.getMessage());
        return null;
    }
  }

   /**
    * Records the fact that a booking request has been accepted by a 
    * homeowner. 
    *
    * If a booking request was made and the corresponding booking has not been
    * recorded, records it by adding a row to the Booking table, and returns 
    * true. Otherwise, returns false. 
    *
    * @param  requestID  id of the booking request
    * @param  start      start date for the booking
    * @param  numNights  number of nights booked
    * @param  price      amount paid to the homeowner
    * @return            true if the operation was successful, false otherwise
    */
   public boolean booking(int requestId, Date start, int numNights, int price) {
      // Implement this method!
    try {
      // the requestId is not in the BookingRequest table
      String sql = "SELECT * FROM BookingRequest WHERE requestId = ?";
      PreparedStatement st = connection.prepareStatement(sql);
      st.setInt(1, requestId);
      ResultSet rs = st.executeQuery();
      if (!rs.next()) {
        return false;
      }

      // check whether there is an overlap on the new booking with those already booked
      int listingId = rs.getInt("listingId");
      int travelerid = rs.getInt("travelerid");
      int numGuests = rs.getInt("numGuests");
      java.sql.Date date = new java.sql.Date(start.getTime());
      
      Calendar s = Calendar.getInstance();
      s.setTime(start);
      s.add(Calendar.DATE, numNights);
      int year = s.get(Calendar.YEAR);
      int month = s.get(Calendar.MONTH) + 1;
      int day = s.get(Calendar.DAY_OF_MONTH);
      String m;
      String d;
      if (month < 10) {
        m = "0" + month;
      } else {
        m = "" + month;
      }
      if (day < 10) {
        d = "0" + day;
      } else {
        d = "" + day;
      }

      sql = "SELECT listingId FROM Booking WHERE listingId = " + listingId +
      " AND ((startdate = ?) OR (? <= startdate + numNights AND ? >= startdate)" + 
      " OR (startdate >= ? AND startdate <= '" + year + "-" + m + "-" + d + "'))"; 
      st = connection.prepareStatement(sql);
      st.setDate(1, date);
      st.setDate(2, date);
      st.setDate(3, date);
      st.setDate(4, date);
      rs = st.executeQuery();
      if (rs.next()) {
        return false;
      }

      // new booking is valid, insert into Booking table

      st = connection.prepareStatement("INSERT INTO Booking " +
        "VALUES (?, ?, ?, ?, ?, ?); ");
      st.setInt(1, listingId);
      st.setDate(2, date);
      st.setInt(3, travelerid);
      st.setInt(4, numNights);
      st.setInt(5, numGuests);
      st.setInt(6, price);
      st.executeUpdate();
    } catch (SQLException e) {
        System.err.println("SQL Exception." +
                        "<Message>: " + e.getMessage());
        return false;
    }
    return true;
   }

   public static void main(String[] args) {
      // You can put testing code in here. It will not affect our autotester.
       // You can put testing code in here. It will not affect our autotester.
      System.out.println("Boo! 1");
  }
}

