package Command;

import java.util.ArrayList;
import System.Path;


public class CommandCd extends Command {
	protected String myCurrentPath = Path.getPath();
//	public CommandCd(currentPath){
//		currentPath = myCurrentPath;
//	}

    public void execute(String[] myArguments){
	    if (myArguments.length > 2){
	      System.out.println("Too many arguments.");
	    }else{
	     	String myDir = myArguments[1];
		    ArrayList<String> allPath = Path.getPathArray();
		    int i;
		    String myPath;
		    String[] dirArray = myDir.split("/");

		    for (i = 0; i < allPath.size(); i++){
				 myPath = allPath.get(i);
				 String[] myPathArray = myPath.split("/");
				 if (dirArray.length == 1){
					 String newPath = myCurrentPath + dirArray[0];
					 if (newPath == myPath){
						 Path.setPath(newPath);
				 }else{
					 if(dirArray.length == myPathArray.length+1){
						 int j;
						 Boolean isFound = true;
					     for (j = 0; j < myPathArray.length; j++){
					        if (myPathArray[j] != dirArray[j]){
					    	    isFound = false;
					    	}
					     if (isFound){
					    	 if (myDir == myPath){
					    		 Path.setPath(myPath);
					    	 }
					     }else{
					    	 System.out.println("No such file or directory.");
					     }
					     }

					  }
				  }
				 }
		    }
	    }
    }

}
  

	    		
	      


