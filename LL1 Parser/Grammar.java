import java.io.*;
import java.util.*;
@SuppressWarnings("deprecation")
class Grammar
{
    String fLine="";
    static HashMap<String,Character> encodeNonTerminals = new HashMap<>();
    static HashMap<String,Character> encodeTerminals=new HashMap<>();
    static HashMap<Character,String> decodeNonTerminals=new HashMap<>();
    static HashMap<Character,String> decodeTerminals=new HashMap<>();
    static ArrayList<String> tokens= new ArrayList<>();

    Grammar() throws IOException {
    }

    static void encode(boolean flag) throws IOException {
        char ch='A';
        String nonTerminals="AE BE D DL E F ES IOS IS NE P PE RE S SL T TY VL WS U W V X Y [ Z";
        String terminals="+ - * / = < > ( ) { } := ; and else end ic id if int do fc float not or print prog scan str then while";
        String s1[]=nonTerminals.split(" ");
        int i;
        for(i=0;i<s1.length;i++)
        {
            encodeNonTerminals.put(s1[i],ch);
            decodeNonTerminals.put(ch,s1[i]);
            ch++;
        }
        ch='a';
        String s2[]=terminals.split(" ");
        for(i=0;i<s2.length;i++)
        {
            if(i<26)
            {
                encodeTerminals.put(s2[i],ch);
                ch++;
            }
            else if(i==26)
            {
                encodeTerminals.put(s2[i],'+');
                decodeTerminals.put('+',s2[i]);
            }
            else if(i==27)
            {
                encodeTerminals.put(s2[i],'-');
                decodeTerminals.put('-',s2[i]);
            }
            else if(i==28)
            {
                encodeTerminals.put(s2[i],'*');
                decodeTerminals.put('*',s2[i]);
            }
            else if(i==29)
            {
                encodeTerminals.put(s2[i],'/');
                decodeTerminals.put('/',s2[i]);
            }
            else if(i==30)
            {
                encodeTerminals.put(s2[i],'%');
                decodeTerminals.put('%',s2[i]);
            }
            else if(i==31)
            {
                decodeTerminals.put('~',s2[i]);
                encodeTerminals.put(s2[i],'~');
            }
        }
        encodeTerminals.put("#",'#');
        decodeTerminals.put('#',"#");
        if(flag){
            BufferedReader br=new BufferedReader(new FileReader("tokens.txt"));
            String str="";

            while((str=br.readLine())!=null){
                tokens.add(str);
            }
            br.close();
            System.out.println(tokens);
            mapTokens(tokens);}
    }
    static void mapTokens(ArrayList<String> tokens) throws IOException {
        ArrayList<Character> mapTokens=new ArrayList<>();
        char c;
        for (int i = 0; i < tokens.size(); i++) {
            if (encodeNonTerminals.containsKey(tokens.get(i)))
                mapTokens.add(encodeNonTerminals.get(tokens.get(i)));
            else if(encodeTerminals.containsKey(tokens.get(i)))
                mapTokens.add(encodeTerminals.get(tokens.get(i)));
            else
                continue;
        }
        System.out.println(mapTokens);
        PrintWriter writer = new PrintWriter(new FileWriter("mappedTokens.txt"));
        for (int i = 0; i < mapTokens.size(); i++) {
            writer.println(mapTokens.get(i));
        }
        writer.close();
    }
    public static  void  grammar1() throws IOException {
        LL1parser obj1=new LL1parser();
        ArrayList<String> arr1[][]=obj1.module1("GrammarLL_1.txt");
        if(arr1==null)
        {
            System.out.println("Not a Valid grammar");
        }
        String decision;
        System.out.println("Do you want to test parser (y/n):");
        Scanner sc = new Scanner(System.in);
        decision = sc.next();
        while (decision.equalsIgnoreCase("y"))
        {
            System.out.println("Enter an expression:");
            String str = sc.next();
            String input="";
            for (int i = 0; i < str.length(); i++) {
                input+=str.charAt(i)+" ";
            }
            System.out.println("Input Expression: "+input+"$");
            obj1.module2(arr1,input+"$");
            System.out.println("You can check stack contents in Stack.txt file");
            System.out.println("--------------------------------------------------------------------");

            System.out.println("Do you want to continue (y/n):");
            decision = sc.next();
        }
        if(decision.equalsIgnoreCase("n"))
        {
            test();
        }

    }
    public static void grammar2() throws IOException {
        encode(true);
        readGrammar();
        String str="";
        LL1parser obj=new LL1parser();
        ArrayList<String> arr[][]=obj.module1("GrammarLL_2.txt");
        if(arr==null)
        {
            System.out.println("Not a Valid Grammar");
        }
        BufferedReader br=new BufferedReader(new FileReader("mappedTokens.txt"));
        String p="";
        while((str=br.readLine())!=null)
        {
            p=p+str+" ";
        }
        p=p+"$ ";
        String decision;
        System.out.println("The input Program file is parsed to following tokens :\n"+p+"\n");
        obj.module2(arr,p);
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Do you want to test parser (y/n):");
        Scanner sc = new Scanner(System.in);
        decision = sc.next();
        while (decision.equalsIgnoreCase("y"))
        {
            System.out.println("Enter an expression:");
            String str1 = sc.next();
            String input="";
            for (int i = 0; i < str1.length(); i++) {
                input+=str1.charAt(i)+" ";
            }
            System.out.println("Input Expression: "+input+"$");
            obj.module2(arr,input+"$");
            System.out.println("You can check stack contents in Stack.txt file");
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Do you want to continue (y/n):");
            decision = sc.next();
        }
        if(decision.equalsIgnoreCase("n"))
        {
            test();
        }
    }
    public static void test() throws IOException {
        System.out.println("Choose grammar");
        System.out.println("Enter\n\t1.Grammar-1\n\t2.Grammar-2\n\t3.Exit");
        Scanner sc = new Scanner(System.in);
        int choice=sc.nextInt();
        switch (choice){
            case 1:
                grammar1();
                break;
            case 2:
                grammar2();
                break;
            case 3:
                System.out.println("Exitting..");
                break;
            default:
                System.out.println("Enter correct choice");
        }
    }
    public  static void readGrammar() throws IOException {
        BufferedReader br=new BufferedReader(new FileReader("grammar2.txt"));
        String text="";
        String printLine="";
        encode(false);
        while((text=br.readLine())!=null)
        {
            String[] split=text.split(" -> ");
            ArrayList<String> token = new ArrayList<>();
            ArrayList<String> lhs = new ArrayList<>();
            lhs.add(split[0]);

            token.addAll(Arrays.asList(split[1].split(" ")));

            for (int i = 0; i < token.size(); i++) {
                //System.out.println(tokens.get(i)+"-->"+encodeTerminals.containsKey(tokens.get(i).trim().toString()));
                if(encodeNonTerminals.containsKey(token.get(i).trim())){
                    //System.out.println("hi");
                    token.set(i,encodeNonTerminals.get(token.get(i)).toString() );
                }

                else if(encodeTerminals.containsKey(token.get(i).trim()))
                {
                    token.set(i,encodeTerminals.get(token.get(i)).toString() );
                }
                else
                    continue;
            }

            if(encodeNonTerminals.containsKey(lhs.get(0)))
                lhs.set(0,encodeNonTerminals.get(lhs.get(0)).toString() );

            else
                continue;

            String rhs="";
            for (int i = 0; i < token.size(); i++) {
                rhs+=token.get(i);
            }

            printLine+=lhs.get(0)+"->"+rhs+"\n";
        }

        PrintWriter writer = new PrintWriter(new FileWriter("temp.txt"));
        writer.println(printLine);
        writer.flush();

        removeEmptyLines();

    }
    public static  void removeEmptyLines()
    {
        Scanner file;
        PrintWriter writer;
        try {
            file = new Scanner(new File("temp.txt"));
            writer = new PrintWriter("GrammarLL_2.txt");
            while (file.hasNext()) {
                String line = file.nextLine();
                if (!line.isEmpty() && file.hasNext()) {
                    writer.write(line);
                    writer.write("\n");
                }
                else if(!line.isEmpty() && !file.hasNext())
                    writer.write(line);
            }
            file.close();
            writer.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Error");
        }
        finally {
            File files = new File("temp.txt");
            files.deleteOnExit();
        }

    }
    public static void main(String args[])throws IOException
    {
        test();
    }
}
