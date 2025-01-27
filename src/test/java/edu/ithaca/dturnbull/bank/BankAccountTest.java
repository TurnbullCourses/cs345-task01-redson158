package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);

        BankAccount testAccount = new BankAccount("rje@gmail.com", 500);
        assertEquals(500, testAccount.getBalance(), 0.001); // Equivalence Class: valid account, Border case: No
        testAccount.withdraw(250);
        assertEquals(250,  testAccount.getBalance(), 0.001); // Equivalence Class: valid account, Border case: No
        testAccount.withdraw(250);
        assertEquals(0,  testAccount.getBalance(), 0.001); // Equivalence Class: valid account, Border case: Yes

        BankAccount testAccount2 = new BankAccount("a@b.com", 0);
        assertEquals(0, testAccount2.getBalance(), 0.001); // Equivalence Class: valid account, Border case: Yes
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        BankAccount testAccount = new BankAccount("rje@gmail.com", 500);
        assertThrows(IllegalArgumentException.class, () -> testAccount.withdraw(-300)); //equivalence - negative amount
        assertThrows(InsufficientFundsException.class, () -> testAccount.withdraw(600)); //equivalence - not enough funds
        testAccount.withdraw(0);
        assertEquals(500, testAccount.getBalance(), 0.001); //Border case - zero amount
        testAccount.withdraw(1);
        assertEquals(499, testAccount.getBalance(), 0.001); //Border case - just above zero
        testAccount.withdraw(249);
        assertEquals(250, testAccount.getBalance(), 0.001); //Border case - middle of range
        testAccount.withdraw(249);
        assertEquals(1, testAccount.getBalance(), 0.001); //Border case - almost all funds
        testAccount.withdraw(1);
        assertEquals(0, testAccount.getBalance(), 0.001); //Border case - all funds
    }

    @Test
    void isEmailValidTest(){
        // valid email address
        assertTrue(BankAccount.isEmailValid("a@b.com"));   // Equivalence Class: valid email, Border case: No, valid format

        // empty string
        assertFalse(BankAccount.isEmailValid(""));         // Equivalence Class: invalid email (empty), Border case: Yes

        // invalid email, starts with invalid character
        assertFalse(BankAccount.isEmailValid("_a@gmail.com"));  // Equivalence Class: invalid email (starting with underscore), Border case: No

        // invalid email, domain missing after '@'
        assertFalse(BankAccount.isEmailValid("a@.com"));      // Equivalence Class: invalid email (missing domain), Border case: Yes

        // invalid email, missing '@' symbol
        assertFalse(BankAccount.isEmailValid("a@gmail"));     // Equivalence Class: invalid email (missing '@' symbol), Border case: No

        // valid email address from a university domain
        assertTrue(BankAccount.isEmailValid("redson@ithaca.edu"));  // Equivalence Class: valid email, Border case: No, valid format

        // invalid email, missing local part before '@'
        assertFalse(BankAccount.isEmailValid("@gmail.com"));  // Equivalence Class: invalid email (missing local part), Border case: Yes

        // invalid email, contains space
        assertFalse(BankAccount.isEmailValid("a b@gmail.com"));  // Equivalence Class: invalid email (contains space), Border case: No

        // invalid email, missing '@'
        assertFalse(BankAccount.isEmailValid("agmail.com"));  // Equivalence Class: invalid email (missing '@'), Border case: Yes
        
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}