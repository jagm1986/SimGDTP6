/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simtp6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.math3.distribution.TDistribution;

/**
 *
 * @author Usuario
 */
public class Fila {

    double reloj = 0;
    Evento e;
    int material;
    int proximoMaterial;
    double rndPedido;
    double tiempoEntreLlegadas;
    double proxLlegada;
    Actividad A1;
    Actividad A2;
    Actividad A3;
    Actividad A4;
    Actividad A5;
    int colaA3;
    int colaA5;

    private double tiempoAtencionEnsamble = 0.0;
    private double proxFinEnsamble = 999999;
    IActividad calcActividadEnsamble;
    int tareasTerminadas;
    int contadorN;
    ArrayList<Double> tiemposInicio = new ArrayList<Double>();
    double finA4 = 0;
    IActividad llegadaActividadCalc;
    Estado estadoEnsamble;
    double acumEnsamble;
    double promedioEnsamble;
    double proporcionEnsamble;
    int maxColaA1;
    int maxColaA2;
    int maxColaA3;
    int maxColaA4;
    int maxColaA5A2;
    int maxColaA5A4;
    int maxColaTerA3;
    int maxColaTerA5;
    ArrayList<Double> histCola1 = new ArrayList<Double>();
    double acumPermCola1;
    double promPermCola1;
    int acumCola1;
    ArrayList<Double> histCola2 = new ArrayList<Double>();
    double acumPermCola2;
    double promPermCola2;
    int acumCola2;
    ArrayList<Double> histCola3 = new ArrayList<Double>();
    double acumPermCola3;
    double promPermCola3;
    int acumCola3;
    ArrayList<Double> histCola4 = new ArrayList<Double>();
    double acumPermCola4;
    double promPermCola4;
    int acumCola4;
    ArrayList<Double> histCola52 = new ArrayList<Double>();
    double acumPermCola52;
    double promPermCola52;
    int acumCola52;
    ArrayList<Double> histCola54 = new ArrayList<Double>();
    double acumPermCola54;
    double promPermCola54;
    int acumCola54;
    ArrayList<Double> histColaTerA3 = new ArrayList<Double>();
    double acumPermColaTerA3;
    double promPermColaTerA3;
    int acumColaTerA3;
    ArrayList<Double> histColaTerA5 = new ArrayList<Double>();
    double acumPermColaTerA5;
    double promPermColaTerA5;
    int acumColaTerA5;
    double promedioCantColaA1 = 0.0;
    double promedioCantColaA2 = 0.0;
    double promedioCantColaA3 = 0.0;
    double promedioCantColaA4 = 0.0;
    double promedioCantColaA52 = 0.0;
    double promedioCantColaA54 = 0.0;
    double promedioCantColaTerA3 = 0.0;
    double promedioCantColaTerA5 = 0.0;
    double promedioCantSistema = 0.0;
    double acumOcupA1;
    double acumOcupA2;
    double acumOcupA3;
    double acumOcupA4;
    double acumOcupA5;
    double porcOcupacionA1;
    double porcOcupacionA2;
    double porcOcupacionA3;
    double porcOcupacionA4;
    double porcOcupacionA5;
    double acumBloqA5;
    double porcBloqueoA5;
    double acumOcupA5paraBloq;
    double contHoras;
    double acumHoras = 60;
    double cantEnsamblesxHora;
    double promEnsamblesxHora;
    double acumEnsamblesxHora;
    double contMasDe3;
    IEDSolver solver;

    public Fila(Evento e, int material, int proximoMaterial, double rndPedido, double tiempoEntreLlegadas, double proxLlegada, Actividad A1, Actividad A2, Actividad A3, Actividad A4, Actividad A5, int colaA3, int colaA5, int tareasTerminadas, int contadorN, IActividad llegadaActividadCalc, IEDSolver solver) {
        this.e = e;
        this.material = material;
        this.proximoMaterial = proximoMaterial;
        this.rndPedido = rndPedido;
        this.tiempoEntreLlegadas = tiempoEntreLlegadas;
        this.proxLlegada = proxLlegada;
        this.A1 = A1;
        this.A2 = A2;
        this.A3 = A3;
        this.A4 = A4;
        this.A5 = A5;
        this.colaA3 = colaA3;
        this.colaA5 = colaA5;
        this.tareasTerminadas = tareasTerminadas;
        this.contadorN = contadorN;
        this.llegadaActividadCalc = llegadaActividadCalc;
        this.solver = solver;
    }

    public Fila(IEDSolver solver) {
this.solver = solver;
    }

    public void CalcularPrimeraFila(double lambda) {
        e = e.Inicio;
        material = 0;
        proximoMaterial = 1;
        llegadaActividadCalc = new ActividadExponencial(1 / lambda);
        rndPedido = Math.random();
        tiempoEntreLlegadas = llegadaActividadCalc.calcularTiempo(rndPedido);
        proxLlegada = reloj + tiempoEntreLlegadas;

        IActividad paraA1 = new ActividadUniforme(20, 30);
        IActividad paraA2 = new ActividadUniforme(30, 50);
        IActividad paraA3 = new ActividadExponencial(30);
        IActividad paraA4 = new ActividadUniforme(10, 20);
        IActividad paraA5 = new ActividadExponencial(5);

        A1 = new Actividad(Estado.Libre, 0, 0, paraA1);
        A2 = new Actividad(Estado.Libre, 0, 0, paraA2);
        A3 = new Actividad(Estado.Libre, 0, 0, paraA3);
        A4 = new Actividad(Estado.Libre, 0, 0, paraA4);
        A5 = new Actividad(Estado.Libre, 0, 0, paraA5);

        colaA3 = 0;
        colaA5 = 0;
        estadoEnsamble = Estado.Libre;
        tareasTerminadas = 0;
        contadorN = 1;
    }

