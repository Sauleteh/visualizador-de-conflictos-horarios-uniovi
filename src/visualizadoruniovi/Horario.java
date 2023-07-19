package visualizadoruniovi;

/**
 * @author Saulete
 */
public class Horario
{
    private String asignatura;
    private String grupo;
    private String aula;
    private int hora;
    private int dia;
    private int mes;
    private int year;
    
    public Horario(String as, String gr, String au, int h, int d, int m, int y)
    {
        this.asignatura = as;
        this.grupo = gr;
        this.aula = au;
        this.hora = h;
        this.dia = d;
        this.mes = m;
        this.year = y;
    }
    
    public String getAsignatura() { return this.asignatura; }
    public String getGrupo() { return this.grupo; }
    public String getAula() { return this.aula; }
    public int getHora() { return this.hora; }
    public int getDia() { return this.dia; }
    public int getMes() { return this.mes; }
    public int getYear() { return this.year; }
    
    public void setAsignatura(String v) { this.asignatura = v; }
    public void setGrupo(String v) { this.grupo = v; }
    public void setAula(String v) { this.aula = v; }
    public void setHora(int v) { this.hora = v; }
    public void setDia(int v) { this.dia = v; }
    public void setMes(int v) { this.mes = v; }
    public void setYear(int v) { this.year = v; }
    
    @Override
    public String toString()
    {
        return String.format("%02d:00 %s %s %s %02d/%02d/%d\n", getHora(), getAsignatura(), getGrupo(), getAula(), getDia(), getMes(), getYear());
    }
}
