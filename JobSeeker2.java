import java.net.*;
import java.io.*;
//job seeker waits for jobSeeker to connect, accepts job request, does job, reports back
public class JobSeeker2{ //server
    public static void main(String[] args) throws UnknownHostException, IOException{
        String jobString;
        String jobIp;
        String jobPort;
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
                /*
                //get the 2 variables from creator
                jobVar1 = Integer.parseInt(bf.readLine());
                jobVar2 = Integer.parseInt(bf.readLine());
                System.out.println("var1: " + jobVar1);
                System.out.println("var2: " + jobVar2);
                */
                //get ip from creator
                jobIp = bf.readLine();
                System.out.println("IP: " + jobIp);
               
                //get port number from creator
                jobPort = bf.readLine();
                System.out.println("Port: " + jobPort);

                PrintWriter pr = new PrintWriter(s.getOutputStream());
                pr.println("RECEIVED. Job Type: " + jobString + "/n" + "IP: " + jobIp + ", Port: " + jobPort);
                pr.flush();
                //do the job based on job type
                switch (jobString) {
                    case "1":
                        firstJob(jobIp);
                        break;
                    case "2":
                        secondJob(jobIp, jobPort);
                        break;
                    case "3":
                        thirdJob(jobIp, jobPort);
                        break;
                    case "4":
                        fourthJob(jobIp, jobPort);
                        break;
                    default:
                        pr.println("Error. Job not executed.");
                        pr.flush();
                        break;
                }
                if (badJob == false) {
                    System.out.println("Job finished.");
                    pr.println("Job Complete.");
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
            } catch(IOException ioe){
                System.out.println("Error. JobCreator disconnected.");
            }
    }
    //code to do first job
    public static void firstJob(String jobIp) {
        if(isOnline(jobIp) == true) {
            System.out.println("IP online");
            pr.println("This IP Address is online");
            pr.flush();
        } else {
            System.out.println("IP offline");
            pr.println("This IP Address is offline");
            pr.flush();
        }
    }

    public static boolean isOnline(String jobIp) {
        try {
            InetAddress.getByName(jobIp).isReachable(5000);
            return true;
        } catch (UnknownHostException e){
            return false;
        } catch (IOException e){
            return false;
        }
    }
    //code to do second job
    public static void secondJob(String jobIp, String jobPort) {
        
    }
    //code to do third job
    public static void thirdJob(String jobIp, String jobPort) {
        
    }
    //code to do fourth job
    public static void fourthJob(String jobIp, String jobPort) {
        
    }
}