    public void CalcularNuevaFila(int cantXHora) {
        double tempreloj = this.reloj;
        this.reloj = menorTiempo();
        cantEnsamblesxHora = 0.0;
        if (reloj >= acumHoras) {
            e = Evento.Hora;
            this.reloj = acumHoras;
            cantEnsamblesxHora = tareasTerminadas - acumEnsamblesxHora;
            acumEnsamblesxHora = tareasTerminadas;
            acumHoras += 60.0;
            contHoras++;
            if (cantEnsamblesxHora >= cantXHora) {
                contMasDe3++;
            }
        }

        if (reloj == proxLlegada) {
            e = Evento.LlegaPedido;
        } else if (reloj == A1.getProxFin()) {
            e = Evento.FinA1;
        } else if (reloj == A2.getProxFin()) {
            e = Evento.FinA2;
        } else if (reloj == A3.getProxFin()) {
            e = Evento.FinA3;
        } else if (reloj == A4.getProxFin()) {
            e = Evento.FinA4;
        } else if (reloj == A5.getProxFin()) {
            e = Evento.FinA5;
        } else if (reloj == proxFinEnsamble) {
            e = Evento.FinEncastre;
        }

        Estado tempA5 = A5.getE();

        if (e == Evento.LlegaPedido) {
            material += 1;
            proximoMaterial += 1;
            rndPedido = Math.random();
            tiempoEntreLlegadas = llegadaActividadCalc.calcularTiempo(rndPedido);
            proxLlegada += tiempoEntreLlegadas;

            if (A1.getE() == Estado.Libre) {
                A1.setMaterial(A1.getMaterial() + 1);
                double randA1 = Math.random();
                A1.setRnd(randA1);
                double tiempoCalculadoA1 = A1.getCalcActividad().calcularTiempo(randA1);
                A1.setTiempoAtencion(tiempoCalculadoA1);
                A1.setProxFin(reloj + tiempoCalculadoA1);
                A1.setColaUno(0);
            } else if (A1.getE() == Estado.Ocupado) {
                A1.setColaUno(A1.getColaUno() + 1);
                histCola1.add(reloj);
            }
            A1.setE(Estado.Ocupado);

            if (A2.getE() == Estado.Libre) {
                A2.setMaterial(A2.getMaterial() + 1);
                double randA1 = Math.random();
                A2.setRnd(randA1);
                double tiempoCalculadoA1 = A2.getCalcActividad().calcularTiempo(randA1);
                A2.setTiempoAtencion(tiempoCalculadoA1);
                A2.setProxFin(reloj + tiempoCalculadoA1);
                A2.setColaUno(0);
            } else if (A2.getE() == Estado.Ocupado) {
                A2.setColaUno(A2.getColaUno() + 1);
                histCola2.add(reloj);
            }
            A2.setE(Estado.Ocupado);

            if (A3.getE() == Estado.Libre) {
                A3.setMaterial(A3.getMaterial() + 1);
                double randA1 = Math.random();
                A3.setRnd(randA1);
                double tiempoCalculadoA1 = A3.getCalcActividad().calcularTiempo(randA1);
                A3.setTiempoAtencion(tiempoCalculadoA1);
                A3.setProxFin(reloj + tiempoCalculadoA1);
                A3.setColaUno(0);
            } else if (A3.getE() == Estado.Ocupado) {
                A3.setColaUno(A3.getColaUno() + 1);
                histCola3.add(reloj);
            }
            A3.setE(Estado.Ocupado);

            tiemposInicio.add(reloj);

        }

        if (e == Evento.FinA1) {

            if (A1.getColaUno() == 0) {
                A1.setE(Estado.Libre);
                A1.setColaUno(0);
                A1.setProxFin(999999);
                A1.setRnd(0.0);
                A1.setTiempoAtencion(0.0);
            } else if (A1.getColaUno() > 0) {
                A1.setMaterial(A1.getMaterial() + 1);
                double randA1 = Math.random();
                A1.setRnd(randA1);
                double tiempoCalculadoA1 = A1.getCalcActividad().calcularTiempo(randA1);
                A1.setTiempoAtencion(tiempoCalculadoA1);
                A1.setProxFin(reloj + tiempoCalculadoA1);
                A1.setColaUno(A1.getColaUno() - 1);
                acumCola1++;
                acumPermCola1 += reloj - histCola1.get(0);
                histCola1.remove(0);
            }
            if (A4.getE() == Estado.Libre) {
                A4.setMaterial(A4.getMaterial() + 1);
                double randA1 = Math.random();
                A4.setRnd(randA1);
                double tiempoCalculadoA1 = A4.getCalcActividad().calcularTiempo(randA1);
                A4.setTiempoAtencion(tiempoCalculadoA1);
                A4.setProxFin(reloj + tiempoCalculadoA1);
                A4.setColaUno(0);
            } else if (A4.getE() == Estado.Ocupado) {
                A4.setColaUno(A4.getColaUno() + 1);
                histCola4.add(reloj);
            }
            A4.setE(Estado.Ocupado);
        }

        if (e == Evento.FinA2) {
            if (A2.getColaUno() == 0) {
                A2.setE(Estado.Libre);
                A2.setColaUno(0);
                A2.setProxFin(999999);
                A2.setRnd(0.0);
                A2.setTiempoAtencion(0.0);
            } else if (A2.getColaUno() > 0) {
                A2.setMaterial(A2.getMaterial() + 1);
                double randA2 = Math.random();
                A2.setRnd(randA2);
                double tiempoCalculadoA2 = A2.getCalcActividad().calcularTiempo(randA2);
                A2.setTiempoAtencion(tiempoCalculadoA2);
                A2.setProxFin(reloj + tiempoCalculadoA2);
                A2.setColaUno(A2.getColaUno() - 1);
                acumCola2++;
                acumPermCola2 += reloj - histCola2.get(0);
                histCola2.remove(0);
            }
            if (A5.getE() == Estado.Libre) {
                A5.setColaUno(A5.getColaUno() + 1);
                histCola52.add(reloj);
                A5.setE(Estado.Bloqueado);
            } else if (A5.getE() == Estado.Bloqueado) {
                if (A5.getColaDos() == 0) {
                    A5.setColaUno(A5.getColaUno() + 1);
                    histCola52.add(reloj);
                } else {
                    A5.setMaterial(A5.getMaterial() + 1);
                    double randA5 = Math.random();
                    A5.setRnd(randA5);
                    double tiempoCalculadoA5 = A5.getCalcActividad().calcularTiempo(randA5);
                    A5.setTiempoAtencion(tiempoCalculadoA5);
                    A5.setProxFin(reloj + tiempoCalculadoA5);
                    A5.setColaDos(A5.getColaDos() - 1);
                    acumCola54++;
                    acumPermCola54 += reloj - histCola54.get(0);
                    histCola54.remove(0);
                    A5.setE(Estado.Ocupado);
                }
            } else if (A5.getE() == Estado.Ocupado) {
                A5.setColaUno(A5.getColaUno() + 1);
                histCola52.add(reloj);
            }
        }

        if (e == Evento.FinA3) {
            if (A3.getColaUno() == 0) {
                A3.setE(Estado.Libre);
                A3.setE(Estado.Libre);
                //A3.setColaUno(0);
                A3.setProxFin(999999);
                A3.setRnd(0.0);
                A3.setTiempoAtencion(0.0);
            } else if (A3.getColaUno() > 0) {
                A3.setMaterial(A3.getMaterial() + 1);
                double randA3 = Math.random();
                A3.setRnd(randA3);
                double tiempoCalculadoA3 = A3.getCalcActividad().calcularTiempo(randA3);
                A3.setTiempoAtencion(tiempoCalculadoA3);
                A3.setProxFin(reloj + tiempoCalculadoA3);
                A3.setColaUno(A3.getColaUno() - 1);
                acumCola3++;
                acumPermCola3 += reloj - histCola3.get(0);
                histCola3.remove(0);
            }
            if (estadoEnsamble == Estado.Libre) {
                if (this.colaA5 > 0 && this.colaA3 == 0) {
                    this.colaA5--;
                  //  System.out.println(solver.);
                    solver.calcularPrimeraFila();
                    for (int i = 0; i < solver.getMaxIt() - 1; i++) {
                        solver.calcularProxima();
                    }
                    solver.calcularSegundoPico();
                    tiempoAtencionEnsamble = solver.getSegundoMaxT();
                    proxFinEnsamble = reloj + tiempoAtencionEnsamble;
                    acumColaTerA5++;
                    acumPermColaTerA5 += reloj - histColaTerA5.get(0);
                    histColaTerA5.remove(0);
                    //this.tareasTerminadas++;
                  // double duracionEnsamble = reloj - tiemposInicio.get(tareasTerminadas - 1);
                   // acumEnsamble += duracionEnsamble;
                   // promedioEnsamble = acumEnsamble / tareasTerminadas;
                    estadoEnsamble = Estado.Ocupado;

                } else if (this.colaA5 == 0) {
                    this.colaA3++;
                    histColaTerA3.add(reloj);
                }
            } else if (estadoEnsamble == Estado.Ocupado) {
                this.colaA3++;
                histColaTerA3.add(reloj);
            }
        }

        if (e == Evento.FinA4) {
            if (A4.getColaUno() == 0) {
                A4.setE(Estado.Libre);
                A4.setColaUno(0);
                A4.setProxFin(999999);
                A4.setRnd(0.0);
                A4.setTiempoAtencion(0.0);
            } else if (A4.getColaUno() > 0) {
                A4.setMaterial(A4.getMaterial() + 1);
                double randA4 = Math.random();
                A4.setRnd(randA4);
                double tiempoCalculadoA4 = A4.getCalcActividad().calcularTiempo(randA4);
                A4.setTiempoAtencion(tiempoCalculadoA4);
                A4.setProxFin(reloj + tiempoCalculadoA4);
                A4.setColaUno(A4.getColaUno() - 1);
            }
            if (A5.getE() == Estado.Libre) {
                A5.setColaDos(A5.getColaDos() + 1);
                histCola54.add(reloj);
                A5.setE(Estado.Bloqueado);
            } else if (A5.getE() == Estado.Bloqueado) {
                if (A5.getColaUno() == 0) {
                    A5.setColaDos(A5.getColaDos() + 1);
                    histCola54.add(reloj);
                } else {
                    A5.setMaterial(A5.getMaterial() + 1);
                    double randA5 = Math.random();
                    A5.setRnd(randA5);
                    double tiempoCalculadoA5 = A5.getCalcActividad().calcularTiempo(randA5);
                    A5.setTiempoAtencion(tiempoCalculadoA5);
                    A5.setProxFin(reloj + tiempoCalculadoA5);
                    A5.setColaUno(A5.getColaUno() - 1);
                    acumCola52++;
                    acumPermCola52 += reloj - histCola52.get(0);
                    histCola52.remove(0);
                    A5.setE(Estado.Ocupado);
                }
            } else if (A5.getE() == Estado.Ocupado) {
                A5.setColaDos(A5.getColaDos() + 1);
                histCola54.add(reloj);
            }
        }

        if (e == Evento.FinA5) {
            if (A5.getColaDos() > 0 && A5.getColaUno() == 0) {
                A5.setE(Estado.Bloqueado);
                A5.setProxFin(999999);
                A5.setRnd(0.0);
                A5.setTiempoAtencion(0.0);
            } else if (A5.getColaUno() > 0 && A5.getColaDos() == 0) {
                A5.setE(Estado.Bloqueado);
                A5.setProxFin(999999);
                A5.setRnd(0.0);
                A5.setTiempoAtencion(0.0);
            } else if (A5.getColaDos() > 0 && A5.getColaUno() > 0) {
                A5.setE(Estado.Ocupado);
                A5.setMaterial(A5.getMaterial() + 1);
                double randA5 = Math.random();
                A5.setRnd(randA5);
                double tiempoCalculadoA5 = A5.getCalcActividad().calcularTiempo(randA5);
                A5.setTiempoAtencion(tiempoCalculadoA5);
                A5.setProxFin(reloj + tiempoCalculadoA5);
                A5.setColaDos(A5.getColaDos() - 1);
                acumCola54++;
                acumPermCola54 += reloj - histCola54.get(0);
                histCola54.remove(0);
                A5.setColaUno(A5.getColaUno() - 1);
                acumCola52++;
                acumPermCola52 += reloj - histCola52.get(0);
                histCola52.remove(0);
            } else if (A5.getColaDos() == 0 && A5.getColaUno() == 0) {
                A5.setE(Estado.Libre);
                A5.setProxFin(999999);
                A5.setRnd(0.0);
                A5.setTiempoAtencion(0.0);
            }
            if (estadoEnsamble == Estado.Libre) {
                if (this.colaA3 > 0) {
                    this.colaA3--;
                    solver.calcularPrimeraFila();
                    for (int i = 0; i < solver.getMaxIt() - 1; i++) {
                        solver.calcularProxima();
                    }
                    solver.calcularSegundoPico();
                    tiempoAtencionEnsamble = solver.getSegundoMaxT();
                    proxFinEnsamble = reloj + tiempoAtencionEnsamble;
                    acumColaTerA3++;
                    acumPermColaTerA3 += reloj - histColaTerA3.get(0);
                    histColaTerA3.remove(0);
                    //this.tareasTerminadas++;
                  //  double duracionEnsamble = reloj - tiemposInicio.get(tareasTerminadas - 1);
                   // acumEnsamble += duracionEnsamble;
                    //promedioEnsamble = acumEnsamble / tareasTerminadas;
                    estadoEnsamble = Estado.Ocupado;
                } else if (this.colaA3 == 0) {
                    this.colaA5++;
                    histColaTerA5.add(reloj);
                }
            } else {
                this.colaA5++;
                histColaTerA5.add(reloj);
            }
        }

        if (e == Evento.FinEncastre) {
            if (this.colaA3 > 0 && this.colaA5 > 0) {
                colaA3--;
                colaA5--;
                solver.calcularPrimeraFila();
                for (int i = 0; i < solver.getMaxIt() - 1; i++) {
                    solver.calcularProxima();
                }
                solver.calcularSegundoPico();
                tiempoAtencionEnsamble = solver.getSegundoMaxT();
                proxFinEnsamble = reloj + tiempoAtencionEnsamble;
                 
            }
            else {
                estadoEnsamble = Estado.Libre;
                proxFinEnsamble = 999999;
                tiempoAtencionEnsamble = 0.0;
            }
            this.tareasTerminadas++;
             double duracionEnsamble = reloj - tiemposInicio.get(tareasTerminadas - 1);
                    acumEnsamble += duracionEnsamble;
                    promedioEnsamble = acumEnsamble / tareasTerminadas;

        }

        promPermCola1 = acumPermCola1 / (double) acumCola1;
        promPermCola2 = acumPermCola2 / (double) acumCola2;
        promPermCola3 = acumPermCola3 / (double) acumCola3;
        promPermCola4 = acumPermCola4 / (double) acumCola4;
        promPermCola52 = acumPermCola52 / (double) acumCola52;
        promPermCola54 = acumPermCola54 / (double) acumCola54;
        promPermColaTerA3 = acumPermColaTerA3 / (double) acumColaTerA3;
        promPermColaTerA5 = acumPermColaTerA5 / (double) acumColaTerA5;

        proporcionEnsamble = (double) tareasTerminadas / (double) material;
        if (A1.getColaUno() > maxColaA1) {
            maxColaA1 = A1.getColaUno();
        }
        if (A2.getColaUno() > maxColaA2) {
            maxColaA2 = A2.getColaUno();
        }
        if (A3.getColaUno() > maxColaA3) {
            maxColaA3 = A3.getColaUno();
        }
        if (A4.getColaUno() > maxColaA4) {
            maxColaA4 = A4.getColaUno();
        }
        if (A5.getColaUno() > maxColaA5A2) {
            maxColaA5A2 = A5.getColaUno();
        }
        if (A5.getColaDos() > maxColaA5A4) {
            maxColaA5A4 = A5.getColaDos();
        }
        if (colaA3 > maxColaTerA3) {
            maxColaTerA3 = colaA3;
        }
        if (colaA5 > maxColaTerA5) {
            maxColaTerA5 = colaA5;
        }
        double temp = ((double) A1.getColaUno() * (reloj - tempreloj));
        promedioCantColaA1 = ((reloj * promedioCantColaA1) + temp) / reloj;

        temp = ((double) A2.getColaUno() * (reloj - tempreloj));
        promedioCantColaA2 = ((reloj * promedioCantColaA2) + temp) / reloj;

        temp = ((double) A3.getColaUno() * (reloj - tempreloj));
        promedioCantColaA3 = ((reloj * promedioCantColaA3) + temp) / reloj;

        temp = ((double) A4.getColaUno() * (reloj - tempreloj));
        promedioCantColaA4 = ((reloj * promedioCantColaA4) + temp) / reloj;

        temp = ((double) A5.getColaUno() * (reloj - tempreloj));
        promedioCantColaA52 = ((reloj * promedioCantColaA52) + temp) / reloj;

        temp = ((double) A5.getColaDos() * (reloj - tempreloj));
        promedioCantColaA54 = ((reloj * promedioCantColaA54) + temp) / reloj;

        temp = ((double) colaA3 * (reloj - tempreloj));
        promedioCantColaTerA3 = ((reloj * promedioCantColaTerA3) + temp) / reloj;

        temp = ((double) colaA5 * (reloj - tempreloj));
        promedioCantColaTerA5 = ((reloj * promedioCantColaTerA5) + temp) / reloj;

        double temp2 = ((double) (material - tareasTerminadas) * (reloj - tempreloj));
        promedioCantSistema = ((reloj * promedioCantSistema) + temp2) / reloj;

        if (A1.getE() == Estado.Ocupado) {
            acumOcupA1 = (reloj - tempreloj);
        } else {
            acumOcupA1 = 0;
        }
        porcOcupacionA1 = 100 * (((porcOcupacionA1 * tempreloj / 100) + acumOcupA1) / reloj);

        if (A2.getE() == Estado.Ocupado) {
            acumOcupA2 = (reloj - tempreloj);
        } else {
            acumOcupA2 = 0;
        }
        porcOcupacionA2 = 100 * (((porcOcupacionA2 * tempreloj / 100) + acumOcupA2) / reloj);

        if (A3.getE() == Estado.Ocupado) {
            acumOcupA3 = (reloj - tempreloj);
        } else {
            acumOcupA3 = 0;
        }
        porcOcupacionA3 = 100 * (((porcOcupacionA3 * tempreloj / 100) + acumOcupA3) / reloj);

        if (A4.getE() == Estado.Ocupado) {
            acumOcupA4 = (reloj - tempreloj);
        } else {
            acumOcupA4 = 0;
        }
        porcOcupacionA4 = 100 * (((porcOcupacionA4 * tempreloj / 100) + acumOcupA4) / reloj);

        if (A5.getE() == Estado.Ocupado) {
            acumOcupA5 = (reloj - tempreloj);
            //acumOcupA5paraBloq = (reloj - tempreloj);
        } else {
            acumOcupA5 = 0;
        }
        porcOcupacionA5 = 100 * (((porcOcupacionA5 * tempreloj / 100) + acumOcupA5) / reloj);

        if (tempA5 == Estado.Bloqueado) {
            acumBloqA5 += (reloj - tempreloj);
            acumOcupA5paraBloq += (reloj - tempreloj);
        }
        if (tempA5 == Estado.Ocupado) {
            acumOcupA5paraBloq += (reloj - tempreloj);
        }
        if (acumBloqA5 > 0) {
            porcBloqueoA5 = 100 * (acumBloqA5 / acumOcupA5paraBloq);
        }
        if (contHoras > 0) {
            promEnsamblesxHora = tareasTerminadas / contHoras;
        }

        contadorN++;

    }

