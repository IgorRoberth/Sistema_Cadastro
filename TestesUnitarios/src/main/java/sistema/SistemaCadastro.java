package sistema;

import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;
import cadastrocliente.VerificarCadastro;
import cadastroexception.SistemaCadastroException;

public class SistemaCadastro {

	private String consoleOutputString = "Cadastro realizado com sucesso";
	private List<String> usuariosCadastrados = new ArrayList<>();
	private String mensagemConfirmacao;
	private VerificarCadastro verificarCadastroExistente;
	static SistemaCadastro sistemaCadastro = new SistemaCadastro();

	public SistemaCadastro() {
	}

	public void setVerificarCadastro(VerificarCadastro verificarCadastro) {
		this.verificarCadastroExistente = verificarCadastro;
	}

	public void cadastrarUsuario(String email) throws SistemaCadastroException {
		if (verificarCadastroExistente != null && verificarCadastroExistente.verificarCadastroExistente(email)) {
			throw new SistemaCadastroException(List.of("Usuário já está cadastrado no sistema: "));
		}
		usuariosCadastrados.add(email);
		consoleOutputString = "Cadastro realizado com sucesso";
		enviarEmailConfirmacao(email);
	}

	private void enviarEmailConfirmacao(String email) {
		mensagemConfirmacao = "E-mail de confirmação enviado para " + email;
		System.out.println(mensagemConfirmacao);
	}

	public String getMensagemConfirmacao() {
		return mensagemConfirmacao;
	}

	public String getConsoleOutputString() {
		return consoleOutputString;
	}

	public void setConsoleOutputString(String consoleOutputString) {
		this.consoleOutputString = consoleOutputString;
	}

	public static void validarCadastro(String nome, String sobrenome, String cidade, String cep, String endereco,
			String idade, String email) throws SistemaCadastroException {
		List<String> mensagensErro = new ArrayList<>();

		validarCampo(nome, "Nome", mensagensErro);
		validarCampo(sobrenome, "Sobrenome", mensagensErro);
		validarCampo(cidade, "Cidade", mensagensErro);
		validarCampo(cep, "CEP", mensagensErro);
		validarCampo(endereco, "Endereço", mensagensErro);
		validarCampo(idade, "Idade", mensagensErro);
		validarCampo(email, "E-mail", mensagensErro);

		if (!mensagensErro.isEmpty()) {
			throw new SistemaCadastroException(mensagensErro);
		}
	}

	public static void validarCampo(String campo, String nomeCampo, List<String> mensagensErro) {
		if (campo.trim().isEmpty()) {
			mensagensErro.add(nomeCampo + " inválido: campo vazio.");
		} else {
			switch (nomeCampo) {
			case "Nome":
			case "Sobrenome":
			case "Cidade":
			case "Endereço":
				if (!campo.matches("[\\p{L}\\s]+")) {
					mensagensErro.add(nomeCampo + " inválido: caracteres inválidos.");
				}
				break;
			case "CEP":
			case "Idade":
				if (!campo.matches("\\d+")) {
					mensagensErro.add(nomeCampo + " inválido: caracteres inválidos.");
				}
				break;
			case "E-mail":
				if (!campo.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]+")) {
					mensagensErro.add(nomeCampo + " inválido: formato inválido.");
				}
				break;
			}
		}
	}

	public void exibirCadastro(String nome, String sobrenome, String cidade, String cep, String endereco, String idade,
			String email) {
		System.out.println("Nome: " + nome);
		System.out.println("Sobrenome: " + sobrenome);
		System.out.println("Cidade: " + cidade);
		System.out.println("CEP: " + cep);
		System.out.println("Endereço: " + endereco);
		System.out.println("Idade: " + idade);
		System.out.println("E-mail: " + email);
	}

	public static void main(String[] args) {
		String nome = "Olívia";
		String sobrenome = "Cachoeira";
		String cidade = "Crucilândia";
		String cep = "40072562";
		String endereco = "Ponte Francisca Martins";
		String idade = "22";
		String email = "oliviafranc@hotmail.com";

		VerificarCadastro verificarCadastro = Mockito.mock(VerificarCadastro.class);
		Mockito.when(verificarCadastro.verificarCadastroExistente(email)).thenReturn(true);

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email);
			sistemaCadastro.exibirCadastro(nome, sobrenome, cidade, cep, endereco, idade, email);
			System.out.println(sistemaCadastro.getConsoleOutputString());
		} catch (SistemaCadastroException e) {
			for (String mensagemErro : e.getMensagensErro()) {
				System.out.println(mensagemErro);
			}
		}
	}
}