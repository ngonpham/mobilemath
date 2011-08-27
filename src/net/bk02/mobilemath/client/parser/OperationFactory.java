/*
 * @(#)OperationFactory.java	1.00 12/31/2001
 *
 * Copyright 2001 by Oliver Kurt. All Rights Reserved.
 *
 * This software is the proprietary information of Oliver Kurt, Germany.
 * Use is subject to license terms.
 *
 */

package net.bk02.mobilemath.client.parser;

/**
 * Zust�ndig f�r das Erstellen von Operationen.
 *
 * @author: Oliver Kurt
 * @date: 12/31/2001
 */
public class OperationFactory {

  /**
   * Diese Klasse wird niemals instanziiert.
   */
  private OperationFactory() {
  }

  /**
   * Analysiert den String string und erkennt an welche Operation der String aufgebrochen werden muss.
   *
   * @param string der eine zu evaluierende Zeichenkette (die Funktion) enth�lt.
   * @return Gibt ein Operation-Objekt zur�ck, das die 'n�chste' mathematische Operation enth�lt. Hier werden die Priorit�ten der mathematischen Funtionen �berwacht.
   */
  public static Operation getNextOperation(String string) {

    int ka=0,kea=0; // das gibt die anzahl der klammern an
    int plus=-1,minus=-1,mal=-1,geteilt=-1,hoch=-1; // das gibt die position der operation an.

    // geht den gesamten string durch
    // merkt sich in den variabeln das ERSTE vorkommen des operatores, wenn er nicht in klammern() [] steht
    for (int i = 0 ; i< string.length() ; i++) {

      if (string.charAt(i)=='(') ka++;
      if (string.charAt(i)==')') ka--;

      if (string.charAt(i)=='[') kea++;
      if (string.charAt(i)==']') kea--;

      if (plus==-1     && string.charAt(i)=='+' && ka==0 && kea==0)  plus=i;
      if (minus==-1    && string.charAt(i)=='-' && ka==0 && kea==0)  minus=i;
      if (mal==-1      && string.charAt(i)=='*' && ka==0 && kea==0)  mal=i;
      if (geteilt==-1  && string.charAt(i)=='/' && ka==0 && kea==0)  geteilt=i;
      if (hoch==-1     && string.charAt(i)=='^' && ka==0 && kea==0)  hoch=i;

    }

    // die Reihenfolge der nachstehenden Anweisung ist entscheident !!!

    if (plus>-1)    return new BasicOperation(plus, Operation.PLUS);
    // das minus wird mathematisch doppeltdeutig eingesetzt.
    // a) als subtraktionszeichen
    // b) als Kurzform f�r *-1
    // daher muss geschaut werden, ob das minus alleine steht, oder ob es vor einem anderen operator steht
    if (minus>-1 && !(plus==minus-1||mal==minus-1||geteilt==minus-1||hoch==minus-1) )   return new BasicOperation(minus, Operation.MINUS);
    if (mal>-1)     return new BasicOperation(mal, Operation.MULTIPLICATION);
    if (geteilt>-1) return new BasicOperation(geteilt, Operation.DIVISION);
    if (hoch>-1)    return new BasicOperation(hoch, Operation.POWER);

    if ( string.startsWith("LN")   ) return new PostfixOperation(Operation.LN);
    if ( string.startsWith("EXP")  ) return new PostfixOperation(Operation.EXP);
    if ( string.startsWith("SIN")  ) return new PostfixOperation(Operation.SIN);
    if ( string.startsWith("COS")  ) return new PostfixOperation(Operation.COS);
    if ( string.startsWith("TAN")  ) return new PostfixOperation(Operation.TAN);
    if ( string.startsWith("ASIN") ) return new PostfixOperation(Operation.ASIN);
    if ( string.startsWith("ACOS") ) return new PostfixOperation(Operation.ACOS);
    if ( string.startsWith("ATAN") ) return new PostfixOperation(Operation.ATAN);
    if ( string.startsWith("LOG")  ) return new DoubleValueOperation(Operation.LOG);
    if ( string.startsWith("ROOT") ) return new DoubleValueOperation(Operation.ROOT);
	//Add
	if ( string.startsWith("ABS") ) return new PostfixOperation(Operation.ABS);
	if ( string.startsWith("SQRT") ) return new PostfixOperation(Operation.SQRT);
    throw new RuntimeException("Unable to parse string: "+string);
  }

}
