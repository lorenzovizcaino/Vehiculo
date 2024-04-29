import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Vehiculo extends JPanel implements Serializable {
    private static int id=0;

    private String imageUrl;
    private int alto;
    private int ancho;
    private int indice;
    private double tiempoCarrera;
    private boolean carreraTerminada;
    private int velocidad;
    private int posicionX;
    private PropertyChangeSupport changeSupport;


    private int posicionY;
    private int ejecuciones;

    private Image image;
    private Timer timer;
    private TimerTask task;


    public Vehiculo() {
        this.imageUrl="image/nave.png";
        this.alto=100;
        this.ancho=200;
        this.indice=id+1;
        this.carreraTerminada=false;
        this.velocidad=0;
        changeSupport = new PropertyChangeSupport(this);
        this.posicionX =0;
        this.posicionY=0;
        this.ejecuciones=0;
        CargarImagenDesdeArchivo();



    }

    public PropertyChangeSupport getChangeSupport() {
        return changeSupport;
    }

    public void setChangeSupport(PropertyChangeSupport changeSupport) {
        this.changeSupport = changeSupport;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Vehiculo.id = id;
    }

    public void setTiempoCarrera(double tiempoCarrera) {
        this.tiempoCarrera = tiempoCarrera;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int nuevaPosicionX) {
        int viejaPosicionX=this.posicionX;
        this.posicionX=nuevaPosicionX;
        changeSupport.firePropertyChange("posicionX",viejaPosicionX,nuevaPosicionX);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }



    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public double getTiempoCarrera() {
        return tiempoCarrera;
    }

    public void setTiempoCarrera(int tiempoCarrera) {
        this.tiempoCarrera = tiempoCarrera;
    }

    public boolean isCarreraTerminada() {
        return carreraTerminada;
    }

    public void setCarreraTerminada(boolean carreraTerminada) {
        this.carreraTerminada = carreraTerminada;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getEjecuciones() {
        return ejecuciones;
    }

    public void setEjecuciones(int ejecuciones) {
        this.ejecuciones = ejecuciones;
    }

    public void AcelerarFrenar(int anchoPanel){
        Parar();
        Arrancar(posicionX,posicionY,anchoPanel,true);
    }

    public void ReanudarMarcha(int anchoPanel){
       // System.out.println("POS X: "+ x);
        Arrancar(posicionX,posicionY,anchoPanel,false);
    }

    public void IniciarPosicion(int x, int y){
        setBounds(x,y,ancho,alto);
    }

    public void Parar(){
        task.cancel();
        setCarreraTerminada(true);
    }

    public void Arrancar(int x, int y, int anchoPanel, boolean cambiarVelocidad){
        this.posicionX = x;
        this.posicionY = y;
        timer = new Timer();
        if(cambiarVelocidad){
            Random random = new Random();
            velocidad = random.nextInt(50) + 5;
        }


        // Crear una referencia final a 'this' de toda la clase, esto es necesario para poder actualizar el atributo x del movimiento,
        // si ello en la clase interna 'TimerTask' no nos deja acceder a x.
        final Vehiculo referenciaThis = this;

        task = new TimerTask() {

            @Override
            public void run() {
                referenciaThis.ejecuciones++;
                if ((referenciaThis.posicionX + velocidad) > anchoPanel - 250) {
                    Parar();
                } else {
                    setBounds((referenciaThis.posicionX + velocidad), y, ancho, alto);
                    referenciaThis.posicionX += velocidad;

                }

                tiempoCarrera= referenciaThis.ejecuciones * 50;


                //System.out.println("TiempoCarrera: "+tiempoCarrera);
            }




        };


        timer.schedule(task, 0, 50);
    }



//    public void Arrancar(int x, int y, int anchoPanel){
//        this.x=x;
//        this.y=y;
//
//        final int[] x2 = {x};
//        timer=new Timer();
//        Random random=new Random();
//        velocidad=random.nextInt(50)+5;
//        task=new TimerTask() {
//            int ejecuciones=0;
//
//
//            @Override
//            public void run() {
//                ejecuciones++;
//                if((x2[0] + velocidad)>anchoPanel-250){
//                    Parar();
//                }else{
//                    setBounds((x2[0] + velocidad),y,ancho,alto);
//                    x2[0] +=velocidad;
//
//                }
//
//
//
//
//                tiempoCarrera=ejecuciones*50;
//            }
//
//        };
//
//        timer.schedule(task,0,50);
//
//    }


    @Override
    public String toString() {
        return "Vehiculo{" +
                "imageUrl='" + imageUrl + '\'' +
                ", alto=" + alto +
                ", ancho=" + ancho +
                ", indice=" + indice +
                '}';
    }

    private void CargarImagenDesdeArchivo() {
        try {
            id++;
            // Cargar la imagen desde la ruta local
            File file = new File(imageUrl);
            image = ImageIO.read(file).getScaledInstance(ancho,alto,Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImagenSeleccionada(Image imagenSeleccionada) {
        this.image = imagenSeleccionada.getScaledInstance(ancho,alto,Image.SCALE_SMOOTH);
        repaint();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        if (image != null) {

            g.drawImage(image, 0, 0, ancho, alto, this);

        }

    }

//    @Override
//    public Dimension getPreferredSize() {
//        // Establecer el tamaño preferido del panel al tamaño de la imagen
//        if (image != null) {
//            return new Dimension(image.getWidth(), image.getHeight());
//        }
//        return super.getPreferredSize();
//    }

}
