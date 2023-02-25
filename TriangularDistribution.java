/**
 * This class provides an implementation of a triangular probabilistic
 * distribution. A simple mathematical explanation of this probability
 * distribution is available on Wikipedia at:
 * https://en.wikipedia.org/wiki/Triangular_distribution
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 *
 */
public class TriangularDistribution {

	/**
	 * a, c, b are the three parameters on the x axis of
	 * https://en.wikipedia.org/wiki/File:Triangular_distribution_PMF.png
	 */
	int a, c, b;

	/**
	 * Constructor for TriangularDistribution. You need to verify that the following
	 * condition holds: a < c AND c < b
	 * 
	 * @param a is the lower limit of the distribution
	 * @param c is the mode
	 * @param b is the upper limit of the distribution
	 */
	public TriangularDistribution(int a, int c, int b) {
	
		// WRITE YOUR CODE HERE!
		if (a < c && c < b){
			this.a = a;
			this.b = b;
			this.c = c;
		}

	}

	/**
	 * @param x is a point on the x axis
	 * @return the probability density at point x
	 */
	public Rational pdf(int x) {

		// WRITE YOUR CODE HERE!
		Rational value = new Rational(1,1);
		if (x<this.a || x>this.b) {
			value = new Rational(0,1);

		}
		if (x<this.c){
			int numerator = 2*(x-this.a);
			int denumerator = (this.b-this.a)*(this.c-this.a);
			value = new Rational(numerator, denumerator);
			return value;
		}
		if (x==this.c) {
			int numerator = 2;
			int denumerator = (this.b-this.a);
			value = new Rational(numerator, denumerator);
			return value;
		}
		else {
			int numerator = 2*(this.b-x);
			int denumerator = (this.b-this.a)*(this.b-this.c);
			value = new Rational(numerator, denumerator);
			return value;
		}
	}

	/**
	 * 
	 * This method provides two examples to help you test your implementation of
	 * triangular distribution. The output obtained from running this main method in
	 * the reference solution is provided to you alongside the starter code for A2.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 */
	public static void main(String args[]) {

		System.out.println("=========== TriangularDistribution(0, 5, 10) =============");
		TriangularDistribution dist0 = new TriangularDistribution(0, 5, 10);
		Rational sum0 = new Rational(0, 1);

		for (int i = 0; i <= 10; i++) {
			Rational p = dist0.pdf(i);
			sum0 = sum0.plus(p);
			System.out.println("pdf(" + i + ") = " + dist0.pdf(i));
		}

		System.out.println();

		System.out.println("Integral (Sum) = " + sum0);

		System.out.println();

		
		System.out.println("=========== TriangularDistribution(0, 50, 100) =============");
		TriangularDistribution dist1 = new TriangularDistribution(0, 50, 100);
		Rational sum1 = new Rational(0, 1);

		for (int i = 0; i <= 100; i++) {
			Rational p = dist1.pdf(i);
			sum1 = sum1.plus(p);
			System.out.println("pdf(" + i + ") = " + dist1.pdf(i));
		}

		System.out.println();
		
		System.out.println("Integral (Sum) = " + sum1);

		System.out.println();

		System.out.println("=========== TriangularDistribution(0, 10, 50) =============");
		TriangularDistribution dist2 = new TriangularDistribution(0, 10, 50);
		Rational sum2 = new Rational(0, 1);

		for (int i = 0; i <= 50; i++) {
			Rational p = dist2.pdf(i);
			sum2 = sum2.plus(p);
			System.out.println("pdf(" + i + ") = " + dist2.pdf(i));
		}

		System.out.println();

		System.out.println("Integral (Sum) = " + sum2);

	}
}
