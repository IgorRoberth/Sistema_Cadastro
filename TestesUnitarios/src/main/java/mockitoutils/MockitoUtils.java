package mockitoutils;

import org.mockito.Mockito;

import cadastrocliente.VerificarCadastro;

public class MockitoUtils {

    public static VerificarCadastro criarVerificacaoDeCadastro() {
        return Mockito.mock(VerificarCadastro.class);
    }
}