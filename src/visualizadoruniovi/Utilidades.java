package visualizadoruniovi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author Saulete
 */
public class Utilidades
{
    private static int diaLunes;
    private static int mesLunes;
    private static int yearLunes;
    
    /**
     * Cuenta el número de ocurrencias de una palabra en un String
     * @param text es el String donde se van a buscar las ocurrencias
     * @param substring es la String que se pretende buscar
     * @return el número de veces que aparece substring en text
     */
    public static int countSubstringOccurrences(String text, String substring) {
        int count = 0;
        int index = 0;

        while (index != -1) {
            index = text.indexOf(substring, index);
            if (index != -1) {
                count++;
                index += substring.length();
            }
        }
        return count;
    }
    
    /**
     * Ajusta la altura y anchura de las filas de una tabla en base a su contenido
     * @param table es la tabla donde están los datos
     * @param scrollPane es el panel de scrolling para saber el ancho total que tendrá la tabla
     */
    public static void actualizarSizeTabla(JTable table, JScrollPane scrollPane)
    {
        // Ajustamos la altura de las filas de la tabla
        for (int i = 0; i < table.getRowCount(); i++)
        {
            int numSaltos = 0;
            for (int j = 0; j < table.getColumnCount(); j++)
            {
                if (Utilidades.countSubstringOccurrences(table.getValueAt(i, j).toString(), "\n") > numSaltos)
                {
                    numSaltos = Utilidades.countSubstringOccurrences(table.getValueAt(i, j).toString(), "\n");
                }
            }
            table.setRowHeight(i, 16 * (numSaltos + 1));
        }
        
        // Ajustamos el ancho de las columnas solo si no hemos superado el mínimo de la tabla
        int widthScroll = (scrollPane.getSize().width > table.getMinimumSize().width + scrollPane.getVerticalScrollBar().getSize().width + 2) ? scrollPane.getSize().width : table.getMinimumSize().width + scrollPane.getVerticalScrollBar().getSize().width + 2;
            
        table.getColumnModel().getColumn(0).setPreferredWidth(34); // Columna de las horas
        int sizePerCol = (widthScroll - 34 - scrollPane.getVerticalScrollBar().getSize().width - 2) / 5; // El valor 34 viene de la 1ª columna. Las dos unidades es para evitar errores
        for (int j = 1; j < 6; j++)
        {
            table.getColumnModel().getColumn(j).setPreferredWidth(sizePerCol);
        }
    }
    
    /**
     * Inserta en la tabla los horarios correspondientes en su respectiva hora de la tabla
     * @param tabla es la tabla que simula un calendario
     * @param listaHorarios es la lista de horarios
     * @param asignaturas es la lista de asignaturas con sus nombres
     * @param checkboxes es una lista de listas de checkboxes. Cada lista está asociada a su asignatura correspondiente
     * @param filtrar es el estado de la checkbox que indica si se quieren filtrar los grupos
     */
    public static void actualizarDatosTabla(JTable tabla, List<List<Horario>> listaHorarios, List<String> asignaturas, List<List<JCheckBox>> checkboxes, boolean filtrar)
    {
        int[][] diasBuscar = new int [5][3];
        Calendar cal = Calendar.getInstance(new Locale("es","ES"));
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-LL-yyyy");
        Date fecha = null;
        try { fecha = sdf.parse(getDiaLunes() + "-" + getMesLunes() + "-" + getYearLunes()); }
        catch (ParseException ex) { System.err.println(ex.getMessage()); }
        cal.setTime(fecha);
        
        for (int i = 0; i < 5; i++)
        {
            diasBuscar[i][0] = cal.get(Calendar.DAY_OF_MONTH);
            diasBuscar[i][1] = cal.get(Calendar.MONTH) + 1;
            diasBuscar[i][2] = cal.get(Calendar.YEAR);
            cal.add(Calendar.DATE, 1);
        }
        
        int indiceDia;
        for (List<Horario> lista : listaHorarios)
        {
            for (Horario horario : lista)
            {
                indiceDia = perteneceSemana(diasBuscar, horario);
                if (indiceDia != -1 && filtrar(horario, asignaturas, checkboxes, filtrar)) tabla.setValueAt((tabla.getValueAt(horario.getHora() - 9, indiceDia + 1) == "" ? "" : tabla.getValueAt(horario.getHora() - 9, indiceDia + 1) + "\n") + horario.getAsignatura() + "|" + horario.getGrupo() + "|" + horario.getAula(), horario.getHora() - 9, indiceDia + 1);
            }
        }
    }
    
    /**
     * Procesar el filtrado para un horario
     * @param hor es el horario a comprobar
     * @param asigs es la lista de nombres de las asignaturas
     * @param cbs es la lista de listas de checkboxes
     * @param filtrar es el estado de la checkbox que indica si se quiere filtrar los grupos
     * @return true si se acepta el filtrado, false si no se quiere filtrar
     */
    private static boolean filtrar(Horario hor, List<String> asigs, List<List<JCheckBox>> cbs, boolean filtrar)
    {
        if (!filtrar) return true;
        
        int ind = asigs.indexOf(hor.getAsignatura());
        if (ind == -1) throw new NullPointerException("No se ha conseguido procesar el filtrado: La asignatura buscada no existe y eso es imposible");
        
        List<JCheckBox> listCB = cbs.get(ind);
        for (JCheckBox cb : listCB)
        {
            if (cb.getText().equals(hor.getGrupo())) return cb.isSelected();
        }
        
        throw new NullPointerException("No se ha conseguido procesar el filtrado: El grupo buscado no existe y eso es imposible");
    }
    
    /**
     * Buscar si el horario está en el rango de días de una semana dada
     * @param semana es una matriz con 5 filas (lunes a viernes) y 3 columnas (día, mes y año)
     * @param horario es el horario con un día, mes y año dados para ver si está dentro del rango
     * @return el índice de la semana (siendo 0 lunes y 4 viernes) si el horario pertenece a la semana, -1 en caso contrario
     */
    private static int perteneceSemana(int[][] semana, Horario horario)
    {
        for (int i = 0; i < 5; i++)
        {
            if (semana[i][0] == horario.getDia() &&
                semana[i][1] == horario.getMes() &&
                semana[i][2] == horario.getYear()) return i;
        }
        return -1;
    }
    
    /**
     * Borra los datos actuales de la tabla, excluyendo la primera columna la cual indica la hora
     * @param tabla es la tabla de donde se pretende borrar sus datos
     */
    public static void limpiarTabla(JTable tabla)
    {
        for (int i = 0; i < 11; i++) for (int j = 1; j < 6; j++) tabla.setValueAt("", i, j);
    }
    
    public static int getDiaLunes() { return diaLunes; }
    public static int getMesLunes() { return mesLunes; }
    public static int getYearLunes() { return yearLunes; }
    
    public static void setDiaLunes(int v) { diaLunes = v; }
    public static void setMesLunes(int v) { mesLunes = v; }
    public static void setYearLunes(int v) { yearLunes = v; }
}
