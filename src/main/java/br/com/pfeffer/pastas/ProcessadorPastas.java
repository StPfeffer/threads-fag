package br.com.pfeffer.pastas;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ProcessadorPastas extends RecursiveTask<List<String>> {

    private final String diretorio;
    private final String extensao;

    public ProcessadorPastas(String diretorio, String extension) {
        this.diretorio = diretorio;
        this.extensao = extension;
    }

    @Override
    protected List<String> compute() {
        List<String> lista = new ArrayList<>();
        List<ProcessadorPastas> tarefas = new ArrayList<>();

        processarDiretorio(new File(diretorio), lista, tarefas);

        if (tarefas.size() > 50) {
            System.out.printf("%s: %d tarefas executando.\n", diretorio, tarefas.size());
        }

        adicionarResultadosDaTarefa(lista, tarefas);
        return lista;
    }

    private void processarDiretorio(File directory, List<String> lista, List<ProcessadorPastas> tarefas) {
        File[] conteudo = directory.listFiles();

        if (conteudo != null) {
            for (File file : conteudo) {
                if (file.isDirectory()) {
                    ProcessadorPastas tarefa = new ProcessadorPastas(file.getAbsolutePath(), extensao);
                    tarefa.fork();
                    tarefas.add(tarefa);
                } else if (verificaArquivo(file.getName())) {
                    lista.add(file.getAbsolutePath());
                }
            }
        }
    }

    private void adicionarResultadosDaTarefa(List<String> lista, List<ProcessadorPastas> tarefas) {
        for (ProcessadorPastas task : tarefas) {
            lista.addAll(task.join());
        }
    }

    private boolean verificaArquivo(String nome) {
        return nome.endsWith(extensao);
    }

}
