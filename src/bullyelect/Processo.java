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
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo
 */
public class Processo implements Comparable<Processo> {

    private boolean ativo;
    private long ID;
    private Timer timer;
    private Bully eleicao;
    private SimpleDateFormat formatter;
    private Processo coodernador;
    private LinkedList<Processo> filaDeRequisicao;
    private Thread servico;

    public Processo(long id, Bully eleicao) {
        this.ID = id;
        ativo = true;
        this.eleicao = eleicao;
        //Se ele for o primeiro ele é o coordenador...
        if (eleicao.getCoodernador() == null) {
            this.eleicao.setCoordenador(this);
        }
        //SetCoordenador(this.eleicao.getCoodernador());
        timer = new Timer();
        formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        filaDeRequisicao = new LinkedList<>();

        servico = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (getAtivo()) {
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
                servico.interrupt();
            }
        });        
    }

    public void requisitarCoordeandor() {
        ///Ao fazer uam requisição e o coordenador estiver inativo ou não existir, deve iniciar uma nova solicitação
        if ((eleicao.getCoodernador() != null) && (eleicao.getCoodernador().getAtivo())) {
            eleicao.getCoodernador().Requisicao(this);
        } else {
            System.out.println(formatter.format(new Date()) + " P: " + ID + " - Coordenador inativo iniciando eleição.");
            eleicao.iniciarEleicao(this);
        }
    }

    public void elegerProcesso() {
        if (eleicao.eleger(this)) {
            eleicao.setCoordenador(this);
        }
    }

    public void Requisicao(Processo Proc) {
        filaDeRequisicao.add(Proc);
        if (!servico.isAlive()){
            servico.start();
        }
        System.out.println(formatter.format(new Date()) + " P: " + ID + " - Processo " + Proc.getID() + " Requisitou ao coordenador.");
    }

    public boolean getAtivo() {
        return this.ativo;
    }

    public long getID() {
        return this.ID;
    }

    public void inativa() {
        ativo = false;        
        filaDeRequisicao.clear();
    }

    public void SetCoordenador(Processo coo) {
        coodernador = coo;
    }

    @Override
    public int compareTo(Processo o) {
        if (o.getID() > ID) {
            return -1;
        } else if (o.getID() < ID) {
            return 1;
        }
        return 0;
    }

}
