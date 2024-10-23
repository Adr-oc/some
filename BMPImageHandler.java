public class BMPImageHandler {
    public static void main(String[] args){
        if(args[0].equals("-core")){                        //Si el primer argumento de la llamada es -core se ejecuta el cambio de colores. 
            BmpHandlerCore colores = new BmpHandlerCore(args[1]);           //Se ejecuta la clase con el segundo argumento como parametro, que seria el nombre de la imagen
        }else if(args[0].equals("-rotate")){                       //Si el primer argumento de la llamada es -rotate se ejecuta la rotacion de imagen. 
            BmpHandlerRotator rotacion = new BmpHandlerRotator(args[1]);    
        }else if(args[0].equals("-resize")){                        //Si el primer argumento de la llamada es -resize se ejecuta el cambio de tamaño. 
            BmpHandlerResizer tamaño = new BmpHandlerResizer(args[1]);
        }else if(args[0].equals("-all")){                           //Si el primer argumento de la llamada es -all se ejecutan todas las clases. 
            BmpHandlerCore colores = new BmpHandlerCore(args[1]);
            BmpHandlerRotator rotacion = new BmpHandlerRotator(args[1]);
            BmpHandlerResizer tamaño = new BmpHandlerResizer(args[1]);
        }else if(args[0].equals("-help")){                          //Si el primer argumento de la llamada es -help se muestran los comandos disponibles. 
            System.out.println("");
            System.out.println("COMANDOS DISPONIBLES: ");
            System.out.println("");
            System.out.println("GENERAR COLORES: java BMPImageHandler -core imagen.bmp ");
            System.out.println("ROTAR IMAGEN: java BMPImageHandler -rotate imagen.bmp ");
            System.out.println("REDIMENSIONAR: java BMPImageHandler -resize imagen.bmp ");
            System.out.println("TODAS LAS OPCIONES: java BMPImageHandler -all imagen.bmp ");
            System.out.println("");
        }else{
            System.out.println("Comando Desconocido.");
        }
    }
}
