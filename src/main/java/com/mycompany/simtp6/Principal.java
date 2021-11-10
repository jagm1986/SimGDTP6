/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simtp6;

//TODO Agregar validaciones para parametros normal y exponencial
//TODO Ultimo punto con los 15 intervalos
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.*;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Usuario
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    private DefaultTableModel tabla = new DefaultTableModel();
    //A
    int N = 0;
    int desde = 0;
    int hasta = 0;
    int cantParaProb;
    double lambdaLl = 0;
    private IActividad auxA1;
    private IActividad auxA2;
    private IActividad auxA3;
    private IActividad auxA4;
    private IActividad auxA5;
    private Fila aux1;
    private Fila aux2;
    private double promedio = 0;
    private double maximo = 0;
    private double minimo = 0;
    private int counterProbabilidad45dias = 0;
    private double bInicial, cInicial, hInicial, x0Inicial, dx0Inicial, aUnif, bUnif;
    long a = 0;
    long c = 0;
    long m = 0;
    long semilla = 0;
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    IEDSolver solverPrincipal;
    private ArrayList<IEDSolver> historialSolvers = new ArrayList();

    DecimalFormat df = new DecimalFormat("#.####");

    private JFrame pantallaActual;

    private Object[] filaAImprimir = new Object[89];
    private Object[] columna = {"Nro Sim", "Reloj", "Evento", "Nro Material", "Proximo Mat", "Rnd Llegada", "T Entre Llegadas"
            , "Prox Llegada", "Estado A1", "Material A1", "rnd A1", "T Atencion A1", "Prox fin A1", "Cola A1"
            , "Estado A2", "Material A2", "rnd A2", "T Atencion A2", "Prox fin A2", "Cola A2", "Estado A3", "Material A3", "rnd A3", "T Atencion A3"
            , "Prox fin A3", "Cola A3", "Estado A4", "Material A4", "rnd A4", "T Atencion A4", "Prox fin A4", "Cola A4", "Estado A5", "Material A5"
            , "rnd A5", "T Atencion A5", "Prox fin A5", "Cola A2 (A5)", "Cola A4 (A5)", "Cola Terminados A3", "Cola Terminados A5","Estado Ensambladora", "Tiempo ensamble", "Proximo Fin Ensamble","Tareas terminadas"
            , "Acum T Ensambles", "Prom T ensambles", "Proporcion Ensambles", "Max Cola A1", "Max Cola A2", "Max Cola A3", "Max Cola A4", "Max Cola A5(A2)"
            , "Max Cola A5(A4)", "Max Cola Ter A3", "Max Cola Ter A5",  "Acum Perm Cola A1", "Prom Perm Cola A1", "Acum Perm Cola A2", "Prom Perm Cola A2",
            "Acum Perm Cola A3", "Prom Perm Cola A3", "Acum Perm Cola A4", "Prom Perm Cola A4", "Acum Perm Cola A5(A2)", "Prom Perm Cola A5(A2)", "Acum Perm Cola A5(A4)",
            "Prom Perm Cola A5(A4)", "Acum Perm Cola Term A3", "Prom Perm Cola Term A3", "Acum Perm Cola Term A5", "Prom Perm Cola Term A5", "Cant prom en Cola A1",
            "Cant prom en Cola A2", "Cant prom en Cola A3", "Cant prom en Cola A4", "Cant prom en Cola A52", "Cant prom en Cola A54",
            "Cant prom en Cola Ter A3","Cant prom en Cola Ter A5", "Cant Promedio en sistema", "% Ocup A1", "% Ocup A2", "% Ocup A3", "% Ocup A4", "% Ocup A5", "% Bloq/Ocup"
            , "Promedio Ter x Hora", "Cant Ter x Hora"};

    public Principal(IActividad auxA1, IActividad auxA2, IActividad auxA3, IActividad auxA4, IActividad auxA5, Fila aux1, Fila aux2, JFrame pantallaActual, double masBajo, JButton BtnSimular, JTable Tabla, JButton btnEvaluar, ButtonGroup buttonGroup1, ButtonGroup buttonGroup2, ButtonGroup buttonGroup3, ButtonGroup buttonGroup4, ButtonGroup buttonGroup5, ButtonGroup buttonGroup6, JLabel jLabel1, JLabel jLabel10, JLabel jLabel11, JLabel jLabel12, JLabel jLabel13, JLabel jLabel14, JLabel jLabel15, JLabel jLabel16, JLabel jLabel17, JLabel jLabel18, JLabel jLabel19, JLabel jLabel2, JLabel jLabel20, JLabel jLabel21, JLabel jLabel22, JLabel jLabel23, JLabel jLabel24, JLabel jLabel25, JLabel jLabel26, JLabel jLabel27, JLabel jLabel28, JLabel jLabel29, JLabel jLabel3, JLabel jLabel30, JLabel jLabel31, JLabel jLabel32, JLabel jLabel33, JLabel jLabel34, JLabel jLabel35, JLabel jLabel4, JLabel jLabel5, JLabel jLabel6, JLabel jLabel7, JLabel jLabel8, JLabel jLabel9, JScrollPane jScrollPane1, JTextField lambdaLlegadas, JLabel lblIntegrantes, JRadioButton radioButtonExpA1, JRadioButton radioButtonExpA2, JRadioButton radioButtonExpA3, JRadioButton radioButtonExpA4, JRadioButton radioButtonExpA5, JRadioButton radioButtonNormalA1, JRadioButton radioButtonNormalA2, JRadioButton radioButtonNormalA3, JRadioButton radioButtonNormalA4, JRadioButton radioButtonNormalA5, JRadioButton radioButtonUniformeA1, JRadioButton radioButtonUniformeA2, JRadioButton radioButtonUniformeA3, JRadioButton radioButtonUniformeA4, JRadioButton radioButtonUniformeA5, JTextField textLambdaA1, JTextField textLambdaA2, JTextField textLambdaA3, JTextField textLambdaA4, JTextField textLambdaA5, JTextField textMuA1, JTextField textMuA2, JTextField textMuA3, JTextField textMuA4, JTextField textMuA5, JTextField textSigmaA1, JTextField textSigmaA2, JTextField textSigmaA3, JTextField textSigmaA4, JTextField textSigmaA5, JTextField textaA1, JTextField textaA2, JTextField textaA3, JTextField textaA4, JTextField textaA5, JTextField textbA1, JTextField textbA2, JTextField textbA3, JTextField textbA4, JTextField textbA5, JTextField txtCantidadFilas, JTextField txtDesde, JTextField txtHasta) throws HeadlessException {
        this.auxA1 = auxA1;
        this.auxA2 = auxA2;
        this.auxA3 = auxA3;
        this.auxA4 = auxA4;
        this.auxA5 = auxA5;
        this.aux1 = aux1;
        this.aux2 = aux2;
        this.pantallaActual = pantallaActual;
        this.masBajo = masBajo;
        this.BtnSimular = BtnSimular;
        this.Tabla = Tabla;

        this.buttonGroup1 = buttonGroup1;
        this.buttonGroup2 = buttonGroup2;
        this.buttonGroup3 = buttonGroup3;
        this.buttonGroup4 = buttonGroup4;
        this.buttonGroup5 = buttonGroup5;
        this.buttonGroup6 = buttonGroup6;

        this.jScrollPane1 = jScrollPane1;
        this.lambdaLlegadas = lambdaLlegadas;
        this.lblIntegrantes = lblIntegrantes;

        this.txtCantidadFilas = txtCantidadFilas;
        this.txtDesde = txtDesde;
        this.txtHasta = txtHasta;
    }

    private String llenarTiempo(double a) {
        if (a == 999999.0 ) {
            return "-";
        }
        return convertirAReloj(a);

    }

    private String convertirAReloj(double a) {
        if (a == 0.0) {
            return "-";
        }
        int parteEntera = (int) a;
        int horas = (int) a / 60;
        int min = parteEntera % 60;
        double resto = a - parteEntera;
        int seg = (int) (resto * 60);

        String horasForm = String.format("%02d", horas);
        String minForm = String.format("%02d", min);
        String segForm = String.format("%02d", seg);
        return horasForm + ":" + minForm + ":" + segForm;
    }

    private void llenarFila(Fila aux) {
        filaAImprimir[0] = (int) aux.getContadorN();
        filaAImprimir[1] = convertirAReloj(aux.getReloj());
        filaAImprimir[2] = aux.getE().toString();
        filaAImprimir[3] = aux.getMaterial();
        filaAImprimir[4] = aux.getProximoMaterial();
        filaAImprimir[5] = df.format(aux.getRndPedido());
        filaAImprimir[6] = convertirAReloj(aux.getTiempoEntreLlegadas());
        filaAImprimir[7] = llenarTiempo(aux.getProxLlegada());
        filaAImprimir[8] = aux.getA1().getE().toString();
        filaAImprimir[9] = aux.getA1().getMaterial();
        filaAImprimir[10] = df.format(aux.getA1().getRnd());
        filaAImprimir[11] = convertirAReloj(aux.getA1().getTiempoAtencion());
        filaAImprimir[12] = llenarTiempo(aux.getA1().getProxFin());
        filaAImprimir[13] = aux.getA1().getColaUno();
        filaAImprimir[14] = aux.getA2().getE().toString();
        filaAImprimir[15] = aux.getA2().getMaterial();
        filaAImprimir[16] = df.format(aux.getA2().getRnd());
        filaAImprimir[17] = convertirAReloj(aux.getA2().getTiempoAtencion());
        filaAImprimir[18] = llenarTiempo(aux.getA2().getProxFin());
        filaAImprimir[19] = aux.getA2().getColaUno();
        filaAImprimir[20] = aux.getA3().getE().toString();
        filaAImprimir[21] = aux.getA3().getMaterial();
        filaAImprimir[22] = df.format(aux.getA3().getRnd());
        filaAImprimir[23] = convertirAReloj(aux.getA3().getTiempoAtencion());
        filaAImprimir[24] = llenarTiempo(aux.getA3().getProxFin());
        filaAImprimir[25] = aux.getA3().getColaUno();
        filaAImprimir[26] = aux.getA4().getE().toString();
        filaAImprimir[27] = aux.getA4().getMaterial();
        filaAImprimir[28] = df.format(aux.getA4().getRnd());
        filaAImprimir[29] = convertirAReloj(aux.getA4().getTiempoAtencion());
        filaAImprimir[30] = llenarTiempo(aux.getA4().getProxFin());
        filaAImprimir[31] = aux.getA4().getColaUno();
        filaAImprimir[32] = aux.getA5().getE().toString();
        filaAImprimir[33] = aux.getA5().getMaterial();
        filaAImprimir[34] = df.format(aux.getA5().getRnd());
        filaAImprimir[35] = convertirAReloj(aux.getA5().getTiempoAtencion());
        filaAImprimir[36] = llenarTiempo(aux.getA5().getProxFin());
        filaAImprimir[37] = aux.getA5().getColaUno();
        filaAImprimir[38] = aux.getA5().getColaDos();
        filaAImprimir[39] = aux.getColaA3();
        filaAImprimir[40] = aux.getColaA5();
        filaAImprimir[41] = aux.getEstadoEnsamble().toString();
        filaAImprimir[42] = convertirAReloj(aux.getTiempoAtencionEnsamble());
        filaAImprimir[43] = llenarTiempo(aux.getProxFinEnsamble());
        filaAImprimir[44] = aux.getTareasTerminadas();
        filaAImprimir[45] = convertirAReloj(aux.getAcumEnsamble());
        filaAImprimir[46] = convertirAReloj(aux.getPromedioEnsamble());
        filaAImprimir[47] = df.format(aux.getProporcionEnsamble());
        filaAImprimir[48] = aux.getMaxColaA1();
        filaAImprimir[49] = aux.getMaxColaA2();
        filaAImprimir[50] = aux.getMaxColaA3();
        filaAImprimir[51] = aux.getMaxColaA4();
        filaAImprimir[52] = aux.getMaxColaA5A2();
        filaAImprimir[53] = aux.getMaxColaA5A4();
        filaAImprimir[54] = aux.getMaxColaTerA3();
        filaAImprimir[55] = aux.getMaxColaTerA5();
        filaAImprimir[56] = convertirAReloj(aux.getAcumPermCola1());
        filaAImprimir[57] = convertirAReloj(aux.getPromPermCola1());
        filaAImprimir[58] = convertirAReloj(aux.getAcumPermCola2());
        filaAImprimir[59] = convertirAReloj(aux.getPromPermCola2());
        filaAImprimir[60] = convertirAReloj(aux.getAcumPermCola3());
        filaAImprimir[61] = convertirAReloj(aux.getPromPermCola3());
        filaAImprimir[62] = convertirAReloj(aux.getAcumPermCola4());
        filaAImprimir[63] = convertirAReloj(aux.getPromPermCola4());
        filaAImprimir[64] = convertirAReloj(aux.getAcumPermCola52());
        filaAImprimir[65] = convertirAReloj(aux.getPromPermCola52());
        filaAImprimir[66] = convertirAReloj(aux.getAcumPermCola54());
        filaAImprimir[67] = convertirAReloj(aux.getPromPermCola54());
        filaAImprimir[68] = convertirAReloj(aux.getAcumPermColaTerA3());
        filaAImprimir[69] = convertirAReloj(aux.getPromPermColaTerA3());
        filaAImprimir[70] = convertirAReloj(aux.getAcumPermColaTerA5());
        filaAImprimir[71] = convertirAReloj(aux.getPromPermColaTerA5());
        filaAImprimir[72] = df.format(aux.getPromedioCantColaA1());
        filaAImprimir[73] = df.format(aux.getPromedioCantColaA2());
        filaAImprimir[74] = df.format(aux.getPromedioCantColaA3());
        filaAImprimir[75] = df.format(aux.getPromedioCantColaA4());
        filaAImprimir[76] = df.format(aux.getPromedioCantColaA52());
        filaAImprimir[77] = df.format(aux.getPromedioCantColaA54());
        filaAImprimir[78] = df.format(aux.getPromedioCantColaTerA3());
        filaAImprimir[79] = df.format(aux.getPromedioCantColaTerA5());
        filaAImprimir[80] = df.format(aux.getPromedioCantSistema());
        filaAImprimir[81] = df.format(aux.getPorcOcupacionA1());
        filaAImprimir[82] = df.format(aux.getPorcOcupacionA2());
        filaAImprimir[83] = df.format(aux.getPorcOcupacionA3());
        filaAImprimir[84] = df.format(aux.getPorcOcupacionA4());
        filaAImprimir[85] = df.format(aux.getPorcOcupacionA5());
        filaAImprimir[86] = df.format(aux.getPorcBloqueoA5());
        filaAImprimir[87] = df.format(aux.getPromEnsamblesxHora());
        filaAImprimir[88] = df.format(aux.getCantEnsamblesxHora());
    }

    /*private void llenarFila(Fila aux) {
        filaAImprimir[0] = (int) aux.getContadorN();
        filaAImprimir[1] = df.format(aux.getReloj());
        filaAImprimir[2] = aux.getE().toString();
        filaAImprimir[3] = aux.getMaterial();
        filaAImprimir[4] = aux.getProximoMaterial();
        filaAImprimir[5] = df.format(aux.getRndPedido());
        filaAImprimir[6] = convertirAReloj(aux.getTiempoEntreLlegadas());
        filaAImprimir[7] = llenarTiempo(aux.getProxLlegada());
        filaAImprimir[8] = aux.getA1().getE().toString();
        filaAImprimir[9] = aux.getA1().getMaterial();
        filaAImprimir[10] = df.format(aux.getA1().getRnd());
        filaAImprimir[11] = aux.getA1().getTiempoAtencion();
        filaAImprimir[12] = llenarTiempo(aux.getA1().getProxFin());
        filaAImprimir[13] = aux.getA1().getColaUno();
        filaAImprimir[14] = aux.getA2().getE().toString();
        filaAImprimir[15] = aux.getA2().getMaterial();
        filaAImprimir[16] = df.format(aux.getA2().getRnd());
        filaAImprimir[17] = aux.getA2().getTiempoAtencion();
        filaAImprimir[18] = llenarTiempo(aux.getA2().getProxFin());
        filaAImprimir[19] = aux.getA2().getColaUno();
        filaAImprimir[20] = aux.getA1().getE().toString();
        filaAImprimir[21] = aux.getA3().getMaterial();
        filaAImprimir[22] = df.format(aux.getA3().getRnd());
        filaAImprimir[23] = aux.getA3().getTiempoAtencion();
        filaAImprimir[24] = llenarTiempo(aux.getA3().getProxFin());
        filaAImprimir[25] = aux.getA3().getColaUno();
        filaAImprimir[26] = aux.getA4().getE().toString();
        filaAImprimir[27] = aux.getA4().getMaterial();
        filaAImprimir[28] = df.format(aux.getA4().getRnd());
        filaAImprimir[29] = aux.getA4().getTiempoAtencion();
        filaAImprimir[30] = llenarTiempo(aux.getA4().getProxFin());
        filaAImprimir[31] = aux.getA4().getColaUno();
        filaAImprimir[32] = aux.getA5().getE().toString();
        filaAImprimir[33] = aux.getA5().getMaterial();
        filaAImprimir[34] = df.format(aux.getA5().getRnd());
        filaAImprimir[35] = aux.getA5().getTiempoAtencion();
        filaAImprimir[36] = llenarTiempo(aux.getA5().getProxFin());
        filaAImprimir[37] = aux.getA5().getColaUno();
        filaAImprimir[38] = aux.getA5().getColaDos();
        filaAImprimir[39] = aux.getColaA3();
        filaAImprimir[40] = aux.getColaA5();
        filaAImprimir[41] = aux.getTareasTerminadas();

    }*/
    private boolean validarNumerosNulos(String a, String b) {

        if (a.equals("") || b.equals("")) {
            JOptionPane.showMessageDialog(new JFrame(), "Parametros no ingresados o deben ser mayores a 0", "Parámetros incorrectos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validarNumerosNulos(String a) {

        if (a.equals("")) {
            JOptionPane.showMessageDialog(new JFrame(), "Parametro no ingresado o deben ser mayor a 0", "Parámetro incorrecto", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;

    }

    public Principal() {

        initComponents();
        pantallaActual = new JFrame();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        BtnSimular = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtCantidadFilas = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDesde = new javax.swing.JTextField();
        txtHasta = new javax.swing.JTextField();
        lblIntegrantes = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        lambdaLlegadas = new javax.swing.JTextField();
        txtCantXhora = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        rButtonEventos = new javax.swing.JRadioButton();
        rButtonEnsambles = new javax.swing.JRadioButton();
        txtProbMas3 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        aUnifTxt = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        bUnifTxt = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        bTxt = new javax.swing.JTextField();
        cTxt = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        hTxt = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        x0Txt = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        dx0Txt = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        rButtonRungeKutta = new javax.swing.JRadioButton();
        rButtonEuler = new javax.swing.JRadioButton();
        jLabel44 = new javax.swing.JLabel();
        graficarTxt = new javax.swing.JTextField();
        graficarBtn = new javax.swing.JButton();
        graficarBtn1 = new javax.swing.JButton();
        graficarBtn2 = new javax.swing.JButton();
        graficarBtn3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 204, 204));

        Tabla.setBackground(new java.awt.Color(204, 204, 255));
        Tabla.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        Tabla.setGridColor(new java.awt.Color(0, 0, 0));
        Tabla.setShowGrid(true);
        jScrollPane1.setViewportView(Tabla);

        BtnSimular.setText("Simular");
        BtnSimular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimularActionPerformed(evt);
            }
        });

        jLabel2.setText("Cantidad de Simulaciones");

        txtCantidadFilas.setText("1000");
        txtCantidadFilas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadFilasActionPerformed(evt);
            }
        });

        jLabel3.setText("Desde");

        jLabel4.setText("Hasta");

        txtDesde.setText("0");

        txtHasta.setText("1000");

        lblIntegrantes.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        lblIntegrantes.setText("Integrantes: Dominguez Ariel, Gudin Andres, Juarez Diego, Paglia Matias, Toia Lucia");

        jLabel34.setText("Pedidos que llegan por hora:");

        lambdaLlegadas.setText("3");
        lambdaLlegadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lambdaLlegadasActionPerformed(evt);
            }
        });

        txtCantXhora.setText("3");
        txtCantXhora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantXhoraActionPerformed(evt);
            }
        });

        jLabel35.setText("Probabilidad de que sea menos de ");

        jLabel36.setText("por hora");

        buttonGroup1.add(rButtonEventos);
        rButtonEventos.setSelected(true);
        rButtonEventos.setText("Eventos");

        buttonGroup1.add(rButtonEnsambles);
        rButtonEnsambles.setText("Ensambles");
        rButtonEnsambles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButtonEnsamblesActionPerformed(evt);
            }
        });

        jLabel37.setText("a Unif:");

        aUnifTxt.setText("0.5");
        aUnifTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aUnifTxtActionPerformed(evt);
            }
        });

        jLabel38.setText("b Unif: ");

        bUnifTxt.setText("2");
        bUnifTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUnifTxtActionPerformed(evt);
            }
        });

        jLabel39.setText("b:");

        bTxt.setText("2.5");
        bTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTxtActionPerformed(evt);
            }
        });

        cTxt.setText("5");
        cTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cTxtActionPerformed(evt);
            }
        });

        jLabel40.setText("c:");

        hTxt.setText("0.05");
        hTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hTxtActionPerformed(evt);
            }
        });

        jLabel41.setText("h:");

        x0Txt.setText("0");
        x0Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                x0TxtActionPerformed(evt);
            }
        });

        jLabel42.setText("x0:");

        dx0Txt.setText("0");
        dx0Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dx0TxtActionPerformed(evt);
            }
        });

        jLabel43.setText("dx0:");

        buttonGroup2.add(rButtonRungeKutta);
        rButtonRungeKutta.setSelected(true);
        rButtonRungeKutta.setText("Runge Kutta");
        rButtonRungeKutta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButtonRungeKuttaActionPerformed(evt);
            }
        });

        buttonGroup2.add(rButtonEuler);
        rButtonEuler.setText("Euler");
        rButtonEuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButtonEulerActionPerformed(evt);
            }
        });

        jLabel44.setText("Graficar ensamble: ");

        graficarTxt.setText("1");
        graficarTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficarTxtActionPerformed(evt);
            }
        });

        graficarBtn.setText("Graficar Todo en funcion de t");
        graficarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficarBtnActionPerformed(evt);
            }
        });

        graficarBtn1.setText("Graficar x'' en funcion de x");
        graficarBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficarBtn1ActionPerformed(evt);
            }
        });

        graficarBtn2.setText("Graficar x' en funcion de x");
        graficarBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficarBtn2ActionPerformed(evt);
            }
        });

        graficarBtn3.setText("Graficar x'' en funcion de x'");
        graficarBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficarBtn3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtProbMas3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCantidadFilas, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(rButtonEventos)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rButtonEnsambles)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel34)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lambdaLlegadas, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23)
                                        .addComponent(jLabel35)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCantXhora, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel36)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnSimular, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(lblIntegrantes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(216, 216, 216))))
            .addGroup(layout.createSequentialGroup()
                .addGap(387, 387, 387)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(aUnifTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel39)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel40)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel38)
                                            .addComponent(jLabel42))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(x0Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(bUnifTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(jLabel43)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dx0Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(9, 9, 9))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(hTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(102, 102, 102)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(496, 496, 496)
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(graficarTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rButtonEuler)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rButtonRungeKutta)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(graficarBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(graficarBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(graficarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(graficarBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1058, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(210, 210, 210))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCantidadFilas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rButtonEventos)
                        .addComponent(rButtonEnsambles))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BtnSimular)
                        .addComponent(txtDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(txtHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lambdaLlegadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantXhora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtProbMas3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(aUnifTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bUnifTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(x0Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dx0Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rButtonRungeKutta)
                                    .addComponent(rButtonEuler)
                                    .addComponent(hTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(graficarTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(graficarBtn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(graficarBtn1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(graficarBtn2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(graficarBtn3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)))
                .addComponent(lblIntegrantes, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    double masBajo;
    private void BtnSimularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimularActionPerformed
        // TODO add your handling code here:
        cantParaProb = Integer.parseInt(txtCantXhora.getText());
        N = Integer.parseInt(txtCantidadFilas.getText());
        desde = Integer.parseInt(txtDesde.getText());
        hasta = Integer.parseInt(txtHasta.getText());
        lambdaLl = Double.parseDouble(lambdaLlegadas.getText());
        aUnif = Double.parseDouble(aUnifTxt.getText());
        bUnif = Double.parseDouble(bUnifTxt.getText());
        bInicial = Double.parseDouble(bTxt.getText());
        cInicial = Double.parseDouble(cTxt.getText());
        hInicial = Double.parseDouble(hTxt.getText());
        x0Inicial = Double.parseDouble(x0Txt.getText());
        dx0Inicial = Double.parseDouble(dx0Txt.getText());
        
        
        if(lambdaLl <= 0){
            lambdaLl = 0.1;
        }else if (lambdaLl > 30){
            lambdaLl = 30;
        }

        lambdaLl = lambdaLl / 60;

        tabla = new DefaultTableModel();
        tabla.setColumnIdentifiers(columna);

        
        // aux2 = new Fila();
        auxA1 = new ActividadUniforme(aUnif, bUnif);
        if (rButtonEuler.isSelected()){
            solverPrincipal = new Euler(0.0, bInicial, cInicial, hInicial, x0Inicial, dx0Inicial, auxA1, 300);
        } else {
            solverPrincipal = new RKutta(0.0, bInicial, cInicial, hInicial, x0Inicial, dx0Inicial, auxA1, 300);
        }
        
        aux1 = new Fila(solverPrincipal);
        historialSolvers.add(solverPrincipal);
        
        aux1.CalcularPrimeraFila(lambdaLl);
        //aux2 = new Fila(aux1.getE(), aux1.getMaterial(), aux1.getProximoMaterial(), aux1.getRndPedido(), aux1.getTiempoEntreLlegadas(), aux1.getProxLlegada(), A1, A2, A3, A4, A5, aux1.getColaA3(), aux1.getColaA5(), aux1.getTareasTerminadas(), aux1.getContadorN(), aux1.getLlegadaActividadCalc());
        if(rButtonEventos.isSelected()){
        for (int i = 0; i < N; i++) {

            if ((aux1.getContadorN() >= desde && aux1.getContadorN() <= hasta) || aux1.getContadorN() == N) {

                llenarFila(aux1);
                tabla.addRow(filaAImprimir);

            }
            if(aux1.getE() == Evento.FinEncastre){
                
                if (rButtonEuler.isSelected()){
            solverPrincipal = new Euler(0.0, bInicial, cInicial, hInicial, x0Inicial, dx0Inicial, auxA1, 300);
        } else {
            solverPrincipal = new RKutta(0.0, bInicial, cInicial, hInicial, x0Inicial, dx0Inicial, auxA1, 300);
        }
                aux1.setSolver(solverPrincipal);
                historialSolvers.add(solverPrincipal);
            }
            aux1.CalcularNuevaFila(cantParaProb);
        }}
        else if (rButtonEnsambles.isSelected()){
            int i = 0;
            while(i <N){
                 i = aux1.getTareasTerminadas();
                  llenarFila(aux1);
                tabla.addRow(filaAImprimir);
                
            
            aux1.CalcularNuevaFila(cantParaProb);
            if(aux1.getContadorN() >= N*100){
                i=N+1;
            }
            }
        }
        double prob = aux1.getContMasDe3() / aux1.getContHoras();
        txtProbMas3.setText("Probabilidad de mas de " + cantParaProb + " por hora: " + prob);

        Tabla.setModel(tabla);

        Tabla.getColumn("Rnd Llegada").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.blue : Color.yellow);
                return this;
            }
        });

        Tabla.getColumn("T Entre Llegadas").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.blue : Color.yellow);
                return this;
            }
        });

        Tabla.getColumn("Prox Llegada").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.blue : Color.yellow);
                return this;
            }
        });

        Tabla.getColumn("Estado A1").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.red : Color.GREEN);
                return this;
            }
        });

        Tabla.getColumn("Material A1").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.red : Color.GREEN);
                return this;
            }
        });

        Tabla.getColumn("rnd A1").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.red : Color.GREEN);
                return this;
            }
        });

        Tabla.getColumn("T Atencion A1").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.red : Color.GREEN);
                return this;
            }
        });

        Tabla.getColumn("Prox fin A1").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.red : Color.GREEN);
                return this;
            }
        });

        Tabla.getColumn("Cola A1").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.red : Color.GREEN);
                return this;
            }
        });

        Tabla.getColumn("Estado A2").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.BLUE : Color.CYAN);
                return this;
            }
        });

        Tabla.getColumn("Material A2").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.BLUE : Color.CYAN);
                return this;
            }
        });

        Tabla.getColumn("rnd A2").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.BLUE : Color.CYAN);
                return this;
            }
        });

        Tabla.getColumn("T Atencion A2").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.BLUE : Color.CYAN);
                return this;
            }
        });

        Tabla.getColumn("Prox fin A2").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.BLUE : Color.CYAN);
                return this;
            }
        });

        Tabla.getColumn("Cola A2").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.BLUE : Color.CYAN);
                return this;
            }
        });

        Tabla.getColumn("Estado A4").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.red : Color.GREEN);
                return this;
            }
        });

        Tabla.getColumn("Material A4").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.red : Color.GREEN);
                return this;
            }
        });

        Tabla.getColumn("rnd A4").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.red : Color.GREEN);
                return this;
            }
        });

        Tabla.getColumn("T Atencion A4").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.red : Color.GREEN);
                return this;
            }
        });

        Tabla.getColumn("Prox fin A4").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.red : Color.GREEN);
                return this;
            }
        });

        Tabla.getColumn("Cola A4").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.red : Color.GREEN);
                return this;
            }
        });

        Tabla.getColumn("Cola Terminados A5").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.WHITE : Color.LIGHT_GRAY);
                return this;
            }
        });

        Tabla.getColumn("Cola Terminados A3").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.WHITE : Color.LIGHT_GRAY);
                return this;
            }
        });

        Tabla.getColumn("Tareas terminadas").setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(isSelected ? Color.WHITE : Color.LIGHT_GRAY);
                return this;
            }
        });
    }//GEN-LAST:event_BtnSimularActionPerformed

    private void lambdaLlegadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lambdaLlegadasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lambdaLlegadasActionPerformed

    private void txtCantidadFilasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadFilasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadFilasActionPerformed

    private void txtCantXhoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantXhoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantXhoraActionPerformed

    private void rButtonEnsamblesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButtonEnsamblesActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_rButtonEnsamblesActionPerformed

    private void aUnifTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aUnifTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aUnifTxtActionPerformed

    private void bUnifTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUnifTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bUnifTxtActionPerformed

    private void bTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bTxtActionPerformed

    private void cTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cTxtActionPerformed

    private void hTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hTxtActionPerformed

    private void x0TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x0TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_x0TxtActionPerformed

    private void dx0TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dx0TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dx0TxtActionPerformed

    private void rButtonEulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButtonEulerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rButtonEulerActionPerformed

    private void rButtonRungeKuttaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButtonRungeKuttaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rButtonRungeKuttaActionPerformed

    private void graficarTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficarTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_graficarTxtActionPerformed

    private void graficarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficarBtnActionPerformed
                                                  
            // TODO add your handling code here:
            int x = Integer.parseInt(graficarTxt.getText());
            
            IEDSolver auxSolver = historialSolvers.get(x);
            
            if (rButtonEuler.isSelected()){
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
                    {
                        dataset.addValue(auxSolver.getHist()[1][i],"x",Double.toString(auxSolver.getHist()[0][i]));
                        dataset.addValue(auxSolver.getHist()[2][i],"x'",Double.toString(auxSolver.getHist()[0][i]));
                        dataset.addValue(auxSolver.getHist()[3][i],"x''",Double.toString(auxSolver.getHist()[0][i]));
                    }
                    
                }
                JFreeChart graficoBarras = ChartFactory.createLineChart(
                        "Todas sobre t",        //Título de la gráfica
                        "t",           //leyenda Eje horizontal
                        "x'' - x' - x",      //leyenda Eje vertical
                        dataset,                  //datos
                        PlotOrientation.VERTICAL,  //orientación
                        true,                      //incluir leyendas
                        true,                      //mostrar tooltips
                        true);
                
                graficoBarras.setBackgroundPaint(Color.lightGray);
                
                CategoryPlot plot =(CategoryPlot)  graficoBarras.getPlot();
                
                //plot.getRangeAxis().setRange(0.0,30.0);
                //plot.getDomainAxis().setMaximumCategoryLabelLines(5);
                
                plot.setBackgroundPaint(Color.white); //fondo del grafico
                plot.setDomainGridlinesVisible(true);//lineas de rangos, visibles
                plot.setRangeGridlinePaint(Color.BLACK);//color de las lineas de rangos
                
                
                
                // crear la ventana del grafico
                ChartFrame frame = new ChartFrame("Gráfico de barras",graficoBarras);
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                
                 Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Test");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);
            
            Row header = sheet.createRow(0);
            
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("t");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = header.createCell(1);
            headerCell.setCellValue("x");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = header.createCell(2);
            headerCell.setCellValue("x'");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = header.createCell(3);
            headerCell.setCellValue("x''");
            headerCell.setCellStyle(headerStyle);
            
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            
            for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
                Row row = sheet.createRow(i+1);
                Cell cell = row.createCell(0);
                cell.setCellValue(auxSolver.getHist()[0][i]);
                cell.setCellStyle(style);
                
                cell = row.createCell(1);
                cell.setCellValue(auxSolver.getHist()[1][i]);
                cell.setCellStyle(style);
                
                cell = row.createCell(2);
                cell.setCellValue(auxSolver.getHist()[2][i]);
                cell.setCellStyle(style);
                
                cell = row.createCell(3);
                cell.setCellValue(auxSolver.getHist()[3][i]);
                cell.setCellStyle(style);
                
            }
            try{
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xls";
            
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(fileLocation);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            workbook.write(outputStream);
            workbook.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
                
            } else {
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
                    {
                        dataset.addValue(auxSolver.getHist()[1][i],"x",Double.toString(auxSolver.getHist()[0][i]));
                        dataset.addValue(auxSolver.getHist()[6][i],"x'",Double.toString(auxSolver.getHist()[0][i]));
                        dataset.addValue(auxSolver.getHist()[7][i],"x''",Double.toString(auxSolver.getHist()[0][i]));
                    }
                    
                }
                JFreeChart graficoBarras = ChartFactory.createLineChart(
                        "Todas sobre t",        //Título de la gráfica
                        "t",           //leyenda Eje horizontal
                        "x'' - x' - x",      //leyenda Eje vertical
                        dataset,                  //datos
                        PlotOrientation.VERTICAL,  //orientación
                        true,                      //incluir leyendas
                        true,                      //mostrar tooltips
                        true);
                
                graficoBarras.setBackgroundPaint(Color.lightGray);
                
                CategoryPlot plot =(CategoryPlot) graficoBarras.getPlot();
                plot.setBackgroundPaint(Color.white); //fondo del grafico
                plot.setDomainGridlinesVisible(true);//lineas de rangos, visibles
                plot.setRangeGridlinePaint(Color.BLACK);//color de las lineas de rangos
                
                
                // crear la ventana del grafico
                ChartFrame frame = new ChartFrame("Gráfico de barras",graficoBarras);
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                
                Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Test");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);
            
            Row header = sheet.createRow(0);
            
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("t");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = header.createCell(1);
            headerCell.setCellValue("x");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = header.createCell(2);
            headerCell.setCellValue("x'");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = header.createCell(3);
            headerCell.setCellValue("x'' (l1)");
            headerCell.setCellStyle(headerStyle);
            
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            
            for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
                Row row = sheet.createRow(i+1);
                Cell cell = row.createCell(0);
                cell.setCellValue(auxSolver.getHist()[0][i]);
                cell.setCellStyle(style);
                
                cell = row.createCell(1);
                cell.setCellValue(auxSolver.getHist()[1][i]);
                cell.setCellStyle(style);
                
                cell = row.createCell(2);
                cell.setCellValue(auxSolver.getHist()[6][i]);
                cell.setCellStyle(style);
                
                cell = row.createCell(3);
                cell.setCellValue(auxSolver.getHist()[7][i]);
                cell.setCellStyle(style);
                
            }
            try{
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xls";
            
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(fileLocation);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            workbook.write(outputStream);
            workbook.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
            
           
        
    }//GEN-LAST:event_graficarBtnActionPerformed

    private void graficarBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficarBtn1ActionPerformed
        // TODO add your handling code here:
        int x = Integer.parseInt(graficarTxt.getText());
        
        IEDSolver auxSolver = historialSolvers.get(x);
        
        if (rButtonEuler.isSelected()){
           DefaultCategoryDataset dataset = new DefaultCategoryDataset();
             for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
            {
                dataset.addValue(auxSolver.getHist()[3][i],"x",Double.toString(auxSolver.getHist()[1][i]));
            }
            
        }
 JFreeChart graficoBarras = ChartFactory.createLineChart(
                     "x'' sobre x",        //Título de la gráfica
                     "x",           //leyenda Eje horizontal
                     "x''",      //leyenda Eje vertical
                     dataset,                  //datos
                    PlotOrientation.VERTICAL,  //orientación
                     true,                      //incluir leyendas
                     true,                      //mostrar tooltips
                    true);                   

            graficoBarras.setBackgroundPaint(Color.lightGray);
            
            CategoryPlot plot =(CategoryPlot)  graficoBarras.getPlot();
           
            //plot.getRangeAxis().setRange(0.0,30.0);
            //plot.getDomainAxis().setMaximumCategoryLabelLines(5);
            CategoryAxis xAxis = plot.getDomainAxis();
            plot.getDomainAxis().setAxisLineVisible(false);
            plot.setBackgroundPaint(Color.white); //fondo del grafico
            plot.setDomainGridlinesVisible(true);//lineas de rangos, visibles
            plot.setRangeGridlinePaint(Color.BLACK);//color de las lineas de rangos

            

            // crear la ventana del grafico
            ChartFrame frame = new ChartFrame("Gráfico de barras",graficoBarras);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Test");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);
            
            Row header = sheet.createRow(0);
            
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("x");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = header.createCell(1);
            headerCell.setCellValue("x''");
            headerCell.setCellStyle(headerStyle);
            
           
            
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            
            for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
                Row row = sheet.createRow(i+1);
                Cell cell = row.createCell(0);
                cell.setCellValue(auxSolver.getHist()[1][i]);
                cell.setCellStyle(style);
                
                cell = row.createCell(1);
                cell.setCellValue(auxSolver.getHist()[3][i]);
                cell.setCellStyle(style);
                
                
            }
            try{
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xls";
            
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(fileLocation);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            workbook.write(outputStream);
            workbook.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
            
             
        } else {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
             for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
            {
                dataset.addValue(auxSolver.getHist()[7][i],"x",Double.toString(auxSolver.getHist()[1][i]));
            }
            
        }
 JFreeChart graficoBarras = ChartFactory.createLineChart(
                     "x'' sobre x",        //Título de la gráfica
                     "x",           //leyenda Eje horizontal
                     "x''",      //leyenda Eje vertical
                     dataset,                  //datos
                    PlotOrientation.VERTICAL,  //orientación
                     true,                      //incluir leyendas
                     true,                      //mostrar tooltips
                    true);                     

            graficoBarras.setBackgroundPaint(Color.lightGray);

            CategoryPlot plot =(CategoryPlot) graficoBarras.getPlot();
            plot.setBackgroundPaint(Color.white); //fondo del grafico
            plot.setDomainGridlinesVisible(true);//lineas de rangos, visibles
            plot.setRangeGridlinePaint(Color.BLACK);//color de las lineas de rangos
            

            // crear la ventana del grafico
            ChartFrame frame = new ChartFrame("Gráfico de barras",graficoBarras);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            
              Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Test");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);
            
            Row header = sheet.createRow(0);
            
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("x");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = header.createCell(1);
            headerCell.setCellValue("x''");
            headerCell.setCellStyle(headerStyle);
            
           
            
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            
            for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
                Row row = sheet.createRow(i+1);
                Cell cell = row.createCell(0);
                cell.setCellValue(auxSolver.getHist()[1][i]);
                cell.setCellStyle(style);
                
                cell = row.createCell(1);
                cell.setCellValue(auxSolver.getHist()[7][i]);
                cell.setCellStyle(style); 
            }
            try{
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xls";
            
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(fileLocation);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            workbook.write(outputStream);
            workbook.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
        
        
    }//GEN-LAST:event_graficarBtn1ActionPerformed

    private void graficarBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficarBtn2ActionPerformed
        // TODO add your handling code here:
        int x = Integer.parseInt(graficarTxt.getText());
        
        IEDSolver auxSolver = historialSolvers.get(x);
        
        if (rButtonEuler.isSelected()){
           DefaultCategoryDataset dataset = new DefaultCategoryDataset();
             for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
            {
                dataset.addValue(auxSolver.getHist()[2][i],"x",Double.toString(auxSolver.getHist()[1][i]));
            }
            
        }
 JFreeChart graficoBarras = ChartFactory.createLineChart(
                     "x' sobre x",        //Título de la gráfica
                     "x",           //leyenda Eje horizontal
                     "x'",      //leyenda Eje vertical
                     dataset,                  //datos
                    PlotOrientation.VERTICAL,  //orientación
                     false,                      //incluir leyendas
                     true,                      //mostrar tooltips
                    true);                   

            graficoBarras.setBackgroundPaint(Color.lightGray);
            
            CategoryPlot plot =(CategoryPlot)  graficoBarras.getPlot();
           
            //plot.getRangeAxis().setRange(0.0,30.0);
            //plot.getDomainAxis().setMaximumCategoryLabelLines(5);
            CategoryAxis xAxis = plot.getDomainAxis();
            plot.getDomainAxis().setAxisLineVisible(false);
            plot.setBackgroundPaint(Color.white); //fondo del grafico
            plot.setDomainGridlinesVisible(true);//lineas de rangos, visibles
            plot.setRangeGridlinePaint(Color.BLACK);//color de las lineas de rangos

            

            // crear la ventana del grafico
            ChartFrame frame = new ChartFrame("Gráfico de barras",graficoBarras);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            
              Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Test");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);
            
            Row header = sheet.createRow(0);
            
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("x");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = header.createCell(1);
            headerCell.setCellValue("x'");
            headerCell.setCellStyle(headerStyle);
            
           
            
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            
            for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
                Row row = sheet.createRow(i+1);
                Cell cell = row.createCell(0);
                cell.setCellValue(auxSolver.getHist()[1][i]);
                cell.setCellStyle(style);
                
                cell = row.createCell(1);
                cell.setCellValue(auxSolver.getHist()[2][i]);
                cell.setCellStyle(style); 
            }
            try{
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xls";
            
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(fileLocation);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            workbook.write(outputStream);
            workbook.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
            
             
        } else {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
             for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
            {
                dataset.addValue(auxSolver.getHist()[6][i],"x",Double.toString(auxSolver.getHist()[1][i]));
            }
            
        }
 JFreeChart graficoBarras = ChartFactory.createLineChart(
                     "x' sobre x",        //Título de la gráfica
                     "x",           //leyenda Eje horizontal
                     "x''",      //leyenda Eje vertical
                     dataset,                   //datos
                     PlotOrientation.VERTICAL,  //orientación
                     false,                      //incluir leyendas
                     true,                      //mostrar tooltips
                     true);                   

            graficoBarras.setBackgroundPaint(Color.lightGray);

            CategoryPlot plot =(CategoryPlot) graficoBarras.getPlot();
            plot.setBackgroundPaint(Color.white); //fondo del grafico
            plot.setDomainGridlinesVisible(true);//lineas de rangos, visibles
            plot.setRangeGridlinePaint(Color.BLACK);//color de las lineas de rangos
            

            // crear la ventana del grafico
            ChartFrame frame = new ChartFrame("Gráfico de barras",graficoBarras);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            
              Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Test");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);
            
            Row header = sheet.createRow(0);
            
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("x");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = header.createCell(1);
            headerCell.setCellValue("x'");
            headerCell.setCellStyle(headerStyle);
            
           
            
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            
            for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
                Row row = sheet.createRow(i+1);
                Cell cell = row.createCell(0);
                cell.setCellValue(auxSolver.getHist()[1][i]);
                cell.setCellStyle(style);
                
                cell = row.createCell(1);
                cell.setCellValue(auxSolver.getHist()[6][i]);
                cell.setCellStyle(style); 
            }
            try{
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xls";
            
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(fileLocation);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            workbook.write(outputStream);
            workbook.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
    }//GEN-LAST:event_graficarBtn2ActionPerformed

    private void graficarBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficarBtn3ActionPerformed
        // TODO add your handling code here:
        int x = Integer.parseInt(graficarTxt.getText());
        
        IEDSolver auxSolver = historialSolvers.get(x);
        
        if (rButtonEuler.isSelected()){
           DefaultCategoryDataset dataset = new DefaultCategoryDataset();
             for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
            {
                dataset.addValue(auxSolver.getHist()[3][i],"x",Double.toString(auxSolver.getHist()[2][i]));
            }
            
        }
 JFreeChart graficoBarras = ChartFactory.createLineChart(
                     "x'' sobre x'",        //Título de la gráfica
                     "x'",           //leyenda Eje horizontal
                     "x''",      //leyenda Eje vertical
                     dataset,                  //datos
                    PlotOrientation.VERTICAL,  //orientación
                     true,                      //incluir leyendas
                     true,                      //mostrar tooltips
                    true);                   

            graficoBarras.setBackgroundPaint(Color.lightGray);
            
            CategoryPlot plot =(CategoryPlot)  graficoBarras.getPlot();
           
            //plot.getRangeAxis().setRange(0.0,30.0);
            //plot.getDomainAxis().setMaximumCategoryLabelLines(5);
            CategoryAxis xAxis = plot.getDomainAxis();
            plot.getDomainAxis().setAxisLineVisible(false);
            plot.setBackgroundPaint(Color.white); //fondo del grafico
            plot.setDomainGridlinesVisible(true);//lineas de rangos, visibles
            plot.setRangeGridlinePaint(Color.BLACK);//color de las lineas de rangos

            

            // crear la ventana del grafico
            ChartFrame frame = new ChartFrame("Gráfico de barras",graficoBarras);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            
              Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Test");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);
            
            Row header = sheet.createRow(0);
            
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("x'");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = header.createCell(1);
            headerCell.setCellValue("x''");
            headerCell.setCellStyle(headerStyle);
            
           
            
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            
            for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
                Row row = sheet.createRow(i+1);
                Cell cell = row.createCell(0);
                cell.setCellValue(auxSolver.getHist()[2][i]);
                cell.setCellStyle(style);
                
                cell = row.createCell(1);
                cell.setCellValue(auxSolver.getHist()[3][i]);
                cell.setCellStyle(style); 
            }
            try{
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xls";
            
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(fileLocation);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            workbook.write(outputStream);
            workbook.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
            
             
        } else {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
             for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
            {
                dataset.addValue(auxSolver.getHist()[7][i],"x",Double.toString(auxSolver.getHist()[6][i]));
            }
            
        }
 JFreeChart graficoBarras = ChartFactory.createLineChart(
                     "x'' sobre x'",        //Título de la gráfica
                     "x'",           //leyenda Eje horizontal
                     "x''",      //leyenda Eje vertical
                     dataset,                  //datos
                    PlotOrientation.VERTICAL,  //orientación
                     true,                      //incluir leyendas
                     true,                      //mostrar tooltips
                    true);                   

            graficoBarras.setBackgroundPaint(Color.lightGray);

            CategoryPlot plot =(CategoryPlot) graficoBarras.getPlot();
            plot.setBackgroundPaint(Color.white); //fondo del grafico
            plot.setDomainGridlinesVisible(true);//lineas de rangos, visibles
            plot.setRangeGridlinePaint(Color.BLACK);//color de las lineas de rangos
            

            // crear la ventana del grafico
            ChartFrame frame = new ChartFrame("Gráfico de barras",graficoBarras);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            
              Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Test");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);
            
            Row header = sheet.createRow(0);
            
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("x'");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = header.createCell(1);
            headerCell.setCellValue("x''");
            headerCell.setCellStyle(headerStyle);
            
           
            
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            
            for (int i = 0; i < auxSolver.getHist()[0].length; i++) {
                Row row = sheet.createRow(i+1);
                Cell cell = row.createCell(0);
                cell.setCellValue(auxSolver.getHist()[6][i]);
                cell.setCellStyle(style);
                
                cell = row.createCell(1);
                cell.setCellValue(auxSolver.getHist()[7][i]);
                cell.setCellStyle(style); 
            }
            try{
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xls";
            
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(fileLocation);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            workbook.write(outputStream);
            workbook.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
    }//GEN-LAST:event_graficarBtn3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnSimular;
    private javax.swing.JTable Tabla;
    private javax.swing.JTextField aUnifTxt;
    private javax.swing.JTextField bTxt;
    private javax.swing.JTextField bUnifTxt;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.JTextField cTxt;
    private javax.swing.JTextField dx0Txt;
    private javax.swing.JButton graficarBtn;
    private javax.swing.JButton graficarBtn1;
    private javax.swing.JButton graficarBtn2;
    private javax.swing.JButton graficarBtn3;
    private javax.swing.JTextField graficarTxt;
    private javax.swing.JTextField hTxt;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lambdaLlegadas;
    private javax.swing.JLabel lblIntegrantes;
    private javax.swing.JRadioButton rButtonEnsambles;
    private javax.swing.JRadioButton rButtonEuler;
    private javax.swing.JRadioButton rButtonEventos;
    private javax.swing.JRadioButton rButtonRungeKutta;
    private javax.swing.JTextField txtCantXhora;
    private javax.swing.JTextField txtCantidadFilas;
    private javax.swing.JTextField txtDesde;
    private javax.swing.JTextField txtHasta;
    private javax.swing.JLabel txtProbMas3;
    private javax.swing.JTextField x0Txt;
    // End of variables declaration//GEN-END:variables
}
