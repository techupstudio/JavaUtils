package com.techupstudio.otc_chingy.mychurch.core.utils.general;

import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.Action;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.Filterer;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.IndexedAction;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.IndexedFilterer;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.IndexedMapper;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.Joiner;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.MapAction;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.MapFilterer;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.Mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Funcs {

    private static Printer printer = new Printer();
    private static Printer printerln = new Printer("", " ", "\n");

    private Funcs() {
    }

    public static Scanner Scan() {
        return new Scanner(System.in);
    }

    //TODO: create pretty printer class

    public static <T> void print(T... objects) {
        printer.print(objects);
    }

    //######    Printer    ######
    //######    Printer    ######
    //######    Printer    ######
    //######    Printer    ######
    //######    Printer    ######

    public static <T> void println(T... objects) {
        printerln.print(objects);
    }

    public static <T> void print(Object object) {
        printer.print(object);
    }

    public static <T> void println(Object object) {
        printerln.print(object);
    }

    public static Integer inputInt(String show) {
        switch (show) {
            case "":
                break;
            case "default":
                print("Enter a value : ");
                break;
            default:
                print(show);
                break;
        }
        int value;
        Scanner input = Scan();
        if (!input.hasNextInt()) {
            print("( invalid input )  enter number(s) : ");
            return inputInt("");
        }
        value = input.nextInt();
        return value;
    }

    //##### End of Printers #####
    //##### End of Printers #####
    //##### End of Printers #####
    //##### End of Printers #####
    //##### End of Printers #####

    public static String inputStr(String show) {
        switch (show) {
            case "":
                break;
            case "default":
                print("Enter a value : ");
                break;
            default:
                print(show);
                break;
        }
        String value;
        Scanner input = Scan();
        value = input.next();
        return value;
    }

    public static String getStr(String show) {
        switch (show) {
            case "":
                break;
            case "default":
                print("Enter a value : ");
                break;
            default:
                print(show);
                break;
        }
        String value;
        Scanner input = Scan();
        value = input.nextLine();
        return value;
    }

    public static Character inputChar(String show) {
        switch (show) {
            case "":
                break;
            case "default":
                print("Enter a value : ");
                break;
            default:
                print(show);
                break;
        }
        String value;
        Scanner input = Scan();
        value = input.next();
        return value.charAt(0);
    }

    public static Boolean inputBool(String show) {
        switch (show) {
            case "":
                break;
            case "default":
                print("Enter a value : ");
                break;
            default:
                print(show);
                break;
        }
        String value;
        Scanner input = Scan();
        value = input.next().toLowerCase();
        if (!(value.equals("true") || value.equals("false") || value.equals("t") || value.equals("f"))) {
            return inputBool("( invalid input )  enter boolean : ");
        }
        return value.equals("true") || value.equals("t");
    }

    public static Float inputFloat(String show) {
        switch (show) {
            case "":
                break;
            case "default":
                print("Enter a value : ");
                break;
            default:
                print(show);
                break;
        }
        float value;
        Scanner input = Scan();
        if (!input.hasNextFloat()) {
            return inputFloat("( invalid input )  enter number(s) : ");
        }
        value = input.nextFloat();
        return value;
    }

    public static Double inputDouble(String show) {
        switch (show) {
            case "":
                break;
            case "default":
                print("Enter a value : ");
                break;
            default:
                print(show);
                break;
        }
        double value;
        Scanner input = Scan();
        if (!input.hasNextDouble()) {
            return inputDouble("( invalid input )  enter number(s) : ");
        }
        value = input.nextDouble();
        return value;
    }

    //######  Input Functions  ######
    //######  Input Functions  ######
    //######  Input Functions  ######
    //######  Input Functions  ######
    //######  Input Functions  ######

    public static <T> Object dressNumber(T obj) {
        if (obj.toString().contains(".0")) {
            return toInteger(obj);
        }
        return obj;
    }

    public static <T> String toString(T obj) {
        return obj.toString();
    }

    public static <T> Character toCharacter(T obj) {
        return (char) obj.hashCode();
    }

    public static <T> Integer toInteger(T obj) {
        if (obj instanceof Boolean) {
            if (obj.toString().equals("true")) {
                return 1;
            }
            return 0;
        } else if (obj instanceof String) {
            return Integer.valueOf(obj.toString());
        } else {
            return toRound(toDouble(obj));
        }
    }

    public static <T> Double toDouble(T obj) {
        return Double.valueOf(obj.toString());
    }

    public static Float toFloat(Object obj) {
        return Float.valueOf(obj.toString());
    }

    public static <T> Boolean toBoolean(T obj) {
        if (obj instanceof Integer) {
            return !obj.toString().equals("0");
        }
        return Boolean.valueOf(obj.toString());
    }

    //##### End of Input Functions #####
    //##### End of Input Functions #####
    //##### End of Input Functions #####
    //##### End of Input Functions #####
    //##### End of Input Functions #####


    //#### Object Convertor Functions #####
    //#### Object Convertor Functions #####
    //#### Object Convertor Functions #####
    //#### Object Convertor Functions #####
    //#### Object Convertor Functions #####

    public static <T> Integer toRound(T obj) {
        return (int) Math.round(toDouble(obj));
    }

    public static <T> int[] toInteger(T... obj) {
        int[] retArr = new int[len(obj)];
        for (int i = 0; i < len(obj); i++) {
            retArr[i] = toInteger(obj[i]);
        }
        return retArr;
    }

    public static <T> int[] toRound(T... obj) {
        int[] retArr = new int[len(obj)];
        for (int i = 0; i < len(obj); i++) {
            retArr[i] = toRound(obj[i]);
        }
        return retArr;
    }

    public static <T> double[] toDouble(T... obj) {
        double[] retArr = new double[len(obj)];
        for (int i = 0; i < len(obj); i++) {
            retArr[i] = toDouble(obj[i]);
        }
        return retArr;
    }

    public static <T> float[] toFloat(T... obj) {
        float[] retArr = new float[len(obj)];
        for (int i = 0; i < len(obj); i++) {
            retArr[i] = toFloat(obj[i]);
        }
        return retArr;
    }

    public static <T> boolean[] toBoolean(T... obj) {
        boolean[] retArr = new boolean[len(obj)];
        for (int i = 0; i < len(obj); i++) {
            retArr[i] = toBoolean(obj[i]);
        }
        return retArr;
    }

    public static <T> char[] toCharacter(T... obj) {
        char[] retArr = new char[len(obj)];
        for (int i = 0; i < len(obj); i++) {
            retArr[i] = toCharacter(obj[i]);
        }
        return retArr;
    }

    public static <T> String[] toString(T... obj) {
        String[] retArr = new String[len(obj)];
        for (int i = 0; i < len(obj); i++) {
            retArr[i] = toString(obj[i]);
        }
        return retArr;
    }

    public static <T> List<Integer> toInteger(Collection<T> collection) {
        List<Integer> retArr = new ArrayList<>();
        forEach(collection, (o) -> retArr.add(toInteger(o)));
        return retArr;
    }

    public static <T> List<Integer> toRound(Collection<T> collection) {
        List<Integer> retArr = new ArrayList<>();
        forEach(collection, (o) -> retArr.add(toRound(o)));
        return retArr;
    }

    public static <T> List<Double> toDouble(Collection<T> collection) {
        List<Double> retArr = new ArrayList<>();
        forEach(collection, (o) -> retArr.add(toDouble(o)));
        return retArr;
    }

    public static <T> List<Float> toFloat(Collection<T> collection) {
        List<Float> retArr = new ArrayList<>();
        forEach(collection, (o) -> retArr.add(toFloat(o)));
        return retArr;
    }

    public static <T> List<Boolean> toBoolean(Collection<T> collection) {
        List<Boolean> retArr = new ArrayList<>();
        forEach(collection, (o) -> retArr.add(toBoolean(o)));
        return retArr;
    }

    public static <T> List<Character> toCharacter(Collection<T> collection) {
        List<Character> retArr = new ArrayList<>();
        forEach(collection, (o) -> retArr.add(toCharacter(o)));
        return retArr;
    }

    public static <T> List<String> toString(Collection<T> collection) {
        List<String> retArr = new ArrayList<>();
        forEach(collection, (o) -> retArr.add(toString(o)));
        return retArr;
    }

    public static Object power(Object number, Object exponent) {
        Object obj = Math.pow(toDouble(number), toDouble(exponent));
        if (obj.toString().contains(".0")) {
            return toInteger(obj);
        }
        return obj;
    }

    public static <T> int len(T obj) {
        return obj.toString().length();
    }

    //##### End of Converters #####
    //##### End of Converters #####
    //##### End of Converters #####
    //##### End of Converters #####
    //##### End of Converters #####


    //##### Math Functions #####
    //##### Math Functions #####
    //##### Math Functions #####
    //##### Math Functions #####
    //##### Math Functions #####

    @SafeVarargs
    public static <T> int len(T... obj) {
        return obj.length;
    }

    public static <T> int len(Collection<T> obj) {
        return obj.size();
    }

    public static <X, Y> int len(Map<X, Y> obj) {
        return obj.size();
    }

    public static Object[] getFIB(double num_a, double num_b, double end) {
        double sum = num_a + num_b;
        double first_num = num_b;
        double next_num = sum;
        List<Object> ret_list = new ArrayList<>();
        ret_list.add(num_a);
        ret_list.add(num_b);
        while (sum < end) {
            ret_list.add(sum);
            sum = first_num + next_num;
            first_num = next_num;
            next_num = sum;
        }
        return ret_list.toArray();
    }

    public static Object getHCF(double num_a, double num_b) {
        if (num_b != 0) {
            return getHCF(num_b, num_a % num_b);
        }
        Object obj = num_a;
        return dressNumber(num_a);
    }

    public static Object getLCM(double num_a, double num_b) {
        Object ret_value;
        if (num_a == num_b) {
            ret_value = num_a;
        } else if ((num_a % num_b == 0) || (num_b % num_a == 0)) {
            ret_value = Math.max(num_a, num_b);
        } else {
            ret_value = (num_a * num_b) / toDouble(getHCF(num_a, num_b));
        }
        return dressNumber(ret_value);
    }

    public static Object getHCF(double... numbers) {
        double hcf = toDouble(getHCF(numbers[0], numbers[1]));
        for (int i : range(2, numbers.length)) {
            hcf = toDouble(getHCF(hcf, numbers[i]));
        }
        return dressNumber(hcf);
    }

    public static Object getLCM(double... numbers) {
        double lcm = toDouble(getHCF(numbers[0], numbers[1]));
        for (int i : range(2, numbers.length)) {
            lcm = toDouble(getLCM(lcm, numbers[i]));
        }
        return dressNumber(lcm);
    }

    public static Object getHCF(Double[] numbers) {
        double hcf = toDouble(getHCF(numbers[0], numbers[1]));
        for (int i : range(2, numbers.length)) {
            hcf = toDouble(getHCF(hcf, numbers[i]));
        }
        return dressNumber(hcf);
    }

    public static Object getLCM(Double[] numbers) {
        double lcm = toDouble(getHCF(numbers[0], numbers[1]));
        for (int i : range(2, numbers.length)) {
            lcm = toDouble(getLCM(lcm, numbers[i]));
        }
        return dressNumber(lcm);
    }

    public static double random() {
        return Math.random();
    }

    public static int randint() {
        int random1 = randint((int) (random() * 100) + 1, (int) (random() * 1000) + 1);
        int random2 = randint((int) (random() * 100) + 1, (int) (random() * 10000) + 1);
        return randint((int) (random() * random1) + 1, (int) (random() * random2) + 1);
    }

    public static int randint(int max) {
        return randint(0, max);
    }

    public static int randint(int min, int max) {
        return (int) (random() * (max - min + 1)) + min;
    }

    public static Integer[] range(int max) {
        if (max < 0) {
            return new Integer[]{};
        }
        Integer[] ret_range = new Integer[max];
        for (int i = 0; i < max; i++) {
            ret_range[i] = i;
        }
        return ret_range;
    }

    public static Integer[] range(int min, int max) {
        if (min < 0 || max < 0) {
            return new Integer[]{};
        }
        int size = max - min;
        Integer[] ret_range = new Integer[size];
        for (int i = 0; i < size; i++) {
            ret_range[i] = i + min;
        }
        return ret_range;
    }

    public static Integer[] range(int min, int max, int step) {
        if (min < 0 || max < 0 || step < 0) {
            return new Integer[]{};
        }
        List<Integer> ret_list = new ArrayList<>();
        int i = 0;
        while ((i + min + (i * (step - 1))) < max) {
            ret_list.add(i + min + (i * (step - 1)));
            i++;
        }

        Integer[] ret_range = new Integer[len(ret_list)];
        return ret_list.toArray(ret_range);
    }

    public static Integer[] randrange(int size) {
        Integer[] ret_range = new Integer[size];
        for (int i = 0; i < size; i++) {
            ret_range[i] = randint();
        }
        return ret_range;
    }

    public static Integer[] randrange(int max, int size) {
        Integer[] ret_range = new Integer[size];
        for (int i = 0; i < size; i++) {
            ret_range[i] = randint(max);
        }
        return ret_range;
    }

    public static Integer[] randrange(int min, int max, int size) {
        Integer[] ret_range = new Integer[size];
        for (int i = 0; i < size; i++) {
            ret_range[i] = randint(min, max);
        }
        return ret_range;
    }

    public static <T> List<T> randsample(T[] list, int sample_size) {
        int size = len(list);
        if (sample_size < size) {
            Random random = new Random();
            List<T> ret_sample = new ArrayList<>();
            List<Integer> indexes = Arrays.asList(range(size));
            for (int i = 0; i < sample_size; i++) {
                int index = random.nextInt(indexes.size());
                ret_sample.add(list[indexes.remove(index)]);
            }
            return ret_sample;
        }
        throw new Error("Sample size cant be greater than the collection size");
    }

    public static <T> List<List<T>> randsample(T[] list, int sample_size, int groups) {
        List<List<T>> ret_sample = new ArrayList<>();
        for (int i = 0; i < groups; i++) {
            ret_sample.add(randsample(list, sample_size));
        }
        return ret_sample;
    }

    public static <T> List<T> randsample(Collection<T> list, int sample_size) {
        int size = list.size();
        if (sample_size < size) {
            Random random = new Random();
            List<T> items = new ArrayList<>(list);
            List<T> ret_sample = new ArrayList<>();
            List<Integer> indexes = Arrays.asList(range(size));
            for (int i = 0; i < sample_size; i++) {
                int index = random.nextInt(indexes.size());
                ret_sample.add(items.get(indexes.remove(index)));
            }
            return ret_sample;
        }
        throw new Error("Sample size cant be greater than the collection size");
    }

    public static <T> List<Collection<T>> randsample(Collection<T> list, int sample_size, int groups) {
        List<Collection<T>> ret_sample = new ArrayList<>();
        for (int i = 0; i < groups; i++) {
            ret_sample.add(randsample(list, sample_size));
        }
        return ret_sample;
    }

    public static <T> T[] shuffle(T... obj) {
        List<T> ret_arr = new ArrayList<T>();
        Integer[] indexed = new Integer[len(obj)];
        for (int i : range(len(ret_arr))) {
            int index = randint(len(obj) - 1);
            while (indexOf(indexed, index) != -1) {
                index = randint(len(obj) - 1);
            }
            ret_arr.add(i, obj[index]);
            indexed[i] = index;
        }
        return ret_arr.toArray(obj);
    }

    public static Integer[] swapSort(Integer[] array) {
        if (len(array) > 1) {
            for (int i : range(len(array)))
                for (int j : range(1, len(array) - i))
                    if (toDouble(array[j - 1]) > toDouble(array[j]))
                        swap(array, j - 1, j);
        }
        return array;
    }

    public static Integer[] pushSort(Integer[] obj) {
        if (len(obj) > 1) {
            for (int i : range(len(obj))) {
                for (int j : range(1, len(obj))) {
                    if (obj[i] > obj[len(obj) - j] && i < len(obj) - j) {
                        obj = pushArray(obj, i, len(obj) - j);
                    }
                }
            }
        }
        return obj;
    }

    public static Integer[] pushArray(Integer[] obj, int old_index, int new_index) {
        if (old_index < new_index) {
            int len = len(obj);
            Integer[] ret_arr = new Integer[len];
            for (int i : range(len)) {
                if (i == new_index) {
                    ret_arr[i] = obj[old_index];
                } else if (i >= old_index && i < new_index) {
                    ret_arr[i] = obj[i + 1];
                } else {
                    ret_arr[i] = obj[i];
                }
            }
            return ret_arr;
        }
        return obj;
    }

    public static Integer[] swap(Integer[] obj, int index1, int index2) {
        int temp = obj[index1];
        obj[index1] = obj[index2];
        obj[index2] = temp;
        return obj;
    }

    public static String[] split(CharSequence line, Object at) {
        List<String> ret_list = new ArrayList<>();
        if (line.toString().contains("<>".replace("<>", at.toString()))) {
            boolean match;
            StringBuilder ret = new StringBuilder();
            line = line.toString() + at.toString();
            for (char i : line.toString().toCharArray()) {
                match = false;
                if (i == toCharacter(at)) {
                    match = true;
                    if (!ret.toString().equals("")) {
                        ret_list.add(ret.toString());
                        ret = new StringBuilder();
                    }
                }
                if (!match) {
                    ret.append(i);
                }
            }
            String[] ret_arr = new String[len(ret_list)];
            if (len(ret_list) != 0) {
                return ret_list.toArray(ret_arr);
            }
        }
        String[] split = new String[1];
        split[0] = line.toString();
        return split;
    }

    public static String trim(CharSequence line) {
        StringBuilder trimmed = new StringBuilder(line.toString().trim());
        line = line.toString().replace(trimmed.toString(), " f|b ");
        String front = (split(line, "|"))[0];
        String back = (split(line, "|"))[1];
        while (front.contains("\n")) {
            trimmed.insert(0, "\n");
            front = front.replaceFirst("\n", "");
        }
        while (back.contains("\n")) {
            trimmed.append("\n");
            back = back.replaceFirst("\n", "");
        }
        return trimmed.toString();
    }

    private static Map<Integer, Integer> getNewlineIndex(CharSequence line) {
        Map<Integer, Integer> newlineIndex = new HashMap<>();
        int memory = 0, index = 0;
        String temp = line.toString();
        while (temp.contains("\n")) {
            if (temp.indexOf("\n") == index) {
                if (newlineIndex.containsKey(memory)) {
                    //.replace
                    newlineIndex.put(memory, toInteger(newlineIndex.get(memory)) + 1);
                } else {
                    newlineIndex.put(memory, 1);
                }
                temp = temp.replaceFirst("\n", "");
                if (temp.indexOf("\n") > index) {
                    memory++;
                }
                index = temp.indexOf("\n");
            } else {
                newlineIndex.put(memory, 0);
                if (temp.replaceFirst("\n", "").indexOf("\n") > index) {
                    memory++;
                }
                index = temp.indexOf("\n");
            }
        }
        return newlineIndex;
    }

    private static String wordwrap(CharSequence _line, Integer length) {
        String line = _line.toString();
        if (line.length() > length) {
            line = line.substring(0, length) + wordwrap("\n" + line.substring(length), length);
        }
        return line;
    }

    private static String wrap(CharSequence _line, Integer length) {
        String line = _line.toString();
        StringBuilder ret = new StringBuilder();
        StringBuilder full_ret = new StringBuilder();
        if (line.length() > length) {
            if (line.split(" ").length != 1) {
                line = line.replace("\t", " |----| ");
                for (String word : line.split(" ")) {
                    if ((ret + word).length() < length) {
                        ret.append(word).append(" ");
                    } else {
                        ret.append("\n");
                        full_ret.append(ret);
                        ret = new StringBuilder(word + " ");
                    }
                }
                full_ret.append(ret);
            } else {
                full_ret = new StringBuilder(wordwrap(line, length));
            }
        } else {
            full_ret = new StringBuilder(line);
        }
        while (full_ret.toString().contains("|----|")) {
            full_ret = new StringBuilder(full_ret.toString().replace("|----|", "\t"));
        }
        return full_ret.toString();
    }

    public static String wrapLine(CharSequence line, Integer wraplenght) {
        Map<Integer, Integer> newlineIndex = getNewlineIndex(line);
        String newline = "\n";
        StringBuilder ret_Str = new StringBuilder();
        for (int j = 0; j < len(newlineIndex); j++) {
            try {
                String wrapped = wrap(split(line.toString().trim(), "\n")[j], wraplenght);
                ret_Str.append(repeatString(newline, newlineIndex.get(j))).append(wrapped);
            } catch (Exception e) {
                ret_Str.append(repeatString(newline, newlineIndex.get(j)));
            }
        }
        if (newlineIndex.isEmpty()) {
            ret_Str = new StringBuilder(wrap(line, wraplenght));
        }
        return ret_Str.toString();
    }

    public static String alignCenter(CharSequence line, Integer barlength) {
        if (line.toString().split("\n").length == 1 && line.toString().split(" ").length == 1) {
            if (line.toString().length() > barlength) {
                line = line + "\n";
            }
        }
        line = wrapLine(line, barlength);
        StringBuilder ret_Str = new StringBuilder();
        Map<Integer, Integer> newlineIndex = getNewlineIndex(line);
        for (int j = 0; j < len(newlineIndex); j++) {
            String tabbing = "\t", spacing = "", newline = "\n";
            try {
                String phrase = split(line.toString().trim(), "\n")[j].trim();

                Object tab = (barlength - phrase.replace("\n", "")
                        .replace("\t", "|--|")
                        .length()) / 2;
                tab = toDouble(tab) / 4;
                tabbing = repeatString(tabbing, toDouble(tab).intValue());
                if (tab.toString().contains(".")) {
                    String ret = "0." + split(tab.toString(), ".")[1];
                    Object ret_num = toDouble(ret) * 5;
                    spacing = repeatString(" ", toInteger(ret_num));
                }
                ret_Str.append(format("<><><>",
                        format(repeatString(newline, newlineIndex.get(j)) + "<>", tabbing),
                        spacing, phrase.trim()));
            } catch (Exception e) {
                ret_Str.append(repeatString(newline, newlineIndex.get(j)));
            }
        }
        if (newlineIndex.isEmpty()) {
            String tabbing = "\t", spacing = "", newline = "\n";
            String phrase = line.toString();

            Object tab = (barlength - phrase.replace("\n", "")
                    .replace("\t", "|--|")
                    .length()) / 2;
            tab = toDouble(tab) / 4;
            tabbing = repeatString(tabbing, toDouble(tab).intValue());
            if (tab.toString().contains(".")) {
                String ret = "0." + split(tab.toString(), ".")[1];
                Object ret_num = toDouble(ret) * 5;
                spacing = repeatString(" ", toInteger(ret_num));
            }
            ret_Str.append(format("<><><>",
                    format("<>", tabbing), spacing, phrase.trim()));
        }
        return ret_Str.toString();
    }

    public static String alignLeft(CharSequence line, Integer barlength) {
        if (line.toString().split("\n").length == 1 && line.toString().split(" ").length == 1) {
            if (line.toString().length() > barlength) {
                line = line + "\n";
            }
        }
        line = wrapLine(line, barlength);
        Map<Integer, Integer> newlineIndex = getNewlineIndex(line);
        StringBuilder ret_Str = new StringBuilder();
        String newline = "\n";
        String phrase;
        for (int j = 0; j < len(newlineIndex); j++) {
            try {
                phrase = split(line.toString().trim(), "\n")[j].trim();
                ret_Str.append(repeatString(newline, newlineIndex.get(j))).append(phrase);
            } catch (Exception e) {
                ret_Str.append(repeatString(newline, newlineIndex.get(j)));
            }
        }
        if (newlineIndex.isEmpty()) {
            ret_Str.append(trim(line));
        }
        return ret_Str.toString();
    }

    public static String alignRight(CharSequence line, Integer barlength) {
        if (line.toString().split("\n").length == 1 && line.toString().split(" ").length == 1) {
            if (line.toString().length() > barlength) {
                line = line + "\n";
            }
        }
        line = wrapLine(line, barlength);
        StringBuilder ret_Str = new StringBuilder();
        String newline = "\n";
        Map<Integer, Integer> newlineIndex = getNewlineIndex(line);

        for (int j = 0; j < len(newlineIndex); j++) {
            try {
                String phrase = split(line.toString().trim(), "\n")[j].trim();
                ret_Str.append(repeatString(newline, newlineIndex.get(j))).append(repeatString(" ", barlength - phrase.length())).append(phrase);
            } catch (Exception e) {
                ret_Str.append(repeatString(newline, newlineIndex.get(j)));
            }
        }
        if (newlineIndex.isEmpty()) {
            String phrase = trim(line);
            ret_Str.append(repeatString(" ", barlength - phrase.length())).append(phrase);
        }
        return ret_Str.toString();
    }

    public static String alignNormal(CharSequence line, Integer barlength) {
        line = wrapLine(line, barlength);
        return line.toString().trim();
    }

    public static String repeatString(CharSequence line, int times) {
        StringBuilder s = new StringBuilder(line);
        for (int i = 0; i < times; i++) {
            s.append(line);
        }
        return s.toString();
    }

    public static String replaceFirst(String line, String section, String newString) {
        int index = line.indexOf(section);
        StringBuilder s = new StringBuilder(line);
        s.replace(index, index + section.length(), newString);
        return s.toString();
    }


    //##### End of Math Functions #####
    //##### End of Math Functions #####
    //##### End of Math Functions #####
    //##### End of Math Functions #####
    //##### End of Math Functions #####


    //##### Text Processing Functions #####
    //##### Text Processing Functions #####
    //##### Text Processing Functions #####
    //##### Text Processing Functions #####
    //##### Text Processing Functions #####

    public static String replaceLast(String line, String section, String newString) {
        int index = line.lastIndexOf(section);
        StringBuilder s = new StringBuilder(line);
        s.replace(index, index + section.length(), newString);
        return s.toString();
    }

    public static String replaceAll(String line, String section, String newString) {
        while (line.contains(section)) {
            line = replaceFirst(line, section, newString);
        }
        return line;
    }

    public static String format(String line, Object... with) {
        //this function replaces the  <> in the String line
        //it assigns the first item in the words to the first <>
        //and so on until there are no more <> to replace
        //Note if words are more than the <> in the String line
        //then the rest are ignored

        StringBuilder newline = new StringBuilder(line);
        Integer i = 1;
        for (Object word : with) {
            if (line.contains("<>")) {
                int index = newline.indexOf("<>");
                if (index != -1) {
                    newline.replace(index, index + 2, (word != null) ? word.toString() : "null");
                }
//                line = line.replaceFirst("<>", (word != null) ? word.toString(): "null");
            } else if (line.contains(format("<<>>", i))) {
                int index = newline.indexOf(format("<<>>", i));
                while (index != -1) {
                    newline.replace(index, index + format("<<>>", i).length(),
                            (word != null) ? word.toString() : "null");
                    index = newline.indexOf(format("<<>>", i));
                }
//                line = line.replace(format("<<>>",i), (word != null) ? word.toString(): "null");
                i++;
            } else {
                break;
            }
        }
        return newline.toString();
    }

    public static String reverse(String line) {
        StringBuilder reversed_line = new StringBuilder();
        for (int i = len(line); i > 0; i--) {
            reversed_line.append(line.charAt(i - 1));
        }
        return reversed_line.toString();
    }

    public static Object[] reverse(Object[] objs) {
        Object[] reversed_obj = new Object[len(objs)];
        for (int i = len(objs); i > 0; i--) {
            reversed_obj[len(objs) - i] = objs[i - 1];
        }
        return reversed_obj;
    }

    public static Integer[] reverse(Integer[] list) {
        Integer[] result = new Integer[len(list)];
        for (int i = 0, j = result.length - 1; i < len(list); i++, j--) {
            result[j] = list[i];
        }
        return result;
    }

    public static boolean choice() {
        String v = inputStr("").toLowerCase();
        switch (v) {
            case "yes":
            case "y":
                return true;
            case "no":
            case "n":
                return false;
            default:
                print("( invalid ) please choose y or n");
                return choice();
        }
    }

    //##### End of Text Processing Functions #####
    //##### End of Text Processing Functions #####
    //##### End of Text Processing Functions #####
    //##### End of Text Processing Functions #####
    //##### End of Text Processing Functions #####

    //##### General Functions #####
    //##### General Functions #####
    //##### General Functions #####
    //##### General Functions #####
    //##### General Functions #####

    public static <T extends Iterable<K>, K, V> List<V> map(T collection, Mapper<K, V> mapper) {
        List<V> mapped = new ArrayList<>();
        for (K item : collection) {
            mapped.add((mapper.map(item)));
        }
        return mapped;
    }

    public static <T extends Iterable<K>, K, V> List<V> map(T collection, IndexedMapper<K, V> mapper) {
        int i = -1;
        List<V> mapped = new ArrayList<>();
        for (K item : collection) {
            i++;
            mapped.add((mapper.map(i, item)));
        }
        return mapped;
    }

    public static <T extends Map<K, V>, K, V, X, Y> Map<X, Y> map(T map, Mapper<Map.Entry<K, V>, Map.Entry<X, Y>> mapper) {
        Map<X, Y> mapped = new HashMap<>();
        for (Map.Entry<K, V> item : map.entrySet()) {
            Map.Entry<X, Y> entry = mapper.map(item);
            mapped.put(entry.getKey(), entry.getValue());
        }
        return mapped;
    }

    public static <T extends Iterable<K>, K> List<K> filter(T collection, Filterer<K> filterer) {
        List<K> filtered = new ArrayList<>();
        for (K item : collection) {
            if (filterer.filter(item)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    public static <T extends Iterable<K>, K> List<K> filter(T collection, IndexedFilterer<K> filterer) {
        int i = -1;
        List<K> filtered = new ArrayList<>();
        for (K item : collection) {
            i++;
            if (filterer.filter(i, item)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    public static <T extends Map<K, V>, K, V> Map<K, V> filter(T map, Filterer<Map.Entry<K, V>> filterer) {
        Map<K, V> mapped = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (filterer.filter(entry)) {
                mapped.put(entry.getKey(), entry.getValue());
            }
        }
        return mapped;
    }

    public static <T extends Iterable<K>, K> void forEach(T collection, Action<K> action) {
        for (K item : collection) {
            action.run(item);
        }
    }

    public static <T extends Iterable<K>, K> void forEach(T collection, IndexedAction<K> action) {
        int i = -1;
        for (K item : collection) {
            i++;
            action.run(i, item);
        }
    }

    public static <T extends Map<K, V>, K, V> void forEach(Map<K, V> map, MapAction<K, V> action) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            action.run(entry.getKey(), entry.getValue());
        }
    }

    public static <T extends Iterable<V>, V> int search(T collection, Filterer<V> filterer) {
        int i = -1;
        for (V item : collection) {
            i++;
            if (filterer.filter(item)) {
                return i;
            }
        }
        return -1;
    }

    public static <T extends Map<K, V>, K, V> K search(T map, Filterer<V> filterer) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (filterer.filter(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static <T extends Map<K, V>, K, V> K search(T map, MapFilterer<K, V> filterer) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (filterer.filter(entry.getKey(), entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static <K, V> List<V> map(K[] collection, Mapper<K, V> mapper) {
        List<V> mapped = new ArrayList<>();
        for (K item : collection) {
            mapped.add((mapper.map(item)));
        }
        return mapped;
    }

    public static <K, V> List<V> map(K[] collection, IndexedMapper<K, V> mapper) {
        int i = -1;
        List<V> mapped = new ArrayList<>();
        for (K item : collection) {
            i++;
            mapped.add((mapper.map(i, item)));
        }
        return mapped;
    }

    public static <T> List<T> filter(T[] collection, Filterer<T> filterer) {
        List<T> filtered = new ArrayList<>();
        for (T item : collection) {
            if (filterer.filter(item)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    public static <T> List<T> filter(T[] collection, IndexedFilterer<T> filterer) {
        int i = -1;
        List<T> filtered = new ArrayList<>();
        for (T item : collection) {
            i++;
            if (filterer.filter(i, item)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    public static <T> void forEach(T[] collection, Action<T> action) {
        for (T item : collection) {
            action.run(item);
        }
    }

    public static <T> void forEach(T[] collection, IndexedAction<T> action) {
        int i = -1;
        for (T item : collection) {
            i++;
            action.run(i, item);
        }
    }

    public static <T> int search(T[] collection, Filterer<T> filterer) {
        int i = -1;
        for (T item : collection) {
            i++;
            if (filterer.filter(item)) {
                return i;
            }
        }
        return -1;
    }

    public static <T extends CharSequence> int indexOf(T[] collection, T key, boolean matchCase) {
        return indexOf(Arrays.asList(collection), key, matchCase);
    }

    public static <T extends Iterable<V>, V extends CharSequence> int indexOf(T collection, V key, boolean matchCase) {
        if (matchCase) {
            return search(collection, (o) -> o.toString().equals(key.toString()));
        } else {
            return search(collection, (o) -> o.toString().equalsIgnoreCase(key.toString()));
        }
    }

    public static <T> int indexOf(Iterable<T> collection, T key) {
        int i = -1;
        for (T obj : collection) {
            i++;
            if (obj == key) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int indexOf(T[] collection, T key) {
        return indexOf(collection, key);
    }

    public static <T extends Iterable<V>, V, R> R join(T values, Joiner<V, R> joiner) {
        R result = null;
        for (V item : values) {
            result = joiner.join(result, item);
        }
        return result;
    }

    public static <T, R> R join(T[] values, Joiner<T, R> joiner) {
        R result = null;
        for (T item : values) {
            result = joiner.join(result, item);
        }
        return result;
    }

    public static <T extends Iterable<V>, V extends Number> double sum(T values) {
        return join(values, (r, o) -> o.doubleValue() + o.doubleValue());
    }

    public static <V extends Number> double sum(V[] values) {
        return join(values, (r, o) -> o.doubleValue() + o.doubleValue());
    }

    public static <T extends Iterable<V>, V> List<V> asList(T collection) {
        return join(collection, (result, item) -> {
            if (result == null)
                result = new ArrayList<>();
            result.add(item);
            return result;
        });
    }

    public static <T> List<T> asList(T[] collection) {
        return Arrays.asList(collection);
    }

    public static class Printer {
        /**
         * This class makes using printStream very easy
         * When Creating new Object instance
         *
         * @param String  startWith, separator and endwith and Integer ConsoleLength
         * eg. Printer pprint = new Printer() for default settings
         * and  Printer pprint = new Printer("", ", ", ".\n")
         * after creating and instance you can set
         * startWith using the dot operator read(...),
         * or set set separator with sep(...) and end(...) to change endWith
         **/
        private Integer consoleLength = 150;
        private String printFormat = "";
        private String separator = " ";
        private String startWith = "";
        private String endWith = "";

        Printer() {
        }

        Printer(String start, String sep, String end) {
            startWith = start;
            separator = sep;
            endWith = end;
        }

        private String align(Object obj) {
            switch (printFormat) {
                case "normal":
                    return Funcs.alignNormal(obj.toString(), consoleLength);
                case "center":
                    return Funcs.alignCenter(obj.toString(), consoleLength);
                case "left":
                    return Funcs.alignLeft(obj.toString(), consoleLength);
                case "right":
                    return Funcs.alignRight(obj.toString(), consoleLength);
                default:
                    return obj.toString();
            }
        }

        //##### class variable setter #####
        public Printer sep(String using) {
            this.separator = using;
            return this;
        }

        public Printer start(String using) {
            this.startWith = using;
            return this;
        }

        public Printer end(String using) {
            this.endWith = using;
            return this;
        }

        public Printer setConsoleLength(Integer value) {
            this.consoleLength = value;
            return this;
        }

        public Printer resetAlignment() {
            this.printFormat = "";
            return this;
        }

        public Printer alignNormal() {
            this.printFormat = "normal";
            return this;
        }

        public Printer alignCenter() {
            this.printFormat = "center";
            return this;
        }

        public Printer alignLeft() {
            this.printFormat = "left";
            return this;
        }

        public Printer alignRight() {
            this.printFormat = "right";
            return this;
        }

        //### Main Print Functions ###

        public <T> void print(T object) {
            String line = startWith + object + endWith;
            System.out.print(align(line));
        }

        public <T> void print(T... objects) {
            if (len(objects) > 0) {
                for (int i : range(len(objects))) {
                    String line = startWith + objects[i] + ((i == len(objects) - 1) ? "" : separator);
                    System.out.print(align(line));
                }
                System.out.print(endWith);
            } else {
                print("\n");
            }
        }

    }

    public static abstract class CountDownTimer {

        private Timer TIMER;
        private TimerTask STARTER; //, START_TASK, TICK_TASK, STOP_TASK
        private int TOTAL_TIME, TIME_LEFT, TICK_TIME;
        private String STATE;

        public CountDownTimer(int totalTime, int tickTime) {
            init(totalTime, tickTime);
        }

        public CountDownTimer(int totalTime) {
            init(totalTime, totalTime);
        }

        private void init(int totalTime, int tickTime) {

            STATE = "created";
            TIMER = new Timer();
            TOTAL_TIME = totalTime;
            TIME_LEFT = totalTime;
            TICK_TIME = tickTime;

        }

        private TimerTask getStarterTask() {
            return new TimerTask() {
                @Override
                public void run() {

                    if (TIME_LEFT > 0) {

                        TIME_LEFT -= 1;

                        if (STATE.equals("resume")) {
                            STATE = "started";
                        }

                    }

                    if (STATE.equals("started") && (TOTAL_TIME - TIME_LEFT) % TICK_TIME == 0) {
                        onTick();
                    }

                    if (TIME_LEFT == 0) {
                        STATE = "stopped";
                        stop();
                    }

                }
            };
        }

        public void start() throws Exception {

            if (STATE.equals("created")) {
                if (TOTAL_TIME < 0) {
                    throw new Exception("Invalid Total Time : totalTime must be > 0");
                } else if (TICK_TIME < 1 && TICK_TIME >= TOTAL_TIME) {
                    throw new Exception("Invalid Total Time : totalTime must be > 0 and <= totalTime");
                } else {
                    STATE = "started";
                    onStart();
                    TIMER.scheduleAtFixedRate(getStarterTask(), 0, 1);
                }
            }
        }

        public void stop() {
            TIMER.cancel();
            TIMER = null;
            STATE = "stopped";
            onStop();
        }

        public void pause() {

            if (!STATE.equals("paused") && !STATE.equals("stopped")) {
                STATE = "paused";
                TIMER.cancel();
                TIMER = null;
            }
        }

        public void resume() {

            if (STATE.equals("paused")) {
                STATE = "resume";
                TIMER = new Timer();
                TIMER.scheduleAtFixedRate(getStarterTask(), 0, 1);
            }

        }

        public int getTimeElapsed() {
            return TOTAL_TIME - TIME_LEFT;
        }

        public int getTimeLeft() {
            return TIME_LEFT;
        }

        public abstract void onStart();

        public abstract void onTick();

        public abstract void onStop();
//        public abstract void onPause();
//        public abstract void onResume();

    }

    public static class QuickSort {

        private static int[] a;

        private QuickSort() {
        }

        public static void run(int[] unsorted) {
            println("Unsorted array:");
            printArray(unsorted);
            int[] sorted = sort(unsorted);
            println("\n\nSorted array:");
            printArray(sorted);
        }

        public static void printArray(int[] array) {
            println();
            for (int i = 0; i < len(array); i++) {
                if (array[i] < 10)
                    System.out.print(" ");
                System.out.print(array[i] + " ");
                if ((i + 1) % 20 == 0)
                    System.out.println();
            }
        }

        public static void printArray(Object[] array) {
            Funcs.println();
            for (int i = 0; i < len(array); i++) {
                System.out.print(array[i] + " ");
                if ((i + 1) % 20 == 0)
                    System.out.println();
            }
        }


        public static int[] sort(int[] array) {
            a = array;
            sort(0, a.length - 1);
            return a;
        }

        private static void sort(int low, int high) {
            if (low >= high)
                return;
            int p = partition(low, high);
            sort(low, p);
            sort(p + 1, high);
        }

        private static int partition(int low, int high) {
            int pivot = a[low];
            int i = low - 1;
            int j = high + 1;
            while (i < j) {
                for (i++; a[i] < pivot; i++)
                    for (j--; a[j] > pivot; j--)
                        if (i < j)
                            swap(i, j);
            }
            return j;
        }

        private static void swap(int i, int j) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            printArray(a);
        }
    }

    public static class MergeSort {

        private Integer[] ARRAY;

        MergeSort(Integer[] array) {
            ARRAY = array;
        }

        private Integer[] mergeSort(Integer[] array, int start, int end) {
            if (end - start > 1) {
                int middle = start + ((end - start) / 2);

                if (end - start == 2) {
                    if (array[start] > array[end - 1]) {
                        swap(array, start, end - 1);
                    }
                } else {
                    array = mergeSort(array, 0, middle);
                    array = mergeSort(array, middle + 1, end);
                }
                array = mergeArray(array, start, middle, end);
            }
            return array;
        }


        private Integer[] mergeArray(Integer[] array, int start, int middle, int end) {

            Integer[] new_array = array;

            for (int i : range(start, end)) {
                if (array[i] > array[middle]) {
                    swap(new_array, i, middle);
                } else {
                    if (middle < end - 1) {
                        middle++;
                    }
                }
            }

            return new_array;
        }

        public Integer[] getSORTED() {
            return mergeSort(ARRAY, 0, ARRAY.length);
        }
    }

}