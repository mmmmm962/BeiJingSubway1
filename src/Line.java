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

	public static HashMap<String, List<Station>> lineData; // 地铁线路数据
	public static LinkedHashSet<List<Station>> lineSet = new LinkedHashSet<>();// 地铁线路集合

	public static void createline() {   //存储地铁线路
		lineData = new HashMap<>();
		for (List<Station> stations : lineSet) {
			lineData.put(stations.get(1).getLineName(), stations);
		}
	}

	public static String getLineNameByStation(Station station) {  //通过站点获取线路名称
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

	public static ArrayList<Station> getLine(String lineName1, String lineName2) { // 读取线路信息
		ArrayList<Station> line = new ArrayList<Station>();
		String[] lineArr = lineName1.split(",");
		for (String s : lineArr) {
			line.add(new Station(s, lineName2));
		}
		return line;
	}

	public static String writeLine(String lineName) throws UnsupportedEncodingException, FileNotFoundException { // 提取相应线路的信息
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

	public void readLine()   //读取相应的线路文件

	{
		try {
			FileReader fr = new FileReader("station.txt");// 需要读取的文件路径
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			while (s != null)// 如果当前行不为空
			{
				System.out.println(s);// 打印当前行
				s = br.readLine();// 读取下一行
			}
			br.close();// 关闭BufferReader流
			fr.close(); // 关闭文件流
		} catch (IOException e)// 捕捉异常
		{
			System.out.println("指定文件不存在");// 处理异常
		}
	}

	public static void writePassStation(Path path) { //输出最短路径

		FileWriter f = null;
		BufferedWriter b = null;
		try {
			f = new FileWriter(new File(writeFile), true);
			b = new BufferedWriter(f);
			b.write((path.getPassStation().size() + 1) + "\t\n"); // 写入站台数
			b.write(path.getStart().getStationName() + "\t\n"); // 写入站台名称
			String startLineName = getLineNameByStation(path.getStart());// 获取地铁线路
			String line = startLineName; // 默认转乘地铁线路与当前一致
			for (Station station : path.getPassStation()) {
				if (!line.equals(station.getLineName())) {
					b.write(station.getLineName() + "号线" + "\t\n"); // 写入转乘线路
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



	public static void readFile() {   //读取地铁线路图信息
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
