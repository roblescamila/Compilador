import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class main {
	public static void main(String[] args) throws IOException {
		Messages msg = new Messages();
		String a = args[0].toString();
		File archivo = new File (a);
		FileReader fr = new FileReader (archivo);
		BufferedReader br = new BufferedReader(fr);
		String linea = new String();
		String valor = new String();		
		while((linea = br.readLine()) != null){
			valor += linea + "\n";
	    }
		valor = valor.substring(0,valor.length()-1);
		valor += "  ";
    	br.close();
        AnalizadorLexico al = new AnalizadorLexico(valor, msg);
        Parser analizadorSintactico = new Parser();
        analizadorSintactico.setLexico(al);
        analizadorSintactico.setMensajes(msg);
        analizadorSintactico.run();
        
        msg.sintacticStructure();
        msg.warning();
        msg.error();
        System.out.println();
        System.out.println("Tabla de símbolos");
		System.out.println();	

        al.print();   
	}
	
//	public static void printTree(String s, boolean n) {
//		if (n)
//			textoArbol.append(s + "\n");
//		else
//			textoArbol.append(s);
//	}
}
