package net.bk02.mobilemath.client;

import net.bk02.mobilemath.client.utils.Graph;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mobilemath implements EntryPoint {
	Canvas canvas;
	static final int canvasHeight = 500;
	static final int canvasWidth = 800;
	TextBox txtExpression;
	Button butDraw;
	HorizontalPanel panelInput;
	Label label;
	Graph g;
	CssColor clYellow = CssColor.make("#00FF00");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		canvas = Canvas.createIfSupported();

		if (canvas == null) {
			RootPanel
					.get()
					.add(new Label(
							"Sorry, your browser doesn't support the HTML5 Canvas element"));
			return;
		}

		canvas.setStyleName("mainCanvas");
		canvas.setWidth(canvasWidth + "px");
		canvas.setCoordinateSpaceWidth(canvasWidth);

		canvas.setHeight(canvasHeight + "px");
		canvas.setCoordinateSpaceHeight(canvasHeight);

		g = new Graph(canvas.getContext2d(), canvasWidth, canvasHeight);

		txtExpression = new TextBox();
		txtExpression.setText("R=5*SIN(4*T)");
		txtExpression.setWidth("400px");
		butDraw = new Button();
		butDraw.setText("Draw");
		butDraw.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String exp = txtExpression.getText().trim().toUpperCase();
				g.DrawAxis();
				if (exp.startsWith("Y="))
					g.DrawYX(clYellow, exp, -10, 10);
				else if (exp.startsWith("X="))
					g.DrawXY(clYellow, exp, -10, 10);
				else if (exp.startsWith("R="))
					g.DrawRT(clYellow, exp, -180, 180);
				else if (exp.startsWith("XT="))
					g.DrawYXT(clYellow, exp, -10, 10);
				else
					Window.alert("Invalid expression!");
			}
		});
		panelInput = new HorizontalPanel();
		panelInput.add(txtExpression);
		panelInput.add(butDraw);
		RootPanel.get().add(panelInput);

		label = new Label(
				"For example: Y=X*COS(X); R=T*T; X=Y*Y; XT=T*SIN(T)_YT=T*COS(T)...");
		RootPanel.get().add(label);

		RootPanel.get().add(canvas);

		HTML html = new HTML(
				"Source code: <a href='http://www.bk02.net/mobilemath'>http://www.bk02.net/mobilemath</a> - <a href='http://code.google.com/p/mobilemath/'>http://code.google.com/p/mobilemath/</a>");
		RootPanel.get().add(html);

		g.DrawAxis();
		g.DrawRT(clYellow, "R=5*SIN(4*T)", -180, 180);
	}

}
