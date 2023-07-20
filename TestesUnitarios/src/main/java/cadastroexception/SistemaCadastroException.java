package cadastroexception;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class SistemaCadastroException extends Exception {

	private List<String> mensagensErro;

	public SistemaCadastroException(List<String> mensagensErro) {
		this.mensagensErro = mensagensErro;
	}

	public SistemaCadastroException(String mensagenErro) {
		this.mensagensErro = new ArrayList<>();
		this.mensagensErro.add(mensagenErro);
	}

	public List<String> getMensagensErro() {
		return mensagensErro;
	}

	public void setMensagensErro(List<String> mensagensErro) {
		this.mensagensErro = mensagensErro;
	}
}