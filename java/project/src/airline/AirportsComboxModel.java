package airline;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import models.Airport;;

@SuppressWarnings("rawtypes")
public class AirportsComboxModel extends AbstractListModel implements ComboBoxModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Airport> aps;
	private Airport selected;
	public AirportsComboxModel(ArrayList<Airport> ports) {
		// TODO �Զ����ɵĹ��캯�����
		super();
		aps = ports;
	}
	@Override
	public int getSize() {
		// TODO �Զ����ɵķ������
		return aps.size();
	}

	@Override
	public Object getElementAt(int index) {
		// TODO �Զ����ɵķ������
		return aps.get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		// TODO �Զ����ɵķ������
		selected = (Airport) anItem;
		
	}

	@Override
	public Object getSelectedItem() {
		// TODO �Զ����ɵķ������
		return selected;
	}

}
