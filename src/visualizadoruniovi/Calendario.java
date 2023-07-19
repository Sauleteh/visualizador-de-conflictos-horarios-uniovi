package visualizadoruniovi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JTable;
import java.util.Date;
import javax.swing.JLabel;
/**
 * @author Saulete
 */
public class Calendario
{
    private Calendar calendar;
    private int avanzado; // Variable que cuenta el número de semanas avanzadas desde el día actual
    private boolean fechaPersonalizada;
    private Date nuevaFecha;
    
    public Calendario()
    {
        this.avanzado = 0;
        this.resetCalendario();
        this.fechaPersonalizada = false;
        this.nuevaFecha = null;
    }
    
    /**
     * Actualiza todos los datos relacionados con las fechas en el calendario (cabecera y título)
     * @param tabla es la tabla donde se encuentran los días de la semana con sus respectivos números
     * @param titulo es donde se ubica el nombre del mes actual junto con el año
     */
    public void actualizarCabeceras(JTable tabla, JLabel titulo)
    {
        String[] dias = {"", "Lunes ", "Martes ", "Miércoles ", "Jueves ", "Viernes "};
        int diaActual = getDiaSemana();
        for (int i = diaActual; i > 0; i--)
        {
            if (i < 6) dias[i] += this.calendar.get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.DATE, -1);
        }
        
        // Este bloque de código es para actualizar los datos en los que nos encontramos actualmente
        calendar.add(Calendar.DATE, 1); // Vamos al lunes
        Utilidades.setDiaLunes(calendar.get(Calendar.DATE));
        Utilidades.setMesLunes(calendar.get(Calendar.MONTH) + 1);
        Utilidades.setYearLunes(calendar.get(Calendar.YEAR));
        
        resetCalendario();
        for (int i = diaActual + 1; i < 6; i++)
        {
            calendar.add(Calendar.DATE, 1);
            dias[i] += this.calendar.get(Calendar.DAY_OF_MONTH);
        }
        resetCalendario();
        
        String mes = new SimpleDateFormat("MMMMMMMMMM").format(calendar.getTime());
        mes = mes.substring(0, 1).toUpperCase() + mes.substring(1);
        
        titulo.setText(mes + " de " + this.calendar.get(Calendar.YEAR));
        tabla.setModel(new MyTableModel(tabla, ((MyTableModel)tabla.getModel()).getAllData(), dias));
    }
    
    /**
     * Ya que el primer día de la semana para la clase Calendar es el domingo, hacemos una función que tome en cuenta el lunes como primer día de la semana
     * @return un entero entre 1 y 7 indicando el día de la semana, siendo el valor 1 para el lunes y el valor 7 para el domingo
     */
    public int getDiaSemana()
    {
        this.calendar.add(Calendar.DATE, -1);
        int num = this.calendar.get(Calendar.DAY_OF_WEEK);
        this.calendar.add(Calendar.DATE, 1);
        return num;
    }
    
    /**
     * Devuelve la fecha del calendario a su día original
     */
    public void resetCalendario()
    {
        this.calendar = Calendar.getInstance(new Locale("es","ES"));
        this.calendar.setFirstDayOfWeek(Calendar.MONDAY);
        if (this.fechaPersonalizada) this.calendar.setTime(nuevaFecha);
        else this.calendar.add(Calendar.DATE, avanzado * 7);
    }
    
    public void avanzarSemanas(int semanas)
    {
        this.calendar.add(Calendar.DATE, semanas * 7);
        avanzado += semanas;
    }
    
    public void setFecha(int dia, String mes, int year) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMMMMMMMM-yyyy");
        this.nuevaFecha = sdf.parse(dia + "-" + mes + "-" + year);
        this.calendar.setTime(nuevaFecha);
        this.fechaPersonalizada = true;
    }
    
    public void moverAlLunes()
    {
        this.calendar.add(Calendar.DATE, -(this.getDiaSemana() - 1));
    }
    
    public Calendar getCalendario() { return this.calendar; }
}
