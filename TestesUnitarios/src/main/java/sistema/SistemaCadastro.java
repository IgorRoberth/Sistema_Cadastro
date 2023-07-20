package sistema;

import java.util.ArrayList;
import java.util.List;
import cadastrocliente.VerificarCadastro;
import cadastroexception.SistemaCadastroException;
import enviaremail.EnviarEmail;
import verificarcadastromock.VerificarCadastroMock;

public class SistemaCadastro {

	private String cadastroRealizado = "Cadastro realizado com sucesso";
	private List<String> usuariosCadastrados = new ArrayList<>();
	private String mensagemConfirmacao;
	private VerificarCadastro verificarCadastroExistente;
	private EnviarEmail enviarEmail;
	private String login;
	private String senha;

	public SistemaCadastro() {
	}

	public static void main(String[] args) {

		SistemaCadastro sistemaCadastro = new SistemaCadastro();
		VerificarCadastroMock verificarCadastroMock = new VerificarCadastroMock();
		verificarCadastroMock.setCadastroExistente(true);
		sistemaCadastro.setVerificarCadastro(verificarCadastroMock);
	}

	public void setVerificarCadastro(VerificarCadastro verificarCadastro) {
		this.verificarCadastroExistente = verificarCadastro;
	}
	
	public void setCadastroSucesso(String mensagemCadastroConcluido) {
	   this.setCadastroSucesso(mensagemCadastroConcluido); 	
	}

	public void setEnviarEmail(EnviarEmail enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void cadastrarUsuario(String email, String login, String senha) throws SistemaCadastroException {
		if (verificarCadastroExistente != null && verificarCadastroExistente.verificarCadastroExistente(email)) {
			throw new SistemaCadastroException(List.of("Usuário já está cadastrado no sistema: "));
		}
		usuariosCadastrados.add(email);
		this.login = login;
		this.senha = senha;
		cadastroRealizado = "Cadastro realizado com sucesso";
		enviarEmailConfirmacao(email);
	}

	public String getCadastroRealizado() {
		return cadastroRealizado;
	}

	public void setCadastroRealizado(String cadastroRealizado) {
		this.cadastroRealizado = cadastroRealizado;
	}

	private void enviarEmailConfirmacao(String email) {
		mensagemConfirmacao = "E-mail de confirmação enviado para " + email;
		System.out.println(mensagemConfirmacao);
	}

	public String getMensagemConfirmacao() {
		return mensagemConfirmacao;
	}

	public static void validarCadastro(String nome, String sobrenome, String cidade, String cep, String endereco,
			String idade, String email, String login, String senha) throws SistemaCadastroException {
		List<String> mensagensErro = new ArrayList<>();

		validarCampo(nome, "Nome", mensagensErro);
		validarCampo(sobrenome, "Sobrenome", mensagensErro);
		validarCampo(cidade, "Cidade", mensagensErro);
		validarCampo(cep, "CEP", mensagensErro);
		validarCampo(endereco, "Endereço", mensagensErro);
		validarCampo(idade, "Idade", mensagensErro);
		validarCampo(email, "E-mail", mensagensErro);
		validarCampo(login, "Login", mensagensErro);
		validarCampo(senha, "Senha", mensagensErro);

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
			String email, String login, String senha) {
		System.out.println("Nome: " + nome);
		System.out.println("Sobrenome: " + sobrenome);
		System.out.println("Cidade: " + cidade);
		System.out.println("CEP: " + cep);
		System.out.println("Endereço: " + endereco);
		System.out.println("Idade: " + idade);
		System.out.println("E-mail: " + email);
		System.out.println("Login: " + login);
		System.out.println("Senha: " + senha);
	}

	public void autenticarUsuario(String login, String senha) throws SistemaCadastroException {
		if (!login.equals(this.login)) {
			throw new SistemaCadastroException("Login incorreto.");
		}
		if (!senha.equals(this.senha)) {
			throw new SistemaCadastroException("Senha incorreta.");
		}
	}

	public String recuperarSenha(String email) throws SistemaCadastroException {
		if (!usuariosCadastrados.contains(email)) {
			throw new SistemaCadastroException("E-mail não cadastrado.");
		}
		// Lógica para recuperação de senha
		String novaSenha = gerarNovaSenha();
		enviarEmailSenha(email, novaSenha);
		setSenha(novaSenha); // Define a nova senha no objeto SistemaCadastro
		return novaSenha;
	}

	public String gerarNovaSenha() {
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder();
		java.util.Random random = new java.util.Random();
		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(caracteres.length());
			sb.append(caracteres.charAt(index));
		}
		return sb.toString();
	}

	private void enviarEmailSenha(String email, String novaSenha) {
		enviarEmail.enviarEmail(email, "Recuperação de senha", "Sua nova senha é: " + novaSenha);
	}
	
	public SistemaCadastro(EnviarEmail enviarEmail) {
        this.enviarEmail = enviarEmail;
    }

    public EnviarEmail getEnviarEmail() {
        return enviarEmail;
    }

	public static void sistemaCadastro(String[] args) {
		SistemaCadastro sistemaCadastro = new SistemaCadastro();

		VerificarCadastroMock verificarCadastroMock = new VerificarCadastroMock();
		sistemaCadastro.setVerificarCadastro(verificarCadastroMock);

		verificarCadastroMock.setCadastroExistente(true);

		try {
			sistemaCadastro.cadastrarUsuario("email@example.com", "login", "senha");
			System.out.println(sistemaCadastro.getMensagemConfirmacao());
		} catch (SistemaCadastroException e) {
			for (String mensagemErro : e.getMensagensErro()) {
				System.out.println(mensagemErro);
			}
		}

		sistemaCadastro.exibirCadastro("Nome", "Sobrenome", "Cidade", "CEP", "Endereço", "Idade", "email@example.com",
				"login", "senha");

		try {
			sistemaCadastro.autenticarUsuario("login", "senha");
			System.out.println("Usuário autenticado com sucesso.");
		} catch (SistemaCadastroException e) {
			System.out.println(e.getMessage());
		}

		try {
			sistemaCadastro.recuperarSenha("email@example.com");
		} catch (SistemaCadastroException e) {
			System.out.println(e.getMessage());
		}
	}
}