
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Dijkstra {

	private static HashMap<Station, Path> resultMap = new HashMap<>(); //结果集
	private static List<Station> visitedStation = new ArrayList<>();   //遍历过的站点

	public static Path calculate(Station start, Station end) { //循环遍历获得结果
		if (!visitedStation.contains(start)) { //
			visitedStation.add(start);
		}
		// 如果开始站点等于终止站点，则设置result，设置距离和station。将开始结点标记已遍历

		if (resultMap.isEmpty()) {
			List<Station> linkStation = getLinkStation(start);
			for (Station station : linkStation) { // 将相邻站点加入结果集
				Path path = new Path();
				path.setStart(start);
				path.setEnd(station);
				path.setDistance(1.0); // 默认站点间距离都为1
				path.getPassStation().add(station);
				resultMap.put(station, path);
			}
		}
		Station parent = getNextStation();

		if (parent == null) { // 所有站点都已经遍历完成
			Path path = new Path();
			path.setDistance(0.0);
			path.setStart(start);
			path.setEnd(end);
			return resultMap.put(end, path);
		}

		// 如果得到的最佳邻点与目标点相同，则直接返回最佳邻点对应的result对象。
		if (parent.equals(end)) {
			return resultMap.get(parent);
		}

		List<Station> childLinkStation = getLinkStation(parent);
		for (Station child : childLinkStation) {
			if (visitedStation.contains(child)) {
				continue;
			}

			Double distance = 0.0;
			if (parent.getStationName().equals(child.getStationName())) {
				distance = 0.0;
			}
			Double parentDistance = resultMap.get(parent).getDistance();
			distance = parentDistance + 1.0;

			List<Station> parentPassStation = resultMap.get(parent).getPassStation();
			Path childResult = resultMap.get(child);
			if (childResult != null) { // 含有最佳相邻点
				if (childResult.getDistance() > distance) {
					childResult.setDistance(distance);
					childResult.getPassStation().clear();
					childResult.getPassStation().addAll(parentPassStation);
					childResult.getPassStation().add(child);
				}

			} else {
				childResult = new Path(); // 没有最佳相邻点
				childResult.setDistance(distance);
				childResult.setStart(start);
				childResult.setEnd(child);
				childResult.getPassStation().addAll(parentPassStation);
				childResult.getPassStation().add(child);
			}
			resultMap.put(child, childResult);
		}
		visitedStation.add(parent);
		return calculate(start, end);
	}
	

	public static List<Station> getLinkStation(Station station) { // 获取所有相邻点
		List<Station> linkedStaion = new ArrayList<Station>();

		for (List<Station> line : Line.lineSet) {
			for (int i = 0; i < line.size(); i++) {
				if (station.equals(line.get(i))) {
					if (i == 0) {
						linkedStaion.add(line.get(i + 1)); //此站点为起始站点时
					} else if (i == (line.size() - 1)) {
						linkedStaion.add(line.get(i - 1)); //此站点是最后一个站点时
					} else {
						linkedStaion.add(line.get(i + 1)); //位于其余位置
						linkedStaion.add(line.get(i - 1));
					}
				}
			}
		}
		return linkedStaion;
	}

	private static Station getNextStation() {  //获得下一个需要分析的点
		Double min = 999999.0;
		Station s = null;
		Set<Station> stations = resultMap.keySet();
		for (Station station : stations) {
			if (visitedStation.contains(station)) {
				continue;
			}
			Path result = resultMap.get(station); 
			if (result.getDistance() < min) {  //比较获得最短距离
				min = result.getDistance();
				s = result.getEnd();
			}
		}
		return s;
	}

	public static void getResult(String file) throws IOException {
		BufferedWriter b = null;
		b = new BufferedWriter(new FileWriter(file, true));
		Set<List<Station>> lineSet = Line.lineSet;
		for (List<Station> station1 : lineSet) {
			for (Station station : station1) {
				for (List<Station> station2 : lineSet) {
					for (Station t : station2) {
						Dijkstra dijkstra = new Dijkstra();
						Path result = dijkstra.calculate(station, t);
						resultMap = new HashMap<>();
						visitedStation = new ArrayList<>();
						for (Station s : result.getPassStation()) {
							if (s.getStationName().equals(t.getStationName())) {
								String f1 = station.getStationName() + "\t" + t.getStationName() + "\t"
										+ result.getPassStation().size() + "\t" + result.getDistance() + "\t";
								for (Station f : result.getPassStation()) {
									f1 = f1 + f.getStationName() + ",";
								}
								b.write(f1);
								b.newLine();
							}
						}
					}
				}
			}
		}
		b.flush();
		b.close();
	}
}
