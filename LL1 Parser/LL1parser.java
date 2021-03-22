
import java.io.*;
import java.util.*;
@SuppressWarnings({"deprecation","unchecked"})
class LL1parser
{

    String fLine="";
    HashMap<String,ArrayList<String>> productionRules=new HashMap<>();
    HashMap<String,ArrayList<String>> firstset=new HashMap<>();
    HashMap<String,ArrayList<String>> followset=new HashMap<>();
    ArrayList<Character> nonterminalList=new ArrayList<>();
    ArrayList<Character> terminalList=new ArrayList<>();
    HashSet<Character> nonterminalset=new HashSet<>();
    HashSet<Character> terminalset=new HashSet<>();
    int a=-1;
    String getChar()
    {
        a++;
        return ((char)('M'+a))+"";
    }
    public ArrayList<String>[][] module1(String filename)throws IOException
    {
        BufferedReader br=new BufferedReader(new FileReader(filename));
        String str="";
        int i,line=0;


        while((str=br.readLine())!=null)//Read the string, put in map
        {
            int l=str.indexOf("->");
            String left=str.substring(0,l).trim();
            nonterminalset.add(left.charAt(0));
            String right=str.substring(l+2).trim();
            l=right.length();
            for(i=0;i<l;i++)
            {
                char ch=right.charAt(i);
                if(ch=='|')
                    continue;
                if(isNonTerminal(ch))
                {
                    nonterminalset.add(ch);
                }
                else
                {
                    terminalset.add(ch);
                }
            }
            String tokens[]=right.split("[|]");
            //System.out.println("Tokens: "+Arrays.toString(tokens));
            if(line==0)
            {
                fLine=left;
            }
            line++;
            productionRules.put(left,new ArrayList<String>(Arrays.asList(tokens)));//Put all the rules in the map
        }
        br.close();

        char c='a';
        for(i=0;i<26;i++)
        {
            ArrayList<String> ch=new ArrayList<>();
            ch.add(c+"");
            firstset.put(c+"",ch);
            c++;
        }
        try
        {
            System.out.println("Terminals: ");
            terminalset.remove('#');
            System.out.println(terminalset);
            terminalset.add('$');
            System.out.println("--------------------------------------------------------------------");

            System.out.println("Non Terminals: ");
            System.out.println(nonterminalset);
            System.out.println("--------------------------------------------------------------------");

            System.out.println("First Sets");
            System.out.println("--------------------------------------------------------------------");
            findFirst();
            System.out.println("--------------------------------------------------------------------");

            System.out.println("Follow Sets");
            System.out.println("--------------------------------------------------------------------");
            findFollowt();
            System.out.println("--------------------------------------------------------------------");

            System.out.println("Parse Table: ");
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Parse Table is written into Table.txt file");
            System.out.println("--------------------------------------------------------------------");

            return getMatrix();
        }catch(Exception e)
        {
            System.out.println("This grammar is not perfect. It needs to be either left factored or left recursion should be removed");
        }
        System.out.println(fLine);
        return null;
    }
    public void module2( ArrayList<String> arr[][],String expr) throws IOException {
        String tokens[]=expr.split(" ");
        Stack<Character> stack=new Stack<>();
        stack.push('$');
        stack.push(fLine.charAt(0));
        int i,j,l;
        String stackoutput="";
        PrintWriter writer = new PrintWriter(new FileWriter("stack.txt"));

        for(i=0;i<tokens.length;)
        {
            if(stack.empty())
                break;
            char ch=stack.peek();
            char top=tokens[i].charAt(0);
            if(isNonTerminal(ch))
            {
                stack.pop();
                try
                {
                    if(arr[nonterminalList.indexOf(ch)][terminalList.indexOf(top)].size()==0)
                    {
                        System.out.println("Rejected");
                        break;
                    }
                }catch (Exception e)
                {
                    System.out.println("Rejected");
                    break;
                }
                String str=arr[nonterminalList.indexOf(ch)][terminalList.indexOf(top)].get(0);
                str=str.split("->")[1];
                l=str.length();
                for(j=l-1;j>=0;j--)
                {
                    if(str.charAt(j)=='#')
                        continue;
                    stack.push(str.charAt(j));
                }
            }
            else
            {
                if(ch==top)
                {
                    i++;
                    stack.pop();
                }
                else
                {
                    System.out.println("Rejected");
                    break;
                }
            }
            //System.out.println("Stack: "+stack);
            stackoutput+=stack+"\n";

        }

        if(stack.empty()&&i==tokens.length)
            System.out.println("Accepted");

        writer.println(stackoutput);
        writer.flush();
    }

