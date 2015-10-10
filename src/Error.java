
public class Error {
	
	int line;
	String error;
	String type;
	
	public Error(int l, String e, String t){
		line = l;
		error = e;
		type = t;
	}
	
	public int getLine(){
		return line;
	}
	
	public String getError(){
		return error;
	}
	
	public String getType(){
		return type;
	}
}
