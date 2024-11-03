import java.util.Scanner;
import java.io.File;
import java.util.LinkedList;

public class Main
{
    public static void main(String[] args) throws Exception {

        // reading the runners from the file
        String file = "runners.txt";
        LinkedList<Runner> runners = readRunners(file);

        // sorting runners according to their run time
        Runner[] sorted = sortRunTimes(runners);

        // scanner for reading input for search key from user
        // searching for runners based on age group or gender
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running)
        {
            String forUser = "('end' to quit) Please type a search key (gender or age group: youth, master, senior): ";
            System.out.print(forUser);
            String key = scanner.next();
            if (key.equals("end"))
                running = false;
            else
            {
                LinkedList<Runner> selectedRunners = searchRunners(sorted, key);
                if (selectedRunners.isEmpty())
                    System.out.println("Runners not found");
                else
                    for (Runner out : selectedRunners)
                        System.out.println(out);
            }
            System.out.println();
        }
    }

    // method for reading runners from file and returning a list of runners
    static LinkedList<Runner> readRunners (String file) throws Exception {
        // create a scanner for the file
        Scanner scanner = new Scanner(new File(file));

        // storing Runners into a list, because we don't know how many runners there are
        LinkedList<Runner> runners = new LinkedList<>();

        // read while there are next lines in the file
        while (scanner.hasNextLine())
        {
            // each line has a runner's name (first and last), gender, age group and run time
            String firstN = scanner.next();
            String lastN = scanner.next();
            String gender = scanner.next();
            String ageG = scanner.next();
            double runT = scanner.nextDouble ();

            runners.add(new Runner(firstN, lastN, gender, ageG, runT));
        }
        // return list
        return runners;
    }

    // sorting runners by their run time from fastest to slowest
    static Runner[] sortRunTimes (LinkedList<Runner> runners)
    {
        Runner[] runsInOrder = new Runner [runners.size()];

        // putting runners into the array for sorting
        for (int i = 0; i < runsInOrder.length; i++)
            runsInOrder[i] = runners.get(i);

       // sorting run times into increasing order into the array
        // with selection sort
        int s = 0; // index of selected
       while (s < runsInOrder.length - 1)
       {
           int minIndex = s;
           // compare against other values
           for (int c = s + 1; c < runners.size(); c++)
           {
               double minRunTime = runsInOrder[minIndex].getRunTime();
               double comparison = runsInOrder[c].getRunTime();
               if (comparison < minRunTime)
                   minIndex = c;
           }
           if (minIndex != s)
           {
               // swap runner with fastest time with runner with index s
               Runner tmp = runsInOrder[minIndex];
               runsInOrder[minIndex] = runsInOrder[s];
               runsInOrder[s] = tmp;
           }
           s++;
       }
       return runsInOrder;
    }

    // return a list of runners that start with a key
    // which is either the gender or age group
    static LinkedList<Runner> searchRunners (Runner[] runners, String key)
    {
        // using the sorted array for search
        // using sequential search to look for the key
        LinkedList<Runner> keyRunners = new LinkedList<>();

        for (int index = 0; index < runners.length; index++)
        {
            Runner runner = runners[index];
            if (runner.getGender().startsWith(key) || runner.getAgeGroup().startsWith(key))
            {
               keyRunners.add(runner);
            }
        }
        return keyRunners;
    }
}

// class Runner
    class Runner
{
        public String firstName, lastName, gender, ageGroup;
        double runTime;

        public Runner(String firstName, String lastName, String gender, String ageGroup, double runTime) {
            this.ageGroup = ageGroup;
            this.firstName = firstName;
            this.lastName = lastName;
            this.runTime = runTime;
            this.gender = gender;
        }

        public String toString()
        {
            // abbreviation of gender
            String genderAbrv = "M";
            if (gender.startsWith("f"))
                genderAbrv = "F";
            return genderAbrv + " " + firstName + " " + lastName + ", 10k: " + runTime + ", " + ageGroup;
        }

        // accessor to get the runner's run time
        public double getRunTime() {
            return runTime;
        }

        // accessor to get the runner's gender
        public String getGender() {
            return gender;
        }

        // accessor to get the runner's age group
        public String getAgeGroup ()
        {
            return ageGroup;
        }
}