    ArrayList<String>[][] getMatrix() throws IOException {
        ArrayList<String> arr[][]=new ArrayList[nonterminalset.size()][terminalset.size()];

        nonterminalList.addAll(nonterminalset);
        terminalList.addAll(terminalset);
        int i,j;
        for(i=0;i<nonterminalset.size();i++)
        {
            for(j=0;j<terminalset.size();j++)
            {
                arr[i][j]=new ArrayList<>();

            }
        }
        for(Map.Entry<String, ArrayList<String>> mp:productionRules.entrySet())
        {
            String terminal=mp.getKey();
            ArrayList<String> rules=mp.getValue();
            //System.out.println("Considering: "+terminal+"->"+rules);
            for(String rule:rules)
            {
                char ch=rule.charAt(0);
                if(isNonTerminal(ch))
                {
                    ArrayList<String> al=firstset.get(ch+"");
                    for(String str:al)
                    {
                        ch=str.charAt(0);
                        int row=nonterminalList.indexOf(terminal.charAt(0));
                        int col=terminalList.indexOf(ch);
                        //System.out.println(row+","+col+","+terminal+"->"+rule);
                        if(arr[row][col].size()==0)
                            arr[row][col].add(terminal+"->"+rule);
                    }
                }
                else if(ch!='#')
                {
                    int row=nonterminalList.indexOf(terminal.charAt(0));
                    int col=terminalList.indexOf(ch);
                    //System.out.println(row+","+col+","+terminal+"->"+rule);
                    if(arr[row][col].size()==0)
                        arr[row][col].add(terminal+"->"+rule);
                }
            }
            if(firstset.get(terminal).contains("#"))
            {
                for(String str:followset.get(terminal))
                {
                    int row=nonterminalList.indexOf(terminal.charAt(0));
                    int col=terminalList.indexOf(str.charAt(0));
                    //System.out.println(row+","+col+","+terminal+"->#");
                    if(arr[row][col].size()==0)
                        arr[row][col].add(terminal+"->#");
                }
            }
        }
        boolean flag=false;
        for(i=0;i<nonterminalset.size();i++)
        {
            //System.out.print(nonterminalList.get(i)+"\t\t");
            for(j=0;j<terminalset.size();j++)
            {
                //System.out.print(arr[i][j]+"\t\t");
                if(arr[i][j].size()>1)
                    flag=true;
            }
            //System.out.println();
        }
        ArrayList<String> final2Darray[][]=new ArrayList[nonterminalset.size()+1][terminalset.size()+1];
        for(i=0;i<nonterminalset.size()+1;i++)
        {
            for(j=0;j<terminalset.size()+1;j++)
            {
                final2Darray[i][j]=new ArrayList<>();
            }
        }
        final2Darray[0][0].add("");
        for(i=1;i<terminalset.size()+1;i++)
        {
            final2Darray[0][i].add(terminalList.get(i-1)+"");
        }
        for(i=1;i<nonterminalset.size()+1;i++)
        {
            final2Darray[i][0].add(nonterminalList.get(i-1)+"");
        }
        for(i=1;i<nonterminalset.size()+1;i++)
        {
            for(j=1;j<terminalset.size()+1;j++)
            {
                final2Darray[i][j].addAll(arr[i-1][j-1]);
            }
        }
        for(i=1;i<nonterminalset.size()+1;i++)
        {
            for(j=1;j<terminalset.size()+1;j++)
            {
                if(final2Darray[i][j].isEmpty())
                    final2Darray[i][j].add("null");
            }
        }

        String printTable="";
        for(i=0;i<nonterminalset.size()+1;i++)
        {
            String out ="";
            for(j=0;j<terminalset.size()+1;j++)
            {
                if(i==0 && j>0)
                {
                    out += "\t" + final2Darray[i][j].toString().replace("[", "").replace("]", "") + "\t\t    |";
                }
                else
                {
                    out+="\t"+final2Darray[i][j].toString().replace("[","").replace("]","")+"\t\t    |";
                }

            }
            printTable+=out+"\n";

        }
        System.out.println(printTable);
        System.out.println("--------------------------------------------------------------------");

        PrintWriter writer = new PrintWriter(new FileWriter("Table.txt"));
        writer.println(printTable);
        writer.flush();

        if(flag)
        {
            System.out.println("This Grammar is not LL(1) ");
        }
        else
        {
            System.out.println("This Grammar is LL(1)  ");
        }
        return arr;
    }
    boolean isNonTerminal(char ch)
    {
        return ch>=65&&ch<=91;
    }
    void findFirst()
    {
        for(Map.Entry<String, ArrayList<String>> mp:productionRules.entrySet())
        {
            String terminal=mp.getKey();
            ArrayList<String> rules=mp.getValue();
            //System.out.println("Terminal,Rules: "+terminal+","+rules);
            if(firstset.get(terminal)!=null)//Already done;
            {
                System.out.println(terminal+"->"+unique(firstset.get(terminal)));
                continue;
            }
            System.out.println(terminal+"->"+unique(findFirstSet(terminal,rules)));
        }
    }
    ArrayList<String> findFirstSet( String terminal, ArrayList<String> rules)
    {
        if(firstset.get(terminal)!=null)
            return firstset.get(terminal);
        //System.out.println("Terminal,Rules: "+terminal+","+rules);
        for(String rule:rules)
        {
            int i,l=rule.length();
            for(i=0;i<l;i++)
            {
                char ch=rule.charAt(i);
                ArrayList<String> a=firstset.get(terminal);
                if(a==null)
                    a=new ArrayList<>();
                if(!isNonTerminal(ch))
                {
                    a.add(ch+"");
                    firstset.put(terminal,a);
                    break;
                }
                else
                {
                    ArrayList<String> temp=findFirstSet(ch+"",productionRules.get(ch+""));
                    a.addAll(temp);
                    firstset.put(terminal,a);
                    if(!temp.contains("#"))
                        break;
                }
            }
        }
        return firstset.get(terminal);
    }
    ArrayList<String> uniqueAndEpsilonLess(ArrayList<String> al)
    {
        HashSet<String> hs=new HashSet<>();
        hs.addAll(al);
        hs.remove("#");
        al.clear();
        al.addAll(hs);
        return al;
    }
    ArrayList<String> unique(ArrayList<String> al)
    {
        HashSet<String> hs=new HashSet<>();
        hs.addAll(al);
        al.clear();
        al.addAll(hs);
        return al;
    }
    void findFollowt()
    {
        for(Map.Entry<String, ArrayList<String>> mp:productionRules.entrySet())
        {
            String terminal=mp.getKey();
            ArrayList<String> rules=mp.getValue();
            if(followset.get(terminal)!=null)//Already done;
            {
                System.out.println(terminal+"->"+uniqueAndEpsilonLess(followset.get(terminal)));
                continue;
            }
            System.out.println(terminal+"->"+uniqueAndEpsilonLess(findFollowtSet(terminal,0)));
        }
    }
    ArrayList<String> findFollowtSet(String terminal,int count)
    {
        //System.out.println("Computing: "+terminal);
        if(count>=10)
            System.exit(0);
        if(followset.get(terminal)!=null)
            return followset.get(terminal);
        for(Map.Entry<String, ArrayList<String>> mp:productionRules.entrySet())
        {
            ArrayList<String> rules=mp.getValue();
            for(String rule:rules)
            {
                int i,l=rule.length();
                if(rule.equals("#"))
                    continue;
                for(i=0;i<l;i++)
                {
                    char ch=rule.charAt(i);
                    //System.out.println("Considering: "+ch+",Terminal:"+terminal);
                    ArrayList<String> a=followset.get(terminal);
                    if(a==null)
                        a=new ArrayList<>();
                    followset.put(terminal,a);//No NPE
                    if(fLine.equals(terminal)&&!a.contains("$"))
                    {
                        a.add("$");
                        followset.put(terminal,a);
                        a=followset.get(terminal);
                        //System.out.println("1: "+followset);
                    }
                    if(terminal.equals(ch+"")&&i!=l-1)
                    {
                        i++;
                        ch=rule.charAt(i);
                        //System.out.println("We're in");
                        if(!isNonTerminal(ch))
                        {
                            if(ch!='#')
                            {
                                a.add(ch+"");
                                followset.put(terminal,a);
                                //System.out.println("2: "+followset);
                            }
                        }
                        else
                        {
                            //System.out.println("Adding all the stuff of firstset: "+ch);
                            a.addAll(firstset.get(ch+""));
                            while(firstset.get(ch+"").contains("#")&&i+1<l)
                            {
                                i++;
                                ch=rule.charAt(i);
                                a.addAll(firstset.get(ch+""));
                            }
                            if(i==l-1)//Reached end while calculting followset
                            {
                                ///System.out.println("Reached end, key is: "+mp.getKey());
                                ArrayList<String> temp=findFollowtSet(mp.getKey(),count+1);
                                a.addAll(temp);
                            }
                            followset.put(terminal,a);
                            //System.out.println("3: "+followset);
                        }
                    }
                    else if(terminal.equals(ch+"")&&i==l-1&&!mp.getKey().equals(terminal))
                    {
                        //System.out.println("Trying to compute: "+mp.getKey());
                        ArrayList<String> temp=findFollowtSet(mp.getKey(),count+1);
                        a.addAll(temp);
                        followset.put(terminal,a);
                        //System.out.println("4: "+followset);
                    }
                }
            }
        }
        return followset.get(terminal);
    }
}