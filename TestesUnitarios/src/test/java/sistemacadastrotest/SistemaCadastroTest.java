package sistemacadastrotest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import cadastrobuilder.SistemaCadastroBuilder;
import cadastrocliente.VerificarCadastro;
import cadastroexception.SistemaCadastroException;
import sistema.SistemaCadastro;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SistemaCadastroTest {

	static SistemaCadastroException exception;
	private SistemaCadastro sistemaCadastro;

	private VerificarCadastro verificarCadastroExistente() {
		return Mockito.mock(VerificarCadastro.class);
	}

	@Before
	public void setup() {

		// Cria uma instância simulada de VerificarCadastro usando o método
		// criarVerificacaoDeCadastro()
		VerificarCadastro verificarCadastro = verificarCadastroExistente();
		/*
		 * Configura o comportamento da instância simulada para retornar falso quando o
		 * método verificarCadastroExistente() for chamado com qualquer argumento de
		 * string
		 */
		Mockito.when(verificarCadastro.verificarCadastroExistente(Mockito.anyString())).thenReturn(false);
		// Cria uma instância de SistemaCadastroBuilder com a configuração do
		// verificarCadastroExistente
		SistemaCadastroBuilder sistemaCadastroBuilder = SistemaCadastroBuilder.builder()
				.verificarCadastroExistente(verificarCadastro);
		// Constrói uma instância de SistemaCadastro com base na configuração definida
		// no builder
		sistemaCadastro = sistemaCadastroBuilder.build();
	}

	@Test
	public void teste01CadastroConcluidoComSucesso() throws SistemaCadastroException {

		String nome = "Roberto";
		String sobrenome = "Silveira";
		String cidade = "Brasilandia";
		String cep = "41259-387";
		String endereco = "Viela Yasmin de Assunção";
		String idade = "28";
		String email = "robertosilveira@gmail.com";
		String login = "rbt12";
		String senha = "123456";

		sistemaCadastro.cadastrarUsuario(email, login, senha);
		Assert.assertEquals("Cadastro realizado com sucesso", sistemaCadastro.getConsoleOutputString());
		Assert.assertEquals("E-mail de confirmação enviado para " + email, sistemaCadastro.getMensagemConfirmacao());
		System.out.println("Nome: " + nome + "\nSobrenome: " + sobrenome + "\nCidade: " + cidade + "\nCEP: " + cep
				+ "\nEndereço: " + endereco + "\nIdade: " + idade + "\nEmail: " + email + "\nUsuário: " + login
				+ "\nSenha: " + senha);

	}

	@Test
	public void teste02CampoNomeVazio() {

		String nome = "";
		String sobrenome = "Brum";
		String cidade = "Tremembé";
		String cep = "41259-387";
		String endereco = "Viela Yasmin de Assunção";
		String idade = "28";
		String email = "roberta.sales@yahoo.com";
		String login = "br@m";
		String senha = "123456";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals("Nome inválido: campo vazio.", e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste03CampoNomeComCaracteresInvalido() {

		String nome = "Alin$";
		String sobrenome = "Muniz";
		String cidade = "Augusto Pestana";
		String cep = "81129-841";
		String endereco = "Marginal Marcos Palmeira";
		String idade = "28";
		String email = "linemuniz@yahoo.com";
		String login = "Line";
		String senha = "12345";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals("Nome inválido: caracteres inválidos.", e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste04CampoSobrenomeVazio() {

		String nome = "Hélio";
		String sobrenome = "";
		String cidade = "Castro Alves";
		String cep = "57115213";
		String endereco = "Joquetina Soares";
		String idade = "31";
		String email = "arthurhenrique.peres@yahoo.com";
		String login = "hmano";
		String senha = "12345";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals("Sobrenome inválido: campo vazio.", e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste05CampoCEPComLetra() {

		String nome = "Aline";
		String sobrenome = "Muniz";
		String cidade = "Castro Alves";
		String cep = "5711521A";
		String endereco = "Joquetina Soares";
		String idade = "31";
		String email = "line.muniz@yahoo.com";
		String login = "li001";
		String senha = "12345";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals("CEP inválido: caracteres inválidos.", e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste06CadastroUsuarioExistente() throws SistemaCadastroException {

		String email = "madalena@gmail.com";
		String usuarioCadastrado = "Usuário já está cadastrado no sistema: ";
		String login = "mada007";
		String senha = "12345";

		// Cria um mock do VerificarCadastro
		VerificarCadastro verificarCadastro = Mockito.mock(VerificarCadastro.class);

		// Define o comportamento do mock para retornar true, indicando que o usuário já
		// está cadastrado
		Mockito.when(verificarCadastro.verificarCadastroExistente(email)).thenReturn(true);

		// Cria uma instância de SistemaCadastro usando o Builder e define o
		// verificarCadastro
		SistemaCadastro sistemaCadastro = SistemaCadastroBuilder.builder().verificarCadastroExistente(verificarCadastro)
				.build();
		// Executa o teste e captura a exceção lançada
		try {
			sistemaCadastro.cadastrarUsuario(email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(usuarioCadastrado, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste07ValidarEnvioDeEmailDeConfirmacao() throws Exception {

		String email = "antonio.caseira@gmail.com";
		String mensagemEsperada = "E-mail de confirmação enviado para " + email;
		String login = "tonho06";
		String senha = "12345";

		try {
			sistemaCadastro.cadastrarUsuario(email, login, senha);
			Assert.assertEquals(mensagemEsperada, sistemaCadastro.getMensagemConfirmacao());
		} catch (SistemaCadastroException e) {
			Assert.fail("Não era esperada uma exceção de SistemaCadastro.CadastroExistenteException");
		}
	}
}