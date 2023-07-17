package testesunit;

import java.util.Locale;

import org.junit.Test;

import com.github.javafaker.Faker;

public class GeradorDeDados {
	
    @Test
	public void geradorDadosFake() {

	    Faker faker = new Faker(new Locale("pt-BR-SP"));
		String firstName = faker.address().firstName();
		String lastName = faker.address().lastName();
		String cep = faker.address().zipCode();
		String cidade = faker.address().city();
		String streetName = faker.address().streetName();
		String email = faker.internet().emailAddress();
		int idade = faker.number().numberBetween(18, 65);

		System.out.println(" Nome: " + firstName + "\n Sobrenome: " + lastName + "\n Logradouro: " + streetName
				+ "\n Cidade: " + cidade + "\n Cep: " + cep + " \nE-mail " + email + " \nIdade: " + idade);

	}
}