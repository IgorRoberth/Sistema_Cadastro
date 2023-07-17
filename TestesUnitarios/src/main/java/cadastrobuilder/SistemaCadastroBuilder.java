package cadastrobuilder;

import sistema.SistemaCadastro;
import sistema.SistemaCadastro.CadastroExistenteVerifier;

public class SistemaCadastroBuilder {
    private SistemaCadastro sistemaCadastro;
    private CadastroExistenteVerifier cadastroExistenteVerifier;

    private SistemaCadastroBuilder() {
        sistemaCadastro = new SistemaCadastro();
    }

    public static SistemaCadastroBuilder builder() {
        return new SistemaCadastroBuilder();
    }

    public SistemaCadastroBuilder cadastroExistenteVerifier(CadastroExistenteVerifier cadastroExistenteVerifier) {
        this.cadastroExistenteVerifier = cadastroExistenteVerifier;
        return this;
    }

    public SistemaCadastro build() {
        sistemaCadastro.setCadastroExistenteVerifier(cadastroExistenteVerifier);
        return sistemaCadastro;
    }
}