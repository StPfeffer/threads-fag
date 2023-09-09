package br.com.pfeffer.banco;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccountMultiThreaded {
    private double saldo;
    private final Lock lock = new ReentrantLock();

    public BankAccountMultiThreaded(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public void transferir(BankAccountMultiThreaded contaDestino, double quantia) {
        lock.lock();

        try {
            if (this.saldo >= quantia) {
                this.saldo -= quantia;
                contaDestino.depositar(quantia);
            }
        } finally {
            lock.unlock();
        }
    }

    public void depositar(double quantia) {
        lock.lock();

        try {
            this.saldo += quantia;
        } finally {
            lock.unlock();
        }
    }

    public double getSaldo() {
        return saldo;
    }

}
