import java.net.*;
import java.io.*;
//job seeker waits for jobSeeker to connect, accepts job request, does job, reports back
public class JobSeeker{ //server
    public static void main(String[] args) throws IOException{
        String jobString;
        int jobVar1;
        int jobVar2;
        boolean badJob = false;
        boolean jobDone = false;
        double sum = 0;
        ServerSocket ss = new ServerSocket(4999);
        Socket s = new Socket();
            try {
                s = ss.accept();
                //server can only connect to one client at a time
                System.out.println("JobCreator connected. Waiting for Job...");
                InputStreamReader in = new InputStreamReader(s.getInputStream());
                BufferedReader bf = new BufferedReader(in);

                //get the job type from the creator
                jobString = bf.readLine();
                System.out.println("jobCreator: " + jobString);

                //get the 2 variables from creator
                jobVar1 = Integer.parseInt(bf.readLine());
                jobVar2 = Integer.parseInt(bf.readLine());
                System.out.println("var1: " + jobVar1);
                System.out.println("var2: " + jobVar2);

                PrintWriter pr = new PrintWriter(s.getOutputStream());
                pr.println("RECEIVED. Job Type: " + jobString + ", var1: " + jobVar1 + ", var2: " + jobVar2);
                pr.flush();
                //do the calculations
                switch (jobString) {
                    case "addition", "Addition" -> sum = jobVar1 + jobVar2;
                    case "subtraction", "Subtraction" -> sum = jobVar1 - jobVar2;
                    case "division", "Division" -> sum = (double) (jobVar1 / jobVar2);
                    case "multiplication", "Multiplication" -> sum = jobVar1 * jobVar2;
                    default -> badJob = true;
                }
                if (!badJob) {
                    System.out.println("Job finished.");
                    pr.println("Job Complete. sum = " + sum);
                    pr.flush();
                    jobDone = true;
                    pr.println(1);
                    pr.flush();
                } else {
                    System.out.println("Job incomplete. Error.");
                    pr.println("Error. Unknown job type.");
                    pr.flush();
                    pr.println(0);
                    pr.flush();
                }

                //end socket for this job.
                s.close();
            }catch(IOException ioe){
                System.out.println("Error. JobCreator disconnected.");
            }
    }
}
