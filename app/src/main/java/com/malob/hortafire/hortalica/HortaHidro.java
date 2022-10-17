package com.malob.hortafire.hortalica;

public class HortaHidro {
    private String hortalica, lote, dataSemear, dataGerminar, dataBerco, dataEngorda;
    private int tempoEngorda;
    public HortaHidro(String hortalica, String lote, String dataSemear, String dataGerminar, String dataBerco, String dataEngorda,int tempoEngorda) {
        this.hortalica = hortalica;
        this.lote = lote;
        this.dataSemear = dataSemear;
        this.dataGerminar = dataGerminar;
        this.dataBerco = dataBerco;
        this.dataEngorda = dataEngorda;
        this.tempoEngorda=tempoEngorda;
    }
    public HortaHidro() {

    }

    public String getHortalica() {
        return hortalica;
    }

    public void setHortalica(String hortalica) {
        this.hortalica = hortalica;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getDataSemear() {
        return dataSemear;
    }

    public void setDataSemear(String dataSemear) {
        this.dataSemear = dataSemear;
    }

    public String getDataGerminar() {
        return dataGerminar;
    }

    public void setDataGerminar(String dataGerminar) {
        this.dataGerminar = dataGerminar;
    }

    public String getDataBerco() {
        return dataBerco;
    }

    public void setDataBerco(String dataBerco) {
        this.dataBerco = dataBerco;
    }

    public String getDataEngorda() {
        return dataEngorda;
    }

    public void setDataEngorda(String dataEngorda) {
        this.dataEngorda = dataEngorda;
    }

    public int getTempoEngorda() {return tempoEngorda;}

    public void setTempoEngorda(int tempoEngorda) {this.tempoEngorda = tempoEngorda;}
}