    private double menorTiempo() {
        double min = Math.min(proxLlegada, A1.getProxFin());
        min = Math.min(min, A2.getProxFin());
        min = Math.min(min, A3.getProxFin());
        min = Math.min(min, A4.getProxFin());
        min = Math.min(min, A5.getProxFin());
        min = Math.min(min, proxFinEnsamble);

        return min;
    }

    public int getContadorN() {
        return contadorN;
    }

    public void setContadorN(int contadorN) {
        this.contadorN = contadorN;
    }

    public double getReloj() {
        return reloj;
    }

    public void setReloj(double reloj) {
        this.reloj = reloj;
    }

    public Evento getE() {
        return e;
    }

    public void setE(Evento e) {
        this.e = e;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }

    public int getProximoMaterial() {
        return proximoMaterial;
    }

    public void setProximoMaterial(int proximoMaterial) {
        this.proximoMaterial = proximoMaterial;
    }

    public double getRndPedido() {
        return rndPedido;
    }

    public void setRndPedido(double rndPedido) {
        this.rndPedido = rndPedido;
    }

    public double getTiempoEntreLlegadas() {
        return tiempoEntreLlegadas;
    }

    public void setTiempoEntreLlegadas(double tiempoEntreLlegadas) {
        this.tiempoEntreLlegadas = tiempoEntreLlegadas;
    }

