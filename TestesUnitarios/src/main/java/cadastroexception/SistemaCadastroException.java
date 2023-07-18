package cadastroexception;

import java.util.List;

@SuppressWarnings("serial")
public class SistemaCadastroException extends Exception {

	public List<String> mensagensErro;

	    public SistemaCadastroException(List<String> mensagensErro) {
	        this.mensagensErro = mensagensErro;
	    }

	    public List<String> getMensagensErro() {
	        return mensagensErro;
	    }	
}