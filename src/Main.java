public class Main {
    private static TModel tmm;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               new GUI(new TModel(new String[]{"butor_id", "meret", "szin", "anyag", "datum"}));
            }
        });
    }
}
