import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> combinaciones;
        String[][] adn = new String[6][6];
        int opc;
        boolean on = true;
        System.out.println("Bienvenido al identificador de mutantes.");
        while (on){
            System.out.println("Selecciona tu opcion: 1: Ingresar ADN. 2: Comprobar ADN. 3: Salir.");
            opc = sc.nextInt();
            switch (opc){
                case 1:
                    ADNin(adn);
                    break;
                case 2:
                    if (adn[1][1] == null){
                        System.out.println("No has ingresado un ADN aun!");
                        break;
                    }else {
                        combinaciones = pos_comb(adn);
                        if (isMutant(combinaciones)){
                            System.out.println("Es mutante");
                        }else {
                            System.out.println("No es mutante");
                        }
                        break;
                    }
                case 3:
                    System.out.println("Gracias por utilizar el identificador!");
                    on = false;
                    break;
                default:
                    System.out.println("Opcion incorrecta");
            }
        }
/*
    Casos de prueba:
    ADN = ['ATGCGA','CAGTGC','TTATGT','AGAAGG','CCCCTA','TCACTG']
    Mutante
    ADN = ['TTGCGA','GAGCGC','TTAATT','AGAGAA','CCTCTA','TCACTT']
    No mutante
 */



    }
    public static ArrayList<String> pos_comb(String[][] adn) {
        ArrayList<String> combinaciones = new ArrayList<>();
        for (String[] row : adn) {
            combinaciones.add(Arrays.toString(row));
        }
        ArrayList<String> columnas = new ArrayList<>();
        for (int i = 0; i < adn.length; i++) {
            StringBuilder columna = new StringBuilder();
            for (int j = 0; j < adn[0].length; j++) {
                columna.append(adn[j][i]);
            }
            columnas.add(columna.toString());
        }
        combinaciones.addAll(columnas);
        StringBuilder diagonal_pr = new StringBuilder();
        for (int i = 0; i < adn.length; i++) {
            diagonal_pr.append(adn[i][i]);
        }
        combinaciones.add(diagonal_pr.toString());
        StringBuilder diagonal_tr = new StringBuilder();
        for (int i = 0; i < adn.length; i++) {
            diagonal_tr.append(adn[i][adn.length - 1 - i]);
        }
        combinaciones.add(diagonal_tr.toString());
        for (int j = 1; j < adn.length; j++) {
            StringBuilder diagonal = new StringBuilder();
            for (int i = 0; i < adn.length - j; i++) {
                diagonal.append(adn[i][i + j]);
            }
            if (diagonal.length() >= 4) {
                combinaciones.add(diagonal.toString());
            }
        }
        for (int i = 1; i < adn.length; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j < adn.length - i; j++) {
                diagonal.append(adn[i + j][j]);
            }
            if (diagonal.length() >= 4) {
                combinaciones.add(diagonal.toString());
            }
        }
        String[][] adn_volteado = new String[adn.length][adn[0].length];
        for (int i = 0; i < adn.length; i++) {
            for (int j = 0; j < adn[0].length; j++) {
                adn_volteado[i][j] = adn[adn.length - 1 - i][j];
            }
        }
        for (int j = 1; j < adn.length; j++) {
            StringBuilder diagonal = new StringBuilder();
            for (int i = 0; i < adn.length - j; i++) {
                diagonal.append(adn_volteado[i][i + j]);
            }
            if (diagonal.length() >= 4) {
                combinaciones.add(diagonal.toString());
            }
        }
        for (int i = 1; i < adn.length; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j < adn.length - i; j++) {
                diagonal.append(adn_volteado[i + j][j]);
            }
            if (diagonal.length() >= 4) {
                combinaciones.add(diagonal.toString());
            }
        }
        return combinaciones;
    }
    public static int adnFind(String adn) {
        for (int i = 0; i < adn.length() - 3; i++) {
            if (areAllEqual(adn, i, i + 3)) {
                return 1;
            }
        }
        return 0;
    }
    private static boolean areAllEqual(String adn, int start, int end) {
        for (int j = start + 1; j <= end; j++) {
            if (adn.charAt(start) != adn.charAt(j)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isMutant(ArrayList<String> sequences) {
        int count = 0;
        for (String sequence : sequences) {
            count += adnFind(sequence);
            if (count >= 2) {
                return true;
            }
        }
        return false;
    }
    public static String[][] ADNin(String[][] adn){
        Scanner sc = new Scanner(System.in);
        Pattern patternbases = Pattern.compile("[ATGCatgc]{1}");
        String base;
        StringBuilder secuencia = new StringBuilder();
        System.out.println("Ingrese una a una las bases nitrogenadas.");
        for (int i = 0; i<6; i++){
            for (int j = 0; j<6; j++){
                while (true){
                    System.out.println("Ingrese la base nitrogenada.");
                    base = sc.nextLine();
                    Matcher matcherbases = patternbases.matcher(base);
                    if (matcherbases.matches()){
                        adn[i][j] = base.toUpperCase();
                        System.out.println("Base ingresada!");
                        secuencia.append(base);
                        break;
                    }else {
                        System.out.println("Ingreso erroneo, intente otra vez");
                    }
                }
            }
            System.out.println("Secuencia: "+secuencia.toString().toUpperCase()+ " ingresada!");
        }
        return adn;
    }

    }




