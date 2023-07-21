package loginsenhatest;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import cadastrobuilder.SistemaCadastroBuilder;
import cadastrocliente.VerificarCadastro;
import cadastroexception.SistemaCadastroException;
import enviaremail.EnviarEmail;
import mockenviaremail.MockEnviarEmail;
import sistema.SistemaCadastro;
import verificarcadastromock.VerificarCadastroMock;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginSenhaTest {

	@InjectMocks
	private SistemaCadastro sistemaCadastro;
	@Mock
	private EnviarEmail enviarEmail;
	@Mock
	private VerificarCadastro verificarCadastroExistente;
	
	@Test
	public void teste01ValidarLoginCorreto() throws SistemaCadastroException {

		SistemaCadastro sistemaCadastro = new SistemaCadastro(new MockEnviarEmail());
		// Cenario
		sistemaCadastro.setLogin("rbt12");
		sistemaCadastro.setSenha("12345");
		sistemaCadastro.autenticarUsuario("rbt12", "12345");

		// Verificacao e acao
		try {
			sistemaCadastro.autenticarUsuario("rbt12", "12345");
			System.out.println("Usuário autenticado com sucesso.");
		} catch (SistemaCadastroException e) {
			Assert.fail("Não era esperada uma exceção.");
		}
	}

	@Test
	public void teste02ValidarLoginIncorreto() throws SistemaCadastroException {
		// Cenario
		SistemaCadastro sistemaCadastro = new SistemaCadastro(new MockEnviarEmail());
		sistemaCadastro.setLogin("carla06");
		sistemaCadastro.setSenha("12345");
		// Acao e verificacao
		try {
			sistemaCadastro.autenticarUsuario("carla05", "12345");
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals("Login incorreto.", e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste03ValidarSenhaCorreta() {
		// Cenario
		SistemaCadastro sistemaCadastro = new SistemaCadastro(new MockEnviarEmail());
		sistemaCadastro.setLogin("joaolucas");
		sistemaCadastro.setSenha("12345");

		// acao e verificacao
		try {
			sistemaCadastro.autenticarUsuario("joaolucas", "12345");
			System.out.println("Usuário autenticado com sucesso.");
		} catch (SistemaCadastroException e) {
			Assert.fail("Não era esperada uma exceção.");
		}
	}

	@Test
	public void teste04ValidarSenhaIncorreta() throws SistemaCadastroException {
		// Cenario
		SistemaCadastro sistemaCadastro = new SistemaCadastro(new MockEnviarEmail());
		sistemaCadastro.setLogin("josed");
		sistemaCadastro.setSenha("12345");

		// acao e verificacao
		try {
			sistemaCadastro.autenticarUsuario("josed", "12346");
			Assert.fail("Expected SistemaCadastroException");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals("Senha incorreta.", e.getMensagensErro().get(0));
		}
	}

	@Test
	public void teste05RecuperarSenhaSucesso() throws SistemaCadastroException {
	    // Cenário
	    String emailCadastrado = "marcosvi@hotmail.com";
	    String login = "marco06";
	    String senha = "123456";
	    //Parametrização do 
	    SistemaCadastro sistemaCadastro = SistemaCadastroBuilder.builder(new MockEnviarEmail())
	            .verificarCadastroExistente(new VerificarCadastroMock())
	            .build();

	    // Execução do teste
	    sistemaCadastro.cadastrarUsuario(emailCadastrado, login, senha);
	    String resultado = sistemaCadastro.recuperarSenha(emailCadastrado);

	    // Verificação
	    Assert.assertFalse(resultado.contains("Sua senha foi encaminhada para email:"));
	    Assert.assertTrue(resultado.startsWith("Sua nova senha é:"));
	    System.out.println(resultado);
	}

	@Test
	public void teste06RecuperarSenhaSemCadastro() {
		// Cenário
		SistemaCadastro sistemaCadastro = new SistemaCadastro(new MockEnviarEmail());
		String email = "gustavo.moniz@bol.com.br";
		final String naoCadastrado = "E-mail não cadastrado.";

		// Ação: cria um mock da interface EnviarEmail
		EnviarEmail enviarEmailMock = Mockito.mock(EnviarEmail.class);
		sistemaCadastro.setEnviarEmail(enviarEmailMock);

		// Verificação
		try {
			sistemaCadastro.recuperarSenha(email);
			Assert.fail("Esperava-se que uma exceção fosse lançada.");
		} catch (SistemaCadastroException e) {
			Assert.assertEquals(naoCadastrado, e.getMensagensErro().get(0));
		}

		// Verifica se o método enviarEmail do objeto enviarEmailMock não foi chamado
		Mockito.verify(enviarEmailMock, Mockito.never()).enviarEmail(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString());
	}
}