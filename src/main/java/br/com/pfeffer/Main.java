package br.com.pfeffer;

import br.com.pfeffer.banco.BankAccountMultiThreaded;
import br.com.pfeffer.banco.BankAccountSingleThreaded;
import br.com.pfeffer.pastas.ForkJoinMain;
import br.com.pfeffer.produto.Cliente;
import br.com.pfeffer.produto.Estoque;
import br.com.pfeffer.produto.Produto;
import br.com.pfeffer.produto.Vendedor;
import br.com.pfeffer.soma.MultithreadedSum;
import br.com.pfeffer.soma.SingleThreadedSum;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Soma Total\n");
//        somaTotal();

//        System.out.println("\nProcessador de Pastas\n");
//        ForkJoinMain.execute();

        System.out.println("\nProduto\n");
        produto();
    }

    private static void somaTotal() {
        long[] array = new long[1450000000];

        for (int i = 0; i < 10000000; i++) {
            array[i] = i + 1;
        }

        int qtdeThreads = 4;

        long inico = System.currentTimeMillis();
        long somaTotal = MultithreadedSum.calcularTotalComMultithreading(array, qtdeThreads);
        long fim = System.currentTimeMillis();

        System.out.println("Soma total: " + somaTotal);
        System.out.println("Tempo gasto com multithreading (" + qtdeThreads + " threads): " + (fim - inico) + " milissegundos");

        inico = System.currentTimeMillis();
        somaTotal = SingleThreadedSum.calculateSum(array);
        fim = System.currentTimeMillis();

        System.out.println("\nSoma total: " + somaTotal);
        System.out.println("Tempo gasto sem multithreading: " + (fim - inico) + " milissegundos");
    }

    private static void transferenciaBancaria() {
        System.out.println("Transferencia SingleThreaded");
        BankAccountSingleThreaded contaSingleThread1 = new BankAccountSingleThreaded(1000);
        BankAccountSingleThreaded contaSingleThread2 = new BankAccountSingleThreaded(500);

        contaSingleThread1.transferir(contaSingleThread2, 200);

        System.out.println("Saldo conta 1: " + contaSingleThread1.getSaldo());
        System.out.println("Saldo conta 2: " + contaSingleThread2.getSaldo());

        System.out.println("\nTransferencia MultiThreaded");
        BankAccountMultiThreaded contaMultiThread1 = new BankAccountMultiThreaded(1000);
        BankAccountMultiThreaded contaMultiThread2 = new BankAccountMultiThreaded(500);

        Thread thread1 = new Thread(() -> contaMultiThread1.transferir(contaMultiThread2, 200));
        Thread thread2 = new Thread(() -> contaMultiThread2.transferir(contaMultiThread1, 100));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Saldo conta 1: " + contaMultiThread1.getSaldo());
        System.out.println("Saldo conta 2: " + contaMultiThread2.getSaldo());
    }

    private static void produto() {
        Estoque estoque = new Estoque();
        estoque.adicionarProduto(new Produto("Camiseta", 1));
        estoque.adicionarProduto(new Produto("Calça", 30));
        estoque.adicionarProduto(new Produto("Tênis", 20));
        estoque.adicionarProduto(new Produto("Bermuda", 15));

        Cliente comprador1 = new Cliente(estoque, "Camiseta", 10);
        Cliente comprador2 = new Cliente(estoque, "Calça", 5);
        Cliente comprador3 = new Cliente(estoque, "Tênis", 4);
        Cliente comprador4 = new Cliente(estoque, "Bermuda", 7);

        Vendedor vendedor1 = new Vendedor(estoque, "Camiseta", 5);
        Vendedor vendedor2 = new Vendedor(estoque, "Calça", 10);
        Vendedor vendedor3 = new Vendedor(estoque, "Tênis", 7);
        Vendedor vendedor4 = new Vendedor(estoque, "Bermuda", 3);

        comprador1.start();
        comprador2.start();
        comprador3.start();
        comprador4.start();
        vendedor1.start();
        vendedor2.start();
        vendedor3.start();
        vendedor4.start();
    }

}