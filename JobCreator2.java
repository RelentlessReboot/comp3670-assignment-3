import java.net.*;
import java.io.*;
import java.util.Scanner;
//jobCreator creates job, connects to jobSeeker, gives job, waits for job to finish and come back.
//create job with type, computational task    i.e multiply, 2, 5. addition,subtraction,division,multiplication
public class JobCreator2{ //client
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
        System.out.println("3 = Execute a TCP flood attack against a port at an IP address");
        System.out.println("4 = Execute a UDP flood attack against a port at an IP address");
        jobType = sc.nextLine();
        pr.println(jobType);
        pr.flush();
        //get ip address from user
        System.out.println("Enter an IP Address/host name:");
        ip = sc2.nextLine();
        pr.println(ip);
        pr.flush();
        //get port from user
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

        int jobDoneNum = 0;
        jobDoneNum = bf.read();
        if (jobDoneNum == 1)
            isJobDone = true;
        }catch(IOException e){
            System.out.println("Connection failed. Abort");
        }
    }
}