    public double getProxLlegada() {
        return proxLlegada;
    }

    public void setProxLlegada(double proxLlegada) {
        this.proxLlegada = proxLlegada;
    }

    public Actividad getA1() {
        return A1;
    }

    public void setA1(Actividad A1) {
        this.A1 = A1;
    }

    public Actividad getA2() {
        return A2;
    }

    public void setA2(Actividad A2) {
        this.A2 = A2;
    }

    public Actividad getA3() {
        return A3;
    }

    public void setA3(Actividad A3) {
        this.A3 = A3;
    }

    public Actividad getA4() {
        return A4;
    }

    public void setA4(Actividad A4) {
        this.A4 = A4;
    }

    public Actividad getA5() {
        return A5;
    }

    public void setA5(Actividad A5) {
        this.A5 = A5;
    }

    public int getColaA3() {
        return colaA3;
    }

    public void setColaA3(int colaA3) {
        this.colaA3 = colaA3;
    }

    public int getColaA5() {
        return colaA5;
    }

    public void setColaA5(int colaA5) {
        this.colaA5 = colaA5;
    }

    public int getTareasTerminadas() {
        return tareasTerminadas;
    }

    public void setTareasTerminadas(int tareasTerminadas) {
        this.tareasTerminadas = tareasTerminadas;
    }

