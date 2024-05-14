import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class VehiculoPropertyChangeListener implements PropertyChangeListener {
    private static int contador=0;
    private static boolean bandera=false;
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName ( );
        Object oldValue = evt.getOldValue ( );
        Object newValue = evt.getNewValue ( );

        Vehiculo vehiculo;
        vehiculo=(Vehiculo) evt.getSource();

        if(propertyName.equals("imagenUrl")){

            if(!oldValue.equals(newValue)){

                vehiculo.setPreparado(true);
            }
        }

        if(propertyName.equals("carreraTerminada")){

            if(!oldValue.equals(newValue)){
                if(bandera==false && vehiculo.getTiempoCarrera()!=5000000){
                    vehiculo.setIdGanadora(vehiculo.getIndice());
                    bandera=true;
                }

                contador++;
                if(contador==3 && newValue.equals(true)){
                    vehiculo.mostrarMensajeFinCarrera();
                    contador=0;
                    bandera=false;
                }
                if(contador==3){
                    contador=0;
                    bandera=false;
                }


            }
        }
    }
}
