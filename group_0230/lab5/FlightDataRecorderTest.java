package aircraft;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FlightDataRecorderTest {

  private FlightDataRecorder recoder1 = new FlightDataRecorder();
  private FlightDataRecorder recoder2 = new FlightDataRecorder();
  private FlightDataRecorder recoder3 = new FlightDataRecorder();
  
  @Before
  public void setUp(){
      recoder2.record(1.0);
      recoder2.record(1.1);
      recoder2.record(1.2);
      recoder2.record(1.3);
      recoder2.record(1.4);
      recoder3.record(1.0);
      recoder3.record(1.1);
      recoder3.record(1.2);   
      recoder3.record(1.3);
      recoder3.record(1.4);
      recoder3.record(1.5);
      recoder3.record(1.6);
      recoder3.record(1.7);
      recoder3.record(1.8);
      recoder3.record(1.9);
      recoder3.record(2.0);
  }
  
  @Test
  public void testNewRecoderAverage(){
      double actual = recoder1.average();
      double expected = 0;
      assertEquals("should be 0", actual, expected,0.00001);
  }
  
  @Test
  public void testFullRecoderAverage(){
      double actual = recoder3.average();
      double expected = 1.55;
      assertEquals("shoule be 1.55", actual, expected, 0.00001);
  }
  
  @Test
  public void testNotFullRecoderAverage(){
      double actual = recoder2.average();
      double expected = 1.2;
      assertEquals("should be 1.2", actual, expected, 0.00001);       
  }
  
  @Test
  public void testEmptyGetRecordedData(){
      List<Double> actual = recoder1.getRecordedData();
      List<Double> expected = new ArrayList<Double>();
      assertArrayEquals(" ", actual.toArray(), expected.toArray());
  }
  
  @Test
  public void testFullGetRecordedData(){
      List<Double> actual = recoder3.getRecordedData();
      List<Double> expected = new ArrayList<Double>();
      expected.add(1.1);
      expected.add(1.2);
      expected.add(1.3);
      expected.add(1.4);
      expected.add(1.5);
      expected.add(1.6);
      expected.add(1.7);
      expected.add(1.8);
      expected.add(1.9);
      expected.add(2.0);
      assertArrayEquals(" ", actual.toArray(), expected.toArray());
  }
  
  @Test
  public void testGetLastDataPoints1(){
      List<Double> actual = recoder3.getLastDataPoints(1);
      List<Double> expected = new ArrayList<Double>();
      expected.add(2.0);
      assertArrayEquals(" ", actual.toArray(), expected.toArray());
  }
  
  @Test
  public void testGetLastDataPoints2(){
      List<Double> actual = recoder2.getLastDataPoints(1);
      List<Double> expected = new ArrayList<Double>();
      expected.add(1.4);
      assertArrayEquals(" ", actual.toArray(), expected.toArray());
  }
  
  @Test
  public void testGetLastDataPoints3(){
      List<Double> actual = recoder3.getLastDataPoints(11);
      List<Double> expected = new ArrayList<Double>();
      expected.add(2.0);
      expected.add(1.9);
      expected.add(1.8);
      expected.add(1.7);
      expected.add(1.6);
      expected.add(1.5);
      expected.add(1.4);
      expected.add(1.3);
      expected.add(1.2);
      expected.add(1.1);
      assertArrayEquals(" ", actual.toArray(), expected.toArray());
  }
  

  @Test
  public void testGetLastDataPoints4(){
      List<Double> actual = recoder2.getLastDataPoints(10);
      List<Double> expected = new ArrayList<Double>();
      expected.add(1.4);
      expected.add(1.3);
      expected.add(1.2);
      expected.add(1.1);
      expected.add(1.0);
      assertArrayEquals(" ", actual.toArray(), expected.toArray());
  }
}
