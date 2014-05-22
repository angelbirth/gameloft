/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resourceManagement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;


public class Scanner {

    /*s
     * Next token
     */
    private String nextToken = null;
    /**
     * The input stream
     */
    private InputStream inputStream;

    /**
     * Create a scanner
     */
    public Scanner(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Create a scanner using a string
     */
    public Scanner(String data) {
        this(new ByteArrayInputStream(data.getBytes()));
    }

    /**
     * Has next element
     * @return
     */
    public boolean hasNext() {
        try {
            nextToken = getNext();
            return nextToken != null;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Get Float from the stream
     * @return
     * @throws IOException
     */
    public float getNextFloat() throws IOException {
        float result = Float.parseFloat(getNext());
        return result;
    }

    /**
     * Get next integer in the stream
     * @return
     */
    public int getNextInt() throws IOException {
        int result = Integer.parseInt(getNext());
        return result;
    }

    public int getNextHex() throws IOException {
        String next = getNext().substring(2);
        int result = Integer.parseInt(next, 16);
        return result;
    }

    /**
     * Get next token in the stream
     * @return
     */
    public String getNext() throws IOException {

        String result = null;

        if (nextToken != null) {
            result = nextToken;
            nextToken = null;
        } else {
            StringBuffer builder = new StringBuffer();

            int readState = 0;

            // ignore space (0) - read character (1) - escape (2)
            while (inputStream.available() > 0 && readState < 2) {
                char c = (char) inputStream.read();

                if (!isSpace(c)) {
                    builder.append(c);
                    readState = 1;
                } else if (readState == 1) {
                    readState = 2;
                }
            }

            if (builder.length() > 0) {
                result = builder.toString();
            }
        }

        return result;

    }

    /**
     * Return if c is a space or not
     * @param c
     * @return
     */
    private boolean isSpace(char c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '=' || c == ',' || c == '"';
    }

    public static String[] Split(String splitStr, String delimiter) {
        System.out.println("splitStr : " + splitStr);
        StringBuffer token = new StringBuffer();
        Vector tokens = new Vector();
        // split
        char[] chars = splitStr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (delimiter.indexOf(chars[i]) != -1) {
                // we bumbed into a delimiter
                if (token.length() > 0) {
                    tokens.addElement(token.toString());
                    token.setLength(0);
                }
            } else {
                token.append(chars[i]);
            }
        }
        // don't forget the "tail"...
        if (token.length() > 0) {
            tokens.addElement(token.toString());
        }
        // convert the vector into an array
        String[] splitArray = new String[tokens.size()];
        for (int i = 0; i < splitArray.length; i++) {
            splitArray[i] = (String) tokens.elementAt(i);
        }
        return splitArray;
    }

    public static int[] stringArrToIntegerArr(String[] stringArr) {

        int[] result = new int[stringArr.length];
        for (int i = 0; i < stringArr.length; i++) {
            result[i] = Integer.parseInt(stringArr[i]);
        }
        return result;
    }
}
