import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;


public class Dictionary {

    private final Logger logger= LogManager.getLogger("Dictionary");
    private static final int LENGTH = 10;
    private Multimap<Character,String> sortedDictByLetter;
    private Multimap<Integer,String> sortedDictBySize;
    private HashSet<String> input;

    private List<String> words;

    Dictionary()  {
        sortedDictByLetter = HashMultimap.create();
        sortedDictBySize = HashMultimap.create();
    }

    public void initialize() throws IOException {
        ClassLoader cl = Dictionary.class.getClassLoader();
        URL url = cl.getResource("3of6all.txt");
        if(url==null) {
            logger.error("Error reading dictionary");
            throw new RuntimeException();
        }
        words =Resources.readLines(url, Charsets.UTF_8);
        Supplier<Stream<String>> stream = words::stream;
        Stream<String> strings = words.stream();
        stream.get().forEach(w-> {
            if(isValidWord(w))
                sortedDictByLetter.put(w.toLowerCase().charAt(0),w.toLowerCase());
        });
        stream.get().forEach(w-> {
            if(isValidWord(w))
                sortedDictBySize.put(w.length(),w.toLowerCase());
        });

    }

    public String getWord() {
        Random rm = new Random();
        Collection<String> iterable = sortedDictBySize.get(LENGTH);
        logger.info(iterable.size());
        int ran = rm.nextInt(iterable.size()-1);
        return Iterables.get(iterable,ran);
    }

    public boolean wordExists(String word) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Collection<String> collByLength = sortedDictBySize.get(word.length());
        Collection<String> collByLetter = sortedDictByLetter.get(word.charAt(0));
        Set<String> col1 = new HashSet<>(collByLength);
        Set<String> col2 = new HashSet<>(collByLetter);
        Set<String> col3 =  Sets.intersection(col1,col2);
        stopwatch.stop(); // optional
        long millis = stopwatch.elapsed(MILLISECONDS);
        logger.info(millis);
        return col3.contains(word);
    }

    public static String shuffle(String word) {
        String[] chars = word.split("");
        List<String> ash = Arrays.asList(chars);
        Collections.shuffle(ash);
        return ash.stream().collect(Collectors.joining());
    }

    private boolean isValidWord(String word) {
        return !word.contains(" ");
    }

}
