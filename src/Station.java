import java.util.ArrayList;
import java.util.List;

/*����洢�Ľṹ*/
public class Station {
	private String stationName; // վ������
	private String lineName; // ��·����
	private List<Station> linkStation = new ArrayList<>(); // ����վ��
	/* get��set���� */

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public List<Station> getLinkStation() {
		return linkStation;
	}

	public void setLinkStation(List<Station> linkStation) {
		this.linkStation = linkStation;
	}

	public Station(String stationName, String lineName) {
		this.stationName = stationName;
		this.lineName = lineName;
	}

	public Station(String stationName) {
		this.stationName = stationName;
	}

	public boolean equals(Object obj) { // �жϴ�����������
		if (this == obj) {
			return true;
		} else if (obj instanceof Station) {
			Station station = (Station) obj;
			if (station.getStationName().equals(this.getStationName())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
