package Command;

import System.Path;

public class CommandMkdir extends Command{
public static void execute(String[] args)
        throws Exception
    {	
    	if (args.length < 2){
        	error().println("mkdir must have at least 1 argument.");
        	}
        else {
        	for(int i=1;i<args.length;i++){
        		Path.setPath(args[i]);
        	}
        		
        }
    }


public void usage()
{
    System.out.println("mkdir DIR ...");
    System.out.println("    Creates the specified directory.");
}
}

// boolean success = file.mkdir();
// if (success)
// out().println(file.getPath());
// else
// error().println("Unable to create directory "+path);
