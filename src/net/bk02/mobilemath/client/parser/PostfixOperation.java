/*
 * @(#)PostfixOperation.java	1.00 12/31/2001
 *
 * Copyright 2001 by Oliver Kurt. All Rights Reserved.
 *
 * This software is the proprietary information of Oliver Kurt, Germany.
 * Use is subject to license terms.
 *
 */

package net.bk02.mobilemath.client.parser;

import java.util.Vector;

import net.bk02.mobilemath.client.utils.Utils;

/**
 *
 *
 * @author: Oliver Kurt
 * @date: 12/31/2001
 */
public class PostfixOperation implements Operation {

  /**
   * Speichert den typ der Operation. z.b. + oder - oder sin...
   */
  protected int typ;
  public static boolean rad = true;

  public PostfixOperation(int typ) {
    this.typ = typ;
  }

  public Node[] devideAndCreate(String string) {
    Node[] nodes = new Node[1];
    switch ( typ ) {
      case LN:
        nodes[0] = NodeFactory.newInstance(string.substring(3, string.length()-1));
        break;
      case EXP:
      case SIN:
      case COS:
      case TAN:
	  case ABS:
        nodes[0] = NodeFactory.newInstance(string.substring(4, string.length()-1));
        break;
      case ASIN:
      case ACOS:
      case ATAN:
	  case SQRT:
        nodes[0] = NodeFactory.newInstance(string.substring(5, string.length()-1));
        break;
    }
    return nodes;
  }


  public double link(Node[] nodes, Vector variables) {
    switch ( typ ) {
      case LN:
        return Utils.ln(nodes[0].getValue(variables));
      case EXP:
        return Utils.exp(nodes[0].getValue(variables));
      case SIN:
		  if(rad)
			  return Math.sin(nodes[0].getValue(variables));
		  else
			  return Math.sin(nodes[0].getValue(variables)*3.1416/180);
      case COS:
		  if(rad)
			  return Math.cos(nodes[0].getValue(variables));
		  else
			  return Math.cos(nodes[0].getValue(variables)*3.1416/180);
      case TAN:
		  if(rad)
			  return Math.tan(nodes[0].getValue(variables));
		  else
			  return Math.tan(nodes[0].getValue(variables)*3.1416/180);
	  case ABS:
	  	return Math.abs(nodes[0].getValue(variables));
      case ASIN:
        //return Math.asin(nodes[0].getValue(variables));
      case ACOS:
        //return Math.acos(nodes[0].getValue(variables));
      case ATAN:
		  //return Math.atan(nodes[0].getValue(variables));
	  case SQRT:
		  return Math.sqrt(nodes[0].getValue(variables));
    }
    throw new RuntimeException("Unknown type: "+typ);
  }

}
