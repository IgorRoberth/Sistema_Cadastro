package mockitoutils;

import org.mockito.Mockito;
import sistema.SistemaCadastro.CadastroExistenteVerifier;

public class MockitoUtils {
  
	public static CadastroExistenteVerifier createMockCadastroExistenteVerifier() {
	return (CadastroExistenteVerifier) Mockito.mock(CadastroExistenteVerifier.class);
    }
}