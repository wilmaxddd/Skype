/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.net.ntp.NTPUDPClient
 *  org.apache.commons.net.ntp.TimeInfo
 */
package codes.wilma24.Skype.headless.v1_0_R1;

import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInLogin;
import codes.wilma24.Skype.headless.v1_0_R1.Skype;
import java.net.InetAddress;
import java.util.Optional;
import java.util.Scanner;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class AppDelegate {
    public static final long VERSION = 3456L;
    public static long TIME_OFFSET = 0L;

    public static void main(String[] args) {
        try {
            String TIME_SERVER = "time-a.nist.gov";
            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
            TIME_OFFSET = returnTime - System.currentTimeMillis();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        System.setProperty("logging", "false");
        System.out.println("Skype-headless 3456");
        System.out.println("type \\help for available commands");
        Skype headless = Skype.getPlugin();
        while (true) {
            try {
                while (true) {
                    String password;
                    String skypeName;
                    System.out.print("> ");
                    String cmd = scanner.nextLine();
                    if (cmd == null || cmd.trim().length() == 0 || cmd.trim().equals("")) continue;
                    if (!(cmd = cmd.trim()).startsWith("\\")) {
                        System.out.println("'" + cmd + "' is not recognized as an internal command");
                        continue;
                    }
                    int index = cmd.indexOf(" ");
                    String command = index != -1 ? cmd.substring(0, index) : cmd;
                    String rawArgs = cmd.length() > command.length() ? cmd.substring(command.length() + 1) : "";
                    String[] stringArray = args = cmd.substring(command.length()).length() > 0 ? rawArgs.split(" ") : new String[]{};
                    if (command.equals("\\help")) {
                        System.out.println("args marked with * are required");
                        System.out.println("\\help");
                        System.out.println("\\hostname [hostname]");
                        System.out.println("\\register [skypeName*] [password*]");
                        System.out.println("\\login [skypeName*] [password*]");
                        System.out.println("\\msg [skypeName*] [msg*]");
                        System.out.println("\\displayName [displayName]");
                        System.out.println("\\clearDisplayName");
                        System.out.println("\\mood [mood]");
                        System.out.println("\\setProfilePic [url]");
                        System.out.println("\\clearMood");
                        System.out.println("\\logout");
                        System.out.println("\\exit");
                        continue;
                    }
                    if (command.equals("\\hostname")) {
                        if (args.length == 0) {
                            System.out.println(Skype.getPlugin().getHostname());
                            continue;
                        }
                        Skype.getPlugin().setHostname(args[0]);
                        System.out.println(Skype.getPlugin().getHostname());
                        if (!Skype.getPlugin().createHandle().isPresent()) continue;
                        System.out.println("connect success");
                        continue;
                    }
                    if (command.equals("\\register")) {
                        if (args.length != 2) {
                            System.out.println("\\register [skypeName*] [password*]");
                            continue;
                        }
                        skypeName = args[0];
                        password = args[1];
                        if (!headless.createHandle().isPresent()) {
                            System.out.println("connect fail!");
                        }
                        if (headless.register(skypeName, password)) {
                            System.out.println("register user success!");
                            continue;
                        }
                        System.out.println("reguster user fail!");
                        continue;
                    }
                    if (command.equals("\\login")) {
                        Optional<PacketPlayInLogin> loginPacket;
                        if (args.length != 2) {
                            System.out.println("\\login [skypeName*] [password*]");
                            continue;
                        }
                        skypeName = args[0];
                        password = args[1];
                        if (!headless.createHandle().isPresent()) {
                            System.out.println("connect fail!");
                        }
                        if ((loginPacket = headless.login(skypeName, password)).isPresent()) {
                            System.out.println("login user success!");
                            continue;
                        }
                        System.out.println("login user fail!");
                        continue;
                    }
                    if (command.equals("\\msg")) {
                        if (args.length != 2) {
                            System.out.println("\\msg [skypeName*] [msg*]");
                            continue;
                        }
                        skypeName = args[0];
                        String msg = args[1];
                        if (headless.sendMessage(skypeName, msg)) {
                            System.out.println("msg send success!");
                            continue;
                        }
                        System.out.println("msg send fail!");
                        continue;
                    }
                    if (command.equals("\\clearDisplayName")) {
                        if (headless.getLoggedInUser() == null) {
                            System.out.println("save user fail!");
                            continue;
                        }
                        headless.getLoggedInUser().setDisplayName(headless.getLoggedInUser().getSkypeName());
                        if (headless.saveUser()) {
                            System.out.println("save user success!");
                            continue;
                        }
                        System.out.println("save user fail!");
                        continue;
                    }
                    if (command.equals("\\displayName")) {
                        if (args.length != 1) {
                            System.out.println("\\displayName [displayName]");
                            System.out.println(headless.getLoggedInUser().getDisplayName());
                            continue;
                        }
                        String displayName = args[0];
                        if (headless.getLoggedInUser() == null) {
                            System.out.println("save user fail!");
                            continue;
                        }
                        headless.getLoggedInUser().setDisplayName(displayName);
                        if (headless.saveUser()) {
                            System.out.println("save user success!");
                            continue;
                        }
                        System.out.println("save user fail!");
                        continue;
                    }
                    if (command.equals("\\clearMood")) {
                        if (headless.getLoggedInUser() == null) {
                            System.out.println("save user fail!");
                            continue;
                        }
                        headless.getLoggedInUser().setMood(null);
                        if (headless.saveUser()) {
                            System.out.println("save user success!");
                            continue;
                        }
                        System.out.println("save user fail!");
                        continue;
                    }
                    if (command.equals("\\mood")) {
                        if (args.length != 1) {
                            System.out.println("\\mood [mood]");
                            System.out.println(headless.getLoggedInUser().getMood());
                            continue;
                        }
                        String mood = args[0];
                        if (headless.getLoggedInUser() == null) {
                            System.out.println("save user fail!");
                            continue;
                        }
                        headless.getLoggedInUser().setMood(mood);
                        if (headless.saveUser()) {
                            System.out.println("save user success!");
                            continue;
                        }
                        System.out.println("save user fail!");
                        continue;
                    }
                    if (command.equals("\\setProfilePic")) {
                        if (args.length != 1) {
                            System.out.println("\\setProfilePic [url]");
                            System.out.println(headless.getLoggedInUser().getImageIconUrl());
                            continue;
                        }
                        String url = args[0];
                        if (headless.getLoggedInUser() == null) {
                            System.out.println("save user fail!");
                            continue;
                        }
                        headless.getLoggedInUser().setImageIconUrl(url);
                        if (headless.saveUser()) {
                            System.out.println("save user success!");
                            continue;
                        }
                        System.out.println("save user fail!");
                        continue;
                    }
                    if (command.equals("\\logout")) {
                        if (headless.getLoggedInUser() != null) {
                            headless.logout();
                            System.out.println("logout user success!");
                            continue;
                        }
                        System.out.println("logout user fail!");
                        continue;
                    }
                    if (command.equals("\\exit")) {
                        System.exit(0);
                        continue;
                    }
                    System.out.println("'" + cmd + "' is not recognized as an internal command");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

