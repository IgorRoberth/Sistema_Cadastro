package cadastrocliente;

public interface VerificarCadastro {

	boolean verificarCadastroExistente(String email);

	void setCadastroExistente(boolean cadastroExistente);
}