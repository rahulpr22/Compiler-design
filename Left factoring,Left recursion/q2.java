import java.util.*;
import  java.text.AttributedString;

public class q2 {
    public static String cfg_Left_factored="";// to check already left factored grammar recursively
    public static String cfg="A--> qB | qC " ;
    public static String Not_LF="";//left factored
    public static String result="";//final result
    public static boolean left_factoring=false;// variable for checking whwther grammar is left factored or not
    public static int f_count =0; //new non-teminal subscript

    public static String test(int i,boolean print)
    {
        System.out.println("Enter Production "+i+" : ");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        check_factor(str);
        result = result + Not_LF;
        Not_LF="";
        if(print)
            System.out.println("Final Result :\n");

        return result;

    }

    public static void main(String[] args) {
        System.out.println("Example Input: "+cfg);
        System.out.println("Enter $ for epsilon");
        System.out.println("Enter number of productions: ");
        Scanner sc = new Scanner(System.in);
        int n=sc.nextInt();
        for(int i=1;i<=n;i++)
        {

            if(i==n) {
                System.out.println(test(i,true));
            }
            else
            {
                test(i,false);
            }
        }
    }

    public static void check_factor(String cfg) {

        String lines [] = cfg.split("\\\n");
        for (String line: lines) {

            line= line.replaceAll("\\s+", "");  // just to remove all white spaces from a line

            String productions [] = line.split("-->",0); // to split the a line at a time into right hand side and left hand side productions according to -->
            String lhs = productions [0];
            String rhs = productions[1];


            String [] rhs_productions = rhs.split("\\|") ; // split right hand side for productions based on |

            left_factoring=false;

            for (int i=0;i<rhs_productions.length-1;i++) {
                for(int j=i+1;j<rhs_productions.length;j++) {

                    if (rhs_productions[i].charAt(0)==rhs_productions[j].charAt(0) && (!rhs_productions[i].equals("$")) ) {
                        left_factoring=true;
                    }


                }
            }

            if(left_factoring) {

                System.out.println("");
                String common_prefix="";
                common_prefix=find_common_prefix(rhs_productions);

                left_factor_out (lhs , common_prefix,  rhs_productions ); // to remove left factoring

            }
            else
            {
                Not_LF+=line +"\n";

            }
        }
    }
    /*
    public static String superscript(String str) {
    str = str.replaceAll("0", "⁰");
    str = str.replaceAll("1", "¹");
    str = str.replaceAll("2", "²");
    str = str.replaceAll("3", "³");
    str = str.replaceAll("4", "⁴");
    str = str.replaceAll("5", "⁵");
    str = str.replaceAll("6", "⁶");
    str = str.replaceAll("7", "⁷");
    str = str.replaceAll("8", "⁸");
    str = str.replaceAll("9", "⁹");
    return str;
}*/
    public static String subscript(String str) {
        str = str.replaceAll("0", "₀");
        str = str.replaceAll("1", "₁");
        str = str.replaceAll("2", "₂");
        str = str.replaceAll("3", "₃");
        str = str.replaceAll("4", "₄");
        str = str.replaceAll("5", "₅");
        str = str.replaceAll("6", "₆");
        str = str.replaceAll("7", "₇");
        str = str.replaceAll("8", "₈");
        str = str.replaceAll("9", "₉");
        return str;
    }

    private static int count=0;
    private static void left_factor_out(String lhs,  String common_prefix,String[] rhs_productions)
    {
        cfg_Left_factored=lhs+"-->"; // rewrite the productions without left factoring
        count++;

        for(String pro:rhs_productions) {
            if(!pro.startsWith(common_prefix) ) {
                cfg_Left_factored+=pro+"|";
            }

        }

        cfg_Left_factored+=common_prefix + "X"+ subscript(f_count+"")  + "\n";
        cfg_Left_factored+="X"+ subscript(f_count+"") + "--> ";

        for(String pro:rhs_productions)
        {

            if(pro.startsWith(common_prefix) )
            {
                if( pro.substring(1 , pro.length()).length() ==0)
                {

                    cfg_Left_factored+="$"+"|";
                }
                else
                {
                    cfg_Left_factored+=pro.substring(1 , pro.length() )+"|";
                }

            }

        }

        cfg_Left_factored = cfg_Left_factored.substring(0, cfg_Left_factored.length()-1); // to remove last | symbol at the end
        System.out.println("left factored "+count+" time \n" + cfg_Left_factored);
        System.out.println("------------------------------------------------------------");
        if(left_factoring)
        {
            f_count++;
            check_factor(cfg_Left_factored);

        }

    }

    public static String find_common_prefix(String[] rhs_productions) {
        String common_prefix = "";
        for (int i=0;i<rhs_productions.length-1;i++)
        {
            for(int j=i+1;j<rhs_productions.length;j++)
            {
                if (rhs_productions[i].charAt(0) == rhs_productions[j].charAt(0) )
                {
                    common_prefix=rhs_productions[i].charAt(0)+"";
                    break;
                }

            }

            if(common_prefix.length()>0)
                break;
        }

        return common_prefix;
    }


}

