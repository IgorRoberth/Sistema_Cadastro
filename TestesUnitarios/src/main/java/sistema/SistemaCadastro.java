package sistema;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

public class SistemaCadastro {

    public static String consoleOutputString = "Cadastro realizado com sucesso";

    public static void main() {
        String nome = "Joyce";
        String sobrenome = "Kelly";
        String cidade = "São Paulo";
        String cep = "1234567";
        String endereco = "Rua principal, 123";
        String idade = "35";
        String email = "joycekelly@gmail.com";

        try {
            validarCadastro(nome, sobrenome, cidade, cep, endereco, idade, email);
            exibirCadastro(nome, sobrenome, cidade, cep, endereco, idade, email);
        } catch (CadastroInvalidoException e) {
            assertTrue(consoleOutputString.contains("Cadastro realizado com sucesso"));
            System.out.println("Cadastro realizado com sucesso" + e.getMessage());
        }
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

    public static void exibirCadastro(String nome, String sobrenome, String cidade, String cep, String endereco,
                                      String idade, String email) {
        System.out.println("Cadastro realizado com sucesso:");
        System.out.println("Nome: " + nome);
        System.out.println("Sobrenome: " + sobrenome);
        System.out.println("Cidade: " + cidade);
        System.out.println("CEP: " + cep);
        System.out.println("Endereço: " + endereco);
        System.out.println("Idade: " + idade);
        System.out.println("E-mail: " + email);
    }

    public static class CadastroInvalidoException extends Exception {
        private List<String> mensagensErro;

        public CadastroInvalidoException(List<String> mensagensErro) {
            this.mensagensErro = mensagensErro;
        }

        public List<String> getMensagensErro() {
            return mensagensErro;
        }
    }
}