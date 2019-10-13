
/*读取命令行的数据*/
import java.io.File;
import java.io.IOException;

public class subway {
	public static void main(String[] args) throws IOException {
		System.out.println("请按照规定输入,否则将会影响程序的执行");
		System.out.println("读取地铁信息的命令为java subway -map subway.txt");
		System.out.println("查询地铁线路的命令为java subway -a 线路名 -map subway.txt -o station.txt");
		System.out.println("查询站点之间最短路径的命令为java subway -b 站点名 站点名 -map subway.txt -o routine.txt");

		switch (args[0]) { // 从命令行读取参数
		case "-map":
			if (args.length == 2) {
				Line.readFile = System.getProperty("user.dir") + File.separator + "\\" + args[1];
				// 读取地铁信息
				Line.readFile();
				System.out.println("成功读取地铁信息");


			} else {
				System.out.println("输入不正确！");
				break;
			}
			break;

		case "-a":
			if (args.length == 6) {
				Line.readFile = System.getProperty("user.dir") + File.separator + "\\" + args[3];
				Line.writeFile = System.getProperty("user.dir") + File.separator + "\\" + args[5];
				Line.readFile();
				Line.writeLine(args[1]);
				System.out.println("该线路的信息为:");
				Line line = new Line();
				line.readLine();
			} else {

				System.out.println("输入不正确！");
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
				System.out.println("最短路径信息为:");
				Line line = new Line();
				path.readShort();
			} else {
				System.out.println("输入不正确！");
				break;
			}
			break;

		default:
			System.out.println("输入不正确！");
		}

	}
}
