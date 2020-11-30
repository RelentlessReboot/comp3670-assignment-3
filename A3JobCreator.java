
import org.pcap4j.packet.IllegalRawDataException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.Scanner;
//jobCreator creates job, connects to JobSeeker, assigns job, and receives the executed job report from JobSeeker.
//create job with type 1 (A3P2Q1 Job 1), 2 (A3P2Q1 Job 2), 3 (A3P2Q2 Job 1), or 4 (A3P2Q2 Job 2)
public class A3JobCreator{ //client
    public static void main(String[] args) throws IOException, IllegalRawDataException {
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
        //job 3 = A3 Q2 Job 1
        System.out.println("3 = Execute an ICMP flood attack against a port at an IP address");
        System.out.println("Warning: Doing this against a valid IP address may count as an illegal DDoS attack");
        System.out.println("Please use dummy IP 0.0.0.0");
        //job 4 = A3 Q2 Job 2
        System.out.println("4 = Execute a TCP flood attack against a port at an IP address");
        System.out.println("Warning: Doing this against a valid IP address may count as an illegal DDoS attack");
        System.out.println("Please use dummy IP 0.0.0.0");
        jobType = sc.nextLine();
        pr.println(jobType);
        pr.flush();
        //get ip address from user
        System.out.println("Enter an IP Address/host name:");
        ip = sc2.nextLine();
        pr.println(ip);
        pr.flush();
        //If job type is not Job 1, get port number from user
        if (!jobType.equalsIgnoreCase("1")){
            System.out.println("Enter a port number:");
            port = sc3.nextLine();
            pr.println(port);
            pr.flush();
        }
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
        if (jobDoneNum == 1)
            isJobDone = true;
        }catch(IOException e){
            System.out.println("Connection failed. Abort");
        }
    }
}
