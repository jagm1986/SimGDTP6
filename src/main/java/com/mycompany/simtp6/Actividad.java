/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simtp6;

/**
 *
 * @author Matias
 */
public class Actividad {
  private  Estado e;
    private int material;
    private double rnd;
    private double tiempoAtencion;
    private double proxFin;
    private int colaUno;
    private int colaDos;
    IActividad calcActividad;

    public Actividad(Estado e, int material, double rnd, double tiempoAtencion, double proxFin, int colaUno, int colaDos, IActividad calcActividad) {
        this.e = e;
        this.material = material;
        this.rnd = rnd;
        this.tiempoAtencion = tiempoAtencion;
        this.proxFin = proxFin;
        this.colaUno = colaUno;
        this.colaDos = colaDos;
        this.calcActividad = calcActividad;
    }

    public Actividad(Estado e, int colaUno, int colaDos, IActividad a) {
        this.e = e;
        this.colaUno = colaUno;
        this.colaDos = colaDos;
        this.calcActividad = a;
        proxFin = 999999.0;
    }

    
    public Estado getE() {
        return e;
    }

    public void setE(Estado e) {
        this.e = e;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }

    public double getRnd() {
        return rnd;
    }

    public void setRnd(double rnd) {
        this.rnd = rnd;
    }

    public double getTiempoAtencion() {
        return tiempoAtencion;
    }

    public void setTiempoAtencion(double tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
    }

    public double getProxFin() {
        return proxFin;
    }

    public void setProxFin(double proxFin) {
        this.proxFin = proxFin;
    }

    public int getColaUno() {
        return colaUno;
    }

    public void setColaUno(int colaUno) {
        this.colaUno = colaUno;
    }

    public int getColaDos() {
        return colaDos;
    }

    public void setColaDos(int colaDos) {
        this.colaDos = colaDos;
    }

    public IActividad getCalcActividad() {
        return calcActividad;
    }

    public void setCalcActividad(IActividad calcActividad) {
        this.calcActividad = calcActividad;
    }
    
    
}
