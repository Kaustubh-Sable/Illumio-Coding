package edu.stonybrook;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Illumio {
    public static void main(String[] args) {
        String singleIPRegex = "[0-9]";
        String rangeRegex = singleIPRegex + "-" + singleIPRegex;
        String input = "";
        if (input.matches(rangeRegex)){
            //do your stuff
            System.out.println("matched");
        }
    }
}

class PortLevelNode {

    int value = -1;
    List<Pair<Integer, Integer>> rules = new ArrayList<>();

    private boolean isMatch(int start, int end) {
        for (Pair<Integer, Integer> rule: rules) {
            if (rule.getKey() <= start && end <= rule.getValue()) {
                return true;
            }
        }
        
        return false;
    }
}

class Util {
    static int convertIpToInteger(String ip) {
        int base = 1;
        int pow = 256;
        int ipInt = 0;
        String components[] = ip.split("\\.");
        for (int i = 3; i >= 0; i--) {
            ipInt += Integer.parseInt(components[i]) * base;
            base *= pow;
        }
        
        return ipInt;
    }
    
    static Pair<Integer, Integer> getIpRange(String ipParam) {
        if (ipParam.contains("-")) {
            String ip1 = ipParam.split("-")[0];
            String ip2 = ipParam.split("-")[0];
            return new Pair<>(convertIpToInteger(ip1), convertIpToInteger(ip2));
        } else {
            int ipInt = convertIpToInteger(ipParam);
            return new Pair<>(ipInt, ipInt);
        }
    }    
}

class AbstractDataStore {
    private Map<String, Map<Integer, PortLevelNode>> protocolTree = new HashMap<>(); 

    public void add(String direction, String protocol, int port, String ipAddress) {
        String key = buildKey(direction, protocol);
        Map<Integer, PortLevelNode> portNodeMap = protocolTree.getOrDefault(key, new HashMap<>());
        PortLevelNode node = null;
        if (portNodeMap.containsKey(port)) {
            node = portNodeMap.get(port);
        } else {
            node = new PortLevelNode();
            node.rules = new ArrayList<>();
            node.value = port;
        }
        node.rules.add(Util.getIpRange(ipAddress));
        portNodeMap.put(port, node);
        protocolTree.put(key, portNodeMap);
    }

    public boolean isMatch(String direction, String protocol, int port, String ipAddress) {
        return false;
    }
    
    private String buildKey(String direction, String protocol) {
        return direction + protocol;
    }
}

class Firewall {
    private String fileName;
    private AbstractDataStore dataStore;

    public Firewall(String fileName) {
        this.fileName = fileName;
        dataStore = new AbstractDataStore();
    }

    public boolean accept_packet(String direction, String protocol, int port, String ipAddress) {
        return dataStore.isMatch(direction, protocol, port, ipAddress);
    }

    private void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = null;
            do {
                try {
                    line = br.readLine();
                    String []cols = line.split(",");
                    storeRule(cols);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while(line != null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void storeRule(String cols[]) {
        dataStore.add(cols[0], cols[1], Integer.parseInt(cols[2]), cols[3]);
    }
}
