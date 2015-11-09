import java.util.Enumeration;
import java.util.Hashtable;


public class Stack {

	Hashtable<String, Terceto> hash;
	
	
	public Stack(){
		hash = new Hashtable<String, Terceto>();
	}

	public int size()
	{
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
}
	
	
	
