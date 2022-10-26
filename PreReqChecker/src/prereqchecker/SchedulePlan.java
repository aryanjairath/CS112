package prereqchecker;

import java.util.*;

/**
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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static Boolean preReq(HashMap<String, ArrayList<String>> x, ArrayList<String> z, String y) {
        for (String t : z) {
            if (t.equals(y)) {
                return true;
            } else {
                if (preReq(x, x.get(t), y))
                    return true;
            }
        }

        return false;
    }
    public static void main(String[] args) {
        StdIn.setFile(args[0]);

        int lines = Integer.parseInt(StdIn.readLine());

        HashSet<String> courseId = new HashSet<>();
        for (int j = 0; j < lines; j++) {
            courseId.add(StdIn.readLine());
        }

        HashMap<String, ArrayList<String>> courses = new HashMap<String, ArrayList<String>>();

        for (String val : courseId) {
            courses.put(val, new ArrayList<String>());
        }
        int li = Integer.parseInt(StdIn.readLine());

        for (int l = 0; l < li; l++) {
            String start = StdIn.readLine();
            if (courseId.contains(start.substring(0, start.indexOf(" ")))) {
                courses.get(start.substring(0, start.indexOf(" "))).add(start.substring(start.indexOf(" ") + 1));
            }

        }

        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);
        String target = StdIn.readLine();
        int num2 = Integer.parseInt(StdIn.readLine());

        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list2 = new ArrayList<String>();
        for (int j = 0; j < num2; j++) {
            String temp = StdIn.readLine();
            list.add(temp);
        }

        for (String val : courses.keySet()) {

            if (preReq(courses, list, val)) {
                if (!list.contains(val)) {
                    list.add(val);
                    num2++;
                }
            }
        }
        for (String val : courses.keySet()) {
            for (String name : courses.get(val)) {
                if (preReq(courses, courses.get(target), name)) {
                    if(!list.contains(name))
                    list2.add(name);
                }
            }
        }

        for (String val : courses.keySet()) {
            if (list.contains(val)) {
                list2.remove(val);
            }
        }

        HashSet<String> needToTake = new HashSet<String>();
        for (String index : list2)
            needToTake.add(index);

        ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
        int u = 0;
        int len = 23;
        while(u<len){
            listOfLists.add(new ArrayList<String>());
            u++;
        }
        list2 = new ArrayList<String>(needToTake);
       
        boolean isit = false;
        int scheduleNums = 0;
        ArrayList<String> temp = new ArrayList<String>();
        list = new ArrayList<>();
        while (list2.size() > 0) {
            for (int s = 0; s < list2.size(); s++) {
                isit = true;
                for (int r = 0; r < list2.size(); r++) {
                    if (preReq(courses, courses.get(list2.get(s)), list2.get(r)) && !list2.get(r).equals(list2.get(s))) {
                        isit = false;
                    }
                }

                if(isit) {
                    temp.add(list2.get(s));
                    list.add(list2.get(s));
                }
            }
            for(String val : list){
                if(list2.contains(val)){
                    list2.remove(val);
                }
            }
           
            for(String val : temp)
            listOfLists.get(scheduleNums).add(val);
            scheduleNums++;
            temp.clear();
        }

        StdOut.println(scheduleNums);
       
        for (int c = 0; c <listOfLists.size(); c++) {

            for (String index : listOfLists.get(c)) {
                StdOut.print(index+ " ");
            }
            StdOut.println();
        }       

    }
}
