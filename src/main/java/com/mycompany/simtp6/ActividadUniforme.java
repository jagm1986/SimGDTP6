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
public class ActividadUniforme implements IActividad{
    private double a;
    private double b;

    public ActividadUniforme() {
    }

    public ActividadUniforme(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    @Override
    public double calcularTiempo(double random) {
        double x = a+random*(b-a);
        x = (double) Math.round(x * 10000d) / 10000d;
        return x;
    }
    
}
