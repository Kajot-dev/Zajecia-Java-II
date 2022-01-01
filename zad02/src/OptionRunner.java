import java.util.*;
import java.util.function.Predicate;

public class OptionRunner {
    private static final  String CHOOSE_OPTION = "Choose an option:";
    private final LinkedHashMap<String, Option> optionsMap;
    private final Set<Runnable> closeCallbacks;

    public void setDefaultHandler(ErrorHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    private ErrorHandler defaultHandler = null;
    protected static final ErrorHandler errHandler = (current, e) -> {
        if (current.defaultHandler != null) current.defaultHandler.handle(current, e);
        else {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    };

    private static final Scanner inScanner = new Scanner(System.in);
    private static final String[] BOOL_ANS = new String[] { "YES", "NO" };

    protected OptionRunner() {
        this.optionsMap = new LinkedHashMap<>();
        this.closeCallbacks = new HashSet<>();
    }

    protected void defineOption(String name, Option o) {
        this.optionsMap.put(name, o);
    }

    protected void runOption(Option opt) throws NoSuchElementException {
        if (this.optionsMap.containsValue(opt)) {
            try {
                opt.run();
            } catch (Exception e) {
                opt.onError(this, e);
            }

        } else throw new NoSuchElementException("This option does not exist!");
    }

    protected void cyclingMenu() {

        while (true) {
            System.out.println(CHOOSE_OPTION);

            Option[] okOptions = printOptions(this.optionsMap);

            final var choice = OptionRunner.askInt(okOptions.length + 1);

            if (choice == okOptions.length + 1) {
                for (var opt : this.closeCallbacks) {
                    opt.run();
                }
                return;
            }

            this.runOption(okOptions[choice-1]);
        }

    }

    protected void onMenuClose(Runnable r) {
        this.closeCallbacks.add(r);
    }

    protected static Option[] printOptions(Map<String, Option> options) {
        final var optionsStream = options.entrySet().stream();
        final var okEntries = optionsStream.filter(entry -> {
            Option o = entry.getValue();
            return o.accept();
        }).toArray(Map.Entry[]::new);
        if (okEntries.length == 0) throw new IndexOutOfBoundsException();
        var sb = new StringBuilder();
        for (var i = 0; i < okEntries.length; i++) {
            sb.append("\t(").append(i + 1).append(") ").append(okEntries[i].getKey()).append('\n');
        }
        sb.append("\t(").append(okEntries.length + 1).append(") ").append("\u001B[35mEXIT\u001B[0m").append('\n');
        System.out.print(sb);

        final var okOptions = new Option[okEntries.length];

        for (var i = 0; i < okEntries.length; i++) {
            okOptions[i] = (Option) okEntries[i].getValue();
        }
        return okOptions;
    }

    protected static void printOptions(String[] options) {
        var sb = new StringBuilder();
        for (var i = 0; i < options.length; i++) {
            sb.append("\t(").append(i + 1).append(") ").append(options[i]).append('\n');
        }
        sb.append("\t(").append(options.length + 1).append(") ").append("\u001B[35mCANCEL\u001B[0m").append('\n');
        System.out.print(sb);
    }

    protected static String askString(String question) {
        return OptionRunner.askString(question, (x) -> true);
    }

    protected static String askString(String question, Predicate<String> predicate) {
        return OptionRunner.askString(question, predicate, "Wrong answer!");
    }

    protected static String askString(String question, Predicate<String> predicate, String errMessage) {
        question += ": ";
        String x;
        while (true) {
            System.out.print(question);
            x = inScanner.nextLine().trim();
            if (x.isEmpty())
                Runner.printYellow("Answer cannot be empty!");
            else if (!predicate.test(x)){
                Runner.printYellow(errMessage);
            } else {
                break;
            }

        }
        return x;
    }


    protected static boolean askBoolean(String k) {
        return askBoolean(k, BOOL_ANS);
    }
    protected static boolean askBoolean(String k, String[] ans) {
        if (ans.length != 2) throw new IllegalArgumentException();
        ans[0] = ans[0].toUpperCase();
        ans[1] = ans[1].toUpperCase();
        k += " (" + ans[0] +"/" + ans[1] + "): ";
        String x;
        while (true) {
            System.out.print(k);
            x = inScanner.nextLine().trim().toUpperCase();
            if (x.equals(ans[0]) || (ans[0].startsWith(x) && !ans[1].startsWith(x))) return true;
            else if (x.equals(ans[1]) || (ans[1].startsWith(x) && !ans[0].startsWith(x))) return false;
            Runner.printYellow("You have to choose \"" + ans[0] + "\" or \"" + ans[1] + "\"!");
        }
    }

    protected static int askInt(int max) {
        return OptionRunner.askInt(1, max, "?");
    }

    protected static int askInt(int min, int max, String k) {
        k += ": ";
        int x;
        while (true) {
            try {
                System.out.print(k);
                x = Integer.parseInt(inScanner.nextLine());
                if (x >= min && x <= max)
                    break;
            } catch (NumberFormatException e) {
                // do nothing
            }
            Runner.printYellow("Invalid number, has to be between " + min + '-' + max + '.');
        }
        return x;
    }

    protected static float askFloat(float min, float max, String k) {
        k += ": ";
        float x;
        while (true) {
            try {
                System.out.print(k);
                x = Float.parseFloat(inScanner.nextLine());
                if (x >= min && x <= max)
                    break;
            } catch (NumberFormatException e) {
                // do nothing
            }
            Runner.printYellow("Invalid number, has to be between " + min + '-' + max + '.');
        }
        return x;
    }

}
