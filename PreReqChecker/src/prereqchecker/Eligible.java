package prereqchecker;

import java.util.*;

/**
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public TreeSet<String> findPreRq(String course, HashMap<String, ArrayList<String>> map, TreeSet<String> tree){
        ArrayList<String> list = map.get(course);
        for(int i = 0; i < list.size(); i++){
            tree.add(list.get(i));
        }
        for(int i = 0 ; i < list.size(); i++)
            findPreRq(list.get(i), map, tree);

        return tree;  

    }
    public String goThrough(TreeSet<String> tree,HashMap<String, ArrayList<String>> map, String id){
        ArrayList<String> list = map.get(id);
        boolean check = true;
        for(String val: list){
            if(!tree.contains(val))
                check=false;
        }
        if(check) return id;

        else return "";
    }
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        int lines = StdIn.readInt();

        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for(int i = 0; i < lines; i++){
            map.put(StdIn.readString(), new ArrayList<String>());
        }        
        int b = StdIn.readInt();
        for(int i = 0; i < b; i++){
            map.get(StdIn.readString()).add(StdIn.readString());
        }    
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);

        int linenum = StdIn.readInt();
        TreeSet<String> tree = new TreeSet<>();

        ArrayList<String> courses = new ArrayList<>();
        for(int i = 0; i < linenum; i++){
            String tadd = StdIn.readString();
            courses.add(tadd);
            tree.add(tadd);
        }

        Eligible r = new Eligible();
        for(int i = 0; i < courses.size(); i++){
            TreeSet<String> x = r.findPreRq(courses.get(i), map, tree);
            for(String val: x){
                tree.add(val);
            }
        }

        ArrayList<String> possible = new ArrayList<>();
        for (Map.Entry<String,ArrayList<String>> e : map.entrySet()){        
            possible.add(r.goThrough(tree,map,e.getKey()));
        }
        for(String val: possible){
            if(!tree.contains(val)&&val!="")
            StdOut.println(val);
        }
    }
}
