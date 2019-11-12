package airline;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import models.CreditCard;

public class CardTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -66779719924315413L;
	private String[] columnNames={"cardNumber","expirationDate","cardHolder","cvv","billingAddress"};
	private ArrayList<CreditCard> lines;
	
	public CardTableModel() {
		// TODO �Զ����ɵĹ��캯�����
		lines = new ArrayList<>();
	}
	public CardTableModel(ArrayList<CreditCard> lines){
		this.lines=lines;
	}
	
	@Override
	public int getColumnCount() {
		// TODO �Զ����ɵķ������
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO �Զ����ɵķ������
		return lines==null?0:lines.size();
	}

	@Override
	public String getColumnName(int col){
		return columnNames[col];
	}

	@Override
	public boolean isCellEditable(int row,int col){
		return false;
	}
	public void changeData(ArrayList<CreditCard> list){
		lines=list;
		fireTableDataChanged();
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO �Զ����ɵķ������
		try{
			CreditCard card = lines.get(rowIndex);
			switch (columnIndex) {
			case 0:return "****"+card.getCardNumber().substring(card.getCardNumber().length()-4);
			case 1:return String.format("%02d/%02d", card.getExpirationDate()/100,card.getExpirationDate()%100);
			case 2:return card.getCardHolder();
			case 3:return String.format("%03d", card.getCvv());
			case 4:return card.getBilliing_address();
			default:return null;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
