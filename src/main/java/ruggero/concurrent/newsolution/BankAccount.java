package ruggero.concurrent.newsolution;

import java.util.UUID;

public class BankAccount {

    private static final double INITIAL_ACCOUNT = 1000;

    private final UUID uuid;

    private double balance;

    public BankAccount() {
        this.uuid = UUID.randomUUID();
        balance = INITIAL_ACCOUNT;
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
