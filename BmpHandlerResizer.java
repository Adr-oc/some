import java.io.*;           //Se importan las librerias necesarias.
import java.util.Scanner;

public class BmpHandlerResizer {
    public BmpHandlerResizer(String imagen){
        try {
            Scanner sc =new Scanner(imagen);            //Se separa el nombre del archivo.
            sc.useDelimiter("\\.");
            String nombre = sc.next();
            FileInputStream originalArchivo = new FileInputStream(imagen);          //Se carga el bmp para lectura de bytes.
            DataInputStream originalData = new DataInputStream(originalArchivo);

            FileOutputStream miniAnchoArchivo = new FileOutputStream(nombre + "-thin.bmp");         //Se crea un archivo de salida para la reduccion en el ancho.
            DataOutputStream miniAnchoData = new DataOutputStream(miniAnchoArchivo);

            FileOutputStream miniAltoArchivo = new FileOutputStream(nombre + "-flat.bmp");          //Se crea un archivo de salida para la reduccion en el alto.
            DataOutputStream miniAltoData = new DataOutputStream(miniAltoArchivo);

            byte[] header = new byte[54];           //Se crea un arreglo de 54 bytes.
            originalData.readFully(header);         //Se asignan al arreglo los primeros 54 bytes del archivo bmp (header).
            header[18] = (byte) (320 & 0xFF);           //Se modifan los bytes que contienen la informacion del ancho de la imagen.
            header[19] = (byte) ((320 >> 8) & 0xFF);
            miniAnchoData.write(header);                //Se copia el header ya con los cambios.

            header[18] = (byte) (640 & 0xFF);           //Se restablecen los cambio de ancho.
            header[19] = (byte) ((640 >> 8) & 0xFF);
            header[22] = (byte) (240 & 0xFF);           //Se cambian los valores del header con contienen el dato de la altura.
            header[23] = (byte) ((240 >> 8) & 0xFF);
            miniAltoData.write(header);                 //Se copian el header ya con los cambios.

            int[][] matrizAzul = new int[480][640];         //Se crea una matriz del tamaño de la imagen para cada color.
            int[][] matrizVerde = new int[480][640];
            int[][] matrizRojo = new int[480][640];

            for(int j=0; j<480; j++){           //Se recorre cada pixel de la imagen y se separan los colores.
                for(int k=0; k<640; k++){
                    int azul = originalData.readUnsignedByte(); 
                    matrizAzul[j][k]= azul; 
                    int verde = originalData.readUnsignedByte();
                    matrizVerde[j][k]= verde; 
                    int rojo = originalData.readUnsignedByte();   
                    matrizRojo[j][k]= rojo; 
                }
            }
            
            int[][] nuevoAzul = new int[480][320];          //Se crean las nuevas matricez reduciendo el ancho.
            int[][] nuevoVerde = new int[480][320];
            int[][] nuevoRojo = new int[480][320];
            for(int i=0; i<480; i++){                       //Se recorre cada fila de pixeles del archivo original.
                int k = -1;                                 //Se establece la variable del numero de columna.
                for(int j=0; j+1<640; j+=2){            //Se recorre cada elemento de la fila de pixeles del archivo original.
                    k++;                                //Se incrementa en 1 el numero de columna.
                    int bit1Azul = matrizAzul[i][j];            //Se lee el dato del pixel y del que tiene alapar.
                    int bit2Azul = matrizAzul[i][j+1];
                    int nuevoBitAzul = (bit1Azul+bit2Azul)/2;           //Se hace un promedio de los dos pixeles.
                    nuevoAzul[i][k] = nuevoBitAzul;                     //Se asigna dicho promedio al nuevo pixel, reduciendo asi la mitad de las columnas.

                    int bit1Verde = matrizVerde[i][j];
                    int bit2Verde = matrizVerde[i][j+1];
                    int nuevoBitVerde = (bit1Verde+bit2Verde)/2;
                    nuevoVerde[i][k] = nuevoBitVerde;

                    int bit1Rojo = matrizRojo[i][j];
                    int bit2Rojo = matrizRojo[i][j+1];
                    int nuevoBitRojo = (bit1Rojo+bit2Rojo)/2;
                    nuevoRojo[i][k] = nuevoBitRojo;
                }
            }
            for(int j=0; j<480; j++){           //Ciclo que le asigna cada valor a la nueva imagen.
                for(int k=0; k<320; k++){
                    miniAnchoData.writeByte(nuevoAzul[j][k]);   
                    miniAnchoData.writeByte(nuevoVerde[j][k]);  
                    miniAnchoData.writeByte(nuevoRojo[j][k]);   
                }
            }
            miniAnchoData.close();          //Se cierra la nueva imagen con ancho comprimido.


            int[][] newAzul = new int[240][640];            //Se crea una matrzi para cada color con la mitad de altura.
            int[][] newVerde = new int[240][640];
            int[][] newRojo = new int[240][640];
            for(int i=0; i<640; i++){                       //Ciclo que recorre cada columna.
                int k=-1;                                   //Se establece el numero de fila.
                for(int j=0; j+1<480; j+=2){            //Ciclo que recorre cada fila de la imagen original.
                    k++;                                //Se incrementa en 1 el valor de la fila.
                    int bit1Azul = matrizAzul[j][i];            //Se lee el dato del pixel y del que esta arriba de el.
                    int bit2Azul = matrizAzul[j+1][i];
                    int nuevoBitAzul = (bit1Azul+bit2Azul)/2;           //Se hace un promedio entre los dos pixeles.
                    newAzul[k][i] = nuevoBitAzul;                       //Se asigna el nuevo pixel combinado, reduciendo la altura en un 50%.

                    int bit1Verde = matrizVerde[j][i];
                    int bit2Verde = matrizVerde[j+1][i];
                    int nuevoBitVerde = (bit1Verde+bit2Verde)/2;
                    newVerde[k][i] = nuevoBitVerde;

                    int bit1Rojo = matrizRojo[j][i];
                    int bit2Rojo = matrizRojo[j+1][i];
                    int nuevoBitRojo = (bit1Rojo+bit2Rojo)/2;
                    newRojo[k][i] = nuevoBitRojo;
                }
            }
            for(int j=0; j<240; j++){           //Ciclo que llena los valores en sus respectivas matrices.
                for(int k=0; k<640; k++){
                    miniAltoData.writeByte(newAzul[j][k]);   
                    miniAltoData.writeByte(newVerde[j][k]);  
                    miniAltoData.writeByte(newRojo[j][k]);   
                }
            }

            miniAltoData.close();           //Se cierra el archivo con la imagen reducida en la mitad de su alto.
            originalData.close();           //Se cierra la imagen original.

        } catch (Exception e) {             //Manejo de errores.
            System.out.println("Error.");
        }
    }
}
