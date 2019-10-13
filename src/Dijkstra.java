
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

	private static HashMap<Station, Path> resultMap = new HashMap<>(); //�����
	private static List<Station> visitedStation = new ArrayList<>();   //��������վ��

	public static Path calculate(Station start, Station end) { //ѭ��������ý��
		if (!visitedStation.contains(start)) { //
			visitedStation.add(start);
		}
		// �����ʼվ�������ֹվ�㣬������result�����þ����station������ʼ������ѱ���

		if (resultMap.isEmpty()) {
			List<Station> linkStation = getLinkStation(start);
			for (Station station : linkStation) { // ������վ���������
				Path path = new Path();
				path.setStart(start);
				path.setEnd(station);
				path.setDistance(1.0); // Ĭ��վ�����붼Ϊ1
				path.getPassStation().add(station);
				resultMap.put(station, path);
			}
		}
		Station parent = getNextStation();

		if (parent == null) { // ����վ�㶼�Ѿ��������
			Path path = new Path();
			path.setDistance(0.0);
			path.setStart(start);
			path.setEnd(end);
			return resultMap.put(end, path);
		}

		// ����õ�������ڵ���Ŀ�����ͬ����ֱ�ӷ�������ڵ��Ӧ��result����
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
			if (childResult != null) { // ����������ڵ�
				if (childResult.getDistance() > distance) {
					childResult.setDistance(distance);
					childResult.getPassStation().clear();
					childResult.getPassStation().addAll(parentPassStation);
					childResult.getPassStation().add(child);
				}

			} else {
				childResult = new Path(); // û��������ڵ�
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
	

	public static List<Station> getLinkStation(Station station) { // ��ȡ�������ڵ�
		List<Station> linkedStaion = new ArrayList<Station>();

		for (List<Station> line : Line.lineSet) {
			for (int i = 0; i < line.size(); i++) {
				if (station.equals(line.get(i))) {
					if (i == 0) {
						linkedStaion.add(line.get(i + 1)); //��վ��Ϊ��ʼվ��ʱ
					} else if (i == (line.size() - 1)) {
						linkedStaion.add(line.get(i - 1)); //��վ�������һ��վ��ʱ
					} else {
						linkedStaion.add(line.get(i + 1)); //λ������λ��
						linkedStaion.add(line.get(i - 1));
					}
				}
			}
		}
		return linkedStaion;
	}

	private static Station getNextStation() {  //�����һ����Ҫ�����ĵ�
		Double min = 999999.0;
		Station s = null;
		Set<Station> stations = resultMap.keySet();
		for (Station station : stations) {
			if (visitedStation.contains(station)) {
				continue;
			}
			Path result = resultMap.get(station); 
			if (result.getDistance() < min) {  //�Ƚϻ����̾���
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
