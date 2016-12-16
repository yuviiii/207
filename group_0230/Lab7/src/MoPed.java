
public class MoPed  extends Bicycle{
  private int percentCharged;

  public int getPercentCharged() {
    return percentCharged;
  }

  public void setPercentCharged(int percentCharged) {
    this.percentCharged = percentCharged;
  }
    

  public MoPed(int minimumGear, int maximumGear, int percentCharged) {
    super(minimumGear, maximumGear);
    this.percentCharged = percentCharged;
  }
  
  public void printDescription() {

    System.out.println("\nMoPed is " + this.percentCharged + " percentage charged.");
  }


}
