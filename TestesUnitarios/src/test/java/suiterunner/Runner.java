package suiterunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import loginsenhatest.LoginSenhaTest;
import sistemacadastrotest.SistemaCadastroTest;

@RunWith(Suite.class)
@SuiteClasses({

	SistemaCadastroTest.class,
	LoginSenhaTest.class
})
public class Runner {
}