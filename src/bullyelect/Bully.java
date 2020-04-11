/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bullyelect;

import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo
 */
public class Bully {

    boolean ativa;
    ArrayList<Processo> listaProcessos;
    private Processo coodernador;
    private SimpleDateFormat formatter;

    public Bully() {
        ativa = false;
        listaProcessos = new ArrayList<Processo>();
        coodernador = null;
        formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");        
    }

    public void iniciarEleicao(Processo solicitante) {
        System.out.println(formatter.format(new Date()) + ": Eleição iniciada pelo processo " + solicitante.getID());
        if (ativa) {
            //dois processos de eleição não podem acontecer simultaneamente
            System.out.println(formatter.format(new Date()) + ": Eleição já esta em andamento...");
        } else {
            ativa = true;
            listaProcessos.sort(null);
            //Solicitar pra todos ativos maior que ele.
            for (int i = listaProcessos.indexOf(solicitante); i < listaProcessos.size(); i++) {
                if (listaProcessos.get(i).getAtivo()) {
                    listaProcessos.get(i).elegerProcesso();
                }
            }
            ativa = false;
        }
    }

    public boolean eleger(Processo candidato) {
        //Se o id for maior então ele é o coordenador
        if (candidato.getID() == listaProcessos.get(listaProcessos.size() - 1).getID()) {
            System.out.println(formatter.format(new Date()) + ": O processo " + candidato.getID() + " ganhou a eleição e é o novo coordenador!");
            return true;
        } else {
            return false;
        }
    }

    public void InativarProcesso(Processo p) {
        listaProcessos.remove(p);
        p.inativa();       
    }

    public void criaProcesso() {
        Processo p = new Processo(new Date().getTime(), this);
        listaProcessos.add(p);
        System.out.println(formatter.format(new Date()) + ": Processo " + p.getID() + " Criado.");
    }

    public Processo getCoodernador() {
        return coodernador;
    }

    public void setCoordenador(Processo p) {
        for (Processo processo : listaProcessos) {
            if (processo.getAtivo()) {
                processo.SetCoordenador(p);
            }
        }
        this.coodernador = p;
    }
}
