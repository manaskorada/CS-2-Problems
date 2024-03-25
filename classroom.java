/*
Manas Korada
COP 3503C - 0001
Spring 2024
Arup Guha
2/28/24
Classroom Problem - RP3
Finding number of n activities that can be accomodated in k classrooms based on their start and end time
*/ 
import java.util.*;

public class classrooms {
    public static void main(String[] args) {
        // takes in integers n and k to represent number of activities and number of classrooms
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); 
        int k = scanner.nextInt(); 

        // Makes treeset and an arraylist of activities to hold the activities when they are first scanned
        List<Activity> activities = new ArrayList<>();

        // Fills up activity arraylist
        for (int i = 0; i < n; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            activities.add(new Activity(start, end));
        }
        // Sorts activity arraylist
        Collections.sort(activities);

        // Creates treeset of k classrooms
        TreeSet<SingleClassroom> activityBooking = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            activityBooking.add(new SingleClassroom(0, i));
        }

        // Adds to treeset and updates it. Updates count to reflect number of activities that were able to fit.
        int count = 0;
        for (Activity curAct : activities) {
            // floor will get the greatest element that is less than or equal to the classroom with the given start time
            SingleClassroom classroomToChange = activityBooking.floor(new SingleClassroom(curAct.start, k));
            
            // If a classroom is found, the end time for that classroom is updated
            if (classroomToChange != null) {
                int classNum = classroomToChange.classNum;
                activityBooking.remove(classroomToChange);
                activityBooking.add(new SingleClassroom(curAct.finish + 1, classNum));
                count++;
            }
        }

        System.out.println(count);
        scanner.close();
    }
}

// Holds the start and end time for the Activity
class Activity implements Comparable<Activity> {
    int start;
    int finish;

    Activity(int s, int f) {
        start = s;
        finish = f;
    }

    @Override
    public int compareTo(Activity o) {
        if (finish == o.finish) {
            return start - o.start;
        }
        return finish - o.finish;
    }
}

// Holds the end time of the current activity as well as the classroom number
class SingleClassroom implements Comparable<SingleClassroom> {
    int endTime;
    int classNum;

    SingleClassroom(int e, int c) {
        endTime = e;
        classNum = c;
    }

    @Override
    public int compareTo(SingleClassroom o) {
        if (endTime == o.endTime) {
            return classNum - o.classNum;
        }
        return endTime - o.endTime;
    }
}