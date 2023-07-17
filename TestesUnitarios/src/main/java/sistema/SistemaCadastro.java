package sistema;

import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class SistemaCadastro {

    private String consoleOutputString = "Cadastro realizado com sucesso";
    private List<String> usuariosCadastrados = new ArrayList<>();
    private CadastroExistenteVerifier cadastroExistenteVerifier;
    private String ultimaMensagemEnvio;

    public SistemaCadastro() {
    }

    public void setCadastroExistenteVerifier(CadastroExistenteVerifier cadastroExistenteVerifier) {
        this.cadastroExistenteVerifier = cadastroExistenteVerifier;
    }

    public void cadastrarUsuario(String email) throws CadastroExistenteException {
        if (cadastroExistenteVerifier != null && cadastroExistenteVerifier.verificarCadastroExistente(email)) {
            throw new CadastroExistenteException("Cliente já está cadastrado no sistema.");
        }
        usuariosCadastrados.add(email);
        consoleOutputString = "Cadastro realizado com sucesso";
        enviarEmailConfirmacao(email);
    }

    private void enviarEmailConfirmacao(String email) {
        ultimaMensagemEnvio = "E-mail de confirmação enviado para " + email;
        System.out.println(ultimaMensagemEnvio);
    }

    public String getUltimaMensagemEnvio() {
        return ultimaMensagemEnvio;
    }

    public String getConsoleOutputString() {
        return consoleOutputString;
    }
    
    public void setConsoleOutputString(String consoleOutputString) {
        this.consoleOutputString = consoleOutputString;
    }

    public static void validarCadastro(String nome, String sobrenome, String cidade, String cep, String endereco,
                                String idade, String email) throws CadastroInvalidoException {
        List<String> mensagensErro = new ArrayList<>();

        validarCampo(nome, "Nome", mensagensErro);
        validarCampo(sobrenome, "Sobrenome", mensagensErro);
        validarCampo(cidade, "Cidade", mensagensErro);
        validarCampo(cep, "CEP", mensagensErro);
        validarCampo(endereco, "Endereço", mensagensErro);
        validarCampo(idade, "Idade", mensagensErro);
        validarCampo(email, "E-mail", mensagensErro);

        if (!mensagensErro.isEmpty()) {
            throw new CadastroInvalidoException(mensagensErro);
        }
    }

    public static void validarCampo(String campo, String nomeCampo, List<String> mensagensErro) {
        if (campo.trim().isEmpty()) {
            mensagensErro.add(nomeCampo + " inválido: campo vazio.");
        } else {
            switch (nomeCampo) {
                case "Nome":
                case "Sobrenome":
                case "Cidade":
                case "Endereço":
                    if (!campo.matches("[\\p{L}\\s]+")) {
                        mensagensErro.add(nomeCampo + " inválido: caracteres inválidos.");
                    }
                    break;
                case "CEP":
                case "Idade":
                    if (!campo.matches("\\d+")) {
                        mensagensErro.add(nomeCampo + " inválido: caracteres inválidos.");
                    }
                    break;
                case "E-mail":
                    if (!campo.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]+")) {
                        mensagensErro.add(nomeCampo + " inválido: formato inválido.");
                    }
                    break;
            }
        }
    }

    public void exibirCadastro(String nome, String sobrenome, String cidade, String cep, String endereco,
                               String idade, String email) {
        System.out.println("Nome: " + nome);
        System.out.println("Sobrenome: " + sobrenome);
        System.out.println("Cidade: " + cidade);
        System.out.println("CEP: " + cep);
        System.out.println("Endereço: " + endereco);
        System.out.println("Idade: " + idade);
        System.out.println("E-mail: " + email);
    }

    public static class SistemaCadastroBuilder {
        private SistemaCadastro sistemaCadastro;
        private CadastroExistenteVerifier cadastroExistenteVerifier;
        private String consoleOutputString;

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

        public SistemaCadastroBuilder consoleOutputString(String consoleOutputString) {
            this.consoleOutputString = consoleOutputString;
            return this;
        }

        public SistemaCadastro build() {
            sistemaCadastro.setCadastroExistenteVerifier(cadastroExistenteVerifier);
            sistemaCadastro.setConsoleOutputString(consoleOutputString);
            return sistemaCadastro;
        }
    }

    public interface CadastroExistenteVerifier {
        boolean verificarCadastroExistente(String email);
    }

    @SuppressWarnings("serial")
    public static class CadastroExistenteException extends Exception {
        public CadastroExistenteException(String message) {
            super(message);
        }
    }

    @SuppressWarnings("serial")
    public static class CadastroInvalidoException extends Exception {
        private List<String> mensagensErro;

        public CadastroInvalidoException(List<String> mensagensErro) {
            this.mensagensErro = mensagensErro;
        }

        public List<String> getMensagensErro() {
            return mensagensErro;
        }
    }

    public static void main(String[] args) {
        String nome = "Olívia";
        String sobrenome = "Cachoeira";
        String cidade = "Crucilândia";
        String cep = "40072562";
        String endereco = "Ponte Francisca Martins";
        String idade = "22";
        String email = "oliviafranc@hotmail.com";

        CadastroExistenteVerifier cadastroExistenteVerifier = Mockito.mock(CadastroExistenteVerifier.class);
        Mockito.when(cadastroExistenteVerifier.verificarCadastroExistente(email)).thenReturn(true);

        SistemaCadastro sistemaCadastro = SistemaCadastro.SistemaCadastroBuilder.builder()
                .cadastroExistenteVerifier(cadastroExistenteVerifier)
                .build();

        try {
            sistemaCadastro.validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email);
            sistemaCadastro.exibirCadastro(nome, sobrenome, cidade, cep, endereco, idade, email);
            System.out.println(sistemaCadastro.getConsoleOutputString());
        } catch (CadastroInvalidoException e) {
            for (String mensagemErro : e.getMensagensErro()) {
                System.out.println(mensagemErro);
            }
        }
    }
}