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
public class ActividadNormal implements IActividad{
    private double mu;
    private double sigma;

    public ActividadNormal(double mu, double sigma) {
        this.mu = mu;
        this.sigma = sigma;
    }

    public double getMu() {
        return mu;
    }

    public void setMu(double mu) {
        this.mu = mu;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    @Override
    public double calcularTiempo(double random) {
        double z = Math.sqrt(-2*Math.log(1-random))*Math.cos(2*Math.PI*random);
        double x = mu+z*sigma;
        x = (double) Math.round(x * 10000d) / 10000d;
        return x;
    }
    
    
}
