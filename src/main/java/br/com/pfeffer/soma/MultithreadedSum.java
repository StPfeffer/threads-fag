package br.com.pfeffer.soma;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultithreadedSum {

    public static long calcularSomaParcial(long[] numeros, int inicio, int fim) {
        long somaParcial = 0;

        for (int i = inicio; i < fim; i++) {
            somaParcial += numeros[i];
        }

        return somaParcial;
    }

    public static long calcularTotalComMultithreading(long[] numeros, int qtdeThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(qtdeThreads);

        int chunk = numeros.length / qtdeThreads;
        long[] somasParciais = new long[qtdeThreads];

        for (int i = 0; i < qtdeThreads; i++) {
            final int threadIndex = i;

            executor.submit(() -> {
                int inicio = threadIndex * chunk;
                int fim = (threadIndex == qtdeThreads - 1) ? numeros.length : (threadIndex + 1) * chunk;
                somasParciais[threadIndex] = calcularSomaParcial(numeros, inicio, fim);
            });
        }

        executor.shutdown();

        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long somaTotal = 0;

        for (long somaParcial : somasParciais) {
            somaTotal += somaParcial;
        }

        return somaTotal;
    }

}