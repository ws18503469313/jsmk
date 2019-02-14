package com.itmuch.util;

import java.io.UnsupportedEncodingException;

public class WebParamUtils
{
  public static String getUTF8Param(String value)
    throws UnsupportedEncodingException
  {
    if (value == null) {
      return null;
    }

    if ((MyWebUtils.getCurrentRequest() != null) && 
      ("post".equalsIgnoreCase(MyWebUtils.getCurrentRequest().getMethod()))) {
      return value;
    }

    return new String(value.getBytes("ISO8859-1"), "UTF-8");
  }

  public static void main(String[] args) throws UnsupportedEncodingException {
    System.out.println(getUTF8Param("éå¨"));
  }
}
