/*
 * @(#)Node.java	1.00 12/31/2001
 *
 * Copyright 2001 by Oliver Kurt. All Rights Reserved.
 *
 * This software is the proprietary information of Oliver Kurt, Germany.
 * Use is subject to license terms.
 *
 */

package net.bk02.mobilemath.client.parser;

import java.util.Vector;

/**
 * Definiert einen Knoten.
 * <p>
 * Jeder Knoten muss seine Wert, in Abh�ngigkeit der Variablen, zur�ckgeben k�nnen.
 *
 * @author: Oliver Kurt
 * @date: 12/31/2001
 */
public interface Node {

  /**
   * Gibt den Wert in Abh�ngigkeit der Variablen variables zur�ck.
   *
   * @param variables ver�nderliche Variablen.
   * @return Wert den der Knoten nach Evaluierung der variables hat.
   */
  double getValue(Vector variables);

}
