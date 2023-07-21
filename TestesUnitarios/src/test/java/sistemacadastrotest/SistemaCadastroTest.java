package sistemacadastrotest;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import cadastrobuilder.SistemaCadastroBuilder;
import cadastrocliente.VerificarCadastro;
import cadastroexception.SistemaCadastroException;
import enviaremail.EnviarEmail;
import mockenviaremail.MockEnviarEmail;
import sistema.SistemaCadastro;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SistemaCadastroTest {

	static SistemaCadastroException exception;
	@InjectMocks
	private SistemaCadastro sistemaCadastro;
	@Mock
	private VerificarCadastro verificarCadastroExistente;
	@Mock
	private EnviarEmail enviarEmailMock;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		enviarEmailMock = mock(EnviarEmail.class);
        verificarCadastroExistente = mock(VerificarCadastro.class);
	}

	@Test
    public void teste01CadastroConcluidoComSucesso() throws SistemaCadastroException {
        // Cenário
        String email = "robertosilveira@gmail.com";
        String login = "rbt12";
        String senha = "123456";
        final String Sucess = "Cadastro realizado com sucesso";
        final String confirmEmail = "E-mail de confirmação enviado para ";

        // Configuração do mock
        Mockito.when(verificarCadastroExistente.verificarCadastroExistente(email)).thenReturn(false);
        // Ação
        sistemaCadastro.cadastrarUsuario(email, login, senha);
        // Verificação
        Assert.assertEquals(Sucess, sistemaCadastro.getCadastroRealizado());
        Assert.assertEquals(confirmEmail + email, sistemaCadastro.getMensagemConfirmacao());
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
		final String erroSobrenome = "Sobrenome inválido: campo vazio.";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(erroSobrenome, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste05CampoSobrenomeComCaracteresInvalido() {

		String nome = "Hélio";
		String sobrenome = "Brab@";
		String cidade = "Castro Alves";
		String cep = "57115213";
		String endereco = "Joquetina Soares";
		String idade = "31";
		String email = "arthurhenrique.peres@yahoo.com";
		String login = "hmano";
		String senha = "12345";
		final String erroSobrenome = "Sobrenome inválido: caracteres inválidos.";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(erroSobrenome, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste06CampoCidadeVazio() {

		String nome = "Master";
		String sobrenome = "Capista";
		String cidade = "";
		String cep = "57115213";
		String endereco = "Joquetina Soares";
		String idade = "31";
		String email = "arthurhenrique.peres@yahoo.com";
		String login = "hmano";
		String senha = "12345";
		final String erroCidade = "Cidade inválido: campo vazio.";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(erroCidade, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste07CampoCidadeComCaracteresInvalido() {

		String nome = "Master";
		String sobrenome = "Capista";
		String cidade = "Alvares12";
		String cep = "57115213";
		String endereco = "Joquetina Soares";
		String idade = "31";
		String email = "arthurhenrique.peres@yahoo.com";
		String login = "hmano";
		String senha = "12345";
		final String erroCidade = "Cidade inválido: caracteres inválidos.";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(erroCidade, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste08CampoCEPComCaracteresInvalido() {

		String nome = "Aline";
		String sobrenome = "Muniz";
		String cidade = "Castro Alves";
		String cep = "5711521A";
		String endereco = "Joquetina Soares";
		String idade = "31";
		String email = "line.muniz@yahoo.com";
		String login = "li001";
		String senha = "12345";
		final String erroCep = "CEP inválido: caracteres inválidos.";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(erroCep, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste09CampoCEPVazio() {

		String nome = "Aline";
		String sobrenome = "Muniz";
		String cidade = "Castro Alves";
		String cep = "";
		String endereco = "Joquetina Soares";
		String idade = "31";
		String email = "line.muniz@yahoo.com";
		String login = "li001";
		String senha = "12345";
		final String erroCep = "CEP inválido: campo vazio.";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(erroCep, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste10CampoEnderecoVazio() {

		String nome = "Aline";
		String sobrenome = "Muniz";
		String cidade = "Castro Alves";
		String cep = "34322";
		String endereco = "";
		String idade = "31";
		String email = "line.muniz@yahoo.com";
		String login = "li001";
		String senha = "12345";
		final String erroEnd = "Endereço inválido: campo vazio.";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(erroEnd, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste11CampoEnderecoComCaracteresInvalido() {

		String nome = "Aline";
		String sobrenome = "Muniz";
		String cidade = "Castro Alves";
		String cep = "34322";
		String endereco = "Rua Cba@";
		String idade = "31";
		String email = "line.muniz@yahoo.com";
		String login = "li001";
		String senha = "12345";
		final String erroEnd = "Endereço inválido: caracteres inválidos.";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(erroEnd, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste12CampoIdadeVazio() {

		String nome = "Josefa";
		String sobrenome = "Bulhoes";
		String cidade = "Castro Alves";
		String cep = "34323232";
		String endereco = "Rua Salinas";
		String idade = "";
		String email = "line.muniz@yahoo.com";
		String login = "li001";
		String senha = "12345";
		final String erroIdade = "Idade inválido: campo vazio.";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(erroIdade, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste13CampoIdadeComCaracteresInvalido() {

		String nome = "Josefa";
		String sobrenome = "Bulhoes";
		String cidade = "Castro Alves";
		String cep = "34323232";
		String endereco = "Rua Salinas";
		String idade = "2O";
		String email = "line.muniz@yahoo.com";
		String login = "li001";
		String senha = "12345";
		final String erroIdade = "Idade inválido: caracteres inválidos.";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(erroIdade, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste14CampoEmailVazio() {

		String nome = "Josefa";
		String sobrenome = "Bulhoes";
		String cidade = "Castro Alves";
		String cep = "34323232";
		String endereco = "Rua Salinas";
		String idade = "25";
		String email = "";
		String login = "li001";
		String senha = "12345";
		final String erroEmail = "E-mail inválido: campo vazio.";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(erroEmail, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste15CampoEmailEscritoIncorreto() {

		String nome = "Mauricio";
		String sobrenome = "Borges";
		String cidade = "Peripécia";
		String cep = "34323232";
		String endereco = "Rua Das Alturas";
		String idade = "29";
		String email = "mauricio@gmailcom";
		String login = "li001";
		String senha = "12345";
		final String erroEmail = "E-mail inválido: formato inválido.";

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email, login, senha);
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(erroEmail, e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste16CadastroUsuarioExistente() throws SistemaCadastroException {

		String email = "madalena@gmail.com";
		final String usuarioCadastrado = "Usuário já está cadastrado no sistema: ";
		String login = "mada007";
		String senha = "12345";
		// Cria um mock do VerificarCadastro
		VerificarCadastro verificarCadastro = Mockito.mock(VerificarCadastro.class);
		// Define o comportamento do mock para retornar true, indicando que o usuário já
		// está cadastrado
		Mockito.when(verificarCadastro.verificarCadastroExistente(email)).thenReturn(true);
		// Cria uma instância de SistemaCadastro usando o Builder e define o
		// verificarCadastro
		SistemaCadastro sistemaCadastro = SistemaCadastroBuilder.builder(new MockEnviarEmail()).verificarCadastroExistente(verificarCadastro)
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
	public void teste17ValidarEnvioDeEmailDeConfirmacao() throws Exception {

		String email = "antonio.caseira@gmail.com";
		String login = "tonho06";
		String senha = "12345";
		final String mensagemEsperada = "E-mail de confirmação enviado para " + email;

		try {
			sistemaCadastro.cadastrarUsuario(email, login, senha);
			Assert.assertEquals(mensagemEsperada, sistemaCadastro.getMensagemConfirmacao());
		} catch (SistemaCadastroException e) {
			Assert.fail("Não era esperada uma exceção de SistemaCadastro.CadastroExistenteException");
		}
	}
}