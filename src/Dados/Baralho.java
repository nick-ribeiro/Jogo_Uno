package Dados;

import java.util.ArrayList;
import java.util.Collections;

public class Baralho{

    public Baralho() {
        
    }
    
    public static void criacaoBaralho(ArrayList<String> Baralho) {
        
        String nome = "";
        int i, j;
    
        for(i = 0; i < 3; i++) {
            if(i == 0) {
                nome = "zero";
                Baralho.add(nome + "_amarelo");
                Baralho.add(nome + "_vermelho");
                Baralho.add(nome + "_verde");
                Baralho.add(nome + "_azul");
            }
            if(i == 1) {
                nome = "+quatro";
            }
            if(i == 2) {
                nome = "inverte_cor";
            }
            if(i == 1 || i == 2) {
                for(j = 0; j < 4; j++) {
                    Baralho.add(nome);
                }
            }
        }
    
        for(i = 1; i <= 12; i++) {
            for(j = 0; j < 2; j++) {
                if(i == 1) {
                    nome = "um";
                }if(i == 2) {
                    nome = "dois";
                }if(i == 3) {
                    nome = "tres";
                }if(i == 4) {
                    nome = "quatro";
                }if(i == 5) {
                    nome = "cinco";
                }if(i == 6) {
                    nome = "seis";
                }if(i == 7) {
                    nome = "sete";
                }if(i == 8) {
                    nome = "oito";
                }if(i == 9) {
                    nome = "nove";
                }if(i == 10) {
                    nome = "inverso";
                }if(i == 11) {
                    nome = "bloqueio";
                }if(i == 12) {
                    nome = "+dois";
                }
                Baralho.add(nome + "_amarelo");
                Baralho.add(nome + "_vermelho");
                Baralho.add(nome + "_verde");
                Baralho.add(nome + "_azul");
            }
        }
    
        embaralhar(Baralho);
    }
    
    public static void embaralhar(ArrayList<String> Baralho) {
        Collections.shuffle(Baralho);
        mostraBaralho(Baralho);
    }
    
    public static void mostraBaralho(ArrayList<String> Baralho) {
        for(String m : Baralho) {
            System.out.println(m);
        }
    }
    
    public static void removerCarta(ArrayList<String> Baralho) {
        
    }
    
    public static void cartasJogador(ArrayList<String> Baralho) {
        
    }
    
    public static void main(String[] args) {
        
        ArrayList<String> Baralho = new ArrayList<String>();
        
        criacaoBaralho(Baralho);
    }
}
