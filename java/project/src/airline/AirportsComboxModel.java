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
		// TODO 自动生成的构造函数存根
		super();
		aps = ports;
	}
	@Override
	public int getSize() {
		// TODO 自动生成的方法存根
		return aps.size();
	}

	@Override
	public Object getElementAt(int index) {
		// TODO 自动生成的方法存根
		return aps.get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		// TODO 自动生成的方法存根
		selected = (Airport) anItem;
		
	}

	@Override
	public Object getSelectedItem() {
		// TODO 自动生成的方法存根
		return selected;
	}

}
