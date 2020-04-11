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
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final int INICIAPROCESSO = 40000;
    private static final int REQUISICAO = 25000;
    private static final int INATIVACOORDENADOR = 120000;

    public static void main(String[] args) {
        System.err.println("Sistemas Distribuídos - Bully - Exclusão mutua");
        System.err.println("Leonardo Pereira - Alexandre Romero");
        bully = new Bully();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    try {
                        //a cada 30 segundos um novo processo deve ser criado
                        bully.criaProcesso();
                        sleep(INICIAPROCESSO);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BullyElect.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    try {
                        //a cada 2 min o coordenador fica inativo            
                        sleep(INATIVACOORDENADOR);
                        bully.InativarProcesso(bully.getCoodernador());
                        System.out.println(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(new Date()) + ": Coordenador " + bully.getCoodernador().getID() + " foi inativado!");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BullyElect.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    try {
                        //Requisitar coodernador
                        Random r = new Random();
                        int tempo = r.nextInt((25 - 10) + 1) + 10;
                        Processo p;
                        if (bully.listaProcessos.size() > 0) {
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
                        }
                        sleep(tempo * 1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BullyElect.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }).start();
    }

}
