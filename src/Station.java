import java.util.ArrayList;
import java.util.List;

/*具体存储的结构*/
public class Station {
	private String stationName; // 站点名称
	private String lineName; // 线路名称
	private List<Station> linkStation = new ArrayList<>(); // 相邻站点
	/* get、set方法 */

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

	public boolean equals(Object obj) { // 判断传入对象的属性
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
