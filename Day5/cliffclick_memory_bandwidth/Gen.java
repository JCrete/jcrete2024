/* @2018 Rocket Realtime School of Programming and Performance
 *
 * Generate psuedo-random fin tech data
 */

import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Random;

abstract class Gen {
  static final Random R = new Random();

  static final String[] PRODS = new String[1000];
  static int PIDX, QTY;
  static { init(); }

  static void init() {
    int x=0;
    for( int i=0; i<26; i++ )
      for( int j=0; j<26; j++ )
        for( int k=0; k<26; k++ ) {
          PRODS[x++] = ""+(char)('A'+i)+""+(char)('A'+j)+""+(char)('A'+k);
          if( x>= PRODS.length ) return;
        }
  }
  
  public static void main( String[] args ) throws IOException {
    if( args.length==0 ) {
      System.out.println("Usage: java gen nrows\nExample, redirecting stdout:\njava gen 5000000 > ANON2.CSV\n");
      return;
    }
    int nrows = Integer.valueOf(args[0]);

    Col[] cols = new Col[]{
      new CntCol("RecordNo"),
      new TimeIncrCol("Date/Time",LocalDateTime.of(2018,8,29,0,0,24)),
      new ConCol("Exch","CME-C"),
      new ConCol("SrsKey","00A0FO00NQZ"),
      new Con4Col("Source","FrmClnt","ToHst","ToClnt","FrmHst"), // Used for filter on ToClnt
      new Con4Col("Status","VOID","VOID","OK","VOID"),
      new Cnt4Col("OrderNo",13454096),
      new ConCol("ExchOrderId","80D8U"),
      new ConCol("Action","Change"),
      new Con4RandCol("B/S","Buy","Sell"),    // Used
      new QtyCol("OrdQty"),                   // Used
      new QtyCol("WrkQty"),                   // Used
      new XQtyCol("ExcQty"),                  // Used
      new ProdCol("Prod"),                    // Used 
      new ConCol("Expiry","JUN19"),
      new ConCol("O/C","Open"),             // 
      new ConCol("C/P",""),             // 
      new ConCol("LimitPrc","348475.000000"),        // 
      new ConCol("StopPrc",""),         // 
      new ConCol("Strike",""),          // 
      new ConCol("OrderType","Limit"),       // 
      new ConCol("OrderRes","GTD"),        // 
      new ConCol("ExchMember","TAG737"),      // 
      new ConCol("ExchGroup","JJ9"),       // 
      new ConCol("ExchTrader","JJ9"),      // 
      new ConCol("User ID","TRADER1"),         // 
      new ConCol("Member","TAG737"),          // 
      new ConCol("Group","JJ9"),           // 
      new ConCol("Trader","JJ9"),          // 
      new ConCol("Account","ACCT1"),         // 
      new ConCol("FFT1","FFT1"),            // 
      new ConCol("FFT2","C"),            // 
      new ConCol("FFT3",""),            // 
      new ConCol("ClrMember",""),       // 
      new ConCol("ExchTime","0:0:0.0"),        // 
      new ConCol("ExchDate","2018-08-29"),        // 
      new ConCol("Srvr","172.29.110.246"),            // 
      new ConCol("TxtMsg",""),          // 
      new ConCol("GW Specific",""),     // 
      new ConCol("Remaining Fields","StopTrigQty=0,SOK=098L2U010,Sndr=-160555604,DisclQty=0,ExchOrdId=80D8U,ExchTransNo=,OrdNoOld=13454094,FirstOrderSrc=Autospreader Engine,LastOrderSrc=Autospreader Engine,OrderSourceHistory=Autotrader|Autospreader Engine|X_TRADER Pro|Autospreader SE,OrdKey=0,OrdActOrig=Change,OrdFlgs=0,MinQty=0,Contract=NQM9,CTI=1,Origin=0,CountryCode=US,RegionCode=IL,OrderSourceAutomated=1,ExchangeCredentials=,senderSubID=,")
    };


    
    // Header
    StringBuilder sb = new StringBuilder();
    for( Col col : cols ) {
      sb.append(col._title).append(',');
    }
    sb.setLength(sb.length()-1);
    System.out.println(sb.toString());

    // Rows
    for( int i=0; i<nrows; i++ ) {
      sb.setLength(0);
      if( (i%4)==0 ) {
        double d0 = R.nextGaussian(); // 99% numbers fall -3 to +3
        double d1 = d0+3;             // 99% numbers fall 0 to 6
        double d2 = d1*PRODS.length/6;// 99% numbers fall 0 to PRODS.length
        PIDX = Math.max(0,Math.min(PRODS.length-1,(int)d2));
        QTY = Math.max(1,R.nextInt()%(Math.max(1,(int)d2)));
        //System.out.printf("%g %g %g %d %d\n",d0,d1,d2,PIDX,QTY);
      }
      for( Col col : cols )
        sb.append(col.col(i%4)).append(',');
      sb.setLength(sb.length()-1);
      System.out.println(sb.toString());
    }
    
  }
  
  abstract static class Col {
    final String _title;
    Col(String title) { _title=title; }
    abstract String col(int i);
  }

  static class ConCol extends Col {
    final String _col;
    ConCol(String title, String col) { super(title); _col=col; }
    @Override String col(int i) { return _col; }
  }

  static class QtyCol extends Col {
    QtyCol(String title) { super(title); }
    @Override String col(int i) { return ""+QTY; }
  }
  static class XQtyCol extends Col {
    XQtyCol(String title) { super(title); }
    @Override String col(int i) {
      return R.nextBoolean() ? ""+QTY : "0";
    }
  }
  static class ProdCol extends Col {
    ProdCol(String title) { super(title); }
    @Override String col(int i) { return PRODS[PIDX]; }
  }

  
  static class Con4Col extends Col {
    final String[] _cols;
    Con4Col(String title, String... cols) { super(title); _cols=cols; }
    @Override String col(int i) { return _cols[i]; }
  }

  static class Con4RandCol extends Col {
    final String[] _cols;
    int _idx;
    Con4RandCol(String title, String... cols) { super(title); _cols=cols; }
    @Override String col(int i) {
      if( i==0 ) _idx=Math.abs(R.nextInt())%_cols.length;
      return _cols[_idx];
    }
  }

  static class CntCol extends Col {
    int _cnt;
    CntCol(String title) { super(title); }
    @Override String col(int i) { return ""+_cnt++; }
  }

  static class Cnt4Col extends Col {
    int _cnt;
    Cnt4Col(String title, int cnt) { super(title); _cnt=cnt; }
    @Override String col(int i) {
      if( i==0 ) _cnt++;
      return ""+_cnt;
    }
  }
  
  static class TimeIncrCol extends Col {
    LocalDateTime _t;
    TimeIncrCol(String title, LocalDateTime t) { super(title); _t=t; }
    @Override String col(int i) {
      if( i==0 ) {
        long delta = (long)Math.abs((R.nextGaussian()+1)*1000);
        _t = _t.plus(delta,ChronoUnit.MILLIS);
      }
      return _t.toString();
    }
  }
  
  
}
