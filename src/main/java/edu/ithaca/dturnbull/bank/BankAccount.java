package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email or starting balance is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email) && isAmountValid(startingBalance)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            if(!isAmountValid(startingBalance)){
                throw new IllegalArgumentException("Amount: " + startingBalance + " is invalid, cannot create account");
            }else{
                throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
            }
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if amount is greater than balance
     * @throws IllegalArgumentException if amount is negative or invalid
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if(isAmountValid(amount)){
            if(amount <= balance){
                balance -= amount;
            }else{
                throw new InsufficientFundsException("Not enough money");
            }
        }else{
            throw new IllegalArgumentException("Amount: " + amount + " is invalid, cannot withdraw");
        }
    }


    public static boolean isEmailValid(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        int atPosition = email.indexOf('@');
        int dotPosition = email.lastIndexOf('.');

        // Checks if '@' exists and is not the first or last character
        if (atPosition <= 0 || atPosition >= email.length() - 1) {
            return false;
        }

        // Checks if '.' exists after '@' and is not the last character
        if (dotPosition < atPosition || dotPosition >= email.length() - 1) {
            return false;
        }

        // checks part that starts with a valid character
        char firstChar = email.charAt(0);
        if (!Character.isLetterOrDigit(firstChar)) {
            return false;
        }

        // checks no spaces exist in the email
        if (email.contains(" ")) {
            return false;
        }

        // checks "@" is followed by a valid domain
        if (email.charAt(atPosition + 1) == '.' || dotPosition == atPosition + 1) {
            return false;
        }

        // checks no consecutive dots exist
        if (email.contains("..")) {
            return false;
        }

        return true;
    }

    /**
    *returns true if amount is positive and has no more than 2 decimal places
    */
    public static boolean isAmountValid(double amount){
        if (amount < 0){
            return false;
        }else{
            String amountString = Double.toString(amount);
            int decimalIndex = amountString.indexOf('.');
            if (decimalIndex == -1){
                return true;
            }else{
                return amountString.length() - decimalIndex <= 3;
            }
        }
    }

    /**
     * @post increases the balance by amount
     * @throws IllegalArgumentException if amount is negative or invalid
     */
    public void deposit(double amount){
        if(isAmountValid(amount)){
            balance += amount;
        }else{
            throw new IllegalArgumentException("Amount: " + amount + " is invalid, cannot deposit");
        }
    }

    /**
     * @post transfers amount from this account to other account
     * @throws InsufficientFundsException if amount is greater than balance
     * @throws IllegalArgumentException if amount is negative or invalid
     * @throws IllegalArgumentException if other is null
     * @throws IllegalArgumentException if other is the same account
     */
    public void transfer(BankAccount other, double amount) throws InsufficientFundsException{
        if(other == null){
            throw new IllegalArgumentException("No Other Account, cannot transfer");
        }else if(email == other.getEmail()){
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }else{
            if(isAmountValid(amount)){
                if(amount <= balance){
                    balance -= amount;
                    other.deposit(amount);
                }else{
                    throw new InsufficientFundsException("Not enough money");
                }
            }else{
                throw new IllegalArgumentException("Amount: " + amount + " is invalid, cannot transfer");
            }
        }
    }
}