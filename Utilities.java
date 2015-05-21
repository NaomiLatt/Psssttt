import java.util.*;
public class Utilities
{
 /*
    Patrick L. Jarvis
    May 4, 2015

    CISC 370 Peer to Peer project

    Some methods that are used throughout the system
 */

 private Utilities() {}

 public static int    getLengthInBytes ( int  x )           { return 4;  }
 public static int    getLengthInBytes ( long x )           { return 8;  }

 public static byte[] getBytes         ( int    x )         { return Utilities.toByteArray ( x, Utilities.getLengthInBytes ( x ) ); }
 public static byte[] getBytes         ( long   x )         { return Utilities.toByteArray ( x, Utilities.getLengthInBytes ( x ) ); }
 public static byte[] getBytes         ( String x )         { check ( "getBytes(String)", x ); return x.getBytes();                 }
 public static byte[] getBytes         ( byte[] source, int offset, int howManyBytes )
 {
  byte[] result;

  check("getBytes(byte[]", source);
  if ( offset < 0 )                             throw new IllegalArgumentException("Utilities.getBytes(byte[]): offset is less than zero: " + offset);
  if ( howManyBytes < 0 )                       throw new IllegalArgumentException("Utilities.getBytes(byte[]): howManyBytes is less than zero: " + howManyBytes);
  if ( source.length < (offset + howManyBytes)) throw new IllegalArgumentException("Utilities.getBytes(byte[]): source does not contain at least " + (offset + howManyBytes) + " bytes");

  result = new byte [ howManyBytes ];
  System.arraycopy ( source, offset, result, 0, howManyBytes );
  return result;
 }

 public  static int   getIntegerFrom   ( byte[] byteArray ) { check ("getIntegerFrom(byte[]", byteArray); return (int) getLongFrom ( byteArray ); }
 public  static long  getLongFrom      ( byte[] byteArray )
 {
  int  longLengthInBytes;
  long result;
  int  shiftAmount;

  check("getLongFrom(byte[])", byteArray);

  result = 0;
  longLengthInBytes = Utilities.getLengthInBytes(result);
  if ( byteArray.length > longLengthInBytes )
      throw new IllegalArgumentException("Utilities.getLongFrom(byte{}): byte array length exceeds " + longLengthInBytes + ": " + byteArray.length);

  for (int i=0; i<byteArray.length; i++) { result = result << 8; result = result |  (0xFF & byteArray[i]); } // byte is sign extended to int so it needs to be masked
  shiftAmount = ( longLengthInBytes - byteArray.length ) * 8;
  result      = ( result << shiftAmount ) >> shiftAmount ;

  return result;
 }


 public static byte[] concatenate ( byte[] a, byte[] b, byte[] c, byte[] d, byte[] e, byte[] f) { return Utilities.concatenate ( a, Utilities.concatenate ( b, c, d, e, f ) ); }
 public static byte[] concatenate ( byte[] a, byte[] b, byte[] c, byte[] d, byte[] e)           { return Utilities.concatenate ( a, Utilities.concatenate ( b, c, d, e ) ); }
 public static byte[] concatenate ( byte[] a, byte[] b, byte[] c, byte[] d)                     { return Utilities.concatenate ( a, Utilities.concatenate ( b, c, d ) ); }
 public static byte[] concatenate ( byte[] a, byte[] b, byte[] c )                              { return Utilities.concatenate ( a, Utilities.concatenate ( b, c ) ); }
 public static byte[] concatenate ( byte[] a, byte[] b )
 {
  byte[] c;
  check ( "concatenate(byte[])", a );
  check ( "concatenate(byte[])", b );
  c = new byte[ a.length + b.length ];
  System.arraycopy ( a, 0, c, 0,        a.length );
  System.arraycopy ( b, 0, c, a.length, b.length );
  return c;
 }


 private static void check ( String methodName, Object message )
 {
  if ( message == null ) throw new IllegalArgumentException ("Utilities." + methodName + "): parameter is null.");
 }

 //private static long   fromByteArray (

 private static byte[] toByteArray   ( long data, int numberOfBytes )
 {
  byte[] byteArray;

  byteArray = new byte[ numberOfBytes ];
  for ( int i=byteArray.length-1; i>=0; i--) { byteArray[i] = (byte) (data & 0xFF);  data = data >> 8;}
//System.out.println("Utilities.87 " + data + ", "+ Utilities.getLongFrom(byteArray));
  return byteArray;
 }



 public static String toDelimitedString ( char delimiter, String[] text )
 {
  String result;
  //
  //  Return a String that containe each array element separated from
  //  the next element by the delimiter character;

  if ( text == null )    throw new IllegalArgumentException ("Utilities. + toDelimitedString: + String[] parameter is null.");
  if ( text.length < 1 ) throw new IllegalArgumentException ("Utilities. + toDelimitedString: + String[] parameter is zero length.");

  result = text[0];
  for ( int i=1; i<text.length; i++ ) { result = result + delimiter + text[i]; }
  return result;
 }

 public static String[] toStringArray ( char delimiter, String text )
 {
  String[]        result;
  StringTokenizer tokenizer;
  if ( text == null )    throw new IllegalArgumentException ("Utilities. + fromDelimitedString: + String[] parameter is null.");
  //
  // Can't just use the String class split method since we don't know if
  // the delimiter has special meaning in a regular expression.
  //
  tokenizer = new StringTokenizer ( text, delimiter+"" );
  result    = new String [ tokenizer.countTokens() ];
  for (int i=0; i<result.length; i++) { result[i] = tokenizer.nextToken(); }
  return result;
 }


 public static void main (String[] args)
 {
  long a;
  byte[] b;

  for(int i=0; i<Integer.MAX_VALUE; i++)
  {
   a = i;
   b = getBytes(a);
   if ( a != getIntegerFrom(b))
    {
     System.out.println("eror: " + a + ", " + getLongFrom(b));
    }
  }

 }


}  // class Utilities