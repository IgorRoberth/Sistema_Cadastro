package loginsenhatest;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import cadastroexception.SistemaCadastroException;
import enviaremail.EnviarEmail;
import sistema.SistemaCadastro;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginSenhaTest {

	@Test
	public void teste01ValidarLoginCorreto() throws SistemaCadastroException {
		// Arrange
		SistemaCadastro sistemaCadastro = new SistemaCadastro();
		sistemaCadastro.setLogin("rbt12");
		sistemaCadastro.setSenha("12345");
		sistemaCadastro.autenticarUsuario("rbt12", "12345");

		// Act & Assert
		try {
			sistemaCadastro.autenticarUsuario("rbt12", "12345");
			System.out.println("Usuário autenticado com sucesso.");
		} catch (SistemaCadastroException e) {
			Assert.fail("Não era esperada uma exceção.");
		}
	}

	@Test
	public void teste02ValidarLoginIncorreto() throws SistemaCadastroException {
		// Arrange
		SistemaCadastro sistemaCadastro = new SistemaCadastro();
		sistemaCadastro.setLogin("carla06");
		sistemaCadastro.setSenha("12345");

		// Act & Assert
		try {
			sistemaCadastro.autenticarUsuario("carla05", "12345");
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals("Login incorreto.", e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste03ValidarSenhaCorreta() {
		// Arrange
		SistemaCadastro sistemaCadastro = new SistemaCadastro();
		sistemaCadastro.setLogin("joaolucas");
		sistemaCadastro.setSenha("12345");

		// Act & Assert
		try {
			sistemaCadastro.autenticarUsuario("joaolucas", "12345");
			System.out.println("Usuário autenticado com sucesso.");
		} catch (SistemaCadastroException e) {
			Assert.fail("Não era esperada uma exceção.");
		}
	}

	@Test
	public void teste04ValidarSenhaIncorreta() throws SistemaCadastroException {
		// Arrange
		SistemaCadastro sistemaCadastro = new SistemaCadastro();
		sistemaCadastro.setLogin("josed");
		sistemaCadastro.setSenha("12345");

		// Act & Assert
		try {
			sistemaCadastro.autenticarUsuario("josed", "12346");
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals("Senha incorreta.", e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste05RecuperarSenha() throws SistemaCadastroException {
		// Arrange
		SistemaCadastro sistemaCadastro = new SistemaCadastro();
		String email = "gustavo.moniz@bol.com.br";
		String login = "gutomoniz";
		String senha = "12345";

		// Cria um mock da interface EnviarEmail
		EnviarEmail enviarEmailMock = Mockito.mock(EnviarEmail.class);
		sistemaCadastro.setEnviarEmail(enviarEmailMock);

		// Act
		sistemaCadastro.cadastrarUsuario(email, login, senha); // Cadastra o usuário antes de recuperar a senha
		sistemaCadastro.recuperarSenha(email);

		// Assert
		Mockito.verify(enviarEmailMock, Mockito.times(1)).enviarEmail(Mockito.eq(email), Mockito.anyString(),
				Mockito.anyString());
	}
}