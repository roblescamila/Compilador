
public class Token {

	public static final Short ID = 265;
	public static final Short CONSTANT = 266;
	public static final Short STRING = 268;
	private String lexema;
	private Short id;
	private String type;
	private boolean charAdded;
	private int refCounter;
	private TSEntry entry;
	
	public Token(){
		lexema = new String();
		charAdded = false;
		refCounter = 1;
		id = 0; 
		type = null;		
	}

	// Agrega el caracter recibido al final del string
    public void addChar(char c){
        this.lexema = this.lexema + c;
        charAdded = true;
    }
    
    //Setea que el último caracter leído no fue agregado al token
    public void readCharNotAdded(){
    	charAdded = false;
    }
    //Setea que el último caracter leído fue agregado al token
    public void readCharAdded(){
    	charAdded = true;
    }
    
    public boolean getCharAdded(){
        return charAdded;
    }
    
    //Devuelve la longitud del valor
    public int getLength(){
        return this.lexema.length();
    }
    
    //Devuelve el valor del token
    public String getLexema(){
        return this.lexema;
    }
    
    //Trunca a 15 caracteres el valor del token
    public void truncateId() {
    	this.lexema = this.lexema.substring(0,14);
    }
    
    public void setLexema(String name){
        this.lexema = name;
    }
    
    public boolean equals(Token t){
        return this.lexema.equals(t.getLexema());
    }
    
    public void clear(){
        this.lexema = new String();
    }
    
    public int getRefCounter(){
        return refCounter;
    }
    public void incRefCounter(){
    	refCounter++;
    }
    public void decRefCounter(){
    	refCounter--;
    }
    
    public String getType(){
        return type;
    }
    
    public void setType(String t){
        type = t;
    }   
    
    public Short getId(){
        if (entry != null)
            return this.entry.getId();
        return id;
    }
    
    public void setId(Short id) {
        if ((id.equals(ID)) || (id.equals(STRING)) || (id.equals(CONSTANT))) 
            entry = new TSEntry(id, this.lexema);  // Creo la entrada para la tabla de simbolos
        this.id = id;
    }   
    
    public TSEntry getETS(){
        return this.entry;
    }
    
    public void setTSEntry(TSEntry ent) {
		this.entry = ent;
	}
}
