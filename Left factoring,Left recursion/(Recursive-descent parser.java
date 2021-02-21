import java.util.*;
import java.io.*;
class q3
{
    public static void main(String args[])throws IOException
    {
        String input= "\nE->TA\n" +
                "A->+TA|$\n" +
                "T->FC\n" +
                "C->*FC|$\n" +
                "F->(E)|a";
        System.out.println("Given Input productions\n"+input+"\n");
        System.out.println("Sample input1 : "+" a+a");
        System.out.println("Sample input2 : "+" a*a\n");
        System.out.println("Enter Input String :");
        BufferedReader inp=new BufferedReader(new InputStreamReader(System.in));
        BufferedReader inpGrammar=new BufferedReader(new FileReader("parser.txt"));
        check(inp,inpGrammar);
    }


    public static void check(BufferedReader br,BufferedReader br1)throws IOException
    {
        String str="";
        int i=0;
        int l=str.length();
        HashMap<String,ArrayList<String>> rules=new HashMap<>();//Map containing rhs and lhs
        int line=0;
        ArrayList<String> dfsSources=new ArrayList<>();// store start tokens for dfs
        HashSet<String> epsilonAble=new HashSet<>();//If it has an epsilon production, it has a production with all non-terminals and they are epsilonable, it is epsilonable
        HashSet<String> notEpsilonAble=new HashSet<>();
        while((str=br1.readLine())!=null)
        {
            if(str.length()>15)
                break;
            l=str.indexOf("->");
            String left=str.substring(0,l).trim();// lhs
            String right=str.substring(l+2).trim();// rhs
            String token[]=right.split("[|]");// split tokens for a production when '|'
            ArrayList<String> al=new ArrayList<>();// all tokens
            for(i=0;i<token.length;i++)
            {
                if(line==0)//Save the Start rules as source of DFS
                {
                    dfsSources.add(token[i]);
                }
                line++;
                al.add(token[i]);
            }
            rules.put(left,al);
        }
        ArrayList<String> keys = new ArrayList<>(rules.keySet());//all keys or non terminals
        for(int k=0;k<keys.size();k++)
        {
            if(epsilonAble.size()+notEpsilonAble.size()==rules.size())//We are done
                break;
            String left=keys.get(k);
            ArrayList<String> al=rules.get(left);
            boolean done=false;
            int count=0,epsCount=0;
            for(String s:al)
            {
                if(s.equals("$"))
                {
                    epsilonAble.add(left);//Add production which has epsilon transition
                    done=true;
                    break;
                }
                else if(containsBad(s))//This has a terminal
                {
                    count++;
                    continue;
                }
                else//This rule has all nonterminals
                {
                    for(int r=0;r<s.length();r++)
                    {
                        if(epsilonAble.contains(s.charAt(r)+""))//Increase the count of epsilonable terminals
                        {
                            epsCount++;
                        }
                        else if(notEpsilonAble.contains(s.charAt(r)+""))//non epsilonables
                        {
                            count++;
                            break;
                        }
                    }
                    if(epsCount==s.length())//All characters were epsilonable
                    {
                        epsilonAble.add(left);
                        done=true;
                        break;
                    }
                }
            }
            if(count==al.size())//count denotes number of rules that are non-epsilonable; if we check all rules, we are done, it's not epsilonable
            {
                done=true;
                notEpsilonAble.add(left);
            }
            if(!done)//For rules like A->BC and we don't yet know if B is epsilonble or not, so save the task for later
                keys.add(left);
        }
        str=br.readLine();
        //Call dfs from all start productions.
        for(String s:dfsSources)
        {
            dfs(str,s,rules,new HashSet<String>(),epsilonAble,notEpsilonAble);
        }
        System.out.println("Rejected");
    }
    public static boolean nonTerminal(int ch)
    {
        return ch>=65&&ch<=90;
    }


    public static void dfs(String target, String formed, HashMap<String,ArrayList<String>> rules, HashSet<String> visited,HashSet<String> e,HashSet<String> nE)
    {
        int i,l;
        if(formed.equals(target))
        {
            System.out.println("Accepted");
            System.exit(0);
        }
        visited.add(formed);
        l=formed.length();
        for(i=0;i<l;i++)
        {
            char ch=formed.charAt(i);
            if(nonTerminal(ch))
            {
                ArrayList<String> al=rules.get(ch+"");
                for(String s:al)
                {
                    if(s.equals("$"))
                        s="";
                    String possible=formed.substring(0,i)+s+formed.substring(i+1);
                    if(valid(possible,target,e,nE)&&!visited.contains(possible))
                    {
                        visited.add(possible);
                        dfs(target,possible,rules,visited,e,nE);
                    }
                }
            }
        }
    }

    public static boolean valid(String str, String target,HashSet<String> e,HashSet<String> nE)
    {
        int count=0,i,l=str.length(),l1=target.length(),j;
        for(i=0;i<l;i++)
            if(nonTerminal(str.charAt(i))&&e.contains(str.charAt(i)+""))//Then we have more non-Terminals than necessary; this won't work
                count++;
        if(target.length()<str.length()-count)
            return false;
        return true;
    }
    public static boolean containsBad(String str)
    {
        int i,l=str.length();
        for(i=0;i<l;i++)
        {
            if(!nonTerminal(str.charAt(i)))
                return true;
        }
        return false;
    }

}
