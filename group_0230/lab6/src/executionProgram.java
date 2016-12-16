import java.util.Hashtable;

public class executionProgram {
  Hashtable<String, String> myTable = new Hashtable<String, String>();
  String fileExtension = "pdf";

  public void executionpProgram() {
    myTable.put("pdf", "openPDF");
    myTable.put("doc", "openDOC");
  }

 
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    filee[] fileArray;
    fileArray = new filee[10];

    fileArray[0] = new openPDF();
    fileArray[1] = new openPDF();
    fileArray[2] = new OpenWord();
    fileArray[3] = new openTxt();
    fileArray[4] = new openPDF();
    fileArray[5] = new OpenWord();
    fileArray[6] = new openTxt();
    fileArray[7] = new openTxt();
    fileArray[8] = new openPDF();
    fileArray[9] = new OpenWord();


    for (filee singleFile : fileArray) {
      singleFile.NameOfFile();
      singleFile.readSingleLine();
      System.out.println();
      System.out.println();
      System.out.println();
    }
  }
}
