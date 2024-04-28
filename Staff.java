package 과제12;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Staff {
    private Map<String, SneakersInfo> sneakersInfoMap;
    private Map<String, Long> sneakersStockMap;
    private List<Customer> customerList;
    private long dailySalesAmount;

    public Staff() {
        this.sneakersInfoMap = new HashMap<>();
        this.sneakersStockMap = new HashMap<>();
        this.customerList = new ArrayList<>();
        this.dailySalesAmount = 0;
    }

    public void readFileAndSetSneakerInfoMap() {
        // 파일 경로 설정
        String filePath = "C:/Users/USER/OneDrive/바탕 화면/공부/백엔드/과제모음/과제12/nike-sneaker-characters.txt";
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");
                if (tokens.length >= 3) {
                    String modelName = tokens[0].trim();
                    long price = Long.parseLong(tokens[1].trim());
                    String features = tokens[2].trim();
                    SneakersInfo sneakersInfo = new SneakersInfo(modelName, price, features);
                    sneakersInfoMap.put(modelName, sneakersInfo);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readFileAndSetSneakersStockMap() {
        // 파일 경로 설정
        String filePath = "C:/Users/USER/OneDrive/바탕 화면/공부/백엔드/과제모음/과제12/nike-sneaker-stocks.txt";
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");
                if (tokens.length >= 2) {
                    String modelName = tokens[0].trim();
                    long stockQuantity = Long.parseLong(tokens[1].trim());
                    sneakersStockMap.put(modelName, stockQuantity);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void processCustomers() {
        for (Customer customer : customerList) {
            processCustomer(customer);
        }
        generateSalesReport();
    }

    private void processCustomer(Customer customer) {
        String modelName = customer.getRequestedModel();

        if (!sneakersInfoMap.containsKey(modelName)) {
            System.out.println("매장 직원: 죄송합니다. 해당 모델의 운동화는 판매하지 않습니다.");
            customer.rejectAndExit();
            return;
        }

        SneakersInfo requestedSneaker = sneakersInfoMap.get(modelName);
        System.out.println("매장 직원: " + requestedSneaker);

        long originalPrice = requestedSneaker.getPrice();
        long discountedPrice = calculateDiscountedPrice(customer, originalPrice);

        if (customer.getBudget() < discountedPrice) {
            System.out.println("매장 직원: 죄송합니다. 예산 부족으로 구매가 불가능합니다.");
            customer.rejectAndExit();
            return;
        }

        if (!sneakersStockMap.containsKey(modelName) || sneakersStockMap.get(modelName) <= 0) {
            System.out.println("매장 직원: 죄송합니다. 해당 모델의 재고가 부족합니다.");
            customer.rejectAndExit();
            return;
        }

        // 운동화를 판매 처리
        sneakersStockMap.put(modelName, sneakersStockMap.get(modelName) - 1);
        customer.purchaseSneakers(requestedSneaker, discountedPrice);

        // 매상액 누적
        dailySalesAmount += discountedPrice;
    }

    private long calculateDiscountedPrice(Customer customer, long originalPrice) {
        switch (customer.getCustomerLevel()) {
            case VIP:
                return Math.round(originalPrice * 0.8); // VIP는 20% 할인
            case GOLD:
                return Math.round(originalPrice * 0.9); // GOLD는 10% 할인
            case SILVER:
            default:
                return originalPrice; // SILVER는 할인 없음
        }
    }

    private void generateSalesReport() {
        try (FileWriter writer = new FileWriter("sales_report.txt")) {
            writer.write("금일 매상: " + dailySalesAmount + "원\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(Customer customer) {
        boolean isAlreadyRegistered = false;
        for (Customer existingCustomer : customerList) {
            if (existingCustomer.getName().equals(customer.getName())) {
                isAlreadyRegistered = true;
                break;
            }
        }

        if (isAlreadyRegistered) {
            System.out.println("매장 직원: 이미 등록된 고객입니다.");
        } else {
            customerList.add(customer);
            System.out.println("매장 직원: " + customer.getName() + "님 등록 완료!");
        }
    }
}
