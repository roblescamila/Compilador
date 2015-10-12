
public class Terceto {

	private TSEntry entry;
	
//	private String type = "NADA";
	private int rule;
	private Terceto left, right;
	private Token op;
	
	
//	static Terceto backward = null; // puntero anterior
//	Terceto pAnt = null;
//	static char lastVisit;
//	char uVis;

	private static boolean error = false;

//	public Terceto (Token t, Terceto l){
//		left = l;
//		op = t;
//	}
	
	public Terceto (Token t, Terceto l, Terceto r){
		left = l;
		op = t;
		right = r;
	}
	
	public static void resetError(){
		error = false;
	}
	public Terceto (TSEntry e ,String valor){
		this.entry = e;
//		this.value = valor;
		this.right = null;
		this.left = null;
	}

	public Terceto (String val, Terceto i , Terceto d){
//		this.value = val;
		this.left = i;
		this.right = d;
	}

	public TSEntry getEntry() {
		return entry;
	}

	public void setEntry(TSEntry e) {
		this.entry = e;
	}

//	public void setValue (String v){
//		this.value = v;
//	}
//
//	public String getValue (){
//		return this.value;
//	}

	public static void setError (){
		error = true;
	}

	public static boolean hasError (){
		return error;
	}

//	public void setTipo (String t){
//		this.type = t;
//	}
//
//	public String getTipo (){
//		return this.type;
//	}
		
	
//	public void print (int level){
//		int a = 0;
//		if (this != null){
//			while (a < level){
//				main.printTree("           ", false);
//				a++;
//			}
//			main.printTree(value, true);
//			level++;
//			if (this.esNodo()){
//				if(this.left!=null)
//					this.left.print(level);
//				if(this.right!=null)
//					this.right.print(level);
//			}
//		}
//	}
}
