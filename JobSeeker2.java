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
                        //assign part of create-assign-execute-report process for each job
                        switch (jobString) {
                            case "1":
                                //assign 1st job to JobSeeker, Q1 Job 1
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
                                //assign 2nd job to JobSeeker, Q1 Job 2
                                //detect status of port jobPort at IP address jobIP
                                String pstat = portStatus(jobIp, jobPort);
                                //report part of create-assign-execute-report process for A3P2Q1 Job 2
                                if(pstat.equalsIgnoreCase("open")){
                                    System.out.println("Port Open");
                                    pr.println("Port " + jobPort + " at IP address " + jobIp + " is open.");
                                    pr.flush();
                                } else if (pstat.equalsIgnoreCase("closed")){
                                    System.out.println("Port Closed");
                                    pr.println("Port " + jobPort + " at IP address " + jobIp + " is closed.");
                                    pr.flush();
                                } else if(pstat.equalsIgnoreCase("filtered")){
                                    System.out.println("Port Filtered");
                                    pr.println("Port " + jobPort + " at IP address " + jobIp + " is filtered.");
                                    pr.flush();
                                } else {
                                    System.out.println("Status Unknown");
                                    pr.println("The status of port " + jobPort + " at IP address " + jobIp + " is unknown.");
                                    pr.flush();
                                }
                                break;
                            case "3":
                                //assign 3rd job to JobSeeker, Q2 Job 2
                                thirdJob(jobIp, jobPort);
                                break;
                            case "4":
                                //assign 4th job to JobSeeker, Q2 Job 3
                                fourthJob(jobIp, jobPort);
                                break;
                            default:
                                pr.println("Error. Job not assigned.");
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
    //job 1 = check if given IP address is online or not
    //execute part of create-assign-execute-report process for A3P2Q1 Job 1
    public static boolean isOnline(String jobIp) throws IOException {
        return InetAddress.getByName(jobIp).isReachable(5000);
    }
    //job 2 = check status of given port number at given IP address
    //execute part of create-assign-execute-report process for A3P2Q1 Job 2
    public static String portStatus(String jobIp, String jobPort) {
        int port = Integer.parseInt(jobPort);
        Socket sckt = null;//create socket variable
        String status = "unknown";
        try {
            //connection to port at host established, new socket
            sckt = new Socket(jobIp, port);
        } catch (IOException e) {
            if(e.getMessage().equals("Connection refused")){
                //returns port status as closed
                status = "closed";
            }
            if(e instanceof SocketTimeoutException){
                //connection timed out, port blocked by firewall
                //returns port status as filtered
                status = "filtered";
            }
        } finally {
            //checks if port is not filtered
            if (sckt != null) {
                //returns port status as open
                if (sckt.isConnected()){
                    status = "open";
                }
                try {
                    //closes connection
                    sckt.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }
    //job 3 = code to do third job
    //execute part of create-assign-execute-report process for A3P2Q2 Job 2
    public static void thirdJob(String jobIp, String jobPort) {
        
    }
    //job 4 = code to do fourth job
    //execute part of create-assign-execute-report process for A3P2Q2 Job 3
    public static void fourthJob(String jobIp, String jobPort) {
        
    }
}