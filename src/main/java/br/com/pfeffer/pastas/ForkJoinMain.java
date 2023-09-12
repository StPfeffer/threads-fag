package br.com.pfeffer.pastas;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinMain {

    public static void execute() {
        ProcessadorPastas sistema = new ProcessadorPastas("C:/Windows", ".exe");
        ProcessadorPastas aplicativos = new ProcessadorPastas("C:/Program Files", ".exe");
        ProcessadorPastas documentos = new ProcessadorPastas("C:/users", ".doc");

        ForkJoinPool pool = new ForkJoinPool();

        pool.execute(sistema);
        pool.execute(aplicativos);
        pool.execute(documentos);

        monitorDeThreads(pool);

        pool.shutdown();

        exibirResultados("Sistema", sistema);
        exibirResultados("Aplicativos", aplicativos);
        exibirResultados("Documentos", documentos);
    }

    private static void monitorDeThreads(ForkJoinPool pool) {
        do {
            System.out.println("----------------------------------------");
            System.out.printf("-> Paralelismo: %d\n", pool.getParallelism());
            System.out.printf("-> Threads Ativas: %d\n", pool.getActiveThreadCount());
            System.out.printf("-> Tarefas: %d\n", pool.getQueuedTaskCount());
            System.out.printf("-> Roubos: %d\n", pool.getStealCount());
            System.out.println("----------------------------------------");

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!pool.isQuiescent());

    }

    private static void exibirResultados(String category, ProcessadorPastas processor) {
        List<String> resultados = processor.join();
        System.out.printf("%s: %d encontrados.\n", category, resultados.size());
    }

}
