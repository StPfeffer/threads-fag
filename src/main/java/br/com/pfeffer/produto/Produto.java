package br.com.pfeffer.produto;

public class Produto {
    private final String nome;
    private int quantidade;

    public Produto(String nome, int quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public synchronized int comprar(int quantidadeComprar) {
        if (quantidade >= quantidadeComprar) {
            quantidade -= quantidadeComprar;

            return quantidadeComprar;
        } else {
            int quantidadeDisponivel = quantidade;
            quantidade = 0;

            return quantidadeDisponivel;
        }
    }

    public synchronized void vender(int quantidadeVender) {
        quantidade += quantidadeVender;
    }

    public String getNome() {
        return nome;
    }

}
