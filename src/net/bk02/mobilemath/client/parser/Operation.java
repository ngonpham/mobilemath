/*
 * @(#)Operation.java	1.00 12/31/2001
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
 * Ist f�r eine(!) math. Operation zust�ndig.
 *
 * @author: Oliver Kurt
 * @date: 12/31/2001
 */
public interface Operation {

  /**
   * Konstanten Definitionen f�r die unterst�tzen Operatoren.
   */

  int PLUS = 1;
  int MINUS = 2;
  int MULTIPLICATION = 3;
  int DIVISION = 4;
  int POWER = 5;

  int LN = 6;
  int EXP = 7;
  int SIN = 8;
  int COS = 9;
  int TAN = 10;
  int ASIN = 11;
  int ACOS = 12;
  int ATAN = 13;

  int ROOT = 14;
  int LOG = 15;

  int ABS = 16;
  int SQRT = 17;


  /**
   * Zerteilt den String string in weitere Nodes und gibt diese zur�ck.
   *
   * @param string zu parsender String.
   * @return Kindknoten der Operation. 1 oder 2.
   */
  Node[] devideAndCreate(String string);


  /**
   * Wertet die Knoten nodes anhand der akutellen Operation und den Werten in variables aus.
   *
   * @param nodes Kindknoten.
   * @param variables Wertezuweisungen f�r die Variablen.
   * @return Wert der Kindknoten.
   */
  double link(Node[] nodes, Vector variables);


}
