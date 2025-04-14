package com.univille;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Classe principal para resolver o problema das 8 rainhas.
 */
public class Main {

    /**
     * @param args Argumentos de linha de comando. O primeiro argumento pode ser usado como seed.
     * 
     * Exemplo de uso: java Main 123456789
     * Sem argumentos, uma seed aleatória será gerada e impressa.
     */
    public static void main(String[] args) {
        long seed;
        if (args.length > 0) {
            seed = Long.parseLong(args[0]);
        } else {
            seed = System.currentTimeMillis();
            System.out.println("Seed gerada: " + seed);
        }
        
        // Gerar ordem aleatória das linhas
        List<Integer> linhas = new ArrayList<>();
        for (int i = 0; i < 8; i++) linhas.add(i);
        Collections.shuffle(linhas, new Random(seed));

        List<int[]> solucao = resolver(0, new ArrayList<>(), linhas);
        
        if (solucao != null) {
            imprimirTab(solucao);
        } else {
            System.out.println("Nenhuma solução encontrada. Quase certamente há algo de errado com o código.");
        }
    }

    /**
     * Resolve o problema das 8 rainhas através de backtracking.
     * 
     * @param idxLinha Índice da linha atual a ser processada.
     * @param rainhasPosicionadas Lista de posições já ocupadas pelas rainhas.
     * @param ordemLinhas Ordem aleatória das linhas para posicionar as rainhas.
     * @return Uma lista de posições das rainhas que representam uma solução, ou {@code null} se nenhuma solução for encontrada.
     */
    private static List<int[]> resolver(int idxLinha, List<int[]> rainhasPosicionadas, List<Integer> ordemLinhas) {
        if (idxLinha == 8) {
            return new ArrayList<>(rainhasPosicionadas);
        }

        int linhaAtual = ordemLinhas.get(idxLinha);
        
        for (int col = 0; col < 8; col++) {
            int[] pos = {linhaAtual, col};
            if (posEhValida(pos, rainhasPosicionadas)) {
                rainhasPosicionadas.add(pos);
                List<int[]> solucao = resolver(idxLinha + 1, rainhasPosicionadas, ordemLinhas);
                if (solucao != null) {
                    return solucao;
                }
                rainhasPosicionadas.remove(rainhasPosicionadas.size() - 1);
            }
        }
        
        return null;
    }

    /**
     * Verifica se uma posição é válida para colocar uma rainha.
     * 
     * @param pos Posição a ser verificada, representada como um array de dois inteiros: [linha, coluna].
     * @param rainhasPosicionadas Lista de posições já ocupadas pelas rainhas.
     * @return {@code true} se a posição for válida, {@code false} caso contrário.
     */
    private static boolean posEhValida(int[] pos, List<int[]> rainhasPosicionadas) {
        int novaLinha = pos[0];
        int novaCol = pos[1];

        for (int[] rainha : rainhasPosicionadas) {
            int linhaExistente = rainha[0];
            int colExistente = rainha[1];

            if (novaCol == colExistente ||
                Math.abs(novaLinha - linhaExistente) == Math.abs(novaCol - colExistente)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Imprime o tabuleiro com a solução encontrada.
     * 
     * @param solucao Lista de posições das rainhas na solução.
     */
    private static void imprimirTab(List<int[]> solucao) {
        char[][] tabuleiro = new char[8][8];
        for (char[] linha : tabuleiro) Arrays.fill(linha, '.');

        for (int[] rainha : solucao) {
            tabuleiro[rainha[0]][rainha[1]] = 'R';
        }

        for (char[] linha : tabuleiro) {
            for (char celula : linha) {
                System.out.print(celula + " ");
            }
            System.out.println();
        }
    }
}
