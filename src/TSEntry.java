//Entrada a la tabla de símbolos

public class TSEntry {

	private short id;
    private String lexema;
    private String type;
    private int refCounter;
    
    public TSEntry(short id, String lexema) {
		this.id = id; 
		this.setLexema(lexema);
		type = "ID";
		refCounter = 1;
	}
   
    public int getRefCounter() {
		return refCounter;
	}

	public void setRefCounter(int refCounter) {
		this.refCounter = refCounter;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String t) {
		this.type = t;	
	}
	
	public short getId() {
		return this.id;
	}
	
	public void setId(short id) {
		this.id = id;
	}
    
	public void incCounter() {
		refCounter++;
	}
	public void decCounter(){
		this.refCounter--;
	}

	public String getLexema() {
		return lexema;
	}	

	public void setLexema(String l) {
		this.lexema = l;
	}
	
	public void invert(){
		this.lexema = '-' + this.lexema;
	}
    
	public String print(){
        return lexema;
    }
	
	public boolean isDeclared(){
		return (type != "ID");
	}
}