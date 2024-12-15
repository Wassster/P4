package OV.Domein;

import java.sql.Date;

public class OVChipkaart {

    private int kaartnummer;
    private Date geldige_tot;
    private int klasse;
    private double saldo;
    private Reiziger reiziger;

    public OVChipkaart(int kaartnummer, Date geldige_tot, int klasse, double saldo) {
        this.kaartnummer = kaartnummer;
        this.geldige_tot = geldige_tot;
        this.klasse = klasse;
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
        if (reiziger != null && !reiziger.getOvChipkaarts().contains(this)) {
            reiziger.addOVChipkaart(this); // Zorg voor consistentie
        }
    }

    public Date getGeldige_tot() {
        return geldige_tot;
    }

    public int getKaartnummer() {
        return kaartnummer;
    }

    public int getKlasse() {
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setGeldige_tot(Date geldige_tot) {
        this.geldige_tot = geldige_tot;
    }

    public void setKaartnummer(int kaartnummer) {
        this.kaartnummer = kaartnummer;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}

