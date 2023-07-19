package visualizadoruniovi;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * @author Esqueleto obtenido de https://coderanch.com/t/522452/java/JTable-multiple-lines-cell-cells
 */
class MyTableModel extends AbstractTableModel {

    private JTable table;
    private String[][] data;
    private String[] columnNames;

    public MyTableModel(JTable t, String[][] d, String[] cn) {
        this.table = t;
        data = d;
        columnNames = cn;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        this.data[rowIndex][columnIndex] = aValue.toString();
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public String[][] getAllData()
    {
        return this.data;
    }
}
