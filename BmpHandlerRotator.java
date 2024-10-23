import java.io.*;           //Se importan las librerias necesarias.
import java.util.Scanner;

public class BmpHandlerRotator {
    public BmpHandlerRotator(String imagen){
        try {
            Scanner sc =new Scanner(imagen);            //Se separa el nombre del archivo.
            sc.useDelimiter("\\.");
            String nombre = sc.next();
            FileInputStream originalArchivo = new FileInputStream(imagen);          //Se carga la imagen original para su lectura.
            DataInputStream originalData = new DataInputStream(originalArchivo);

            FileOutputStream rotadoHorizontalArchivo = new FileOutputStream(nombre + "-hrotation.bmp");         //Se crea un archivo para la imagen rotada horizontalmente.
            DataOutputStream rotadoHorizontalData = new DataOutputStream(rotadoHorizontalArchivo);

            FileOutputStream rotadoVerticalArchivo = new FileOutputStream(nombre + "-vrotation.bmp");           //Se crea un archivo para la imagen rotada verticalmente.
            DataOutputStream rotadoVerticalData = new DataOutputStream(rotadoVerticalArchivo);

            byte[] header = new byte[54];           //Se crea un arreglo de bytes con 54 espacios.
            originalData.readFully(header);         //Se llena el arreglo con la informacion del header.
            rotadoHorizontalData.write(header);         //Se copia el header en ambas imagenes.
            rotadoVerticalData.write(header);

            int[][] matrizAzul = new int[480][640];         //Se crean las matrices para cada color con su respectivo tamaño.
            int[][] matrizVerde = new int[480][640];
            int[][] matrizRojo = new int[480][640];

            for(int j=0; j<480; j++){              //Ciclo que llena los valores de las matrices con sus respectivos datos.
                for(int k=0; k<640; k++){
                    int azul = originalData.readUnsignedByte(); 
                    matrizAzul[j][k]= azul; 
                    int verde = originalData.readUnsignedByte();
                    matrizVerde[j][k]= verde; 
                    int rojo = originalData.readUnsignedByte();   
                    matrizRojo[j][k]= rojo; 
                }
            }

            for(int j=479; j>=0; j--){          //Ciclo que empieza a recorrer la imagen desde la primer fila.
                for(int k=0; k<640; k++){           //Ciclo que recorre cada columna en el mismo orden normal.
                    rotadoHorizontalData.writeByte(matrizAzul[j][k]);           //Se asignan los valores de la primer fila de pixeles a la ultima que seria la primera en escribirse.
                    rotadoHorizontalData.writeByte(matrizVerde[j][k]);
                    rotadoHorizontalData.writeByte(matrizRojo[j][k]);
                }
            }
            rotadoHorizontalData.close();           //Se cierra la imagen rotada horizontalmente.
            
            for(int j=0; j<480; j++){           //Ciclo que recorre las filas normalmente.
                for(int k=639; k>=0; k--){          //Ciclo que recorre desde la ultima columna leida.
                    rotadoVerticalData.writeByte(matrizAzul[j][k]);         //Se le asigna a la primer columna el dato de la ultima columna.
                    rotadoVerticalData.writeByte(matrizVerde[j][k]);
                    rotadoVerticalData.writeByte(matrizRojo[j][k]);
                }
            }
            rotadoVerticalData.close();         //Se cierra la imagen rotada verticalmente.

            originalData.close();           //Se cierra la imagen original.

        } catch (Exception e) {             //Manejo de errores.
            System.out.println("Error.");
        }
    }
}