    public double getFinA4() {
        return finA4;
    }

    public void setFinA4(double finA4) {
        this.finA4 = finA4;
    }

    public IActividad getLlegadaActividadCalc() {
        return llegadaActividadCalc;
    }

    public void setLlegadaActividadCalc(IActividad llegadaActividadCalc) {
        this.llegadaActividadCalc = llegadaActividadCalc;
    }

    public ArrayList<Double> getTiemposInicio() {
        return tiemposInicio;
    }

    public void setTiemposInicio(ArrayList<Double> tiemposInicio) {
        this.tiemposInicio = tiemposInicio;
    }

    public double getAcumEnsamble() {
        return acumEnsamble;
    }

    public void setAcumEnsamble(double acumEnsamble) {
        this.acumEnsamble = acumEnsamble;
    }

    public double getPromedioEnsamble() {
        return promedioEnsamble;
    }

    public void setPromedioEnsamble(double promedioEnsamble) {
        this.promedioEnsamble = promedioEnsamble;
    }

    public double getProporcionEnsamble() {
        return proporcionEnsamble;
    }

    public void setProporcionEnsamble(double proporcionEnsamble) {
        this.proporcionEnsamble = proporcionEnsamble;
    }

    public int getMaxColaA1() {
        return maxColaA1;
    }

