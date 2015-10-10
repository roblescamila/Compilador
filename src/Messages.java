import java.util.Vector;

public class Messages {

		public void SymbolsTable(){}
		
		Vector<Error> errores = new Vector<Error>();
		Vector<Warning> warnings = new Vector<Warning>();
		Vector<ES> structures = new Vector<ES>();
		
		public void error() {
			if (errores.size() > 0){
				if (errores.size() == 1) 
					System.out.println("No se pudo compilar. Hay sólo un error." + "\n");
				else
					System.out.println("No se pudo compilar. Hay " + errores.size() + " errores." + "\n");
					for (int i = 0; i < errores.size(); i++)
						System.out.println("Error de tipo " + errores.elementAt(i).getType() + " en línea " + errores.elementAt(i).getLine() + ": " + errores.elementAt(i).getError());
				}
			else
				System.out.println("Compilación satisfactoria.");
		}

		public void addWarning(Warning w){
			warnings.add(w);
		}
		
		public void addError(Error r){
			errores.add(r);
		}
		
		public void addStructure(ES s){
			structures.add(s);
		}
		
		public void token(int nroLinea, String lexema) {
			System.out.println("Token en línea " + nroLinea + ": " + lexema);	
		}
		
		public void sintacticStructure() {
			System.out.println();	
			for (int i = 0; i < structures.size(); i++)
				System.out.println("Estructura sintáctica en línea " + structures.elementAt(i).getLine() + ": " + structures.elementAt(i).getStructure());
			System.out.println();	
		}
		
		public void warning() {
			for (int i = 0; i < warnings.size(); i++)
				System.out.println("Warning: " + warnings.elementAt(i).getWarning() + " En linea " + warnings.elementAt(i).getLine() + "\n");	
		}
}