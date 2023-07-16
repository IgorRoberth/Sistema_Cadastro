package TestesUnit;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import sistema.SistemaCadastro;
import sistema.SistemaCadastro.CadastroExistenteException;
import sistema.SistemaCadastro.CadastroExistenteVerifier;
import sistema.SistemaCadastro.CadastroInvalidoException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SistemaCadastroTest {
	
	@SuppressWarnings("unused")
	@Test
	public void teste01CadastroConcluidoComSucesso() throws CadastroInvalidoException {

		String nome = "Roberto";
		String sobrenome = "Silveira";
		String cidade = "Santa Cruz do Sul";
		String cep = "61701-292";
		String endereco = "Viela Vitor Silva";
		String idade = "28";
		String email = "antonio.caseira@gmail.com";

		ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(consoleOutput));
		SistemaCadastro.main(null);
		String consoleOutputString = consoleOutput.toString();
		Assert.assertTrue(consoleOutputString.contains("Cadastro realizado com sucesso"));
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

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email);
			Assert.fail("Expected CadastroInvalidoException to be thrown");
		} catch (SistemaCadastro.CadastroInvalidoException e) {
			List<String> mensagensErro = e.getMensagensErro();
			Assert.assertEquals("Nome inválido: campo vazio.", mensagensErro.get(0));
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

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email);
			Assert.fail("Expected CadastroInvalidoException to be thrown");
		} catch (SistemaCadastro.CadastroInvalidoException e) {
			List<String> mensagensErro = e.getMensagensErro();
			Assert.assertEquals("Nome inválido: caracteres inválidos.", mensagensErro.get(0));
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

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email);
			Assert.fail("Expected CadastroInvalidoException to be thrown");
		} catch (SistemaCadastro.CadastroInvalidoException e) {
			List<String> mensagensErro = e.getMensagensErro();
			Assert.assertEquals("Sobrenome inválido: campo vazio.", mensagensErro.get(0));
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

		try {
			SistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email);
			Assert.fail("Expected CadastroInvalidoException to be thrown");
		} catch (SistemaCadastro.CadastroInvalidoException e) {
			List<String> mensagensErro = e.getMensagensErro();
			Assert.assertEquals("CEP inválido: caracteres inválidos.", mensagensErro.get(0));
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void teste06CadastroUsuarioExistente() {
	    String nome = "Roberto";
	    String sobrenome = "Silveira";
	    String cidade = "Santa Cruz do Sul";
	    String cep = "61701-292";
	    String endereco = "Viela Vitor Silva";
	    String idade = "28";
	    String email = "antonio.caseira@gmail.com";

	    // Cria um mock do CadastroExistenteVerifier
	    CadastroExistenteVerifier cadastroExistenteVerifier = Mockito.mock(CadastroExistenteVerifier.class);
	    
	    // Define o comportamento do mock para retornar true, indicando que o usuário já está cadastrado
	    Mockito.when(cadastroExistenteVerifier.verificarCadastroExistente(email)).thenReturn(true);

	    // Cria uma instância de SistemaCadastro usando o Builder
	    SistemaCadastro sistemaCadastro = SistemaCadastro.builder()
	            .cadastroExistenteVerifier(cadastroExistenteVerifier)
	            .build();

	    // Executa o teste e captura a saída do console
	    ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
	    PrintStream originalOut = System.out;
	    System.setOut(new PrintStream(consoleOutput));
	    try {
	        sistemaCadastro.cadastrarUsuario(email);
	    } catch (CadastroExistenteException e) {
	        // Verifica se a mensagem de erro é exibida corretamente
	        Assert.assertEquals("Cliente já está cadastrado no sistema.", e.getMessage());
	    }
	    System.setOut(originalOut);
	}
}