import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Line {

	public static String readFile;  
	public static String writeFile;

	public static HashMap<String, List<Station>> lineData; // ������·����
	public static LinkedHashSet<List<Station>> lineSet = new LinkedHashSet<>();// ������·����

	public static void createline() {   //�洢������·
		lineData = new HashMap<>();
		for (List<Station> stations : lineSet) {
			lineData.put(stations.get(1).getLineName(), stations);
		}
	}

	public static String getLineNameByStation(Station station) {  //ͨ��վ���ȡ��·����
		createline();
		String startname = station.getStationName();
		for (Map.Entry<String, List<Station>> entry : lineData.entrySet()) {
			List<Station> stations = entry.getValue();
			for (Station station1 : stations) {
				if (station1.getStationName().equals(startname)) {
					return entry.getKey();
				}
			}
		}
		return "";
	}

	public static ArrayList<Station> getLine(String lineName1, String lineName2) { // ��ȡ��·��Ϣ
		ArrayList<Station> line = new ArrayList<Station>();
		String[] lineArr = lineName1.split(",");
		for (String s : lineArr) {
			line.add(new Station(s, lineName2));
		}
		return line;
	}

	public static String writeLine(String lineName) throws UnsupportedEncodingException, FileNotFoundException { // ��ȡ��Ӧ��·����Ϣ
		createline();
		lineName = lineName.substring(0, 1);
		List<Station> lineInfo = lineData.get(lineName);

		String line = lineInfo.stream().map(x -> x.getStationName()).collect(Collectors.joining(","));

		try {
			Files.write(Paths.get(writeFile), line.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return line;
	}

	public void readLine()   //��ȡ��Ӧ����·�ļ�

	{
		try {
			FileReader fr = new FileReader("station.txt");// ��Ҫ��ȡ���ļ�·��
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			while (s != null)// �����ǰ�в�Ϊ��
			{
				System.out.println(s);// ��ӡ��ǰ��
				s = br.readLine();// ��ȡ��һ��
			}
			br.close();// �ر�BufferReader��
			fr.close(); // �ر��ļ���
		} catch (IOException e)// ��׽�쳣
		{
			System.out.println("ָ���ļ�������");// �����쳣
		}
	}

	public static void writePassStation(Path path) { //������·��

		FileWriter f = null;
		BufferedWriter b = null;
		try {
			f = new FileWriter(new File(writeFile), true);
			b = new BufferedWriter(f);
			b.write((path.getPassStation().size() + 1) + "\t\n"); // д��վ̨��
			b.write(path.getStart().getStationName() + "\t\n"); // д��վ̨����
			String startLineName = getLineNameByStation(path.getStart());// ��ȡ������·
			String line = startLineName; // Ĭ��ת�˵�����·�뵱ǰһ��
			for (Station station : path.getPassStation()) {
				if (!line.equals(station.getLineName())) {
					b.write(station.getLineName() + "����" + "\t\n"); // д��ת����·
					b.write(station.getStationName() + "\t\n");
					line = station.getLineName();
				} else {
					b.write(station.getStationName() + "\t\n");
				}
			}
			b.close();
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public static void readFile() {   //��ȡ������·ͼ��Ϣ
		File file = new File(readFile);
		BufferedReader reader = null;

		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");

			reader = new BufferedReader(inputStreamReader);
			String line = null;
			String lineName = "1";
			while ((line = reader.readLine()) != null) {
				if (line.trim().startsWith("*")) {
					String[] lineInfo = line.substring(1).split("-");
					lineSet.add(getLine(lineInfo[1].trim(), lineInfo[0].trim()));
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}

	}

}
