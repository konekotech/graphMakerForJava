package graphMaker;
import java.util.ArrayList;
import java.util.List;
public class GraphBuilder {
	List<String> build(List<Double> xList, List<Double> yList, String xLabel, String yLabel, String caption, String label, Boolean zero){
		double xRange = this.max(xList) - this.min(xList);
		double yRange = this.max(yList) - this.min(yList);
		double xMin;
		double yMin;
		//範囲やグラフのメモリの大きさを指定
		if (zero == false){
			xMin = this.min(xList) - xRange * 1 / 10.0;
			yMin = this.min(yList) - yRange * 1 / 10.0;
		}else{
			xMin = 0;
			yMin = 0;
		}
		double xMax = this.max(xList) + xRange * 1 / 10.0;
		double yMax = this.max(yList) + yRange * 1 / 10.0;
		double xTick = this.tick(xRange);
		double yTick = this.tick(yRange);
		//数値計算
		double d = xList.size() * this.sum(this.product(xList, xList)) - Math.pow(this.sum(xList), 2);
		double d1 = this.sum(yList) * this.sum(this.product(xList, xList)) - this.sum(xList) * this.sum(this.product(xList, yList));
		double d2 = xList.size() * this.sum(this.product(xList, yList)) - this.sum(xList) * this.sum(yList);
		double a = d1/d;
		double b = d2/d;
		List<Double> sList = this.errors(a, b, xList, yList);
		double sigma = Math.sqrt(this.sum(sList)/(xList.size() - 2));
		double wa = d/this.sum(this.product(xList, xList));
		double wb = d/xList.size();
		double da = sigma/Math.sqrt(wa);
		double db = sigma/Math.sqrt(wb);
		List<String> datas = new ArrayList<String>();
		datas.add(String.valueOf(a));
		datas.add(String.valueOf(b));
		datas.add(String.valueOf(da));
		datas.add(String.valueOf(db));
		datas.add("%TikZ\\usepackage{tikz}\n\\usetikzlibrary{intersections,calc,arrows.meta}\n\\usepackage{pgfplots}\n\\def\\axisdefaultwidth{8cm}\n\\def\\axisdefaultheight{8cm}\n\\tikzset{% スタイルの作成\npointtype triangle/.style={mark=triangle*,mark size=4pt},\nevery mark/.style={fill=black,solid}\n}\n\\pgfplotsset{% グラフ全体の見た目の設定\ncompat=1.17,\nmajor tick length=0.2cm,\nminor tick length=0.1cm,\nevery axis/.style={semithick},\ntick style={semithick,black},\n}\n");
		datas.add("\\begin{figure}[H]\n\\centering\n\\begin{tikzpicture}[scale = 1.5]\n\\begin{axis}[\ncompat = newest, \n xmin = " + xMin + ", xmax = " + xMax + ",\nymin = " + yMin + ", ymax = " + yMax + ",\nxtick distance = " + xTick + ",\nminor x tick num = 4,\nytick distance = " + yTick + ",\n minor y tick num = 4,\nxlabel = {" + xLabel + "}, ylabel = {" + yLabel + "},\nenlarge x limits = false]\n\\addplot[black!55!black, mark = *, only marks]table{\n");
		StringBuilder sb = new StringBuilder();
		sb.append(datas.get(5));
		for (int i = 0; i < xList.size(); i++){
			sb.append(xList.get(i) + " " + yList.get(i) + "\n");
		}
		sb.append("};\n\\addplot[samples = 200, domain = " + xMin + ":" + xMax + "]{" + a + "+" + b + "*x};\n\\end{axis}\n\\end{tikzpicture}\n\\caption{" + caption + "}\n\\label{" + label + "}\n\\end{figure}");
		datas.set(5, sb.toString());
		return datas;
	}


	//このクラス内でしか使わないメソッドはprivateにする
	private double max(List<Double> list){
		int i;
		double max = list.get(0);
		for (i = 1; i < list.size(); i++){
			if (list.get(i) > max){
				max = list.get(i);
			}
		}
		return max;
	}
	private double min(List<Double> list) {
		int i;
		double min = list.get(0);
		for (i = 1; i < list.size(); i++) {
			if (list.get(i) < min) {
				min = list.get(i);
			}
		}
		return min;
	}
	private double sum(List<Double> list){
		double sum = 0;
		int i;
		for (i = 0; i < list.size(); i++){
			sum = sum + list.get(i);
		}
		return sum;
	}
	private List<Double> product(List<Double> a, List<Double> b){
		List<Double> ret = new ArrayList<Double>();
		int i;
		for (i = 0; i < a.size(); i++){
			ret.add(a.get(i) * b.get(i));
		}
		return ret;
	}
	private List<Double> errors(double a, double b, List<Double> xList, List<Double> yList){
		List<Double> sList = new ArrayList<Double>();
		int i;
		for (i = 0; i < xList.size(); i++){
			sList.add(a + b * xList.get(i) - yList.get(i));
		}
		return sList;
	}

	private double coef(double x){
		String[] s = String.format("%E", x).split("E");
		return Double.parseDouble(s[0]);
	}
	private double index(double x) {
		String[] s = String.format("%E", x).split("E");
		return Double.parseDouble(s[1]);
	}
	private double tick(double range){
		double val = this.coef(range);
		if (val >= 1 && val < 1.5) {
			val = 0.1 * Math.pow(10,this.index(range));
		} else if (val >= 1.5 && val < 4.0) {
			val = 0.2 * Math.pow(10, this.index(range));
		} else if (val >= 4.0 && val < 6.0) {
			val = 0.5 * Math.pow(10, this.index(range));
		} else {
			val = 1 * Math.pow(10, this.index(range));
		}
		return val;
	}
}
