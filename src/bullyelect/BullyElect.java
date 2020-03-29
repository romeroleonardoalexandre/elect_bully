/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bullyelect;

import java.util.Timer;
import java.util.ArrayList;
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
    private static Timer timer = new Timer();
    private static int P1 = 30;
    private static int P2 = 80;
    private static int P3 = 25;
    private static int P4 = 100;

    public static void main(String[] args) {
        bully = new Bully();
        timer.schedule(new CriarProcesso(), 1);
        timer.schedule(new InativarProcesso(), P2 * 1000);
        timer.schedule(new RequisitarCoordenador(), P3 * 100);
        timer.schedule(new InativarCoordenador(), P4 * 1000); 

    }

    public static class CriarProcesso extends TimerTask {

        public void run() {
            //a cada 30 segundos um novo processo deve ser criado
            bully.criaProcesso();
            timer.schedule(new CriarProcesso(), P1 * 1000);
        }
    }

    public static class InativarProcesso extends TimerTask {

        public void run() {
            //a cada 80 segundos um processo da lista de processos fica inativo 
            Random r = new Random();
            Processo p = bully.listaProcessos.get(r.nextInt(bully.listaProcessos.size() - 1));
            p.inativa();
            System.out.println("Processo " + p.getID() + " foi inativado!");
            timer.schedule(new InativarProcesso(), P2 * 1000);
        }
    }

    public static class RequisitarCoordenador extends TimerTask {

        public void run() {
            Random r = new Random();
            Processo p = null;
            ///a cada 25 segundos um processo fazer uma requisição para o coordenador
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
            } while (p == null || !p.getAtivo());
            timer.schedule(new RequisitarCoordenador(), P3 * 1000);
        }
    }
    
    public static class InativarCoordenador extends TimerTask {

        public void run() {   
            //a cada 100 segundos o coordenador fica inativo
            bully.getCoodernador().inativa();
            System.out.println("Coordenador " + bully.getCoodernador().getID() + " foi inativado!");
            timer.schedule(new InativarCoordenador(), P4 * 1000);            
        }
    }
    
    
    

}
