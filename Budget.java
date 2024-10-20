import java.util.Scanner;

public class Budget {
    private double income;
    private int amountOfBills;
    private String[] nameOfBill;
    private double[] costOfBill;
    private int amountOfMisc;
    private String[] nameOfMisc;
    private double[] costOfMisc;

    public Budget() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Before we get started, we will be working with the 50/30/20 rule, which states that 50% of your income should be bills (necessities), 30% on miscellaneous, and 20% should be for savings.");
        System.out.println("In other words, we don't want bills and miscellaneous to go over 80% together.");

        System.out.print("\nHow much is your monthly income: ");
        income = scanner.nextDouble();

        System.out.print("How many bills do you have (not including misc things like subscriptions to Netflix): ");
        amountOfBills = scanner.nextInt();

        createListsOfBills();
        fillingListOfBillsWithCost(scanner);

        System.out.print("How many miscellaneous costs do you have (like subscriptions or takeout costs that aren't necessity): ");
        amountOfMisc = scanner.nextInt();

        createListsOfMisc();
        fillingListOfMiscWithCost(scanner);

        display();
        recommendation();

        scanner.close();
    }

    private void createListsOfBills() {
        costOfBill = new double[amountOfBills];
        nameOfBill = new String[amountOfBills];
    }

    private void fillingListOfBillsWithCost(Scanner scanner) {
        for (int i = 0; i < amountOfBills; i++) {
            System.out.print("Please enter the name of the bill: ");
            nameOfBill[i] = scanner.next();
            System.out.print("Please enter the cost of the bill: ");
            costOfBill[i] = scanner.nextDouble();
        }
    }

    private void createListsOfMisc() {
        costOfMisc = new double[amountOfMisc];
        nameOfMisc = new String[amountOfMisc];
    }

    private void fillingListOfMiscWithCost(Scanner scanner) {
        for (int i = 0; i < amountOfMisc; i++) {
            System.out.print("Please enter the name of the miscellaneous cost: ");
            nameOfMisc[i] = scanner.next();
            System.out.print("Please enter the cost of the miscellaneous: ");
            costOfMisc[i] = scanner.nextDouble();
        }
    }

    private void display() {
        System.out.println("\nYour income: " + income);
        System.out.println("Total cost of bills: $" + totalCostOfBills());
        System.out.println("Total cost of miscellaneous: $" + totalCostOfMisc());
        System.out.println("The amount of your income leftover: $" + (income - (totalCostOfBills() + totalCostOfMisc())));

        System.out.printf("Percent of bills that take up your income: %.2f%%\n", percentage(totalCostOfBills()));
        if (percentage(totalCostOfBills()) <= 50) {
            System.out.println("Your bills are perfect for your income");
        } else {
            System.out.println("Your bills are too high; they should only make up 50% or less of your income");
        }

        System.out.printf("Percent of miscellaneous that take up your income: %.2f%%\n", percentage(totalCostOfMisc()));
        if (percentage(totalCostOfMisc()) <= 30) {
            System.out.println("Your miscellaneous expenses are perfect for your income");
        } else {
            System.out.println("Your miscellaneous expenses are too high; they should only make up 30% or less of your income");
        }

        System.out.printf("Percent of leftover income you have for savings: %.2f%%\n", percentage(income - (totalCostOfBills() + totalCostOfMisc())));
        if (percentage(income - (totalCostOfBills() + totalCostOfMisc())) <= 20) {
            System.out.println("Your leftover income is perfect for saving, which will help in a rainy day.");
        } else {
            System.out.println("Your leftover income for savings is higher than the minimum of 20%, which means your expenses aren't that high, and you're doing great!");
        }
    }

    private double totalCostOfBills() {
        double total = 0;
        for (double cost : costOfBill) {
            total += cost;
        }
        return total;
    }

    private double totalCostOfMisc() {
        double total = 0;
        for (double cost : costOfMisc) {
            total += cost;
        }
        return total;
    }

    private double percentage(double numerator) {
        return (numerator * 100) / income;
    }

    private void recommendation() {
        System.out.println("\nMy recommendation is:");
        if (percentage(totalCostOfBills()) > 80) {
            System.out.println("Your bills are too high for your income; you're not going to be able to budget since your bills alone are too high.");
        } else if (percentage(totalCostOfBills()) + percentage(totalCostOfMisc()) > 80 && percentage(totalCostOfBills()) >= 50) {
            System.out.println("You're spending too much on miscellaneous items and should cut back on the following item to stay within the 80% range:");
            recommendationForBills();
        } else if (percentage(totalCostOfBills()) + percentage(totalCostOfMisc()) > 80 && percentage(totalCostOfBills()) < 50) {
            recommendationOfMisc();
        } else if (percentage(totalCostOfMisc()) > 30) {
            recommendationOfMisc();
            System.out.printf("Percent of savings because bills/miscellaneous were less than 80%%: %.2f%%\n", percentage(income - (totalCostOfBills() + totalCostOfMisc())));
        } else {
            System.out.println("You're doing great, and all your expenses are perfect!");
        }
    }

    private void recommendationForBills() {
        double decreaseBy = (20 * income) / 100;
        sortListMisc();
        for (int i = 0; i < amountOfMisc; i++) {
            if (costOfMisc[i] >= decreaseBy) {
                System.out.println("The miscellaneous cost you should eliminate is: " + nameOfMisc[i] + " to stay at 80% or lower on bills/miscellaneous cost, which will go into savings.");
                return;
            }
        }
    }

    private void recommendationOfMisc() {
        double limit = (30 * income) / 100;
        double over = totalCostOfMisc() - limit;
        sortListMisc();
        for (int i = 0; i < amountOfMisc; i++) {
            if (costOfMisc[i] >= over) {
                System.out.println("The miscellaneous cost you should eliminate is: " + nameOfMisc[i] + " to stay at 30% or lower on miscellaneous cost, which will go into savings.");
                return;
            }
        }
    }

    private void sortListMisc() {
        for (int i = 0; i < amountOfMisc - 1; i++) {
            for (int j = 0; j < amountOfMisc - i - 1; j++) {
                if (costOfMisc[j] > costOfMisc[j + 1]) {
                    // Swap costs
                    double tempCost = costOfMisc[j];
                    costOfMisc[j] = costOfMisc[j + 1];
                    costOfMisc[j + 1] = tempCost;

                    // Swap names
                    String tempName = nameOfMisc[j];
                    nameOfMisc[j] = nameOfMisc[j + 1];
                    nameOfMisc[j + 1] = tempName;
                }
            }
        }
    }

    public static void main(String[] args) {
        new Budget(); // Create a new Budget instance to run the application
    }
}
