import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

//Tabla de s�mbolos
public class TS {
	
	Vector<String> reservedWords;
	Hashtable<String,TSEntry> lexemas;

	public TS(){
		reservedWords = new Vector<String>();
	    lexemas = new Hashtable <String,TSEntry>();
	    reservedWords.addElement("IF");
	    reservedWords.addElement("THEN");
	    reservedWords.addElement("ELSE");
	    reservedWords.addElement("ENDIF");
	    reservedWords.addElement("PRINT");
	    reservedWords.addElement("INT");
	    reservedWords.addElement("BEGIN");
	    reservedWords.addElement("END");
	    reservedWords.addElement("GLOBAL");
	    reservedWords.addElement("FLOAT");
	    reservedWords.addElement("LOOP");
	    reservedWords.addElement("FROM");
	    reservedWords.addElement("TO");
	    reservedWords.addElement("BY");
	    reservedWords.addElement("TOFLOAT");
	    reservedWords.addElement("STRING");
	}
	
    public Hashtable <String,TSEntry> getTable(){
    	return this.lexemas;
    }

    public boolean hasLexema(String lexema) {
        return this.lexemas.containsKey(lexema);
    }

    
    public boolean isDeclared(String lexema)
    {
    	return lexemas.get(lexema).isDeclared();
    }
    
    public void setkey(String viejo, Token a)
    {
    	
       System.out.println("meti en la tabla "+ a.getLexema() +"  saque a   " +viejo);
    	lexemas.put(a.getLexema(), a.getETS());
    	lexemas.remove(viejo);
    }
    
    public TSEntry getTSEntry(String lexema) {
        return this.lexemas.get(lexema);
    }

    public boolean isResWord(String value) {
        return this.reservedWords.contains(value);
    }

    public void addTSEntry(String lexema, TSEntry entry) {
        this.lexemas.put(lexema, entry);
    }
    

    
    public void limpiar()
    {
    	 Enumeration<String> e = lexemas.keys();
         while (e.hasMoreElements()){
         	String aux =(String) e.nextElement();
            if (lexemas.get(aux).getType() == "ID")
            	lexemas.remove(aux);
    }
    }
    
    
    public void remove(String lexema) {
        this.lexemas.remove(lexema);
    }
    
    public void showTable(){
        Enumeration<String> e = lexemas.keys();
        while (e.hasMoreElements()){
        	String aux =(String) e.nextElement();
            System.out.println("key  "+  aux + " Lexema: " + lexemas.get(aux).getLexema() + "  Tipo de token: " + lexemas.get(aux).getType());
        }
    }  
 }