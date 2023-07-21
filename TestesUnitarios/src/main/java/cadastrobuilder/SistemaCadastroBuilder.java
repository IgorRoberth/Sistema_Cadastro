package cadastrobuilder;

import cadastrocliente.VerificarCadastro;
import enviaremail.EnviarEmail;
import sistema.SistemaCadastro;

public class SistemaCadastroBuilder {

	private final SistemaCadastro sistemaCadastro;
	private VerificarCadastro verificarCadastroExistente;

	private SistemaCadastroBuilder(EnviarEmail enviarEmail) {
		sistemaCadastro = new SistemaCadastro(enviarEmail);
	}

	public static SistemaCadastroBuilder builder(EnviarEmail enviarEmail) {
		return new SistemaCadastroBuilder(enviarEmail);
	}

	public SistemaCadastroBuilder verificarCadastroExistente(VerificarCadastro cadastroExistente) {
		this.verificarCadastroExistente = cadastroExistente;
		return this;
	}

	public SistemaCadastroBuilder MensagemCadastroConcluido(String MensagemCadastroConcluido) {
		sistemaCadastro.setCadastroRealizado(MensagemCadastroConcluido);
		return this;
	}

	public SistemaCadastro build() {
		sistemaCadastro.setVerificarCadastro(verificarCadastroExistente);
		return sistemaCadastro;
	}
}