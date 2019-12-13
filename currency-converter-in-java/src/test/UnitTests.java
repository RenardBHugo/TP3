import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;

class UnitTests {
	//array de currency
	private ArrayList<Currency> currencies = Currency.init();
	//array de currency vide
	private ArrayList<Currency> currencyVide = new ArrayList<Currency>();
	@Test
	void CurrencyTestBlackBox() {
		//assertEquals(0.0, Currency.convert(-1.0, 3.291));
		// Test l'arrondissement
		assertEquals(15.23, Currency.convert(10.0, 1.5234));
		assertEquals(15.24, Currency.convert(10.0, 1.5235));
	}
	
	@Test
	void mainWindowTestBlackBox() {
		double epsilon = 0.1;
		double amount  = 100000.0;
		
		// Test toutes les valeurs d'échange entre elles (ex: USD -> EUR =?= EUR <- USD)
		for (int i = 0; i < currencies.size(); i++) {
			for (int j = 0; j < currencies.size(); j++) {
				if (j != i) {
					double tmp = MainWindow.convert(currencies.get(i).getName(), currencies.get(j).getName(), Currency.init(), amount);
					tmp = MainWindow.convert(currencies.get(j).getName(), currencies.get(i).getName(), Currency.init(), tmp);
					assertTrue(Math.abs(amount - tmp) / amount < epsilon);
				}
			}
		}
		
		// Test avec des devises qui n'existent pas
		assertEquals(0.0, MainWindow.convert(currencies.get(1).getName(), "Canadian Dollar", currencies, 1.0));
		assertEquals(0.0, MainWindow.convert("Canadian Dollar", currencies.get(1).getName(), currencies, 1.0));
		assertEquals(0.0, MainWindow.convert("Canadian Dollar", "Canadian Dollar", currencies, 1.0));
		//assertEquals(0.0, MainWindow.convert(currencies.get(0).getName(), currencies.get(1).getName(), currencies, -1.0));
	}
	
	@Test 
	void mainWindowTestB() {
		assertEquals(0.0, MainWindow.convert("Test", "Test2", new ArrayList<Currency>(), 10.0));
		assertEquals(0.0, MainWindow.convert("Test", "Test2", Currency.init(), 10.0));
		assertEquals(0.0, MainWindow.convert(Currency.init().get(2).getName(), "Test", Currency.init(), 10.0));
		assertEquals(15.2, MainWindow.convert(Currency.init().get(2).getName(), Currency.init().get(3).getName(), Currency.init(), 10.0));
	}
	void mainWindowTestC() {


		ArrayList<Currency> currencyNomVide = new ArrayList<Currency>();
		currencyNomVide.add( new Currency("US Dollar", null) );

		//cas 1 : Currency 2 et currency 1 premiere monnaie du tableau(donc egales), shortNameCurrency 2 != null
		assertEquals(1.0,MainWindow.convert("US Dollar", "US Dollar", currencies, 1.0));
		//cas 2 : Currency 2 et currency 1 premiere monnaie du tableau(donc egales), shortNameCurrency 2 == null
		assertEquals(0.0,MainWindow.convert("US Dollar", "US Dollar", currencyNomVide, 1.0));
		//cas 3-4 : currencies.size() == 0
		assertEquals(0.0,MainWindow.convert("US Dollar", "US Dollar", currencyVide, 1.0));
		//cas 5-6 impossibles a tester.
	}
	@Test
	void mainWindowTestD() {
		//cas 1 : tout est vrai
		assertEquals(1.0,MainWindow.convert("US Dollar", "US Dollar", currencies, 1.0));
		//cas 2: devise 2 n'existe pas
		assertEquals(0.0,MainWindow.convert("US Dollar", "Boing", currencies, 1.0));
		//cas 3: devise 3 1 n'existe pas
		assertEquals(0.0,MainWindow.convert("Boing", "US Dollar", currencies, 1.0));
		//cas 4 : currencies.size() == 0
		assertEquals(0.0,MainWindow.convert("US Dollar", "US Dollar", currencyVide, 1.0));
	}
}
