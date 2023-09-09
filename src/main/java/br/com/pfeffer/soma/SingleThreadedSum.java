package br.com.pfeffer.soma;

public class SingleThreadedSum {

    public static long calculateSum(long[] numeros) {
        long soma = 0;

        for (long numero : numeros) {
            soma += numero;
        }

        return soma;
    }

}