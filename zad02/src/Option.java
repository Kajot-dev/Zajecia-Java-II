
interface Option {
    void run() throws Exception;
    default void onError(OptionRunner current, Exception e) {
        OptionRunner.errHandler.handle(current, e);
    }
    default boolean accept() {
        return true;
    }


}
