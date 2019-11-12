package airline;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import models.AirlineMileage;

public class AirlineMileageTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1028547903597753725L;
	private String[] columnNames={"AirLine","Mileage"};
	private ArrayList<AirlineMileage> list;
	
	public AirlineMileageTableModel() {
		// TODO �Զ����ɵĹ��캯�����
		list = new ArrayList<>();
	}
	public AirlineMileageTableModel(ArrayList<AirlineMileage> list){
		this.list=list;
	}
	
	@Override
	public int getColumnCount() {
		// TODO �Զ����ɵķ������
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO �Զ����ɵķ������
		return list==null?0:list.size();
	}

	@Override
	public String getColumnName(int col){
		return columnNames[col];
	}

	@Override
	public boolean isCellEditable(int row,int col){
		return false;
	}

	@Override
	public Object getValueAt(int row, int col) {
		try{
			AirlineMileage aMileage = list.get(row);
			switch(col){
			case 0: return aMileage.getAirline();
			case 1: return aMileage.getMileage();
			default: return null;
			}
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
