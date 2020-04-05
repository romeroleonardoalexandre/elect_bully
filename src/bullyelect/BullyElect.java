/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bullyelect;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.TimerTask;

/**
 *
 * @author romer
 */
public class BullyElect {

    /**
     * @param args the command line arguments
     */
    private static Bully bully;
    private static final Timer TIMER = new Timer();
    private static final int INICIAPROCESSO = 30;
    private static final int INATIVAPROCESSO = 80;
    private static final int REQUISICAO = 25;
    private static final int INATIVACOORDENADOR = 100;
    
    public static void main(String[] args) {
        System.err.println("Sistemas Distribuídos - Bully");
        System.err.println("Leonardo Pereira - Alexandre Romero");
        bully = new Bully();
        TIMER.schedule(new CriarProcesso(), 1);
        TIMER.schedule(new InativarProcesso(), INATIVAPROCESSO * 1000);
        TIMER.schedule(new RequisitarCoordenador(), REQUISICAO * 1000);
        TIMER.schedule(new InativarCoordenador(), INATIVACOORDENADOR * 1000); 

    }

    public static class CriarProcesso extends TimerTask {

        /**
         * Inicializa timer para riar o processo
         */
        @Override
        public void run() {
            //a cada 30 segundos um novo processo deve ser criado
            bully.criaProcesso();
            TIMER.schedule(new CriarProcesso(), INICIAPROCESSO * 1000);
        }
    }

    public static class InativarProcesso extends TimerTask {

        /**
         * Inicializa timer para inativar um processo a cada 80 segundos
         */
        @Override
        public void run() {
            //a cada 80 segundos um processo da lista de processos fica inativo 
            Random r = new Random();
            Processo p = bully.listaProcessos.get(r.nextInt(bully.listaProcessos.size() - 1));
            p.inativa();
            System.out.println(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(new Date())+": Processo " + p.getID() + " foi inativado!");
            TIMER.schedule(new InativarProcesso(), INATIVAPROCESSO * 1000);
        }
    }

    public static class RequisitarCoordenador extends TimerTask {

        /**
         * Inicializa timer para requisitar ao coodenador a cada 25 segundos
         */
        @Override
        public void run() {
            Random r = new Random();
            Processo p;
            ///a cada 25 segundos um processo fazer uma requisição para o coordenador
            try{

                do {

                    if (bully.listaProcessos.size() == 1) {
                        p = bully.listaProcessos.get(0);
                    } else {
                        int qtd = bully.listaProcessos.size();
                        int idx = r.nextInt(qtd - 1);
                        p = bully.listaProcessos.get(idx);
                    }
                    if (p.getAtivo()) {
                        p.requisitarCoordeandor();
                    }
                } while ( !p.getAtivo());
            }catch(NullPointerException e){
                System.out.println(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(new Date())+": Não encontrou processo!!");
            }
            TIMER.schedule(new RequisitarCoordenador(), REQUISICAO * 1000);
        }
    }
    
    public static class InativarCoordenador extends TimerTask {

        /**
         * inicializa timer para inativar o coordenador atual
         */
        @Override
        public void run() {   
            //a cada 100 segundos o coordenador fica inativo
            bully.getCoodernador().inativa();
            System.out.println(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(new Date())+": Coordenador " + bully.getCoodernador().getID() + " foi inativado!");
            TIMER.schedule(new InativarCoordenador(), INATIVACOORDENADOR * 1000);            
        };
    }
    
}
