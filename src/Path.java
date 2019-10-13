import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Path {

	private Station start; // 开始站点
	private Station end; // 结束站点
	private Double distance = 0.0; // 站点间的距离
	private List<Station> passStation = new ArrayList<>();  //经过的站点

	/* get、set方法 */
	public Station getStart() {
		return start;
	}

	public void setStart(Station start) {
		this.start = start;
	}

	public Station getEnd() {
		return end;
	}

	public void setEnd(Station end) {
		this.end = end;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public List<Station> getPassStation() {
		return passStation;
	}

	public void setPassStation(List<Station> passStation) {
		this.passStation = passStation;
	}


	public void readShort()  //读取最短路径的文件

	{
		try {
			FileReader fr = new FileReader("routine.txt");// 需要读取的文件路径
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

}
