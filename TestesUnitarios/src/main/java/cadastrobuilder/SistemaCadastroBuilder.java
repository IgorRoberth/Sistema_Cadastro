package cadastrobuilder;

import cadastrocliente.VerificarCadastro;
import sistema.SistemaCadastro;

public class SistemaCadastroBuilder {

    private final SistemaCadastro sistemaCadastro;
    private VerificarCadastro verificarCadastroExistente;

    private SistemaCadastroBuilder() {
        sistemaCadastro = new SistemaCadastro();
    }

    public static SistemaCadastroBuilder builder() {
        return new SistemaCadastroBuilder();
    }

    public SistemaCadastroBuilder verificarCadastroExistente(VerificarCadastro cadastroExistente) {
        this.verificarCadastroExistente = cadastroExistente;
        return this;
    }

    public SistemaCadastroBuilder consoleOutputString(String consoleOutputString) {
        sistemaCadastro.setConsoleOutputString(consoleOutputString);
        return this;
    }

    public SistemaCadastro build() {
        sistemaCadastro.setVerificarCadastro(verificarCadastroExistente);
        return sistemaCadastro;
    }
}