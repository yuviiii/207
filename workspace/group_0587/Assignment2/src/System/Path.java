package System;

import java.util.ArrayList;

public class Path {
	protected static String path = "~/";
	protected static String initialDirectory;
	protected static String fileName;
	protected static String createdPath = "~/";
	protected static ArrayList<String> pathArray = new ArrayList<String>();

	public String tostring() {
		return path;
	}

	public boolean absolutePath(String path) {
		return path != null && path.charAt(0) == '/' && path.charAt(1) == '~' && path.charAt(2) == '/';
	}

	public boolean relativePath(String path) {
		return !absolutePath(path);
	}

	public static void setPath(String a) {
		createdPath = path + "/" + a;
		pathArray.add(createdPath);

	}

	
	public void deeper() {
		path = createdPath;
	}
	public static String getPath(){
		return path;
		
	}

	public static String getInitialDirectory() {
		return initialDirectory;
	}

	public static String getFileName() {
		return fileName;
	}

	public static String getCreatedPath() {
		return createdPath;
	}
	

	public static ArrayList<String> getPathArray() {
		return pathArray;
	}
}
    