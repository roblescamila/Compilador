import java.util.Enumeration;
import java.util.Hashtable;


public class Stack {

	Hashtable<String, Terceto> hash;
	TS table;
	
	public Stack(TS table){
		this.table = table;
		hash = new Hashtable<String, Terceto>();
	}

	public int size() {
		return hash.size();
	}
	public Hashtable<String, Terceto> getHash() {
		return hash;
	}

	public void setHash(Hashtable<String, Terceto> hash) {
		this.hash = hash;
	}
	
	public void add(Terceto t){
		String a = String.valueOf(t.getId());
		hash.put(a, t);
	}
	
	public void imprimir (){
		System.out.println("Impresion de tercetos");
        System.out.println();
        Enumeration<Terceto> e = hash.elements();
		Terceto valor;
		while(e.hasMoreElements()){
		  valor = (Terceto) e.nextElement();
		  valor.imprimir();
		}
	}
	
	public Terceto get(String s){
		return hash.get(String.valueOf(s));
	}
	
	public void toFloat(Terceto t){
		char c = t.getOp1().charAt(0);
		char c2 = t.getOp2().charAt(0);
		String op1,op2; 
		Terceto t1,t2;
		if ((c == '[') || (c2 == '[')){
			if (c == '['){
				op1 = t.getOp1().substring(1,t.getOp1().length()-1);
				t1 = hash.get(op1);
				this.toFloat(t1);
			}
			if (c == '['){
				op2 = t.getOp2().substring(1,t.getOp2().length()-1);
				t2 = hash.get(op2);
				this.toFloat(t2);
			}
		}
		else{
			t.setType("FLOAT");
			table.getTSEntry(t.getOp1()).setType("FLOAT");	
			table.getTSEntry(t.getOp2()).setType("FLOAT");	
		}
	}
}