    public void setMaxColaA1(int maxColaA1) {
        this.maxColaA1 = maxColaA1;
    }

    public int getMaxColaA2() {
        return maxColaA2;
    }

    public void setMaxColaA2(int maxColaA2) {
        this.maxColaA2 = maxColaA2;
    }

    public int getMaxColaA3() {
        return maxColaA3;
    }

    public void setMaxColaA3(int maxColaA3) {
        this.maxColaA3 = maxColaA3;
    }

    public int getMaxColaA4() {
        return maxColaA4;
    }

    public void setMaxColaA4(int maxColaA4) {
        this.maxColaA4 = maxColaA4;
    }

    public int getMaxColaA5A2() {
        return maxColaA5A2;
    }

    public void setMaxColaA5A2(int maxColaA5A2) {
        this.maxColaA5A2 = maxColaA5A2;
    }

    public int getMaxColaA5A4() {
        return maxColaA5A4;
    }

    public double getTiempoAtencionEnsamble() {
        return tiempoAtencionEnsamble;
    }

    public void setTiempoAtencionEnsamble(double tiempoAtencionEnsamble) {
        this.tiempoAtencionEnsamble = tiempoAtencionEnsamble;
    }

    public double getProxFinEnsamble() {
        return proxFinEnsamble;
    }

    public void setProxFinEnsamble(double proxFinEnsamble) {
        this.proxFinEnsamble = proxFinEnsamble;
    }

    public IActividad getCalcActividadEnsamble() {
        return calcActividadEnsamble;
    }

    public void setCalcActividadEnsamble(IActividad calcActividadEnsamble) {
        this.calcActividadEnsamble = calcActividadEnsamble;
    }

    public Estado getEstadoEnsamble() {
        return estadoEnsamble;
    }

    public void setEstadoEnsamble(Estado estadoEnsamble) {
        this.estadoEnsamble = estadoEnsamble;
    }

    public IEDSolver getSolver() {
        return solver;
    }

    public void setSolver(IEDSolver solver) {
        this.solver = solver;
    }
    
    

    public void setMaxColaA5A4(int maxColaA5A4) {
        this.maxColaA5A4 = maxColaA5A4;
    }

    public ArrayList<Double> getHistCola1() {
        return histCola1;
    }

    public void setHistCola1(ArrayList<Double> histCola1) {
        this.histCola1 = histCola1;
    }

    public double getAcumPermCola1() {
        return acumPermCola1;
    }

    public void setAcumPermCola1(double acumPermCola1) {
        this.acumPermCola1 = acumPermCola1;
    }

    public double getPromPermCola1() {
        return promPermCola1;
    }

    public void setPromPermCola1(double promPermCola1) {
        this.promPermCola1 = promPermCola1;
    }

    public int getAcumCola1() {
        return acumCola1;
    }

    public void setAcumCola1(int acumCola1) {
        this.acumCola1 = acumCola1;
    }

    public ArrayList<Double> getHistCola2() {
        return histCola2;
    }

    public void setHistCola2(ArrayList<Double> histCola2) {
        this.histCola2 = histCola2;
    }

    public double getAcumPermCola2() {
        return acumPermCola2;
    }

    public void setAcumPermCola2(double acumPermCola2) {
        this.acumPermCola2 = acumPermCola2;
    }

    public double getPromPermCola2() {
        return promPermCola2;
    }

    public void setPromPermCola2(double promPermCola2) {
        this.promPermCola2 = promPermCola2;
    }

    public int getAcumCola2() {
        return acumCola2;
    }

    public void setAcumCola2(int acumCola2) {
        this.acumCola2 = acumCola2;
    }

