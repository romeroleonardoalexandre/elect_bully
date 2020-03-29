/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bullyelect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**
 *
 * @author Leonardo
 */
public class Processo implements Comparable<Processo> {

    private boolean ativo;
    private int ID;
    private Timer timer;
    private Bully eleicao;
    private SimpleDateFormat formatter;

    public Processo(int id, Bully eleicao) {
        this.ID = id;
        ativo = true;
        this.eleicao = eleicao;
        //Se ele for o primeiro ele é o coordenador...
        if (this.eleicao.getCoodernador() == null) {
            this.eleicao.setCoordenador(this);
        }
        timer = new Timer();
        formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
    }

    public void requisitarCoordeandor() {
        ///Ao fazer uam requisição e o coordenador estiver inativo ou não existir, deve iniciar uma nova solicitação
        if ((eleicao.getCoodernador() != null) && (eleicao.getCoodernador().getAtivo())) {
            eleicao.getCoodernador().Requisicao(ID);
        } else {
            System.out.println(formatter.format(new Date()) +" P: " + ID + " - Coordenador inativo iniciando eleição.");
            eleicao.iniciarEleicao(this);
        }
    }

    public void elegerProcesso() {
        if (eleicao.eleger(this)) {
            eleicao.setCoordenador(this);
        }
    }

    public void Requisicao(int idProc) {
        System.out.println(formatter.format(new Date())+" P: " + ID + " - Processo " + idProc + " Requisitou ao coordenador.");
    }

    public boolean getAtivo() {
        return this.ativo;
    }

    public int getID() {
        return this.ID;
    }

    public void inativa() {
        ativo = false;
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
