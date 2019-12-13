import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;

class UnitTests {
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
		ArrayList<Currency> currencies = Currency.init();
		
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

}
