
import controller.ProductController;
import model.Product;

public class Reproduction {
    public static void main(String[] args) {
        System.out.println("Starting reproduction...");
        ProductController pc = new ProductController();

        // Try to find P1009 which is in sample data
        String targetId = "P1009";
        System.out.println("Attempting to find " + targetId + " using binarySearchProduct...");

        try {
            Product p = pc.binarySearchProduct(targetId);
            if (p == null) {
                System.out.println("FAILURE: Product " + targetId + " not found!");
            } else {
                System.out.println("SUCCESS: Found " + p.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Verify if list is actually sorted
        boolean isSorted = true;
        java.util.List<Product> list = pc.getAllProducts();
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getProductId().compareToIgnoreCase(list.get(i + 1).getProductId()) > 0) {
                isSorted = false;
                System.out.println("List NOT sorted at index " + i + ": " + list.get(i).getProductId() + " > "
                        + list.get(i + 1).getProductId());
                break;
            }
        }
        if (isSorted) {
            System.out.println("List is sorted.");
        } else {
            System.out.println("List is NOT sorted.");
        }
    }
}
