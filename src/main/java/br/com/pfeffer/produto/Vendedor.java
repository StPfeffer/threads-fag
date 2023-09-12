package br.com.pfeffer.produto;

public class Vendedor extends Thread {
    private final Estoque estoque;
    private final String produtoNome;
    private final int quantidadeVender;

    public Vendedor(Estoque estoque, String produtoNome, int quantidadeVender) {
        this.estoque = estoque;
        this.produtoNome = produtoNome;
        this.quantidadeVender = quantidadeVender;
    }

    @Override
    public void run() {
        Produto produto = estoque.getProduto(produtoNome);

        if (produto != null) {
            produto.vender(quantidadeVender);
            System.out.println(getClass().getSimpleName() + '-' + produtoNome + " vendeu " + quantidadeVender + " unidades de " + produtoNome);
        } else {
            System.out.println("Produto não encontrado: " + produtoNome);
        }
    }

}