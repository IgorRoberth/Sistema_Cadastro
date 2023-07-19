package verificarcadastromock;

import cadastrocliente.VerificarCadastro;

public class VerificarCadastroMock implements VerificarCadastro {
    private boolean cadastroExistente;

    @Override
    public boolean verificarCadastroExistente(String email) {
        return cadastroExistente;
    }

    @Override
    public void setCadastroExistente(boolean cadastroExistente) {
        this.cadastroExistente = cadastroExistente;
    }
}