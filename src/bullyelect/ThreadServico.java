/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bullyelect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo
 */
public class ThreadServico extends Thread {

    LinkedList<Processo> filaDeRequisicao = new LinkedList<>();
    

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(10);
                if (filaDeRequisicao.size() > 0) {
                    Processo p = filaDeRequisicao.pop();
                    Random r = new Random();
                    int tempo = r.nextInt((25 - 5) + 1) + 5;
                    System.out.println(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(new Date()) + ": Imprimindo de " + p.getID() + " em " + tempo + " segundos...");
                    Thread.sleep(tempo * 1000);
                    System.out.println("Fim da impressão de " + p.getID() + "...");
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Processo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void AddRequicicao(Processo p){
        filaDeRequisicao.add(p);
    }

}
