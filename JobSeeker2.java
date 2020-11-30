import java.net.*;
import java.io.*;
//job seeker waits for jobSeeker to connect, accepts job request, does job, reports back
public class JobSeeker2{ //server
    public static void main(String[] args) throws IOException{
        String jobString;
        String jobIp;
        String jobPort;
        boolean badJob = false;
        boolean jobDone = false;
        ServerSocket ss = new ServerSocket(4999);
        Socket s;
            try {
                s = ss.accept();
                //server can only connect to one client at a time
                System.out.println("JobCreator connected. Waiting for Job...");
                InputStreamReader in = new InputStreamReader(s.getInputStream());
                BufferedReader bf = new BufferedReader(in);

                //get the job type from the creator
                jobString = bf.readLine();
                System.out.println("jobCreator: " + jobString);
                //get IP address from JobCreator
                jobIp = bf.readLine();
                System.out.println("IP: " + jobIp);

                //get port number from JobCreator
                jobPort = bf.readLine();
                System.out.println("Port: " + jobPort);

                try (PrintWriter pr = new PrintWriter(s.getOutputStream())) {
                    pr.println("RECEIVED. Job Type: " + jobString + "/n" + "IP: " + jobIp + ", Port: " + jobPort);
                    pr.flush();
                    //determine if job type is valid
                    switch (jobString) {
                        case "1":
                        case "2":
                        case "3":
                        case "4":
                            break;
                        default:
                            badJob = true;
                            pr.println("Error. Invalid job type.");
                            pr.flush();
                            break;
                    }
                    if (badJob) {
                        System.out.println("Job incomplete. Error.");
                        pr.println("Error. Unknown job type.");
                        pr.flush();
                        pr.println(0);
                        pr.flush();
                    } else {
                        //do the job based on job type
                        switch (jobString) {
                            case "1":
                                //code for first job, Q1 Job 1
                                if (isOnline(jobIp)) {
                                    System.out.println("IP online");
                                    pr.println("This IP Address/hostname is online.");
                                    pr.flush();
                                } else {
                                    System.out.println("IP offline");
                                    pr.println("This IP Address/hostname is offline.");
                                    pr.flush();
                                }
                                break;
                            case "2":
                                //code for 2nd job, Q1 Job 2
                                secondJob(jobIp, jobPort);
                                break;
                            case "3":
                                //code for 3rd job, Q2 Job 2
                                thirdJob(jobIp, jobPort);
                                break;
                            case "4":
                                //code for 4th job, Q2 Job 3
                                fourthJob(jobIp, jobPort);
                                break;
                            default:
                                pr.println("Error. Job not executed.");
                                pr.flush();
                                break;
                        }

                        System.out.println("Job finished.");
                        pr.println("Job Complete.");
                        pr.flush();
                        jobDone = true;
                        pr.println(1);
                        pr.flush();
                    }
                }
                //end socket for this job.
                s.close();
            } catch(IOException ioe){
                System.out.println("Error. JobCreator disconnected.");
            }
    }
    //check if given IP address is online or not
    public static boolean isOnline(String jobIp) {
        try {
            return InetAddress.getByName(jobIp).isReachable(5000);
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