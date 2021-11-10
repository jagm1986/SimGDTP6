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
public interface IEDSolver {
    public void calcularPrimeraFila();
    public void calcularProxima();
    public void calcularSegundoPico();
    public int getMaxIt();
    public double getSegundoMaxT();
    public double[][] getHist();
}
