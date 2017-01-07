package timeseries.operators;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import data.TestData;
import timeseries.TimeSeries;

public final class LagPolynomialSpec {
  
  @Test
  public void whenLagPolyAppliedIsolatedResultCorrect() {
    TimeSeries series = TestData.ausbeerSeries();
    LagPolynomial poly = LagPolynomial.firstDifference();
    assertThat(poly.fit(series, 2), is(equalTo(series.at(1))));
    poly = LagPolynomial.movingAverage(0.5);
    assertThat(poly.fit(series, 2), is(equalTo(series.at(1) * 0.5)));
  }

  @Test
  public void whenLagPolyAppliedResultCorrect() {
    TimeSeries series = TestData.ausbeerSeries();
    LagPolynomial poly = LagPolynomial.firstDifference();
    assertThat(poly.apply(series, 1), is(equalTo(series.at(1) - series.at(0))));
  }
  
  @Test
  public void whenLagPolyTwoDiffAppliedIsolatedResultCorrect() {
    TimeSeries series = TestData.ausbeerSeries();
    LagPolynomial poly = LagPolynomial.differences(2);
    assertThat(poly.fit(series, 2), is(equalTo(2 * series.at(1) - series.at(0))));
  }

  @Test
  public void whenZeroDegreeLagPolynomialCreatedZeroDegreePolyReturned() {
    LagPolynomial poly = new LagPolynomial();
    assertThat(poly.coefficients, is(equalTo(new double[] {1.0})));
  }
  
  @Test
  public void whenZeroDegreeLagPolyMultipliedByDiffPolyCoeffsCorrect() {
    LagPolynomial poly = new LagPolynomial();
    LagPolynomial diff = LagPolynomial.firstDifference();
    LagPolynomial polyDiff = poly.times(diff);
    assertThat(polyDiff.coefficients, is(equalTo(new double[] {1.0, -1.0})));
  }
  
  @Test
  public void whenSecondDiffLagPolyCreatedCoefficientsCorrect() {
    LagPolynomial poly = LagPolynomial.differences(2);
    assertThat(poly.coefficients, is(equalTo(new double[] {1.0, -2.0, 1.0})));
  }
  
  @Test
  public void whenSecondDiffArOneLagPolyCreatedCoefficientsCorrect() {
    LagPolynomial diffPoly = LagPolynomial.differences(2);
    LagPolynomial arOne = LagPolynomial.autoRegressive(0.5);
    LagPolynomial poly = diffPoly.times(arOne);
    assertThat(poly.coefficients, is(equalTo(new double[] {1.0, -2.5, 2.0, -0.5})));
  }
  
  @Test
  public void whenNewAutoRegressivePolyCreatedCofficientsCorrect() {
    LagPolynomial poly = LagPolynomial.autoRegressive(0.4);
    assertThat(poly.coefficients, is(equalTo(new double[] {1.0, -0.4})));
  }
  
  @Test
  public void whenNewMovingAveragePolyCreatedCofficientsCorrect() {
    LagPolynomial poly = LagPolynomial.movingAverage(0.4);
    assertThat(poly.coefficients, is(equalTo(new double[] {1.0, 0.4})));
  }
}
  