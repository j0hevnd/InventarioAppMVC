import mintic.view.MainWindow;
import mintic.controller.ProductController;

public class Main {
    public static void main(String[] args) {
        ProductController productController = new ProductController(new MainWindow());
    }
}
