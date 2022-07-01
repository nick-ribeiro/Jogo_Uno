package Servidor;

import Dados.Jogador;
import java.io.*;
import java.net.*;
import java.util.*;

public class Partida extends Thread {

    // Parte que controla as conexões por meio de threads.
    // Note que a instanciação está no main.
    private static List<Jogador> clientes;

    private Jogador cliente;

    // socket deste cliente
    private Socket conexao;

    // nome deste cliente
    private String nomeJogador;
    
    // construtor que recebe o socket deste cliente
    public Partida(Jogador c) {
        cliente = c;
    }

    // execução da thread
    public void run() {
        try {
            // objetos que permitem controlar fluxo de comunicação
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getSocket().getInputStream()));
            PrintStream saida = new PrintStream(cliente.getSocket().getOutputStream());
            cliente.setSaida(saida);
            
            nomeJogador = entrada.readLine();
            // agora, verifica se string recebida é valida, pois
            // sem a conexão foi interrompida, a string é null.
            // Se isso ocorrer, deve-se terminar a execução.
            if (nomeJogador == null) {
                return;
            }
            cliente.setNome(nomeJogador);

            // clientes é objeto compartilhado por várias threads!
            // De acordo com o manual da API, os métodos são
            // sincronizados. Portanto, não há problemas de acessos
            // simultâneos.
            // Loop principal: esperando por alguma string do cliente.
            // Quando recebe, envia a todos os conectados até que o
            // cliente envie linha em branco.
            // Verificar se linha é null (conexão interrompida)
            // Se não for nula, pode-se compará-la com métodos string
            String linha = entrada.readLine();
            while (linha != null && !(linha.trim().equals(""))) {
                // reenvia a linha para todos os clientes conectados
                sendToAll(saida, " disse: ", linha);
                // espera por uma nova linha.
                linha = entrada.readLine();
            }
            // Uma vez que o cliente enviou linha em branco, retira-se
            // fluxo de saída do vetor de clientes e fecha-se conexão.
            sendToAll(saida, " saiu ", "do chat!");
            clientes.remove(saida);
            conexao.close();
        } catch (IOException e) {
            // Caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
    }

    // enviar uma mensagem para todos, menos para o próprio
    public void sendToAll(PrintStream saida, String acao,
            String linha) throws IOException {

        Iterator<Jogador> iter = clientes.iterator();
        while (iter.hasNext()) {
            Jogador outroJogador = iter.next();
            // obtém o fluxo de saída de um dos clientes
            PrintStream chat = (PrintStream) outroJogador.getSaida();
            // envia para todos, menos para o próprio usuário
            if (chat != saida) {
                chat.println(cliente.getNome() + " com IP: " + cliente.getSocket().getRemoteSocketAddress() + acao + linha);
            }
        }
    }

    public static void main(String args[]) {
        // instancia o vetor de clientes conectados
        clientes = new ArrayList<Jogador>();
        try {
            // criando um socket que fica escutando a porta 2222.
            ServerSocket s = new ServerSocket(2222);
            // Loop principal.
            while (true) {
                // aguarda algum cliente se conectar. A execução do
                // servidor fica bloqueada na chamada do método accept da
                // classe ServerSocket. Quando algum cliente se conectar
                // ao servidor, o método desbloqueia e retorna com um
                // objeto da classe Socket, que é porta da comunicação.
                System.out.print("Esperando alguem se conectar...");
                Socket conexao = s.accept();
                Jogador cliente = new Jogador();
                cliente.setId(conexao.getRemoteSocketAddress().toString());
                cliente.setIp(conexao.getRemoteSocketAddress().toString());
                cliente.setSocket(conexao);

                clientes.add(cliente);

                System.out.println(" Conectou!: " + conexao.getRemoteSocketAddress());

                // cria uma nova thread para tratar essa conexão
                Thread t = new Partida(cliente);
                t.start();
                // voltando ao loop, esperando mais alguém se conectar.
            }
        } catch (IOException e) {
            // caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
    }
    
    
                                // O que falta:
    /*  
        criar metodo pescar -> Receber o Array das Cartas - os Arrays das Cartas dos Jogadores
        criar metodo de embaralhar
        criar o array list para as cartas do jogador -> 7 Cartas
        criar metodo de primeira jogada -> Irá virar a primeira carta do baralho
        criar o ranking -> Soma da pontuação dos Jogadores e irá mostrar o ranking de acordo com a pontuação
        criar metodo para declarar o vencedor 
        criar o if para o Uno -> O Servidor irá checar o jogador que disse Uno, se por acaso ele tiver uma carta na mão o jogo segue
        se tiver sido outro jogador que tiver dito Uno quando o outro não disse, o outro jogador compra cartas.
    */
}