    public ArrayList<Double> getHistCola3() {
        return histCola3;
    }

    public void setHistCola3(ArrayList<Double> histCola3) {
        this.histCola3 = histCola3;
    }

    public double getAcumPermCola3() {
        return acumPermCola3;
    }

    public void setAcumPermCola3(double acumPermCola3) {
        this.acumPermCola3 = acumPermCola3;
    }

    public double getPromPermCola3() {
        return promPermCola3;
    }

    public void setPromPermCola3(double promPermCola3) {
        this.promPermCola3 = promPermCola3;
    }

    public int getAcumCola3() {
        return acumCola3;
    }

    public void setAcumCola3(int acumCola3) {
        this.acumCola3 = acumCola3;
    }

    public ArrayList<Double> getHistCola4() {
        return histCola4;
    }

    public void setHistCola4(ArrayList<Double> histCola4) {
        this.histCola4 = histCola4;
    }

    public double getAcumPermCola4() {
        return acumPermCola4;
    }

    public void setAcumPermCola4(double acumPermCola4) {
        this.acumPermCola4 = acumPermCola4;
    }

    public double getPromPermCola4() {
        return promPermCola4;
    }

    public void setPromPermCola4(double promPermCola4) {
        this.promPermCola4 = promPermCola4;
    }

    public int getAcumCola4() {
        return acumCola4;
    }

    public void setAcumCola4(int acumCola4) {
        this.acumCola4 = acumCola4;
    }

    public ArrayList<Double> getHistCola52() {
        return histCola52;
    }

    public void setHistCola52(ArrayList<Double> histCola52) {
        this.histCola52 = histCola52;
    }

    public double getAcumPermCola52() {
        return acumPermCola52;
    }

    public void setAcumPermCola52(double acumPermCola52) {
        this.acumPermCola52 = acumPermCola52;
    }

    public double getPromPermCola52() {
        return promPermCola52;
    }

    public void setPromPermCola52(double promPermCola52) {
        this.promPermCola52 = promPermCola52;
    }

    public int getAcumCola52() {
        return acumCola52;
    }

    public void setAcumCola52(int acumCola52) {
        this.acumCola52 = acumCola52;
    }

    public ArrayList<Double> getHistCola54() {
        return histCola54;
    }

    public void setHistCola54(ArrayList<Double> histCola54) {
        this.histCola54 = histCola54;
    }

    public double getAcumPermCola54() {
        return acumPermCola54;
    }

    public void setAcumPermCola54(double acumPermCola54) {
        this.acumPermCola54 = acumPermCola54;
    }

    public double getPromPermCola54() {
        return promPermCola54;
    }

    public void setPromPermCola54(double promPermCola54) {
        this.promPermCola54 = promPermCola54;
    }

    public int getAcumCola54() {
        return acumCola54;
    }

    public void setAcumCola54(int acumCola54) {
        this.acumCola54 = acumCola54;
    }

    public int getMaxColaTerA3() {
        return maxColaTerA3;
    }

    public void setMaxColaTerA3(int maxColaTerA3) {
        this.maxColaTerA3 = maxColaTerA3;
    }

    public int getMaxColaTerA5() {
        return maxColaTerA5;
    }

    public void setMaxColaTerA5(int maxColaTerA5) {
        this.maxColaTerA5 = maxColaTerA5;
    }

    public ArrayList<Double> getHistColaTerA3() {
        return histColaTerA3;
    }

    public void setHistColaTerA3(ArrayList<Double> histColaTerA3) {
        this.histColaTerA3 = histColaTerA3;
    }

    public double getAcumPermColaTerA3() {
        return acumPermColaTerA3;
    }

    public void setAcumPermColaTerA3(double acumPermColaTerA3) {
        this.acumPermColaTerA3 = acumPermColaTerA3;
    }

    public double getPromPermColaTerA3() {
        return promPermColaTerA3;
    }

    public void setPromPermColaTerA3(double promPermColaTerA3) {
        this.promPermColaTerA3 = promPermColaTerA3;
    }

    public int getAcumColaTerA3() {
        return acumColaTerA3;
    }

    public void setAcumColaTerA3(int acumColaTerA3) {
        this.acumColaTerA3 = acumColaTerA3;
    }

    public ArrayList<Double> getHistColaTerA5() {
        return histColaTerA5;
    }

    public void setHistColaTerA5(ArrayList<Double> histColaTerA5) {
        this.histColaTerA5 = histColaTerA5;
    }

    public double getAcumPermColaTerA5() {
        return acumPermColaTerA5;
    }

    public void setAcumPermColaTerA5(double acumPermColaTerA5) {
        this.acumPermColaTerA5 = acumPermColaTerA5;
    }

    public double getPromPermColaTerA5() {
        return promPermColaTerA5;
    }

    public void setPromPermColaTerA5(double promPermColaTerA5) {
        this.promPermColaTerA5 = promPermColaTerA5;
    }

    public int getAcumColaTerA5() {
        return acumColaTerA5;
    }

    public void setAcumColaTerA5(int acumColaTerA5) {
        this.acumColaTerA5 = acumColaTerA5;
    }

    public double getPromedioCantColaA1() {
        return promedioCantColaA1;
    }

    public void setPromedioCantColaA1(double promedioCantColaA1) {
        this.promedioCantColaA1 = promedioCantColaA1;
    }

    public double getPromedioCantColaA2() {
        return promedioCantColaA2;
    }

    public void setPromedioCantColaA2(double promedioCantColaA2) {
        this.promedioCantColaA2 = promedioCantColaA2;
    }

