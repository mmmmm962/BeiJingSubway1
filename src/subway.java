
/*��ȡ�����е�����*/
import java.io.File;
import java.io.IOException;

public class subway {
	public static void main(String[] args) throws IOException {
		System.out.println("�밴�չ涨����,���򽫻�Ӱ������ִ��");
		System.out.println("��ȡ������Ϣ������Ϊjava subway -map subway.txt");
		System.out.println("��ѯ������·������Ϊjava subway -a ��·�� -map subway.txt -o station.txt");
		System.out.println("��ѯվ��֮�����·��������Ϊjava subway -b վ���� վ���� -map subway.txt -o routine.txt");

		switch (args[0]) { // �������ж�ȡ����
		case "-map":
			if (args.length == 2) {
				Line.readFile = System.getProperty("user.dir") + File.separator + "\\" + args[1];
				// ��ȡ������Ϣ
				Line.readFile();
				System.out.println("�ɹ���ȡ������Ϣ");


			} else {
				System.out.println("���벻��ȷ��");
				break;
			}
			break;

		case "-a":
			if (args.length == 6) {
				Line.readFile = System.getProperty("user.dir") + File.separator + "\\" + args[3];
				Line.writeFile = System.getProperty("user.dir") + File.separator + "\\" + args[5];
				Line.readFile();
				Line.writeLine(args[1]);
				System.out.println("����·����ϢΪ:");
				Line line = new Line();
				line.readLine();
			} else {

				System.out.println("���벻��ȷ��");
				break;
			}
			break;

		case "-b":
			if (args.length == 7) {
				Line.readFile = System.getProperty("user.dir") + File.separator + "\\" + args[4];
				Line.writeFile = System.getProperty("user.dir") + File.separator + "\\" + args[6];
				Line.readFile();
				Path path = Dijkstra.calculate(new Station(args[1]), new Station(args[2]));
				Line.writePassStation(path);
				System.out.println("���·����ϢΪ:");
				Line line = new Line();
				path.readShort();
			} else {
				System.out.println("���벻��ȷ��");
				break;
			}
			break;

		default:
			System.out.println("���벻��ȷ��");
		}

	}
}
