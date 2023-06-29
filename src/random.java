public class random {
	public static void main(String[] args) {
		int p = 0;
		int k = 100;
		int res = (int) (Math.random() * k - p + 1) + p;
		for (int i = 0; i < 100; i++) {
			System.out.println(res);
			res = (int) (Math.random() * k - p + 1) + p;
		}
	}
}
