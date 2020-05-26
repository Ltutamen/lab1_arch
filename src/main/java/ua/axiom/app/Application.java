package ua.axiom.app;

import com.apptastic.rssreader.Item;
import com.apptastic.rssreader.RssReader;
import ua.axiom.app.fio.InputXmlReader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Application {
    private static final Pattern RESULT_REGEX = Pattern.compile("([A-Z])(\\w+)([\\s])*([-])([\\s])*([A-Z](\\w+))([\\s])*([\\d])(\\s)*:([\\d])(\\s)*");
    private final String xmlInputPath;

    public Application(String xmpInputPath) {
        this.xmlInputPath = xmpInputPath;
    }

    public void run() throws IOException {
        Set<String> paths = InputXmlReader.readURLs(xmlInputPath);
        RssReader reader = new RssReader();

        paths.stream()
                .map(s -> {
                    try {
                        return reader.read(s);
                    } catch (IOException ioe) {
                        return Stream.<Item>empty();
                    }
                })
                .map(itemStream -> itemStream.map(item -> {
                    String article = item.getDescription() + "" + item.getTitle() + "" + item.getGuid();
                    Matcher matcher = RESULT_REGEX.matcher(article);

                    List<String> results = new LinkedList<>();

                    while (matcher.find()) {
                        results.add(matcher.group());
                    }

                    return results;
                }))
                .flatMap(Stream::distinct)
                .forEach(System.out::println);
    }
}
