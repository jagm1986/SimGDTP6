/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simtp6;

import java.text.DecimalFormat;

/**
 *
 * @author Matias
 */
public class RKutta implements IEDSolver {

    private double a, b, c, h, x0, dx0, t, x1, x2, k1, k2, k3, k4, l1, l2, l3, l4;
    private IActividad generador;
    private double[][] hist;
    private int count, maxIt;
    private double segundoMaxT;

    public RKutta(double a, double b, double c, double h, double x0, double dx0, IActividad generador, int maxIt) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.h = h;
        this.x0 = x0;
        this.dx0 = dx0;
        this.generador = generador;
        this.maxIt = maxIt;
        count = 1;
    }

    public double getK1() {
        return k1;
    }

    public void setK1(double k1) {
        this.k1 = k1;
    }

    public double getK2() {
        return k2;
    }

    public void setK2(double k2) {
        this.k2 = k2;
    }

    public double getK3() {
        return k3;
    }

    public void setK3(double k3) {
        this.k3 = k3;
    }

    public double getK4() {
        return k4;
    }

    public void setK4(double k4) {
        this.k4 = k4;
    }

    public double getL1() {
        return l1;
    }

    public void setL1(double l1) {
        this.l1 = l1;
    }

    public double getL2() {
        return l2;
    }

    public void setL2(double l2) {
        this.l2 = l2;
    }

    public double getL3() {
        return l3;
    }

    public void setL3(double l3) {
        this.l3 = l3;
    }

    public double getL4() {
        return l4;
    }

    public void setL4(double l4) {
        this.l4 = l4;
    }

    public IActividad getGenerador() {
        return generador;
    }

    public void setGenerador(IActividad generador) {
        this.generador = generador;
    }

    public double[][] getHist() {
        return hist;
    }

    public void setHist(double[][] hist) {
        this.hist = hist;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMaxIt() {
        return maxIt;
    }

    public void setMaxIt(int maxIt) {
        this.maxIt = maxIt;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getSegundoMaxT() {
        return segundoMaxT;
    }

    public void setSegundoMaxT(double segundoMaxT) {
        this.segundoMaxT = segundoMaxT;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
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

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public double getDx0() {
        return dx0;
    }

    public void setDx0(double dx0) {
        this.dx0 = dx0;
    }

    @Override
    public void calcularPrimeraFila() {

        hist = new double[11][maxIt];
        t = 0;
        x1 = this.x0;
        double r = Math.random();
        a = generador.calcularTiempo(r);
        //a = 1.5239;
        x2 = dx0;
        l1 = h * (Math.exp(-c * t) - (a * x2) - (b * x1));
        k1 = h * x2;
        l2 = h * (Math.exp(-c * (t+0.5*h)) - (a * (x2+0.5*l1)) - (b * (x1+0.5*k1)));
        k2 = h* (x2 + 0.5*l1);
        l3 = h * (Math.exp(-c * (t+0.5*h)) - (a * (x2+0.5*l2)) - (b * (x1+0.5*k2)));
        k3 = h* (x2 + 0.5*l2);
        l4 = h * (Math.exp(-c * (t+h)) - (a * (x2+l3)) - (b * (x1+k3)));
        k4 = h* (x2+l3);
        hist[0][0] = t;
        hist[1][0] = x1;
        hist[2][0] = k1;
        hist[3][0] = k2;
        hist[4][0] = k3;
        hist[5][0] = k4;
        hist[6][0] = x2;
        hist[7][0] = l1;
        hist[8][0] = l2;
        hist[9][0] = l3;
        hist[10][0] = l4;
        count = 1;
    }

    @Override
    public void calcularProxima() {
        if(count < maxIt){
        t += h;
        x1 = x1 +  (k1 + 2*k2 + 2*k3 + k4)/6;
        x2 = x2 + (l1 + 2*l2 + 2*l3 + l4)/6;

        l1 = h * (Math.exp(-c * t) - (a * x2) - (b * x1));
        k1 = h * x2;
        l2 = h * (Math.exp(-c * (t+0.5*h)) - (a * (x2+0.5*l1)) - (b * (x1+0.5*k1)));
        k2 = h* (x2 + 0.5*l1);
        l3 = h * (Math.exp(-c * (t+0.5*h)) - (a * (x2+0.5*l2)) - (b * (x1+0.5*k2)));
        k3 = h* (x2 + 0.5*l2);
        l4 = h * (Math.exp(-c * (t+h)) - (a * (x2+l3)) - (b * (x1+k3)));
        k4 = h* (x2+l3);
        hist[0][count] = t;
        hist[1][count] = x1;
        hist[2][count] = k1;
        hist[3][count] = k2;
        hist[4][count] = k3;
        hist[5][count] = k4;
        hist[6][count] = x2;
        hist[7][count] = l1;
        hist[8][count] = l2;
        hist[9][count] = l3;
        hist[10][count] = l4;
        count++;}
    }

    public String toString() {
        String fila;
        DecimalFormat df = new DecimalFormat("#.####");
        fila = "";
        for (int j = 0; j < hist[0].length; j++) {
            fila += df.format(hist[0][j]) + " | " + df.format(hist[1][j]) + " | " + df.format(hist[2][j]) + " | " + df.format(hist[3][j]) +  " | " + df.format(hist[4][j]) +  " | " + df.format(hist[5][j]) +  " | " + df.format(hist[6][j])  + " | " + df.format(hist[7][j]) + " | " + df.format(hist[8][j]) +  " | " + df.format(hist[9][j]) +  " | " + df.format(hist[10][j]) + "\n";
        }

        fila += "Segundo pico: " + df.format(segundoMaxT);
        return fila;
    }

    public void calcularSegundoPico() {
        double primerMax = 0;
        segundoMaxT = 0;
        boolean encontroPrimero = false;
        outerloop:
        for (int i = 1; i < hist[1].length - 1; i++) {
            if (hist[1][i] > hist[1][i - 1] && hist[1][i] > hist[1][i + 1]) {
                primerMax = hist[0][i];
                encontroPrimero = true;
            }
            if (encontroPrimero == true) {
                for (int j = i + 1; j < hist[1].length - 1; j++) {
                    if (hist[1][j] > hist[1][j - 1] && hist[1][j] > hist[1][j + 1]) {
                        segundoMaxT = hist[0][j];
                        break outerloop;
                    }
                }
            }
            if (segundoMaxT == 0) {
                segundoMaxT = primerMax;
            }
        }   
        //System.out.println(segundoMaxT);
    }
    

}
