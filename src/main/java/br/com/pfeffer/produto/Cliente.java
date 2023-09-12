package br.com.pfeffer.produto;

public class Cliente extends Thread {
    private final Estoque estoque;
    private final String produtoNome;
    private final int quantidadeComprar;

    public Cliente(Estoque estoque, String produtoNome, int quantidadeComprar) {
        this.estoque = estoque;
        this.produtoNome = produtoNome;
        this.quantidadeComprar = quantidadeComprar;
    }

    @Override
    public void run() {
        Produto produto = estoque.getProduto(produtoNome);

        if (produto != null) {
            int quantidadeComprada = produto.comprar(quantidadeComprar);
            System.out.println(getClass().getSimpleName() + '-' + produtoNome + " comprou " + quantidadeComprada + " unidades de " + produtoNome);
        } else {
            System.out.println("Produto não encontrado: " + produtoNome);
        }
    }

}
