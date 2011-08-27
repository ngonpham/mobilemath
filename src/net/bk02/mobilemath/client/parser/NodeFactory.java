/*
 * @(#)NodeFactory.java	1.00 12/31/2001
 *
 * Copyright 2001 by Oliver Kurt. All Rights Reserved.
 *
 * This software is the proprietary information of Oliver Kurt, Germany.
 * Use is subject to license terms.
 *
 */

package net.bk02.mobilemath.client.parser;


/**
 * Zust�ndig f�r das Erstellen von Nodes.
 *
 * @author: Oliver Kurt
 * @date: 12/31/2001
 */
public class NodeFactory {

  /**
   * Diese Klasse wird niemals instanziiert.
   */
  private NodeFactory() {
  }


  /**
   * Erzeugt einen neuen Node aufgrund des Strings creationString.
   *
   * @param creationString ein zu parsender String.
   * @return ein neuer Knoten.
   */
  static public Node newInstance(String creationString) {

    // white-spaces l�schen
    creationString = trim(creationString);

    // sinnlose klammern entfernen
    creationString = deleteSenselessParentheses(creationString);

    // ausnahme: nur zahl
    try {
      double nurEineZahl = Double.valueOf(creationString).doubleValue();
      return new NumberNode(nurEineZahl);
    }
    catch ( NumberFormatException e ) {
      // der regelfall....
    }

    // ausnahme: nur variable
    if ( creationString.length() == 1 ) { // wenn die l�nge nur noch ein zeichen ist, dann muss es eine variable sein, eine zahl kann es schon nicht mehr sein, denn die w�re schon weiter oben abgefangen worden...
      return new VariableNode(creationString.charAt(0));
    }

    creationString = repairPreStandingMinus(creationString);

    // regelfall:
    return new BranchNode(creationString);
  }


  /**
   * Sorgt daf�r dass -x oder -sin(x) erlaubte Schreibweisen sind.
   * <p>
   * -x wird zu -1*x<br>
   * -sin(x) wird zu -1*sin(x)<br>
   * etc...
   */
  static private String repairPreStandingMinus(String buff) {
    if ( buff.length() < 2 ) {
      return buff;
    }
    if ( buff.charAt(0) == '-' && !Character.isDigit(buff.charAt(1)) ) {
      return "-1*"+buff.substring(1);
    }
    return buff;
  }

  /**
   * L�scht mathematisch sinnlose Klemmern um den gesamten String buff.
   */
  static private String deleteSenselessParentheses(String buff) {

    int ka=0,kea=0; // das gibt die anzahl der klammern an
    boolean operatorFound = false; //

    // geht den gesamten string durch
    // pr�ft ob ein +-*/^ operator vorkommt der NICHT in klammer steht.
    // ein kein operator gefunden wird, muss nachher gepr�ft werden, ob der term nicht villeicht
    // sinnlos geklammert ist.
    for (int i = 0 ; i< buff.length() ; i++) {

      if (buff.charAt(i)=='(') ka++;
      if (buff.charAt(i)==')') ka--;

      if (buff.charAt(i)=='[') kea++;
      if (buff.charAt(i)==']') kea--;

      if (buff.charAt(i)=='+' && ka==0 && kea==0) { operatorFound=true; break; }
      if (buff.charAt(i)=='-' && ka==0 && kea==0) { operatorFound=true; break; }
      if (buff.charAt(i)=='*' && ka==0 && kea==0) { operatorFound=true; break; }
      if (buff.charAt(i)=='/' && ka==0 && kea==0) { operatorFound=true; break; }
      if (buff.charAt(i)=='^' && ka==0 && kea==0) { operatorFound=true; break; }

    }

    // wenn ein operator gefunden wird, dann d�rfen klammern die am Anfang oder Ende stehen
    // nicht gel�scht werden, deshalb =>raus
    if ( operatorFound ) {
      return buff;
    }

    int anzahlKlammernAuf = 0;
    int anzahlKlammernZu = 0;
    for ( int i = 0 ; i < buff.length() ; i++ ) {
      if ( buff.charAt(i) == '(' ) {
        anzahlKlammernAuf++;
      }
      else {
        break;
      }
    }
    for ( int i = buff.length()-1 ; i >= 0 ; i-- ) {
      if ( buff.charAt(i) == ')' ) {
        anzahlKlammernZu++;
      }
      else {
        break;
      }
    }
    int anzahlKlammernZuDelete = Math.min(anzahlKlammernAuf, anzahlKlammernZu);
    if ( anzahlKlammernZuDelete > 0 ) {
      buff = buff.substring(0, buff.length()-anzahlKlammernZuDelete);
      buff = buff.substring(anzahlKlammernZuDelete);
    }
    return buff;
  }

  /**
   * Erweiterte trim-funktion, l�scht alle spaces und tabs aus dem string heraus.
   */
  static private String trim(String string) {
    if ( string.indexOf(' ') == -1 && string.indexOf((char)9) == -1 ) {
      return string;
    }
    StringBuffer buff = new StringBuffer(string.length());
    char c;
    for ( int i = 0 ; i < string.length() ; i++) {
      c = string.charAt(i);
      if ( c != 32 && c != 9 ) {
        buff.append(c);
      }
    }
    return buff.toString();
  }

}
