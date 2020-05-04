package edu.sergio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try {
            Files.lines(Paths.get("resources/testlog.txt"))
                    .filter(Predicate.isEqual("").negate())
                    .map(Main::parseLine)
                    .collect(Collectors.toMap(Record::getIp, Record::getBytes, Integer::sum))
                    .entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .limit(10)
                    .forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Record parseLine(String line) {
        String[] data = line.split(" ");
        return new Record(data[0], Integer.parseInt(data[9]));
    }

}

class Record {
    String ip;
    int bytes;

    public Record(String ip, int bytes) {
        this.ip = ip;
        this.bytes = bytes;
    }

    public String getIp() {
        return ip;
    }

    public int getBytes() {
        return bytes;
    }

}
