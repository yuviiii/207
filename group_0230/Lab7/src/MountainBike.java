public class MountainBike extends Bicycle {
  private SuspensionType t;

  public MountainBike(int min, int max, SuspensionType t){
    super(min, max);
    this.t = t;
  }
  // How can you reuse the constructor of the superclass?
  // You'll need to define a private variable to keep track of SuspensionType
  // don't forget to add getters/setters

  public SuspensionType getTerrian() {
    return t;
  }

  public void setTerrian(SuspensionType t) {
    this.t = t;
  }

  public void printDescription() {

    System.out.println("\nMountainBike's SuspensionType is " + this.t);
    
  }

}
