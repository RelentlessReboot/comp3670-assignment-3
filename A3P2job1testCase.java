import java.net.*;
import java.io.*;
import java.util.Scanner;
//creates job, connects to JobSeeker, assigns job, and receives the executed job report from JobSeeker.
//A3P2Q3, test case for job 1 (A3P2Q1 Job 1)
public class A3P2job1testCase{ //client test case for job 1
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
            System.out.println("1 = Detect if an IP address/host name is online");
            System.out.println("2 = Detect the status of a port at an IP address");
            System.out.println("3 = Execute an ICMP flood attack against a port at an IP address");
            System.out.println("Warning: Doing this against a valid IP address may count as an illegal DDoS attack");
            System.out.println("4 = Execute a TCP flood attack against a port at an IP address");
            System.out.println("Warning: Doing this against a valid IP address may count as an illegal DDoS attack");
            jobType = "1";//job for this test case is job 1
            pr.println(jobType);
            pr.flush();
            //get ip address from user, test 3 cases
            String[] hNames[3] = {"www.google.com", "www.facebook.com", "www.twitter.com"};
            //int i for iteration of 3 test cases in while-loop
            int i = 3;
            while(i > 0){
                System.out.println("Enter an IP Address/host name:");
                ip = hNames[i - 1];
                pr.println(ip);
                pr.flush();
                //get report from JobSeeker
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
