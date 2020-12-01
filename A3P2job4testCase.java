import org.pcap4j.packet.IllegalRawDataException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.Scanner;
//jobCreator creates job, connects to JobSeeker, assigns job, and receives the executed job report from JobSeeker.
//test case for job type 2 (A3P2Q1 Job 2)
public class A3P2job2testCase{ //client
    public static void main(String[] args) throws IOException, IllegalRawDataException {
        String jobType;
        String ip;
        String port;
        boolean isJobDone = false;
        //sample data for 3 test cases
        String[] portNums[3] = {"80", "53", "443"};
        int[] servers[3] = {4999, 4998, 4997};//allows 3 different sockets for 3 different JobSeekers
        int i = 0;// for JobSeekers
        //loop for multiple JobSeekers
        while (i < 3) {
            int j = 0;// for test cases
            while (j < 0) {
                try {
                    Socket s = new Socket("localhost", servers[i]);

                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    //Scanner sc = new Scanner(System.in); - unnecessary for test case
                    //Scanner sc2 = new Scanner(System.in); - unnecessary for test case
                    //Scanner sc3 = new Scanner(System.in); - unnecessary for test case
                    //get job from user. one of the 4 types
                    System.out.println("Enter job type:");
                    //job 1 = A3 Q1 Job 1
                    System.out.println("1 = Detect if an IP address/host name is online");
                    //job 2 = A3 Q1 Job 2
                    System.out.println("2 = Detect the status of a port at an IP address");
                    //job 3 = A3 Q2 Job 1
                    System.out.println("3 = Execute an ICMP flood attack against a port at an IP address");
                    System.out.println("Warning: Doing this against a valid IP address may count as an illegal DDoS attack");
                    System.out.println("Please use dummy IP 0.0.0.0");
                    //job 4 = A3 Q2 Job 2
                    System.out.println("4 = Execute a TCP flood attack against a port at an IP address");
                    System.out.println("Warning: Doing this against a valid IP address may count as an illegal DDoS attack");
                    System.out.println("Please use dummy IP 0.0.0.0");
                    jobType = "4";//job type is 4 for this test case
                    pr.println(jobType);
                    pr.flush();
                    //get ip address from user
                    System.out.println("Enter an IP Address/host name:");
                    ip = "0.0.0.0";//use test case sample data - dummy IP, attacking real IP might be a bad idea
                    pr.println(ip);
                    pr.flush();
                    //If job type is not 1, get port number from user
                    if (!jobType.equalsIgnoreCase("1")) {
                        System.out.println("Enter a port number:");
                        port = portNums[j];//use test case sample data
                        pr.println(port);
                        pr.flush();
                    }
                    //get report from JobSeeker
                    InputStreamReader in = new InputStreamReader(s.getInputStream());
                    BufferedReader bf = new BufferedReader(in);

                    String str = bf.readLine();
                    System.out.println("JobSeeker: " + str + "\n");

                    str = bf.readLine();
                    System.out.println("JobSeeker: " + str);

                    str = bf.readLine();
                    System.out.println(str);

                    int jobDoneNum = bf.read();
                    if (jobDoneNum == 1) {
                        isJobDone = true;
                    }
                } catch (IOException e) {
                    System.out.println("Connection failed. Abort");
                }
            }
            j++;//loop for 3 test cases
            //if job type is 1 or 2, there is only 1 JobSeeker, so exit connection while loop
            if (jobType.equalsIgnoreCase("1") || jobType.equalsIgnoreCase("2")) {
                i = 3;
            }
            //otherwise go to next JobSeeker for job types 3 and 4
            else {
                i++;
            }
        }
    }
}
