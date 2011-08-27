package net.bk02.mobilemath.client.utils;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class Graph {
	CssColor clBackground, clAxis, clGrid, clText, clOxy;
	int sizeOxy, sizeText, sizeLine, lineSpace;
	public boolean grid, axisVisible;
	public int ox, oy, op, count;
	int width, height, outx, outy, max;
	int intx1, inty1, intx, inty;
	double d, x, y, t;
	String sx, sy;
	double[] Temp;
	Context2d g;

	public Graph(Context2d g, int width, int height) {
		clBackground = CssColor.make("#000000");
		clAxis = CssColor.make("#00CCCC");// 165317;
		clGrid = CssColor.make("#666666"); // 16777215;
		clText = CssColor.make("#cccccc"); // 16777215;
		clOxy = CssColor.make("#00ff00"); // 65280;
		sizeOxy = 12;
		sizeText = 8;
		sizeLine = 3;
		lineSpace = 1;
		grid = true;
		axisVisible = true;
		this.width = width;
		this.height = height;
		this.g = g;
		ox = width / 2;
		oy = height / 2;
		op = 14;
		max = 176;
		Temp = new double[max];
	}

	private boolean check(int x, int y) {
		return (0 <= x) && (x <= width) && (0 <= y) && (y <= height);
	}

	private void PointRealToForm(double inpx, double inpy) {
		outx = (int) (ox + inpx * op);
		outy = (int) (oy - inpy * op);
	}

	private void drawLine(CssColor color, int x1, int y1, int x2, int y2) {
		g.beginPath();
		g.setStrokeStyle(color);
		g.setLineWidth(1);
		g.moveTo(x1, y1);
		g.lineTo(x2, y2);
		g.closePath();
		g.stroke();
	}

	private void lin(int x1, int y1, int x2, int y2) {
		g.setLineWidth(1);
		g.setStrokeStyle(clGrid);
		int tt;
		if (x1 == x2) {
			tt = oy % op;
			while (tt <= height) {
				g.strokeRect(x1, tt, 1, 1);
				tt += op;
			}
		} else {
			tt = ox % op;
			while (tt <= width) {
				g.strokeRect(tt, y1, 1, 1);
				tt += op;
			}
		}
	}

	public void DrawAxis() {
		int x, y, k, t1;
		// Clear
		g.setFillStyle(clBackground);
		g.fillRect(0, 0, width, height);
		if (!axisVisible)
			return;
		// Font
		g.setFont("Arial");
		// Axis 1
		if (op < 40)
			k = 40 / op;
		else
			k = 1;
		x = ox - op;
		t1 = 0;
		while (x > 10) {
			t1--;
			if (grid)
				if (op >= 10)
					lin(x, 0, x, height);
			if (t1 % k == 0) {
				if (t1 % (k * lineSpace) == 0) {
					g.setStrokeStyle(clText);
					g.strokeText(String.valueOf(t1), x, oy + 12);
				}
				drawLine(clAxis, x, oy - sizeLine, x, oy + sizeLine);
			}
			x = x - op;
		}
		// Axis 2
		x = ox + op;
		t1 = 0;
		while (x < width) {
			t1++;
			if (grid)
				if (op >= 10)
					lin(x, 0, x, height);
			if (t1 % k == 0) {
				if (t1 % (k * lineSpace) == 0) {
					g.setStrokeStyle(clText);
					g.strokeText(String.valueOf(t1), x, oy + 12);
					drawLine(clAxis, x, oy - sizeLine, x, oy + sizeLine);
				}
			}
			x = x + op;
		}
		// Axis 3
		y = oy - op;
		t1 = 0;
		while (y > 0) {
			t1++;
			if (grid)
				if (op >= 10)
					lin(0, y, width, y);
			if (t1 % k == 0) {
				if (ox > 5) {
					drawLine(clAxis, ox - sizeLine, y, ox + sizeLine, y);
				} else {
					drawLine(clAxis, 0, y, ox + sizeLine, y);
				}
				if (t1 % (k * lineSpace) == 0) {
					g.setStrokeStyle(clText);
					g.strokeText(String.valueOf(t1), ox + 10, y);
				}
			}
			y -= op;
		}
		// Axis 4
		y = oy + op;
		t1 = 0;
		while (y < height) {
			t1--;
			if (grid)
				if (op >= 10)
					lin(0, y, width, y);
			if (t1 % k == 0) {
				if (ox > 5) {
					drawLine(clAxis, ox - sizeLine, y, ox + sizeLine, y);
				} else {
					drawLine(clAxis, 0, y, ox + sizeLine, y);
				}
				if (t1 % (k * lineSpace) == 0) {
					g.setStrokeStyle(clText);
					g.strokeText(String.valueOf(t1), ox + 10, y);
				}
			}
			y = y + op;
		}
		// Oxy
		g.setStrokeStyle(clOxy);
		g.setFont("Arial");
		g.strokeText("y", ox + 5, 0);
		g.strokeText("x", width - 20, oy);
		g.setStrokeStyle(clAxis);
		drawLine(clAxis, 0, oy, width, oy);
		drawLine(clAxis, ox, 0, ox, height);
		drawLine(clAxis, width - sizeLine, oy - sizeLine, width, oy);
		drawLine(clAxis, width - sizeLine, oy + sizeLine, width, oy);
		drawLine(clAxis, ox - sizeLine, sizeLine, ox, 0);
		drawLine(clAxis, ox + sizeLine, sizeLine, ox, 0);
	}

	public void DrawYX(CssColor color, String exp, double v1, double v2) {
		int i, k;

		exp = Utils.strDelSpc(exp).toUpperCase();
		exp = Utils.strReplace(exp, "Y=", "");
		// Build TempT,TempR
		if (v1 < -ox / op)
			v1 = -ox / op;
		if (v2 > (width - ox) / op)
			v2 = (width - ox) / op;
		count = (int) (v2 - v1) * op;
		if (count == 0)
			return;
		d = (v2 - v1) / count;
		// Evaluate
		Utils.prepareVal('X', exp);
		PointRealToForm(v1, Utils.Cal(v1));
		intx1 = outx;
		inty1 = outy;

		for (i = 1; i < count; i++) {
			x = v1 + d * i;
			PointRealToForm(x, Utils.Cal(x));
			intx = outx;
			inty = outy;
			if (check(intx, inty) || check(intx1, inty1))
				if (Math.abs(inty - inty1) < height / 2)
					drawLine(color, intx1, inty1, intx, inty);
			intx1 = intx;
			inty1 = inty;
		}
	}

	public void DrawRT(CssColor color, String exp, double v1, double v2) {
		int i, k;
		exp = Utils.strDelSpc(exp).toUpperCase();
		exp = Utils.strReplace(exp, "R=", "");
		// Build TempT,TempR
		v1 = (v1 * Math.PI / 180);
		v2 = (v2 * Math.PI / 180);
		count = max;
		d = ((v2 - v1) / count);
		// Draw
		Utils.prepareVal('T', exp);
		x = v1;
		y = Utils.Cal(x);
		PointRealToForm(y * Math.cos(x), y * Math.sin(x));
		intx1 = outx;
		inty1 = outy;
		for (i = 1; i < count; i++) {
			x = v1 + d * i;
			y = Utils.Cal(x);
			PointRealToForm(y * Math.cos(x), y * Math.sin(x));
			intx = outx;
			inty = outy;
			if (check(intx, inty) || check(intx1, inty1))
				drawLine(color, intx1, inty1, intx, inty);
			intx1 = intx;
			inty1 = inty;
		}
	}

	public void DrawXY(CssColor color, String exp, double v1, double v2) {
		int i, k;

		exp = Utils.strDelSpc(exp).toUpperCase();
		exp = Utils.strReplace(exp, "X=", "");
		// Build TempT,TempR
		if (v1 < -ox / op)
			v1 = -ox / op;
		if (v2 > (width - ox) / op)
			v2 = (width - ox) / op;
		count = (int) (v2 - v1) * op;
		if (count == 0)
			return;
		d = (v2 - v1) / count;
		// Evaluate
		Utils.prepareVal('Y', exp);
		PointRealToForm(Utils.Cal(v1), v1);
		intx1 = outx;
		inty1 = outy;

		for (i = 1; i < count; i++) {
			y = v1 + d * i;
			PointRealToForm(Utils.Cal(y), y);
			intx = outx;
			inty = outy;
			if (check(intx, inty) || check(intx1, inty1))
				if (Math.abs(inty - inty1) < height / 2)
					drawLine(color, intx1, inty1, intx, inty);
			intx1 = intx;
			inty1 = inty;
		}
	}

	public void DrawYXT(CssColor color, String exp, double v1, double v2) {
		int i, k, l;
		exp = Utils.strDelSpc(exp).toUpperCase();
		k = exp.indexOf("_");
		sx = exp.substring(0, k);
		sy = exp.substring(k + 1, exp.length());
		sx = Utils.strReplace(sx, "XT=", "");
		sy = Utils.strReplace(sy, "YT=", "");
		count = max;
		d = (v2 - v1) / count;
		// Evaluate
		Utils.prepareVal('T', sx);
		for (i = 0; i < count; i++) {
			t = v1 + d * i;
			Temp[i] = Utils.Cal(t);
		}
		// Draw
		Utils.prepareVal('T', sy);
		t = v1;
		PointRealToForm(Temp[0], Utils.Cal(t));
		intx1 = outx;
		inty1 = outy;
		for (i = 1; i < count; i++) {
			t = v1 + d * i;
			PointRealToForm(Temp[i], Utils.Cal(t));
			intx = outx;
			inty = outy;
			if (check(intx, inty) || check(intx1, inty1))
				if (Math.abs(inty - inty1) < height / 2)
					drawLine(color, intx1, inty1, intx, inty);
			intx1 = intx;
			inty1 = inty;
		}
	}
}
