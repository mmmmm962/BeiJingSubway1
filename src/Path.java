import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Path {

	private Station start; // ��ʼվ��
	private Station end; // ����վ��
	private Double distance = 0.0; // վ���ľ���
	private List<Station> passStation = new ArrayList<>();  //������վ��

	/* get��set���� */
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


	public void readShort()  //��ȡ���·�����ļ�

	{
		try {
			FileReader fr = new FileReader("routine.txt");// ��Ҫ��ȡ���ļ�·��
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

}
