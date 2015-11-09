
public class Terceto {

	int id;
	String operador, op1, op2;
	String type;

	public Terceto(int i, String op, String op1, String op2){
		this.operador = op;
		this.op1 = op1;
		this.op2 = op2;
		this.id = i;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public String getOp1() {
		return op1;
	}

	public void setOp1(String op1) {
		this.op1 = op1;
	}

	public String getOp2() {
		return op2;
	}

	public void setOp2(String op2) {
		this.op2 = op2;
	}
	
	public void imprimir (){
		System.out.println(this.id + ": (" + this.operador + ", " + this.op1 + ", " + this.op2 + ")" );
	}
	
}
