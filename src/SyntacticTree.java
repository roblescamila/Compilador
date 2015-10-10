
public class SyntacticTree {

	private TSEntry entry ;
	
	//public static final short AUX=275;
	private String type = "NADA" ;
	private String value;

	private SyntacticTree right;
	private SyntacticTree left;
	
	static SyntacticTree backward = null; // puntero anterior
	SyntacticTree pAnt = null;
	static char lastVisit;
	char uVis;

	private static boolean error = false;

	public SyntacticTree (){}
	
	public static void resetError(){
		error = false;
	}
	public SyntacticTree (TSEntry e ,String valor){
		this.entry = e;
		this.value = valor;
		this.right = null;
		this.left = null;
	}

	public SyntacticTree (String val, SyntacticTree i , SyntacticTree d){
		this.value = val;
		this.left = i;
		this.right = d;
	}

	public TSEntry getEntry() {
		return entry;
	}

	public void setEntry(TSEntry e) {
		this.entry = e;
	}
	
	public void setRight (SyntacticTree a){
		this.right = a;
	}

	public void setLeft (SyntacticTree a){
		this.left = a;
	}

	public SyntacticTree getRight (){
		return this.right;
	}

	public SyntacticTree getLeft() {
		return this.left;
	}

	public void setValue (String v){
		this.value = v;
	}

	public String getValue (){
		return this.value;
	}

	public static void setError (){
		error = true;
	}

	public static boolean hasError (){
		return error;
	}

	public void setTipo (String t){
		this.type = t;
	}

	public String getTipo (){
		return this.type;
	}
	
	public boolean esHoja (){
		return false;
	}

	public boolean esNodo (){
		return true;
	}

	
	
	
	
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
