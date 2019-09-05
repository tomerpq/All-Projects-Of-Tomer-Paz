package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class Polynomial {
	private double[] coeffs;
	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */
	public Polynomial()
	{
		coeffs = new double[]{0.0};
		this.coeffs = coeffs;
	} 
	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) 
	{
		if(coefficients == null)
			this.coeffs = null;
		else{
			double[] coeff2 = new double[coefficients.length];
			for(int i = 0; i < coefficients.length; i++)
				coeff2[i] = coefficients[i];
			this.coeffs = coeff2;
		}
	}
	/*
	 * Addes this polynomial to the given one
	 *  and retruns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial)
	{
		if(this == null || polynomial == null)
			return null;
		if(this.coeffs == null || polynomial.coeffs == null)
			return new Polynomial(null);
		int max = Math.max(polynomial.coeffs.length,this.coeffs.length);
		double[] newCoeffs = new double[max];
		int min = Math.min(polynomial.coeffs.length,this.coeffs.length);
		for(int i = 0; i < min; i++)
			newCoeffs[i] += polynomial.coeffs[i] + this.coeffs[i];
		if(polynomial.coeffs.length > this.coeffs.length)
			for(int i = this.coeffs.length; i < polynomial.coeffs.length; i++)
				newCoeffs[i] += polynomial.coeffs[i];
		if(this.coeffs.length > polynomial.coeffs.length)
			for(int i = polynomial.coeffs.length; i < this.coeffs.length; i++)
				newCoeffs[i] += this.coeffs[i];
		return new Polynomial(newCoeffs);
	}
	/*
	 * Multiplies a to this polynomial and returns 
	 * the result as a new polynomial.
	 */
	public Polynomial multiply(double a)
	{
		if(this == null)
			return null;
		if(this.coeffs == null)
			return new Polynomial(null);
		double[] newCoeffs = new double[this.coeffs.length];
		for(int i = 0; i < this.coeffs.length; i++)
			newCoeffs[i] = a * this.coeffs[i];
		return new Polynomial(newCoeffs);
	}
	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree()
	{
		int deg = 0;
		if(this == null || this.coeffs == null)
			return deg;
		for(int i = 0; i < this.coeffs.length; i++)
			if(this.coeffs[i] != 0)
				deg = i;
		return deg;
	}
	/*
	 * Returns the coefficient of the variable x 
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n)
	{
		double coeff = 0.0;
		if(this == null || this.coeffs == null)
			return coeff;
		if(this.coeffs.length > n)
			coeff = coeffs[n];
		return coeff;
	}
	
	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
	
	 */
	public Polynomial getFirstDerivation()
	{
		if(this == null || this.coeffs == null || this.coeffs.length == 0)
			return null;
		if(this.coeffs.length == 1)
			return new Polynomial();
		else{
			double[] newCoeffs = new double[this.coeffs.length -1];
			for(int i = 0; i < newCoeffs.length; i++)
				newCoeffs[i] = (i+1) * (this.coeffs[i+1]);
			return new Polynomial(newCoeffs);
		}
	}
	
	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(int x)
	{
		if(this == null || this.coeffs == null || this.coeffs.length == 0)
			return 0.0;
		double c = 0.0;
		int compute = x;
		c += this.coeffs[0];
		for(int i = 1; i < this.coeffs.length; i++){
			c += this.coeffs[i] * compute;
			compute *= x;
		}
		return c;
	}
	
	
	
	

    
    

}
