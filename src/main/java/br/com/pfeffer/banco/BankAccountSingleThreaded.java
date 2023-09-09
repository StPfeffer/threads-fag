package br.com.pfeffer.banco;

public class BankAccountSingleThreaded {
    private double saldo;

    public BankAccountSingleThreaded(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public void transferir(BankAccountSingleThreaded conta, double quantia) {
        if (this.saldo >= quantia) {
            this.saldo -= quantia;
            conta.depositar(quantia);
        }
    }

    public void depositar(double quantia) {
        this.saldo += quantia;
    }

    public double getSaldo() {
        return saldo;
    }

}