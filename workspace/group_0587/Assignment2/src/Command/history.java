package Command;
import java.util.ArrayList;


public class history {
	public static ArrayList<String> infoArray = new ArrayList<String>();
	public static void setInfo(String x){

		infoArray.add(x);
	
	}
	public static void getHistory(int number){
		for(int i=0;i<number;i++){
			int num = i+1;
			System.out.println(num + "." + infoArray.get(i));

		}

	}
}