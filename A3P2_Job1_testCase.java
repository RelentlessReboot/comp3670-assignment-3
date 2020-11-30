import java.net.*;
import java.io.*;
import java.util.Scanner;
//jobCreator creates job, connects to JobSeeker, assigns job, and receives the executed job report from JobSeeker.
//create job with type 1 (A3P2Q1 Job 1), 2 (A3P2Q1 Job 2), 3 (A3P2Q2 Job 2), or 4 (A3P2Q2 Job 3)
public class A3P2Job1testCase{ //client test case for job 1
    public static void main(String[] args) throws IOException{
        String jobType;
        String ip;
        String port;
        boolean isJobDone = false;
        try{
            Socket s = new Socket("localhost", 4999);

            PrintWriter pr = new PrintWriter(s.getOutputStream());
            Scanner sc = new Scanner(System.in);
            Scanner sc2 = new Scanner(System.in);
            Scanner sc3 = new Scanner(System.in);
            //get job from user. one of the 4 types
            System.out.println("Enter job type:");
            //job 1 = A3 Q1 Job 1
            System.out.println("1 = Detect if an IP address/host name is online");
            //job 2 = A3 Q1 Job 2
            System.out.println("2 = Detect the status of a port at an IP address");
            //job 3 = A3 Q2 Job 2
            System.out.println("3 = Execute a TCP flood attack against a port at an IP address");
            //job 4 = A3 Q2 Job 3
            System.out.println("4 = Execute a UDP flood attack against a port at an IP address");
            jobType = "1";
            pr.println(jobType);
            pr.flush();
            //get ip address from user, test 3 cases
            String[] hNames[3] = {"www.google.com", "www.facebook.com", "localhost"};
            //int i for iteration through test cases
            int i = 3;
            while(i > 0){
                System.out.println("Enter an IP Address/host name:");
                ip = hNames[i - 1];
                pr.println(ip);
                pr.flush();
                //get response
                InputStreamReader in = new InputStreamReader(s.getInputStream());
                BufferedReader bf = new BufferedReader(in);

                String str = bf.readLine();
                System.out.println("JobSeeker: "+ str +"\n");

                str = bf.readLine();
                System.out.println("JobSeeker: "+ str);

                str = bf.readLine();
                System.out.println(str);

                int jobDoneNum = bf.read();
                if (jobDoneNum == 1) isJobDone = true;
                i--;
            }
        }catch(IOException e){
            System.out.println("Connection failed. Abort");
        }
    }
}
