public class Bicycle {

  private int gear;
  private long speed;

  // In TestBikes.main() we're calling the constructor Bicycle()
  // Where is this constructor method ?
  // Create another constructor method that initializes a bicycle with a
  // minimum and maximum gear. Basically, initialize these two variables:

  private int minimumGear;
  private int maximumGear;

  public Bicycle(int minimumGear, int maximumGear) {
    super();
    this.minimumGear = minimumGear;
    this.maximumGear = maximumGear;
  }

  // Create getter and setter methods for all variables
  // See if you can get your IDE to do it automatically for you
  // (the cool IDEs do it)
  // Make sure to add the right checks into the setter!



  public int getGear() {
    return gear;
  }


  public void setGear(int gear) {
    if (gear > minimumGear && gear < maximumGear){
      this.gear = gear;
    }else{
    System.err.println("The gear you set is out of range!");
    }
  }

  public long getSpeed() {
    return speed;
  }

  public void setSpeed(long speed) {
    this.speed = speed;
  }

  public int getMinimumGear() {
    return minimumGear;
  }

  public void setMinimumGear(int minimumGear) {
    this.minimumGear = minimumGear;
  }

  public int getMaximumGear() {
    return maximumGear;
  }

  public void setMaximumGear(int maximumGear) {
    this.maximumGear = maximumGear;
  }

  public void printDescription() {

    System.out.println("\nBike is " + "in gear " + this.gear
        + " and travelling at a speed of " + this.speed + ". ");
    // What happens when gear and speed are not set?
    // What kind of checks can you put in place to take care of non-?

  }

  final public void hitTheBreaks() {
    System.out.println("Break!");
  }

}