    public double getPromedioCantColaA3() {
        return promedioCantColaA3;
    }

    public void setPromedioCantColaA3(double promedioCantColaA3) {
        this.promedioCantColaA3 = promedioCantColaA3;
    }

    public double getPromedioCantColaA4() {
        return promedioCantColaA4;
    }

    public void setPromedioCantColaA4(double promedioCantColaA4) {
        this.promedioCantColaA4 = promedioCantColaA4;
    }

    public double getPromedioCantColaA52() {
        return promedioCantColaA52;
    }

    public void setPromedioCantColaA52(double promedioCantColaA52) {
        this.promedioCantColaA52 = promedioCantColaA52;
    }

    public double getPromedioCantColaA54() {
        return promedioCantColaA54;
    }

    public void setPromedioCantColaA54(double promedioCantColaA54) {
        this.promedioCantColaA54 = promedioCantColaA54;
    }

    public double getPromedioCantColaTerA3() {
        return promedioCantColaTerA3;
    }

    public void setPromedioCantColaTerA3(double promedioCantColaTerA3) {
        this.promedioCantColaTerA3 = promedioCantColaTerA3;
    }

    public double getPromedioCantColaTerA5() {
        return promedioCantColaTerA5;
    }

    public void setPromedioCantColaTerA5(double promedioCantColaTerA5) {
        this.promedioCantColaTerA5 = promedioCantColaTerA5;
    }

    public double getPromedioCantSistema() {
        return promedioCantSistema;
    }

    public void setPromedioCantSistema(double promedioCantSistema) {
        this.promedioCantSistema = promedioCantSistema;
    }

    public double getAcumOcupA1() {
        return acumOcupA1;
    }

    public void setAcumOcupA1(double acumOcupA1) {
        this.acumOcupA1 = acumOcupA1;
    }

    public double getPorcOcupacionA1() {
        return porcOcupacionA1;
    }

    public void setPorcOcupacionA1(double porcOcupacionA1) {
        this.porcOcupacionA1 = porcOcupacionA1;
    }

    public double getAcumOcupA2() {
        return acumOcupA2;
    }

    public void setAcumOcupA2(double acumOcupA2) {
        this.acumOcupA2 = acumOcupA2;
    }

    public double getAcumOcupA3() {
        return acumOcupA3;
    }

    public void setAcumOcupA3(double acumOcupA3) {
        this.acumOcupA3 = acumOcupA3;
    }

    public double getAcumOcupA4() {
        return acumOcupA4;
    }

    public void setAcumOcupA4(double acumOcupA4) {
        this.acumOcupA4 = acumOcupA4;
    }

    public double getAcumOcupA5() {
        return acumOcupA5;
    }

    public void setAcumOcupA5(double acumOcupA5) {
        this.acumOcupA5 = acumOcupA5;
    }

    public double getPorcOcupacionA2() {
        return porcOcupacionA2;
    }

    public void setPorcOcupacionA2(double porcOcupacionA2) {
        this.porcOcupacionA2 = porcOcupacionA2;
    }

    public double getPorcOcupacionA3() {
        return porcOcupacionA3;
    }

    public void setPorcOcupacionA3(double porcOcupacionA3) {
        this.porcOcupacionA3 = porcOcupacionA3;
    }

    public double getPorcOcupacionA4() {
        return porcOcupacionA4;
    }

    public void setPorcOcupacionA4(double porcOcupacionA4) {
        this.porcOcupacionA4 = porcOcupacionA4;
    }

    public double getPorcOcupacionA5() {
        return porcOcupacionA5;
    }

    public void setPorcOcupacionA5(double porcOcupacionA5) {
        this.porcOcupacionA5 = porcOcupacionA5;
    }

    public double getAcumBloqA5() {
        return acumBloqA5;
    }

    public void setAcumBloqA5(double acumBloqA5) {
        this.acumBloqA5 = acumBloqA5;
    }

    public double getPorcBloqueoA5() {
        return porcBloqueoA5;
    }

    public void setPorcBloqueoA5(double porcBloqueoA5) {
        this.porcBloqueoA5 = porcBloqueoA5;
    }

    public double getAcumOcupA5paraBloq() {
        return acumOcupA5paraBloq;
    }

    public void setAcumOcupA5paraBloq(double acumOcupA5paraBloq) {
        this.acumOcupA5paraBloq = acumOcupA5paraBloq;
    }

    public double getContHoras() {
        return contHoras;
    }

    public void setContHoras(double contHoras) {
        this.contHoras = contHoras;
    }

    public double getAcumHoras() {
        return acumHoras;
    }

    public void setAcumHoras(double acumHoras) {
        this.acumHoras = acumHoras;
    }

    public double getCantEnsamblesxHora() {
        return cantEnsamblesxHora;
    }

    public void setCantEnsamblesxHora(double cantEnsamblesxHora) {
        this.cantEnsamblesxHora = cantEnsamblesxHora;
    }

    public double getPromEnsamblesxHora() {
        return promEnsamblesxHora;
    }

    public void setPromEnsamblesxHora(double promEnsamblesxHora) {
        this.promEnsamblesxHora = promEnsamblesxHora;
    }

    public double getAcumEnsamblesxHora() {
        return acumEnsamblesxHora;
    }

    public void setAcumEnsamblesxHora(double acumEnsamblesxHora) {
        this.acumEnsamblesxHora = acumEnsamblesxHora;
    }

    public double getContMasDe3() {
        return contMasDe3;
    }

    public void setContMasDe3(double contMasDe3) {
        this.contMasDe3 = contMasDe3;
    }

}
