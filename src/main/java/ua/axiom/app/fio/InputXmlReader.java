package ua.axiom.app.fio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputXmlReader {
    private static final Pattern pattern = Pattern.compile("<link>([\\d\\w:/.\\n\\&?=-])+<\\/link>");

    public static Set<String> readURLs(String path) throws IOException {

        return Files.readAllLines(Paths.get(path))
                .stream()
                .map(s -> {
            Matcher matcher = pattern.matcher(s);
            Set<String> sset =  new HashSet<>();
            while (matcher.find()) {
                sset.add(matcher.group());
            }
            return sset;
        })
                .flatMap(Set::stream).map(s -> s.substring(6, s.length() - 7))
                .collect(Collectors.toSet());

    }
}
