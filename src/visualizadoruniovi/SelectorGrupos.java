package visualizadoruniovi;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

/**
 * @author Saulete
 */
public class SelectorGrupos extends javax.swing.JFrame {

    private List<String> asignaturas;
    private List<List<JCheckBox>> checkboxes;
    private int numColAsignaturas;
    private int numColGrupos;
    
    public SelectorGrupos()
    {
        initComponents();
        asignaturas = new ArrayList<>(); // [asig1, asig2, asig3...]
        checkboxes = new ArrayList<>();
        numColAsignaturas = 2;
        numColGrupos = 4;
    }
    
    public void actualizarVentana(List<List<Horario>> lista, JTable tabla, JCheckBox checked, JScrollPane scroll, boolean forzarReinicio)
    {
        if (forzarReinicio || asignaturas.isEmpty())
        {
            asignaturas.clear(); // Reiniciamos las dos listas 
            checkboxes.clear();

            // Procesar la lista, para ello, guardamos el nombre de las asignaturas en una lista y sus grupos en otra
            List<List<String>> grupos = new ArrayList<>(); // [{PL1, PL2, PL3}, {A, ING}, {TG4, TG7, TG8}...] siguiendo el mismo orden que el nombre de la asignatura

            for (List<Horario> l : lista)
            {
                for (Horario hor : l)
                {
                    if (!asignaturas.contains(hor.getAsignatura()))
                    {
                        asignaturas.add(hor.getAsignatura()); // Si la asignatura no está en la lista de asignaturas, se añade
                        grupos.add(new ArrayList<>()); // También es necesario añadir una lista vacía para guardar los grupos de esa asignatura
                        checkboxes.add(new ArrayList<>()); // Ya que luego guardaremos los objetos checkbox, también aumentamos el tamaño de esta lista
                    }
                    // Si no está guardado el grupo de la asignatura a analizar, se añade dicho grupo
                    if (!grupos.get(asignaturas.indexOf(hor.getAsignatura())).contains(hor.getGrupo())) grupos.get(asignaturas.indexOf(hor.getAsignatura())).add(hor.getGrupo());
                }
            }

            getContentPane().removeAll();
            getContentPane().setLayout(new MigLayout("", "", "")); // El layout lo transformamos a un MigLayout para un ordenamiento fácil
            this.numColAsignaturas = asignaturas.size() / 5 + 1;
            for (List<String> l : grupos) l.sort(null); // Ordenamos alfabéticamente los grupos de las asignaturas (A, ING, PL1, PL2, TG1, TG2, TG3...)

            int rowActual = 0;
            int maxRow = 0;
            for (int i = 0; i < asignaturas.size(); i++)
            {
                JLabel label = new JLabel();
                label.setText(asignaturas.get(i));
                getContentPane().add(label, "cell " + (i%numColAsignaturas*(numColGrupos+1)) + " " + rowActual + " " + numColGrupos + " 1");
                for (int j = 0; j < grupos.get(i).size(); j++)
                {
                    JCheckBox cb = new JCheckBox(grupos.get(i).get(j)); // Creamos checkbox con el nombre del grupo
                    cb.addItemListener((java.awt.event.ItemEvent evt) -> {
                        Utilidades.limpiarTabla(tabla);
                        Utilidades.actualizarDatosTabla(tabla, lista, getAsignaturas(), getCheckboxes(), checked.isSelected());
                        Utilidades.actualizarSizeTabla(tabla, scroll);
                    });
                    getContentPane().add(cb, "cell " + (i%numColAsignaturas*(numColGrupos+1)+j%numColGrupos) + " " + (rowActual+1+j/numColGrupos) + " 1 1"); // Lo añadimos en su posición correspondiente
                    checkboxes.get(i).add(cb);
                    maxRow = Math.max(maxRow, rowActual+1+j/numColGrupos); // Guardamos la fila más inferior para la siguiente asignatura
                }
                if (i%numColAsignaturas == numColAsignaturas-1 && i != asignaturas.size()-1) rowActual = ++maxRow; // Si acabamos de colocar una asignatura a la derecha, bajamos de fila
            }

            if (!asignaturas.isEmpty()) // Añadimos separador
            {
                for (int i = 0; i < numColAsignaturas - 1; i++)
                {
                    JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
                    Dimension dim = sep.getPreferredSize();
                    dim.height = getContentPane().getPreferredSize().height - 32;
                    sep.setPreferredSize(dim);
                    getContentPane().add(sep, "cell " + ((i+1)*(numColGrupos+1)-1) + " 0 1 " + ++maxRow);
                }
            }
            else getContentPane().add(new JLabel("No hay grupos disponibles"), "cell 0 0");

            // Ajustamos el tamaño de la ventana dependiendo de los componentes
            Dimension dim = getContentPane().getPreferredSize();
            dim.width += 19 + (asignaturas.isEmpty() ? 130 : 0);
            dim.height += 40;
            setPreferredSize(dim);
            pack();
        }
    }
    
    public List<String> getAsignaturas() { return this.asignaturas; }
    public List<List<JCheckBox>> getCheckboxes() { return this.checkboxes; }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setTitle("Selector de grupos");
        setPreferredSize(new java.awt.Dimension(400, 400));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 265, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SelectorGrupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectorGrupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectorGrupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectorGrupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SelectorGrupos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
