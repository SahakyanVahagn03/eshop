

import manager.CategoryManager;
import manager.ProductManager;
import model.Category;
import model.Product;

import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final CategoryManager CATEGORY_MANAGER = new CategoryManager();
    private static final ProductManager PRODUCT_MANAGER = new ProductManager();

    public static void main(String[] args) {
        boolean run = true;
        while (run) {

            commands();
            switch (SCANNER.nextLine()) {
                case "0":
                    run = false;
                    break;
                case "1":
                    addCategory();
                    break;
                case "2":
                    editCategory();
                    break;
                case "3":
                    deleteCategory();
                    break;
                case "4":
                    addProduct();
                    break;
                case "5":
                    editProduct();
                    break;
                case "6":
                    deleteProduct();
                    break;
                case "7":
                    System.out.println("sum of products is " + PRODUCT_MANAGER.SumOfProducts());
                    break;
                case "8":
                    System.out.println("the most expensive price of product is " + PRODUCT_MANAGER.theMostExpensivePriceOfProduct());
                    break;
                case "9":
                    System.out.println("the Cheapest Price Of the Product is " + PRODUCT_MANAGER.theCheapestPriceOfProduct());
                    break;
                case "10":
                    System.out.println("products average is  " + PRODUCT_MANAGER.AvgOfPriceProduct());
                    break;
            }
        }
    }


    private static void editCategory() {
        printCategories();
        System.out.println("write the category ID for edit");
        String id = SCANNER.nextLine();
        if (!id.chars().allMatch(Character::isDigit)) {
            System.out.println("id must be only digit");
            return;
        }
        System.out.println("write name of category");
        String name = SCANNER.nextLine();
        if (name.trim().isEmpty()){
            System.out.println("you must write something");
            return;
        }
        CATEGORY_MANAGER.editCategoryById(new Category(Integer.parseInt(id), name));
    }

    private static void deleteCategory() {
        printCategories();
        System.out.println("write the category ID for delete the category");
        String id = SCANNER.nextLine();
        if (!id.chars().allMatch(Character::isDigit)) {
            System.out.println("id must be only digit");
            return;
        }
        CATEGORY_MANAGER.deleteCategoryById(Integer.parseInt(id));
    }


    private static void addCategory() {
        System.out.println(" write a category for the products");
        String nameCategory = SCANNER.nextLine();
        if (nameCategory.trim().isEmpty()){
            System.out.println("you must write something");
        }
        Category category = Category.builder()
                .name(nameCategory)
                .build();
        CATEGORY_MANAGER.add(category);
    }

    private static void addProduct() {
        System.out.println("write the product (name,description,price,quantity) for add");
        String[] arrayOfStr = SCANNER.nextLine().split(",");
        if (arrayOfStr[0].trim().isEmpty()){
            System.out.println("you must write something");
            return;
        }
        if (arrayOfStr.length != 4) {
            System.out.println("you made a mistake, you need to fill in all the fields");
           return;
        }
        printCategories();
        System.out.println("choose category");
        String category_id = SCANNER.nextLine();
        if (!arrayOfStr[2].chars().allMatch(Character::isDigit) ||
                !arrayOfStr[3].chars().allMatch(Character::isDigit) ||
                !category_id.chars().allMatch(Character::isDigit)) {
            System.out.println("this fields<price,quantity,id> must be only digit");
            return;
        }
        Category category = CATEGORY_MANAGER.getCategoryById(Integer.parseInt(category_id));
        Product product = Product.builder()
                .name(arrayOfStr[0])
                .description(arrayOfStr[1])
                .price(Double.parseDouble(arrayOfStr[2]))
                .quantity(Integer.parseInt(arrayOfStr[3]))
                .category(category)
                .build();
        PRODUCT_MANAGER.add(product);
    }

    private static void deleteProduct() {
        printProducts();
        System.out.println("write the product ID for delete");
        String id = SCANNER.nextLine();
        if (!id.chars().allMatch(Character::isDigit)) {
            System.out.println("id must be only digit");
            return;
        }
        PRODUCT_MANAGER.deleteProductById(Integer.parseInt(id));
    }

    private static void printProducts() {
        for (Product product : PRODUCT_MANAGER.getAllProducts()) {
            System.out.println(product);
        }
    }

    private static void editProduct() {
        printProducts();
        System.out.println("products are listed above");
        System.out.println("write the product (ID,name,description,price,quantity) for edit");
        String[] arrayOfStr = SCANNER.nextLine().split(",");
        if (arrayOfStr[1].trim().isEmpty() || arrayOfStr[2].trim().isEmpty()){
            System.out.println("you must write something in (name and description)");
            return;
        }
        if (arrayOfStr.length != 5) {
            System.out.println("you made a mistake, you need to fill in all the fields");
        }
        if (!arrayOfStr[3].chars().allMatch(Character::isDigit) ||
                !arrayOfStr[4].chars().allMatch(Character::isDigit) ||
                !arrayOfStr[0].chars().allMatch(Character::isDigit)) {
            System.out.println("this fields<price,quantity,id> must be only digit");
            return;
        }
        Category category = willChangeCategoryOfTheProduct(arrayOfStr[0]);
        Product product = Product.builder()
                .id(Integer.parseInt(arrayOfStr[0]))
                .name(arrayOfStr[1])
                .description(arrayOfStr[2])
                .price(Double.parseDouble(arrayOfStr[3]))
                .quantity(Integer.parseInt(arrayOfStr[4]))
                .category(category)
                .build();
        PRODUCT_MANAGER.editProductById(product);
    }

    private static Category willChangeCategoryOfTheProduct(String id) {
        System.out.println("would you like to Leave the same category ('NO' or 'Any word')");
        String answer = SCANNER.nextLine();
        if (answer.trim().equalsIgnoreCase("NO")){
            printCategories();
            System.out.println("which category do you select, choose category id for products");
            String  otherCategoryId= SCANNER.nextLine();
            if (!otherCategoryId.chars().allMatch(Character::isDigit)){
                System.out.println("this field<id> must be only digit");
                editProduct();
            }
            return CATEGORY_MANAGER.getCategoryById(Integer.parseInt(otherCategoryId));
        }
        return PRODUCT_MANAGER.getProductById(Integer.parseInt(id)).getCategory();
    }

    private static void printCategories() {
        for (Category category : CATEGORY_MANAGER.getAllCategories()) {
            System.out.println(category);
        }
    }

    private static void commands() {
        System.out.println(" select 0 for exit");
        System.out.println(" select 1 for add a category");
        System.out.println(" select 2 for edit a category by id");
        System.out.println(" select 3 for delete a category by id");
        System.out.println(" select 4 for add a product");
        System.out.println(" select 5 for edit a product by id");
        System.out.println(" select 6 for delete a product by id");
        System.out.println(" select 7 for print sum of products");
        System.out.println(" select 8 for print the most expensive price of product");
        System.out.println(" select 9 for print the cheapest price of product");
        System.out.println(" select 10 for print products' average");

    }
}
