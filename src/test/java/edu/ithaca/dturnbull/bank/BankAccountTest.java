package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);

        BankAccount testAccount = new BankAccount("rje@gmail.com", 500);
        assertEquals(500, testAccount.getBalance(), 0.001); // Equivalence Class: valid account, Border case: No
        testAccount.withdraw(250);
        assertEquals(250,  testAccount.getBalance(), 0.001); // Equivalence Class: valid account, Border case: No
        testAccount.withdraw(250);
        assertEquals(0,  testAccount.getBalance(), 0.001); // Equivalence Class: valid account, Border case: zero balance

        BankAccount testAccount2 = new BankAccount("a@b.com", 0);
        assertEquals(0, testAccount2.getBalance(), 0.001); // Equivalence Class: valid account, Border case: zero balance
    }

    @Test
    void getEmailTest() {
        BankAccount bankAccount = new BankAccount("rje@gmail.com", 200);
        assertEquals("rje@gmail.com", bankAccount.getEmail());
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
        testAccount.withdraw(249.90);
        assertEquals(249.10, testAccount.getBalance(), 0.001); //equivalence - middle value
        testAccount.withdraw(248.10);
        assertEquals(1, testAccount.getBalance(), 0.001); //Border case - almost all funds
        testAccount.withdraw(1);
        assertEquals(0, testAccount.getBalance(), 0.001); //Border case - all funds
        assertThrows(IllegalArgumentException.class, () -> testAccount.withdraw(0.001)); //equivalence - less than 0.01
        assertThrows(IllegalArgumentException.class, () -> testAccount.withdraw(4.955)); //equivalence - more than 2 decimal places

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
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("rje@gmail.com", -100)); // Equivalence Class: invalid balance, Border case: negative
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("rje@gmail.com", 0.001)); // Equivalence Class: invalid balance, Border case: less than 0.01
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("rje@gmail.com", 4.955)); // Equivalence Class: invalid balance, Border case: more than 2 decimal places
    }

    @Test
    void isAmountValidTest(){
        assertTrue(BankAccount.isAmountValid(0)); // Equivalence Class: valid amount, Border case: zero
        assertTrue(BankAccount.isAmountValid(0.01)); // Equivalence Class: valid amount, Border case: positive
        assertTrue(BankAccount.isAmountValid(100.99)); // Equivalence Class: valid amount, Border case: large positive
        assertFalse(BankAccount.isAmountValid(-0.01)); // Equivalence Class: invalid amount, Border case: negative
        assertFalse(BankAccount.isAmountValid(0.001)); // Equivalence Class: invalid amount, Border case: sless than 0.01
        assertFalse(BankAccount.isAmountValid(4.955)); // Equivalence Class: invalid amount, Border case: more than 2 decimal places
    }

    @Test
    void depositTest(){
        BankAccount bankAccount = new BankAccount("rje@gmail.com", 0);
        assertEquals(0, bankAccount.getBalance(), 0.001); 
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-100)); //equivalence - negative amount
        bankAccount.deposit(0);
        assertEquals(0, bankAccount.getBalance(), 0.001); //Border case - zero amount
        bankAccount.deposit(10.99);
        assertEquals(10.99, bankAccount.getBalance(), 0.001); //equivalence - positive amount
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0.001)); //equivalence - less than 0.01
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(4.955)); //equivalence - more than 2 decimal places
        bankAccount.deposit(100.01);
        assertEquals(111, bankAccount.getBalance(), 0.001); //Border case - large amount
    }

    @Test
    void transferTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("rje@gmail.com", 100);
        BankAccount bankAccount2 = new BankAccount("redson@ithaca.edu", 200);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.transfer(bankAccount2, 200)); //equivalence - not enough funds
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.transfer(bankAccount, 400)); //equivalence - not enough funds
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount2, -100)); //equivalence - negative amount
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount2, 0.001)); //equivalence - less than 0.01
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount2, 4.955)); //equivalence - more than 2 decimal places
        bankAccount.transfer(bankAccount2, 50);
        assertEquals(50, bankAccount.getBalance(), 0.001); //equivalence - positive amount
        assertEquals(250, bankAccount2.getBalance(), 0.001); //equivalence - positive amount
        bankAccount.transfer(bankAccount2, 50);
        assertEquals(0, bankAccount.getBalance(), 0.001); //Border case - zero balance
        assertEquals(300, bankAccount2.getBalance(), 0.001); //equivalence - positive amount
        bankAccount.transfer(bankAccount2, 0);
        assertEquals(0, bankAccount.getBalance(), 0.001); //Border case - zero amount
        assertEquals(300, bankAccount2.getBalance(), 0.001); //equivalence - positive amount
        bankAccount2.transfer(bankAccount, 150.95);
        assertEquals(150.95, bankAccount.getBalance(), 0.001); //equivalence - positive amount
        assertEquals(149.05, bankAccount2.getBalance(), 0.001); //equivalence - positive amount
    }
}