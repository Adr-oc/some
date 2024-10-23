import java.io.*;           //Se importan las librerias.
import java.util.Scanner;

public class BmpHandlerCore {
    public BmpHandlerCore(String imagen){
        try{
            Scanner sc =new Scanner(imagen);            //Se le asigna un Scanner al string de la direccion del bmp.
            sc.useDelimiter("\\.");         
            String nombre = sc.next();                                              //Se separa el nombre del bmp.
            FileInputStream originalArchivo = new FileInputStream(imagen);          //Se carga el archivo bmp para lectura de bytes.
            DataInputStream originalData = new DataInputStream(originalArchivo);            

            FileOutputStream azulArchivo = new FileOutputStream(nombre + "-blue.bmp");          //Se crea un archivo de salida para la imagen azul.
            DataOutputStream azulData = new DataOutputStream(azulArchivo);

            FileOutputStream verdeArchivo = new FileOutputStream(nombre + "-green.bmp");            //Se crea un archivo de salida para la imagen verde.
            DataOutputStream verdeData = new DataOutputStream(verdeArchivo);

            FileOutputStream rojoArchivo = new FileOutputStream(nombre + "-red.bmp");           //Se crea un archivo de salida para la imagen roja.
            DataOutputStream rojoData = new DataOutputStream(rojoArchivo);

            FileOutputStream sepiaArchivo = new FileOutputStream(nombre + "-sepia.bmp");            //Se crea un archivo de salida para la imagen sepia.
            DataOutputStream sepiaData = new DataOutputStream(sepiaArchivo);


            byte[] header = new byte[54];           //Se crea un arreglo de bytes de 54 espacios.
            originalData.readFully(header);         //Se leen los primeros 54 bytes del bmp (header).
            azulData.write(header);         //Se copian los primeros 54 bytes en cada archivo.
            verdeData.write(header);
            rojoData.write(header);
            sepiaData.write(header);

            int[][] matrizAzul = new int[480][640];         //Se crea una matriz del tamaño de la imagen en pixeles, para cada archivo de salida.
            int[][] matrizVerde = new int[480][640];
            int[][] matrizRojo = new int[480][640];

            for(int j=0; j<480; j++){           //Ciclo que recorre cada fila de pixeles.
                for(int k=0; k<640; k++){                               //Ciclo que recorre cada pixel.
                    int azul = originalData.readUnsignedByte();         //Se lee el dato (0-255) del pixel de dicho color.
                    matrizAzul[j][k]= azul;                             //Se asigna dicho valor en su debida matriz.
                    int verde = originalData.readUnsignedByte();
                    matrizVerde[j][k]= verde; 
                    int rojo = originalData.readUnsignedByte();   
                    matrizRojo[j][k]= rojo; 

                    azulData.writeByte(matrizAzul[j][k]);           //Se le asigna a la nueva imagen el dato del pixel de dicho color.   
                    azulData.writeByte(0);                        //Se designan los demas colores en 0.  
                    azulData.writeByte(0);   
                    
                    verdeData.writeByte(0);                       
                    verdeData.writeByte(matrizVerde[j][k]);  
                    verdeData.writeByte(0); 
                    
                    rojoData.writeByte(0);   
                    rojoData.writeByte(0);  
                    rojoData.writeByte(matrizRojo[j][k]);
                    
                    int sepiaAzul = (int) (0.164 * matrizAzul[j][k]);           //Para los colores de la imagen sepia se asigna un porcentaje de cada color.
                    int sepiaVerde = (int) (0.231 * matrizVerde[j][k]);
                    int sepiaRojo = (int) (0.4 * matrizRojo[j][k]);

                    if(sepiaAzul>255){          //Si en dado caso el dato excediera el valor de 255, se toma como 255.
                        sepiaAzul=255;      
                    }
                    if(sepiaVerde>255){
                        sepiaVerde=255;
                    }
                    if(sepiaRojo>255){
                        sepiaRojo=255;
                    }

                    sepiaData.writeByte(sepiaAzul);         //Se escribe cada dato en orden: BGR.
                    sepiaData.writeByte(sepiaVerde);
                    sepiaData.writeByte(sepiaRojo);
                }
            }
                
        }catch (Exception error){           //Manejo de Errores.
            System.out.println("Error.");
        }
    }
}