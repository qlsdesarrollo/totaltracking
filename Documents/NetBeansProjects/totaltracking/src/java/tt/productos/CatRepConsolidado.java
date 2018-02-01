package tt.productos;

import java.io.Serializable;


public class CatRepConsolidado implements Serializable{
    
    private String id_art, nom_mar, nom_fam, cod_art, det_nom, nom_emb, can_pen_val, 
            can_pen_ord_com, can_pen_cot, can_pen_rep, exi_act_tot, lotes, transito, lot_com;

    public CatRepConsolidado() {
    }

    public CatRepConsolidado(String id_art, String nom_mar, String nom_fam, String cod_art, String det_nom, String nom_emb, String can_pen_val, String can_pen_ord_com, String can_pen_cot, String can_pen_rep, String exi_act_tot, String lotes, String transito, String lot_com) {
        this.id_art = id_art;
        this.nom_mar = nom_mar;
        this.nom_fam = nom_fam;
        this.cod_art = cod_art;
        this.det_nom = det_nom;
        this.nom_emb = nom_emb;
        this.can_pen_val = can_pen_val;
        this.can_pen_ord_com = can_pen_ord_com;
        this.can_pen_cot = can_pen_cot;
        this.can_pen_rep = can_pen_rep;
        this.exi_act_tot = exi_act_tot;
        this.lotes = lotes;
        this.transito = transito;
        this.lot_com = lot_com;
    }

    public String getId_art() {
        return id_art;
    }

    public void setId_art(String id_art) {
        this.id_art = id_art;
    }

    public String getNom_mar() {
        return nom_mar;
    }

    public void setNom_mar(String nom_mar) {
        this.nom_mar = nom_mar;
    }

    public String getNom_fam() {
        return nom_fam;
    }

    public void setNom_fam(String nom_fam) {
        this.nom_fam = nom_fam;
    }

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public String getDet_nom() {
        return det_nom;
    }

    public void setDet_nom(String det_nom) {
        this.det_nom = det_nom;
    }

    public String getNom_emb() {
        return nom_emb;
    }

    public void setNom_emb(String nom_emb) {
        this.nom_emb = nom_emb;
    }

    public String getCan_pen_val() {
        return can_pen_val;
    }

    public void setCan_pen_val(String can_pen_val) {
        this.can_pen_val = can_pen_val;
    }

    public String getCan_pen_ord_com() {
        return can_pen_ord_com;
    }

    public void setCan_pen_ord_com(String can_pen_ord_com) {
        this.can_pen_ord_com = can_pen_ord_com;
    }

    public String getCan_pen_cot() {
        return can_pen_cot;
    }

    public void setCan_pen_cot(String can_pen_cot) {
        this.can_pen_cot = can_pen_cot;
    }

    public String getCan_pen_rep() {
        return can_pen_rep;
    }

    public void setCan_pen_rep(String can_pen_rep) {
        this.can_pen_rep = can_pen_rep;
    }

    public String getExi_act_tot() {
        return exi_act_tot;
    }

    public void setExi_act_tot(String exi_act_tot) {
        this.exi_act_tot = exi_act_tot;
    }

    public String getLotes() {
        return lotes;
    }

    public void setLotes(String lotes) {
        this.lotes = lotes;
    }

    public String getTransito() {
        return transito;
    }

    public void setTransito(String transito) {
        this.transito = transito;
    }

    public String getLot_com() {
        return lot_com;
    }

    public void setLot_com(String lot_com) {
        this.lot_com = lot_com;
    }
    
    
    
}
