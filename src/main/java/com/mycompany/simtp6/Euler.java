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
public class Euler implements IEDSolver {

    private double a, b, c, h, x0, dx0, t, x1, x2, fx2;
    private IActividad generador;
    private double[][] hist;
    private int count, maxIt;
    private double segundoMaxT;

    public Euler(double a, double b, double c, double h, double x0, double dx0, IActividad generador, int maxIt) {
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

    public double getFx2() {
        return fx2;
    }

    public void setFx2(double fx2) {
        this.fx2 = fx2;
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

        hist = new double[4][maxIt];
        t = 0;
        x1 = this.x0; 
        double r = Math.random();
        a = generador.calcularTiempo(r);
        //a = 0.5212;
        x2 = dx0;
        fx2 = Math.exp(-c * t) - (a * x2) - (b * x1);
        hist[0][0] = t;
        hist[1][0] = x1;
        hist[2][0] = x2;
        hist[3][0] = fx2;
        count = 1;
    }

    @Override
    public void calcularProxima() {
        t += h;
        x1 = x1 + h * x2;
        x2 = x2 + h * fx2;
        fx2 = Math.exp(-c * t) - (a * x2) - (b * x1);
        hist[0][count] = t;
        hist[1][count] = x1;
        hist[2][count] = x2;
        hist[3][count] = fx2;
        count++;
    }

    public String toString() {
        String fila;
        DecimalFormat df = new DecimalFormat("#.####");
        fila = "tn | x1 | x2 | fx2" + "\n";
        for (int j = 0; j < hist[0].length; j++) {
            fila += df.format(hist[0][j]) + " | " + df.format(hist[1][j]) + " | " + df.format(hist[2][j]) + " | " + df.format(hist[3][j]) + "\n";
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
    }

}
